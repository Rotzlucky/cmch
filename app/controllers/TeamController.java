package controllers;

import models.Team;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.teams.item;

/**
 * Created by marcelsteffen on 13.09.16.
 */
public class TeamController extends Controller {

    public Result teams(String query) {
        return ok(Json.toJson(Team.findSortedByName(query)));
    }

    public Result show(long teamId) {
        return ok(item.render(Team.find.ref(teamId), "teamIds[0]"));
    }
}
