package models;

import com.avaje.ebean.Model;
import models.enums.OrderType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "character_appearances")
public class CharacterAppearance extends Model {

    @Id
    public Long id;
    public String description;
    @ManyToOne
    public Character character;
    @ManyToOne
    public Issue issue;
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, CharacterAppearance> find = new Finder<>(CharacterAppearance.class);

    public CharacterAppearance(String description, Character character, Issue issue) {
        this.description = description;
        this.character = character;
        this.issue = issue;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static CharacterAppearance create(String description, Long characterId, Long issueId) {
        CharacterAppearance appearance = new CharacterAppearance(description,
                Character.find.ref(characterId),
                Issue.find.ref(issueId));
        appearance.save();

        return appearance;
    }

    public static List<CharacterAppearance> findByCharacterAndOrder(Long characterId, OrderType orderType) {
        return find
                .fetch("character")
                .fetch("issue")
                .where()
                .eq("issue.orders.orderType", orderType.name())
                .eq("character.id", characterId)
                .findList();
    }

    public static List<CharacterAppearance> findByCharacterAndTitleAndOrder(Long characterId, Long titleId, OrderType orderType) {
        return find
                .fetch("character")
                .fetch("issue")
                .where()
                .eq("issue.orders.orderType", orderType.name())
                .eq("issue.title.id", titleId)
                .eq("character.id", characterId)
                .findList();
    }


}
