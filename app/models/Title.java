package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "titles", uniqueConstraints = {@UniqueConstraint(columnNames = {"title_name", "title_number"})})
public class Title extends Model {

    @Id
    public Long id;
    public String titleName;
    public String titleNumber;
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, Title> find = new Finder<>(Title.class);

    public Title(String titleName, String titleNumber) {
        this.titleName = titleName;
        this.titleNumber = titleNumber;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static Title create(String titleName, String titleNumber) {
        Title title = new Title(titleName, titleNumber);
        title.save();

        return title;
    }

    public static Title findByNameAndNumber(String titleName, String titleNumber) {
        return find.where().eq("titleName", titleName).eq("titleNumber", titleNumber).findUnique();
    }

}
