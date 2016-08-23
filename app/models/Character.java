package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "characters", uniqueConstraints = {@UniqueConstraint(columnNames = {"character_name", "character_real_name"})})
public class Character extends Model{

    @Id
    public Long id;
    public String characterName;
    public String characterRealName;
    public String imageName;
    @ManyToMany(cascade = CascadeType.REFRESH)
    public List<Team> teams = new ArrayList<>();
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, Character> find = new Finder<>(Character.class);

    public Character(String characterName, String characterRealName, String imageName, List<Team> teams) {
        this.characterName = characterName;
        this.characterRealName = characterRealName;
        this.imageName = imageName;
        this.teams = teams;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static Character create(String characterName, String characterRealName, String imageName, List<Long> teamIds) {
        List<Team> teams = teamIds.stream().map(teamId -> Team.find.ref(teamId)).collect(Collectors.toList());

        Character character = new Character(characterName, characterRealName, imageName, teams);
        character.save();
        Ebean.saveManyToManyAssociations(character, "teams");

        return character;
    }
}
