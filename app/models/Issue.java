package models;

import com.avaje.ebean.Model;
import models.enums.Month;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.List;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "issues", uniqueConstraints = {@UniqueConstraint(columnNames = {"title_id", "issue_number", "issue_number_suffix"})})
public class Issue extends Model{

    @Id
    public Long id;
    public String issueName;
    public int issueNumber;
    public String issueNumberSuffix;
    @Enumerated(EnumType.STRING)
    public Month publishMonth;
    public int publishYear;
    @ManyToOne
    public Title title;
    @OneToMany(cascade = CascadeType.REMOVE)
    public List<CharacterAppearance> characterAppearances;
    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Order> orders;
    @ManyToMany
    public List<Team> teams;
    public Date createdAt;
    public Date modifiedAt;

}
