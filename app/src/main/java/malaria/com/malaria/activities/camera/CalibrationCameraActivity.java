package malaria.com.malaria.activities.camera;

import android.os.Bundle;

import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.fragments.CalibrationFragment;

public class CalibrationCameraActivity extends BaseActivity {

    public CalibrationCameraActivity() {
        super(R.layout.activity_calibration_camera);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalibrationFragment fragment = new CalibrationFragment(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.calibrationFragment, fragment).commit();
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
