import com.google.inject.AbstractModule;

/**
 * Created by marcelsteffen on 23.08.16.
 */
public class GuiceConfiguration extends AbstractModule{
    @Override
    protected void configure() {
        bind(OnStartupService.class).asEagerSingleton();
    }
}
