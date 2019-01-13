package malaria.com.malaria.services;

import android.graphics.Bitmap;

import java.util.ArrayList;

import javax.inject.Inject;

import malaria.com.malaria.interfaces.IAnalysisService;

public class AnalysisService implements IAnalysisService {

    private ArrayList<Bitmap> images;

    @Inject
    public AnalysisService() {
        images = new ArrayList<>();
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
}
