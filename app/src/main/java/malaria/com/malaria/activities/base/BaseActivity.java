package malaria.com.malaria.activities.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;

public abstract class BaseActivity extends AppCompatActivity {

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


    /**
     * Performs dependency injection, using the applicationComponent as the injector.
     * If an Activity only needs injection into this base class, it does not need to override this method.
     * However, if an Activity requires extra injections (has one ore more @Inject annotations in it's source code),
     * then it must override this method, and invoke <code>applicationComponent.inject(this);</code>
     *
     * @param applicationComponent the component being injected
     */
    protected abstract void onInject(MalariaComponent applicationComponent);
}
