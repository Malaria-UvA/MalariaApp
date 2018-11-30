package malaria.com.malaria.dagger;

import android.app.Application;


public class MalariaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        injectDependencies();
    }

    private void injectDependencies() {
        DependencyInjector.initialize(this);
        DependencyInjector.applicationComponent().inject(this);
    }
}
