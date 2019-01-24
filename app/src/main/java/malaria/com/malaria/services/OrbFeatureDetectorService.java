package malaria.com.malaria.services;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
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

import malaria.com.malaria.interfaces.IOrbFeatureDetectorService;

import static org.opencv.core.Core.NORM_HAMMING;

public class OrbFeatureDetectorService implements IOrbFeatureDetectorService {
    private static final String TAG = "OrbFeatureDetector";
    private static final int NUM_FEATURES = 200;
    private static final int DISTANCE_THRESHOLD = 60;
    private static final float PERCENTAGE_GOOD_MATCHES_THRESHOLD = 0.75f;
    private static final int MIN_LENGTH_MATCHES = 20;

    private ORB orb;
    private BFMatcher bf;
    private List<Mat> imagesDescriptors;

    public OrbFeatureDetectorService() {
        this.initialize();
    }

    private static Mat bitmapToMat(Bitmap bitmap) {
        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);
        return mat;
    }

    @Override
    public boolean pictureAlreadyTaken(Bitmap image) {
        Mat descriptor = new Mat();
        Mat mask = new Mat();
        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        orb.detectAndCompute(bitmapToMat(image), mask, matOfKeyPoint, descriptor);
        if (imagesDescriptors.isEmpty()) { // if this is the first image, we cannot compare it to any other
            imagesDescriptors.add(descriptor); // save current descriptor for next call
            return false;
        }

        MatOfDMatch matches = new MatOfDMatch();
        int i = 0;
        for (Mat previousImageDescriptor : imagesDescriptors) {
            bf.match(descriptor, previousImageDescriptor, matches);

            List<DMatch> matchesList = matches.toList();
            if (!matchesList.isEmpty() && matchesList.size() >= MIN_LENGTH_MATCHES) {
                double[] distances = matchesList.parallelStream().mapToDouble(m -> m.distance).toArray();
                double[] distances_good = matchesList.parallelStream().mapToDouble(m -> m.distance).filter(d -> d < DISTANCE_THRESHOLD).toArray();

                float percentage = ((float) distances_good.length) / distances.length;

                Log.i(TAG, "Matches "+ i +": " + matchesList.size());
                Log.i(TAG, "Percentage "+ i +": " + percentage);
                if (percentage > PERCENTAGE_GOOD_MATCHES_THRESHOLD) {
                    return true;
                }
            }
            i++;
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
}
