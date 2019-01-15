package malaria.com.malaria.services;

import android.graphics.Bitmap;

import java.util.ArrayList;

import javax.inject.Inject;

import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IAnalysisService;
import malaria.com.malaria.interfaces.IModelAnalysisService;

public class AnalysisService implements IAnalysisService {

    private ArrayList<Bitmap> images;

    @Inject
    IModelAnalysisService modelAnalysisService;

    @Inject
    public AnalysisService() {
        onInject(DependencyInjector.applicationComponent());
        initialize();
    }

    @Override
    public void initialize() {
        images = new ArrayList<>();
        modelAnalysisService.initialize();
    }

    @Override
    public boolean addPicture(Bitmap image) {
        boolean check = checkPictureAlreadyTaken(image);
        if (check) return false;
        images.add(image);
        return true;
    }

    private boolean checkPictureAlreadyTaken(Bitmap image) {
        // TODO implement using the array images and the image given above
        return false;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
