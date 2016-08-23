package models;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "teams")
public class Team extends Model{

    @Id
    public Long id;
    @Column(unique = true)
    public String teamName;
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, Team> find = new Finder<>(Team.class);

    public Team(String teamName) {
        this.teamName = teamName;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static Team create(String teamName) {
        Team team = new Team(teamName);
        team.save();

        return team;
    }
}
