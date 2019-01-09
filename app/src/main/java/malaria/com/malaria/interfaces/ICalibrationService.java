package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

public interface ICalibrationService {
    void calculateAndSaveThreshold(Bitmap bitmap);
    double getThreshold();
}
