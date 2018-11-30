package malaria.com.malaria.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.services.MalariaKBSService;

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

    @Provides
    @Singleton
    IMalariaKBSService providesMalariaKBSService(){
        return new MalariaKBSService();
    }

}
