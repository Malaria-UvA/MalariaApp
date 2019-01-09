package malaria.com.malaria.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.constants.IntentKeys;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.activities.camera.CalibrationCameraActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.startBtn)
    Button startBtn;

    @BindView(R.id.guideBtn)
    Button guideBtn;

    @BindView(R.id.calibrateBtn)
    Button calibrateBtn;

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
            Intent intent = new Intent(MainActivity.this, AnalysisCameraActivity.class);
            startActivity(intent);
        });
        this.guideBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GuideActivity.class)));
        this.calibrateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalibrationCameraActivity.class);
            startActivity(intent);
        });
    }
}
