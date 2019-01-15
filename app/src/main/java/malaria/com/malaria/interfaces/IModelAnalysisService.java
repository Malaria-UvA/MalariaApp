package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

import malaria.com.malaria.models.ImageFeature;

public interface IModelAnalysisService {

    void initialize();
    ImageFeature processImage(Bitmap image);
    boolean checkStopCondition();
    ImageFeature getTotalAggregation();
    int getTotalFields();
    int getParasitePerMicrolitre();

}
