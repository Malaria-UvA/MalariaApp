package malaria.com.malaria.interfaces;

import java.io.File;

public interface ICalibrationService {
    void calculateAndSaveThreshold(File file);
    double getThreshold();
}
