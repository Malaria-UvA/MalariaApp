package malaria.com.malaria.activities.camera;

import com.google.android.cameraview.CameraView;

import malaria.com.malaria.R;
import malaria.com.malaria.dagger.MalariaComponent;

public class AnalysisCameraActivity extends BaseCameraActivity {

    public AnalysisCameraActivity() {
        super(R.layout.activity_camera);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, byte[] data) {

    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {

    }
}
