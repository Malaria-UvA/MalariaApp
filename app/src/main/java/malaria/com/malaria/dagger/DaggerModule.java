package malaria.com.malaria.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IMainPreferences;
import malaria.com.malaria.services.CalibrationService;
import malaria.com.malaria.utils.MainPreferences;

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
    ICalibrationService providesCalibrationService(){
        return new CalibrationService();
    }

    @Provides
    @Singleton
    IMainPreferences providesMainPreferences(Context context){
        return new MainPreferences(context);
    }

}
