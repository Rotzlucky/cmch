package models;

import com.avaje.ebean.Model;
import play.mvc.Http;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Team> findSortedByName(String query) {
        return find
                .where()
                .like("team_name", "%" + query + "%")
                .orderBy("team_name")
                .findPagedList(0, 5)
                .getList();
    }


    public static Team create(String teamName) {
        Team team = new Team(teamName);
        team.save();

        return team;
    }

    public static List<Long> getTeamIdsFromCreateCharacterRequest(Http.MultipartFormData body) {
        try {
            String teamIdsString = Arrays.asList((String[]) body.asFormUrlEncoded().get("team_ids")).get(0);
            List<String> idsList = Arrays.asList(teamIdsString.split(","));
            return idsList.stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }

    }
}
