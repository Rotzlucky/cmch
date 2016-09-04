package controllers;

import com.google.inject.Inject;
import models.CharacterAppearance;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by marcelsteffen on 24.08.16.
 */
public class Application extends Controller {

    @Inject
    WebJarAssets webJarAssets;

    public Result index() {
        return ok(index.render(
                webJarAssets,
                CharacterAppearance.findNewestEntries(10)
        ));
    }

}
