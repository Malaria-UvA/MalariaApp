package malaria.com.malaria.activities.test;

import android.os.Bundle;

import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.dagger.MalariaComponent;

public class TestSettingActivity extends BaseActivity {
    public TestSettingActivity() {
        super(R.layout.activity_test_setting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}


