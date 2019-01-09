package malaria.com.malaria.activities.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.Injector;

public abstract class BaseActivity extends AppCompatActivity implements Injector {

    protected Context context;
    private Integer layoutId;

    public BaseActivity() {
    }

    public BaseActivity(int layoutId) {
        this.layoutId = layoutId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        onInject(DependencyInjector.applicationComponent());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);

        if (layoutId != null) {
            setContentView(layoutId);
        }

        ButterKnife.bind(this);

        context = getApplicationContext();
    }
}
