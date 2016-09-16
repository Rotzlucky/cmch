package controllers;

import com.google.inject.Inject;
import models.CharacterAppearance;
import models.ComicCharacter;
import models.enums.OrderType;
import play.Environment;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.characters.create;
import views.html.characters.index;
import views.html.characters.list;
import views.html.characters.show;

import java.io.File;
import java.util.Collections;

/**
 * Created by marcelsteffen on 30.08.16.
 */
public class CharactersController extends Controller{

    @Inject WebJarAssets webJarAssets;
    @Inject FormFactory formFactory;

    public Result index(String query) {
        if (isAjax()) {
            return ok(list.render(ComicCharacter.findSortedByName(query)));
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
        return ok(create.render(formFactory.form(ComicCharacter.class)));
    }

    public Result newCharacter() {
        Form<ComicCharacter> comicCharacterForm = formFactory.form(ComicCharacter.class).bindFromRequest();
        ComicCharacter comicCharacter = comicCharacterForm.get();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("characterImage");
        if (picture != null) {
            File file = picture.getFile();
            ComicCharacter character = ComicCharacter.create(
                    comicCharacter.characterName,
                    comicCharacter.characterRealName,
                    file,
                    Collections.emptyList()
            );
            return redirect(routes.CharactersController.show(character.id, OrderType.MAIN.name()));
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

    private boolean isAjax() {
        return "XMLHttpRequest".equals(Http.Context.current().request().getHeader("X-Requested-With"));
    }
}
