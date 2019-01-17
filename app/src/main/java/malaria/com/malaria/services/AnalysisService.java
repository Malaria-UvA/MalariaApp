package malaria.com.malaria.services;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IAnalysisService;
import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.interfaces.IOrbFeatureDetectorService;

public class AnalysisService implements IAnalysisService {
    //private static final String TAG = "AnalysisService";
    private static final String TAG = "OrbFeatureDetector";
    @Inject
    IModelAnalysisService modelAnalysisService;
    @Inject
    IOrbFeatureDetectorService detector;
    private List<Bitmap> images;

    @Inject
    public AnalysisService() {
        onInject(DependencyInjector.applicationComponent());
        initialize();
    }

    @Override
    public void initialize() {
        modelAnalysisService.initialize();
        detector.initialize();
    }

    @Override
    public boolean isPictureAlreadyTaken(Bitmap image) {
        boolean alreadyTaken = this.detector.pictureAlreadyTaken(image);
        Log.i(TAG, "isPictureAlreadyTaken: " + alreadyTaken);
        return alreadyTaken;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
