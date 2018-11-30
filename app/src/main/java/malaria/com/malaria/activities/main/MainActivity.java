package malaria.com.malaria.activities.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.dagger.MalariaComponent;

public class MainActivity extends BaseActivity {
    public MainActivity() {
        super(R.layout.activity_main);
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
