package malaria.com.malaria.services;

import android.graphics.Bitmap;

import java.util.ArrayList;

import javax.inject.Inject;

import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.models.ImageFeature;

public class ModelAnalysisService implements IModelAnalysisService {

    private ArrayList<ImageFeature> features;

    @Inject
    public ModelAnalysisService() {
        this.features = new ArrayList<>();
    }

    @Override
    public ImageFeature processImage(Bitmap image) {
        // TODO Call model.predict() to obtain number of parasites and WBC
        ImageFeature imageFeature = new ImageFeature(50,100);
        features.add(imageFeature);
        return imageFeature;
    }

    @Override
    public boolean checkStopCondition() {
        ImageFeature aggregation = getTotalAggregation();
        return aggregation.getnParasites() >= 100 && aggregation.getnWhiteBloodCells() >= 200 ||
                aggregation.getnParasites() <= 99 && aggregation.getnWhiteBloodCells() >= 500;
    }

    @Override
    public ImageFeature getTotalAggregation() {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (ImageFeature f : features) {
            nParasitesTotal += f.getnParasites();
            nWBC += f.getnWhiteBloodCells();
        }
        return new ImageFeature(nWBC, nParasitesTotal);
    }

    @Override
    public int getTotalFields() {
        return features.size();
    }

    @Override
    public int getParasitePerMicrolitre() {
        ImageFeature aggregation = getTotalAggregation();
        return (int) Math.ceil(8000f * aggregation.getnParasites() / aggregation.getnWhiteBloodCells());
    }
}
