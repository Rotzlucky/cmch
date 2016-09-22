package controllers;

import com.google.inject.Inject;
import models.CharacterAppearance;
import models.ComicCharacter;
import models.Team;
import models.enums.OrderType;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.characters.create;
import views.html.characters.delete_confirm;
import views.html.characters.index;
import views.html.characters.list;
import views.html.characters.show;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by marcelsteffen on 30.08.16.
 */
public class CharactersController extends Controller {

    @Inject
    WebJarAssets webJarAssets;
    @Inject
    FormFactory formFactory;

    public Result index(String query) {
        if (isAjax()) {
            return ok(list.render(ComicCharacter.findSortedByName(query)));
        } else {
            return ok(index.render(webJarAssets, ComicCharacter.findSortedByName(), null));
        }
    }

    public Result show(Long id, String orderType) {
        return ok(show.render(
                webJarAssets,
                ComicCharacter.find.ref(id),
                CharacterAppearance.findByCharacterAndOrder(id, OrderType.valueOf(orderType))));
    }

    public Result create() {
        return ok(create.render(formFactory.form(ComicCharacter.class), null));
    }

    public Result newCharacter() {
        Form<ComicCharacter> comicCharacterForm = formFactory.form(ComicCharacter.class).bindFromRequest();
        if (comicCharacterForm.hasErrors()) {
            return badRequest(index.render(webJarAssets, ComicCharacter.findSortedByName(), create.render(comicCharacterForm, null)));
        }

        ComicCharacter comicCharacter = comicCharacterForm.get();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("characterImage");
        File file = null;
        if (Objects.nonNull(picture) && Objects.nonNull(picture.getFilename()) && !picture.getFilename().isEmpty()) {
            file = picture.getFile();
        }

        ComicCharacter character = ComicCharacter.create(
                comicCharacter.characterName,
                comicCharacter.characterRealName,
                file,
                Team.getTeamIdsFromCreateCharacterRequest(body)
        );
        return redirect(routes.CharactersController.show(character.id, OrderType.MAIN.name()));
    }

    public Result deleteConfirm(Long id) {
        return ok(delete_confirm.render(ComicCharacter.find.ref(id)));
    }

    public Result delete(Long id) {

        ComicCharacter comicCharacter = ComicCharacter.find.ref(id);
        comicCharacter.delete();

        return ok();
    }

    private boolean isAjax() {
        return "XMLHttpRequest".equals(Http.Context.current().request().getHeader("X-Requested-With"));
    }
}
