package malaria.com.malaria.services;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

import javax.inject.Inject;

import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IMainPreferences;
import malaria.com.malaria.interfaces.Injector;
import malaria.com.malaria.utils.MainPreferences;

public class CalibrationService implements ICalibrationService, Injector {
    @Inject()
    IMainPreferences preferences;

    @Override
    public void calculateAndSaveThreshold(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File must be not null and exist");
        }
        String imageName = file.getAbsolutePath();
        // Load an image
        Mat src = Imgcodecs.imread(imageName);
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

        this.saveThreshold(Math.pow(std.get(0, 0)[0], 2));
    }

    @Override
    public double getThreshold() {
        double threshold = preferences.getDouble(MainPreferences.THRESHOLD, Double.NaN);
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
