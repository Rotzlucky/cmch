package controllers;

import com.google.inject.Inject;
import models.CharacterAppearance;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import views.html.*;
import views.html.appearances.list;

/**
 * Created by marcelsteffen on 24.08.16.
 */
public class Application extends Controller {

    @Inject
    WebJarAssets webJarAssets;

    public Result jsRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.Application.more(),
                        routes.javascript.CharactersController.index(),
                        routes.javascript.CharactersController.show(),
                        routes.javascript.CharactersController.create(),
                        routes.javascript.TeamController.teams(),
                        routes.javascript.TeamController.show()
                )
        ).as("text/javascript");
    }

    public Result index() {
        return ok(index.render(
                webJarAssets,
                CharacterAppearance.findNewestEntries(0, 10)
        ));
    }

    public Result more(int page) {
        return ok(list.render(
                CharacterAppearance.findNewestEntries(page, 10)
        ));
    }

}
