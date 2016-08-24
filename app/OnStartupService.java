import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.Issue;
import play.Environment;
import services.Yaml;

import java.util.Collection;

/**
 * Created by marcelsteffen on 23.08.16.
 */
@Singleton
public class OnStartupService {

    @Inject
    private OnStartupService(Environment environment, Yaml yaml) {
        if (environment.isDev()) {
            if (Issue.find.findRowCount() == 0) {
                // TODO Logback implementation
                Ebean.saveAll((Collection<?>) yaml.load("test-data.yml"));
            }
        }
    }
}
