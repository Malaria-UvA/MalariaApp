package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

public interface IAnalysisService extends Injector {

    void initialize();
    boolean isPictureAlreadyTaken(Bitmap image);
}
