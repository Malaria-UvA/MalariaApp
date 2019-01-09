package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

import malaria.com.malaria.models.ImageFeature;

public interface IModelAnalysisService {

    ImageFeature processImage(Bitmap image);
    boolean checkStopCondition();
    int getParasitePerMicrolitre();

}
