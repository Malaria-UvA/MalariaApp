package malaria.com.malaria.activities.camera;

import android.graphics.Bitmap;

import com.google.android.cameraview.CameraView;

import javax.inject.Inject;

import malaria.com.malaria.R;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IAnalysisService;

public class AnalysisCameraActivity extends BaseCameraActivity {

    @Inject
    IAnalysisService analysisService;

    public AnalysisCameraActivity() {
        super(R.layout.activity_camera);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {

    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
