package malaria.com.malaria.dagger;

public class DependencyInjector {

    private static MalariaComponent malariaComponent;

    private DependencyInjector() {
    }

    static void initialize(MalariaApplication application) {
        malariaComponent = DaggerMalariaComponent.builder()
                .daggerModule(new DaggerModule(application))
                .build();
    }

    public static MalariaComponent applicationComponent() {
        return malariaComponent;
    }
}
