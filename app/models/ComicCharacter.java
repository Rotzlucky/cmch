package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import services.LinkUtil;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "characters", uniqueConstraints = {@UniqueConstraint(columnNames = {"character_name", "character_real_name"})})
public class ComicCharacter extends Model{

    @Id
    public Long id;
    @Constraints.Required
    public String characterName;
    @Constraints.Required
    public String characterRealName;
    public String imageName;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Team> teams = new ArrayList<>();
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, ComicCharacter> find = new Finder<>(ComicCharacter.class);

    public ComicCharacter(String characterName, String characterRealName, String imageName, List<Team> teams) {
        this.characterName = characterName;
        this.characterRealName = characterRealName;
        this.imageName = imageName;
        this.teams = teams;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static ComicCharacter create(String characterName, String characterRealName, File image, List<Long> teamIds) {
        List<Team> teams = teamIds.stream().map(teamId -> Team.find.ref(teamId)).collect(Collectors.toList());

        String imageName = LinkUtil.getDefaultCharacterImageName();
        if (Objects.nonNull(image)) {
            imageName = createImageName(characterName, characterRealName);
            image.renameTo(new File(LinkUtil.getCharacterUploadPath(), imageName + ".jpg"));
        }

        ComicCharacter character = new ComicCharacter(characterName, characterRealName, imageName, teams);
        character.save();
        Ebean.saveManyToManyAssociations(character, "teams");

        return character;
    }

    public static List<ComicCharacter> findSortedByName() {
        return findSortedByName("");
    }

    public static List<ComicCharacter> findSortedByName(String query) {
        return find
                .where()
                .like("character_name", "%" + query + "%")
                .orderBy("character_name")
                .findPagedList(0, 15)
                .getList();
    }

    public static ComicCharacter findByCharacterName(String characterName) {
        return find.where().eq("characterName", characterName).findUnique();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(characterName);
        if (!characterName.equals(characterRealName)) {
            stringBuilder.append(" (");
            stringBuilder.append(characterRealName);
            stringBuilder.append(")");
        }

        return stringBuilder.toString();
    }

    public String getImagePath() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(LinkUtil.getCharacterImagePath());
        stringBuilder.append(imageName);
        stringBuilder.append(".jpg");

        return stringBuilder.toString();
    }

    public static String createImageName(String characterName, String characterRealName) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(characterName.toLowerCase().replace(".", "").replace(" ", "_"));
        stringBuilder.append("_(");
        stringBuilder.append(characterRealName.toLowerCase().replace(".", "").replace(" ", "_"));
        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}
