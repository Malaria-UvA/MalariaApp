package malaria.com.malaria.dagger;

import javax.inject.Singleton;

import dagger.Component;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.activities.main.MainActivity;

@Singleton
@Component(modules = {
        DaggerModule.class
})
public interface MalariaComponent {
    void inject(MalariaApplication malariaApplication);
    void inject(MainActivity mainActivity);
    void inject(GuideActivity guideActivity);
}
