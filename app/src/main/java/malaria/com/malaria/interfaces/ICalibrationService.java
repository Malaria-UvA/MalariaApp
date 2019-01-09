package malaria.com.malaria.interfaces;

import java.io.File;

public interface ICalibrationService extends Injector {
    void calculateAndSaveThreshold(File file);
    boolean isBlurry(File file);
    double getThreshold();
}
