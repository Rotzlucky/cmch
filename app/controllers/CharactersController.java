package controllers;

import com.google.inject.Inject;
import models.CharacterAppearance;
import models.ComicCharacter;
import models.enums.OrderType;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.characters.index;
import views.html.characters.list;
import views.html.characters.show;
import views.html.characters.create;

/**
 * Created by marcelsteffen on 30.08.16.
 */
public class CharactersController extends Controller{

    @Inject WebJarAssets webJarAssets;

    public Result index(String query) {
        if (isAjax()) {
            return ok(list.apply(ComicCharacter.findSortedByName(query)));
        } else {
            return ok(index.render(webJarAssets, ComicCharacter.findSortedByName()));
        }
    }

    public Result show(Long id, String orderType) {
        return ok(show.render(
                webJarAssets,
                ComicCharacter.find.ref(id),
                CharacterAppearance.findByCharacterAndOrder(id, OrderType.valueOf(orderType))));
    }

    public Result create() {
        return ok(create.render(webJarAssets));
    }

    private boolean isAjax() {
        return "XMLHttpRequest".equals(Http.Context.current().request().getHeader("X-Requested-With"));
    }
}
