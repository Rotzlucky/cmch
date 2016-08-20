package controllers;

import play.mvc.*;
import views.html.Blabla.*;

/**
 * Created by marcelsteffen on 15.08.16.
 */
public class Titles extends Controller {

    public Result index() {
        return ok(katze.render("Your new application is ready."));
    }
}
