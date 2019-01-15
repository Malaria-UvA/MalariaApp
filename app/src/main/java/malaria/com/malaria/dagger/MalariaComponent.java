package malaria.com.malaria.dagger;

import javax.inject.Singleton;

import dagger.Component;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.camera.CalibrationCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.services.AnalysisService;
import malaria.com.malaria.services.CalibrationService;
import malaria.com.malaria.services.MainPreferences;

@Singleton
@Component(modules = {
        DaggerModule.class
})
public interface MalariaComponent {
    void inject(MalariaApplication malariaApplication);

    // Activities
    void inject(MainActivity mainActivity);

    void inject(GuideActivity guideActivity);

    void inject(ResultsActivity resultsActivity);

    void inject(CalibrationCameraActivity calibrationCameraActivity);

    void inject(AnalysisCameraActivity analysisCameraActivity);

    //Fragments
    void inject(GuideFragment guideFragment);

    //Services
    void inject(CalibrationService calibrationService);

    void inject(AnalysisService analysisService);
}
