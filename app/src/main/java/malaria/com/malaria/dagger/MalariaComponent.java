package malaria.com.malaria.dagger;

import javax.inject.Singleton;

import dagger.Component;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.camera.BaseCameraActivity;
import malaria.com.malaria.activities.camera.CalibrationCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.activities.results.EvidenceActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.activities.tutorial.TutorialActivity;
import malaria.com.malaria.fragments.BaseCameraFragment;
import malaria.com.malaria.fragments.CalibrationFragment;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.services.AnalysisService;
import malaria.com.malaria.services.CalibrationService;

@Singleton
@Component(modules = {
        DaggerModule.class
})
public interface MalariaComponent {
    void inject(MalariaApplication malariaApplication);

    // Activities
    void inject(GuideActivity guideActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(ResultsActivity resultsActivity);

    void inject(AnalysisCameraActivity analysisCameraActivity);

    void inject(CalibrationCameraActivity calibrationCameraActivity);

    void inject(BaseCameraActivity baseCameraActivity);

    void inject(EvidenceActivity evidenceActivity);

    //Fragments
    void inject(GuideFragment guideFragment);

    void inject(CalibrationFragment calibrationFragment);

    void inject(BaseCameraFragment baseCameraFragment);

    //Services
    void inject(CalibrationService calibrationService);

    void inject(AnalysisService analysisService);
}
