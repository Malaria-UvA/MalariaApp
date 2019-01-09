package malaria.com.malaria.interfaces;

import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.dagger.MalariaComponent;

public interface Injector {
    /**
     * Performs dependency injection, using the applicationComponent as the injector.
     * If an Activity only needs injection into this base class, it does not need to override this method.
     * However, if an Activity requires extra injections (has one ore more @Inject annotations in it's source code),
     * then it must override this method, and invoke <code>applicationComponent.inject(this);</code>
     *
     * @param applicationComponent the component being injected
     */
    void onInject(MalariaComponent applicationComponent);
}
