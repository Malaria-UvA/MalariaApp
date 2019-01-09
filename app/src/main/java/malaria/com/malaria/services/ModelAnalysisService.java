package malaria.com.malaria.services;

import android.graphics.Bitmap;

import java.util.ArrayList;

import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.models.ImageFeature;

public class ModelAnalysisService implements IModelAnalysisService {

    private ArrayList<ImageFeature> features;

    public ModelAnalysisService() {
        this.features = new ArrayList<>();
    }

    @Override
    public ImageFeature processImage(Bitmap image) {
        // TODO Call model.predict() to obtain number of parasites and WBC
        return new ImageFeature(25,50);
    }

    @Override
    public boolean checkStopCondition() {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (ImageFeature f : features) {
            nParasitesTotal += f.getnParasites();
            nWBC += f.getnWhiteBloodCells();
        }
        return nParasitesTotal >= 100 && nWBC >= 200 || nParasitesTotal <= 99 && nWBC >= 500;
    }

    @Override
    public int getParasitePerMicrolitre() {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (ImageFeature f : features) {
            nParasitesTotal += f.getnParasites();
            nWBC += f.getnWhiteBloodCells();
        }
        return (int) Math.ceil(8000f * nParasitesTotal / nWBC);
    }
}
