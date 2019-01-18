package malaria.com.malaria.interfaces;

import com.google.android.cameraview.CameraView;

public interface ICameraService {
    void setUpCameraView(CameraView mCameraView);

    void checkCameraPermissions();

    void stopCameraView();

    void takePicture();

    void setListener(OnPictureTakenListener listener);

    void startCameraView();
}
