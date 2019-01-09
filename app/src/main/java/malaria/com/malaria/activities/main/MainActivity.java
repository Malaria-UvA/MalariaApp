package malaria.com.malaria.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.CameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.constants.IntentKeys;
import malaria.com.malaria.dagger.MalariaComponent;

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
        this.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(IntentKeys.ACTIVITY_BEHAVIOUR, IntentKeys.START.toString());
                startActivity(intent);
            }
        });
        this.guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GuideActivity.class));
            }
        });
        this.calibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(IntentKeys.ACTIVITY_BEHAVIOUR, IntentKeys.CALIBRATION.toString());
                startActivity(intent);
            }
        });
    }
}
