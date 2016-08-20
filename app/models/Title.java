package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Issue> issues;


}
