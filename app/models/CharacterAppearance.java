package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "character_appearences")
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

}
