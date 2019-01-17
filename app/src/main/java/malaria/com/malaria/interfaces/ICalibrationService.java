package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

public interface ICalibrationService extends Injector {
    void calculateAndSaveThreshold(Bitmap bitmap);
    boolean isBlurry(Bitmap bitmap);
    double getThreshold();
    double getThresholdAndThrow();
}
