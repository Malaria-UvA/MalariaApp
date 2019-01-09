package malaria.com.malaria.activities.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Surface;

import com.google.android.cameraview.CameraView;

import org.opencv.android.OpenCVLoader;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.fragments.ConfirmationDialogFragment;

public abstract class BaseCameraActivity extends BaseActivity {

    static {
        System.loadLibrary("opencv_java3");
        OpenCVLoader.initDebug(); // TODO delete
    }

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    @BindView(R.id.camera)
    CameraView mCameraView;

    public BaseCameraActivity(int layoutId){
        super(layoutId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                BaseCameraActivity.this.onPictureTaken(cameraView, bmp32);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(getFragmentManager(), "dialog");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    public abstract void onPictureTaken(CameraView cameraView, Bitmap data);
}
