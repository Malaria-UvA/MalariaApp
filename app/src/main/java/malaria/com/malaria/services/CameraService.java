package malaria.com.malaria.services;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.cameraview.CameraView;

import org.opencv.android.OpenCVLoader;

import malaria.com.malaria.R;
import malaria.com.malaria.fragments.ConfirmationDialogFragment;
import malaria.com.malaria.interfaces.ICameraService;
import malaria.com.malaria.interfaces.OnPictureTakenListener;

public class CameraService implements ICameraService {
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    static {
        System.loadLibrary("opencv_java3");
        OpenCVLoader.initDebug(); // TODO delete
    }

    private CameraView mCameraView;
    private OnPictureTakenListener listener;

    public void setListener(OnPictureTakenListener listener) {
        this.listener = listener;
    }

    @Override
    public void setUpCameraView(CameraView mCameraView) {
        this.mCameraView = mCameraView;

        mCameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                CameraService.this.listener.onPictureTaken(cameraView, bmp32);
            }
        });
    }

    @Override
    public void checkCameraPermissions() {
        Activity activity = listener.getActivity();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(activity.getFragmentManager(), "dialog");
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void stopCameraView() {
        mCameraView.stop();
    }

    @Override
    public void takePicture() {
        mCameraView.takePicture();
    }
}
