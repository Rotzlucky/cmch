package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.REMOVE)
    public List<CharacterAppearance> characterAppearances;
    @ManyToMany
    public List<Team> teams;
    public Date createdAt;
    public Date modifiedAt;

}
