package malaria.com.malaria.interfaces;

import android.graphics.Bitmap;

import java.util.List;

import malaria.com.malaria.models.ImageFeature;

public interface IModelAnalysisService {

    void initialize();

    ImageFeature processImage(Bitmap image);

    boolean checkStopCondition();

    ImageFeature getTotalAggregation();

    List<Bitmap> getProcessedImages();

    int getTotalFields();

    int getParasitePerMicrolitre();

}
