package controllers;

import com.google.inject.Inject;
import models.ComicCharacter;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.characters.index;
import views.html.characters.list;

/**
 * Created by marcelsteffen on 30.08.16.
 */
public class CharactersController extends Controller{

    @Inject WebJarAssets webJarAssets;

    public Result index() {
        return ok(index.render(webJarAssets,
                ComicCharacter.findSortedByName()
        ));
    }

    public Result show() {
        return ok(list.apply(ComicCharacter.find.all()));
    }
}
