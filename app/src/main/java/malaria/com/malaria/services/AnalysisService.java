package malaria.com.malaria.services;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
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
    private List<Bitmap> images;

    @Inject
    IModelAnalysisService modelAnalysisService;

    @Inject
    IOrbFeatureDetectorService detector;

    @Inject
    public AnalysisService() {
        onInject(DependencyInjector.applicationComponent());
        initialize();
    }

    @Override
    public void initialize() {
        images = new ArrayList<>();
        modelAnalysisService.initialize();
        detector.initialize();
    }

    @Override
    public boolean addPicture(Bitmap image) {
        boolean check = checkPictureAlreadyTaken(image);
        if (check) return false;
        images.add(image);
        return true;
    }

    private boolean checkPictureAlreadyTaken(Bitmap image) {
        boolean alreadyTaken = this.detector.pictureAlreadyTaken(image, images);
        Log.i(TAG, "checkPictureAlreadyTaken: " + alreadyTaken);
        return alreadyTaken;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
