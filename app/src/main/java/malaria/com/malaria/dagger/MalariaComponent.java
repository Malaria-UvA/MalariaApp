package malaria.com.malaria.dagger;

import javax.inject.Singleton;

import dagger.Component;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.activities.camera.CameraActivity;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.fragments.CameraFragment;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.Injector;
import malaria.com.malaria.services.CalibrationService;

@Singleton
@Component(modules = {
        DaggerModule.class
})
public interface MalariaComponent {
    void inject(MalariaApplication malariaApplication);

    void inject(MainActivity mainActivity);

    void inject(GuideActivity guideActivity);

    void inject(ResultsActivity resultsActivity);

    void inject(CameraActivity cameraActivity);

    void inject(GuideFragment guideFragment);

    void inject(CameraFragment cameraFragment);

    void inject(CalibrationService calibrationService);
}
