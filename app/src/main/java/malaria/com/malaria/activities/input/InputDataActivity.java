package malaria.com.malaria.activities.input;

import android.os.Bundle;

import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.dagger.MalariaComponent;

public class InputDataActivity extends BaseActivity {
    public InputDataActivity() {
        super(R.layout.activity_input_data);
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
