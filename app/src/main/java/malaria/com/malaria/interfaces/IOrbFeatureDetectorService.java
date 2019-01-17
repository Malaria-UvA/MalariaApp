package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

import java.util.List;

public interface IOrbFeatureDetectorService {
    boolean pictureAlreadyTaken(Bitmap image);
    void initialize();
}
