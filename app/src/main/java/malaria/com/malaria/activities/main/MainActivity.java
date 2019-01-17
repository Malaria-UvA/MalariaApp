package malaria.com.malaria.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.camera.CalibrationCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;

public class MainActivity extends BaseActivity {
    @BindView(R.id.startBtn)
    Button startBtn;

    @BindView(R.id.guideBtn)
    Button guideBtn;

    @BindView(R.id.calibrateBtn)
    Button calibrateBtn;

    @Inject
    ICalibrationService calibrationService;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binds();
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    private void binds() {
        this.startBtn.setOnClickListener(v -> {
            startAnalysisCameraActivity();
        });
        this.guideBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GuideActivity.class)));
        this.calibrateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalibrationCameraActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Redirects to the CalibrationCameraActivity if the phone is not calibrated, otherwise it goes to AnalysisCameraActivity
     */
    private void startAnalysisCameraActivity(){
        Intent intent;
        if (Double.isNaN(calibrationService.getThreshold())) { // threshold not defined
            intent = new Intent(MainActivity.this, CalibrationCameraActivity.class);
            intent.putExtra(Key.REDIRECT_TO_ANALYSIS_ACTIVITY, true);
        }else{
            intent = new Intent(MainActivity.this, AnalysisCameraActivity.class);
        }
        startActivity(intent);
    }

    public static class Key {
        public static final String REDIRECT_TO_ANALYSIS_ACTIVITY = "REDIRECT_TO_ANALYSIS_ACTIVITY";
    }
}
