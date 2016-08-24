package controllers;

import models.Issue;
import models.Title;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by marcelsteffen on 24.08.16.
 */
public class Application extends Controller {

    public Result index() {
        return ok(index.render(
                Title.find.all(),
                Issue.find.all()
        ));
    }

}
