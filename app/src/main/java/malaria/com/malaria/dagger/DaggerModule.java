package malaria.com.malaria.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
class DaggerModule {
    private MalariaApplication application;

    DaggerModule(MalariaApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context providesContext(Application application) {
        return application.getApplicationContext();
    }

}
