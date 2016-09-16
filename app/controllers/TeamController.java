package controllers;

import models.Team;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by marcelsteffen on 13.09.16.
 */
public class TeamController extends Controller {

    public Result teams(String query) {
        return ok(Json.toJson(Team.findSortedByName(query)));
    }
}
