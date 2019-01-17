package malaria.com.malaria.services;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.ORB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import malaria.com.malaria.interfaces.IOrbFeatureDetectorService;

import static org.opencv.core.Core.NORM_HAMMING;

public class OrbFeatureDetectorService implements IOrbFeatureDetectorService {
    private static final String TAG = "OrbFeatureDetector";
    private static final int NUM_FEATURES = 100;
    private static final int DISTANCE_THRESHOLD = 60;
    private static final float PERCENTAGE_GOOD_MATCHES_THRESHOLD = 0.85f;

    private ORB orb;
    private BFMatcher bf;
    private List<Mat> imagesDescriptors;

    public OrbFeatureDetectorService() {
        this.initialize();
    }

    @Override
    public boolean pictureAlreadyTaken(Bitmap image, List<Bitmap> previousImages) {
        Mat descriptor = new Mat();
        Mat mask = new Mat();
        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        orb.detectAndCompute(bitmapToMat(image), mask, matOfKeyPoint, descriptor);
        if (previousImages == null || previousImages.isEmpty()) { // if this is the first image, we cannot compare it to any other
            imagesDescriptors.add(descriptor); // save current descriptor for next call
            return false;
        }

        MatOfDMatch matches = new MatOfDMatch();
        for (Mat previousImageDescriptor : imagesDescriptors) {
            bf.match(descriptor, previousImageDescriptor, matches);

            List<DMatch> matchesList = matches.toList();
            if (!matchesList.isEmpty()) {
                double[] distances = matchesList.parallelStream().mapToDouble(m -> m.distance).toArray();
                double[] distances_good = matchesList.parallelStream().mapToDouble(m -> m.distance).filter(d -> d < DISTANCE_THRESHOLD).toArray();

                float percentage = ((float) distances_good.length) / distances.length;

                Log.i(TAG, "Distances:" + Arrays.toString(distances));
                Log.i(TAG, "Percentage:" + percentage);
                if (percentage > PERCENTAGE_GOOD_MATCHES_THRESHOLD) {
                    return true;
                }
            }
        }
        imagesDescriptors.add(descriptor); // save current descriptor for next call
        return false;
    }

    @Override
    public void initialize() {
        this.orb = ORB.create(NUM_FEATURES);
        this.orb.setScoreType(ORB.FAST_SCORE);
        this.bf = BFMatcher.create(NORM_HAMMING, true);
        this.imagesDescriptors = new ArrayList<>();
    }

    private static Mat bitmapToMat(Bitmap bitmap) {
        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);
        return mat;
    }
}
