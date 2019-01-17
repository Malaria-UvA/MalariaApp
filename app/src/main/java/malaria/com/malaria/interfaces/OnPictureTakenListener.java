package malaria.com.malaria.interfaces;

import android.app.Activity;
import android.graphics.Bitmap;

import com.google.android.cameraview.CameraView;

public interface OnPictureTakenListener {
    void onPictureTaken(CameraView cameraView, Bitmap data);

    Activity getActivity();
}
