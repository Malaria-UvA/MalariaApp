package malaria.com.malaria.activities.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.cameraview.CameraView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;

public class CalibrationCameraActivity extends BaseCameraActivity {

    @BindView(R.id.calibrateBtn)
    Button calibrateBtn;

    @Inject
    ICalibrationService calibrationService;
    private boolean redirect;

    public CalibrationCameraActivity() {
        super(R.layout.activity_calibration_camera);
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.redirect = getIntent().getBooleanExtra(MainActivity.Key.REDIRECT_TO_ANALYSIS_ACTIVITY, false);
        calibrateBtn.setOnClickListener(view -> mCameraView.takePicture());
    }


    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {
        Toast.makeText(this, R.string.device_calibrated, Toast.LENGTH_SHORT).show();
        calibrationService.calculateAndSaveThreshold(bitmap);
        if(redirect){
            startActivity(new Intent(this, AnalysisCameraActivity.class));
            finish();
        }
    }
}
