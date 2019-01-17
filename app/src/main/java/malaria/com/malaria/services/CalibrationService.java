package malaria.com.malaria.services;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgproc.Imgproc;

import javax.inject.Inject;

import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IMainPreferences;

public class CalibrationService implements ICalibrationService {

    private static final int MARGIN = 10;

    @Inject
    IMainPreferences preferences;

    @Inject
    public CalibrationService() {
        onInject(DependencyInjector.applicationComponent());
    }

    @Override
    public void calculateAndSaveThreshold(Bitmap bitmap) {
        double threshold = calculateThreshold(bitmap);
        this.saveThreshold(threshold);
    }

    @Override
    public boolean isBlurry(Bitmap bitmap) {
        double threshold = calculateThreshold(bitmap);
        double savedThreshold = getThresholdAndThrow();
        Log.i("SAVED_THRESHOLD:", String.valueOf(savedThreshold));
        Log.i("THRESHOLD:", String.valueOf(threshold));
        return savedThreshold + MARGIN < threshold;
    }

    private double calculateThreshold(Bitmap bitmap) {

        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);
        if (src.empty()) {
            throw new IllegalArgumentException("Invalid image format");
        }
        Mat destination = new Mat();
        Mat matGray = new Mat();

        Imgproc.cvtColor(src, matGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Laplacian(matGray, destination, 3);
        MatOfDouble median = new MatOfDouble();
        MatOfDouble std = new MatOfDouble();
        Core.meanStdDev(destination, median, std);
        return Math.pow(std.get(0, 0)[0], 2);
    }

    @Override
    public double getThreshold() {
        return preferences.getDouble(MainPreferences.THRESHOLD, Double.NaN);
    }

    @Override
    public double getThresholdAndThrow() {
        double threshold = this.getThreshold();
        if (Double.isNaN(threshold)) {
            throw new IllegalStateException("Threshold has not been saved yet");
        }
        return threshold;
    }


    private void saveThreshold(double threshold) {
        preferences.putDouble(MainPreferences.THRESHOLD, threshold);
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
