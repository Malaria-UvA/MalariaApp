package malaria.com.malaria.services;

import android.graphics.Bitmap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import malaria.com.malaria.interfaces.IAnalysisService;

public class AnalysisService implements IAnalysisService {

    private ArrayList<Bitmap> images;
    private Queue<Bitmap> buffer;

    public AnalysisService() {
        images = new ArrayList<>();
        buffer = new ArrayDeque<>();
    }

    @Override
    public boolean addPicture(Bitmap image) {
        boolean check = checkPictureAlreadyTaken(image);
        if (check) return true;
        buffer.add(image);
        images.add(image);
        return false;
    }

    @Override
    public Bitmap getImageFromBuffer() {
        return buffer.isEmpty() ? null : buffer.remove();
    }

    private boolean checkPictureAlreadyTaken(Bitmap image) {
        // TODO implement using the array images and the image given above
        return false;
    }
}
