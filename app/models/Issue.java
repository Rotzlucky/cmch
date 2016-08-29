package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import models.enums.Month;
import services.LinkUtil;

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
import java.lang.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @ManyToMany
    public List<Team> teams;
    @OneToMany
    public List<Order> orders = new ArrayList<>();
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, Issue> find = new Finder<>(Issue.class);

    public Issue(String issueName,
                 int issueNumber,
                 String issueNumberSuffix,
                 Month publishMonth,
                 int publishYear,
                 Title title,
                 List<Team> teams) {
        this.issueName = issueName;
        this.issueNumber = issueNumber;
        this.issueNumberSuffix = issueNumberSuffix;
        this.publishMonth = publishMonth;
        this.publishYear = publishYear;
        this.title = title;
        this.teams = teams;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static Issue create(String issueName,
                               int issueNumber,
                               String issueNumberSuffix,
                               String publishMonth,
                               int publishYear,
                               long titleId,
                               List<Long> teamIds) {
        Title title = Title.find.ref(titleId);

        Issue issue = new Issue(
                issueName,
                issueNumber,
                issueNumberSuffix,
                Month.valueOf(publishMonth),
                publishYear,
                title,
                Team.find.where().idIn(teamIds).findList()
        );
        issue.save();
        Ebean.saveManyToManyAssociations(issue, "teams");
        title.issues.add(issue);
        Ebean.saveAssociation(title, "issues");

        return issue;
    }

    public static Issue findByIssueName(String issueName) {
        return find.fetch("title").where().eq("issueName", issueName).findUnique();
    }

    public String getTitleString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.title.toString());
        stringBuilder.append(" #");
        stringBuilder.append(issueNumber);
        if (issueNumberSuffix != null) {
            stringBuilder.append(issueNumberSuffix);
        }

        return stringBuilder.toString();
    }

    public String getImagePath() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(LinkUtil.getIssueImagePath());
        stringBuilder.append(title.titleName.replace(" ", "_").toLowerCase());
        stringBuilder.append("_");
        stringBuilder.append(title.titleNumber.toLowerCase());
        stringBuilder.append("/");
        stringBuilder.append(getIssueNumberWithXDigits(3));
        if (issueNumberSuffix != null) {
            stringBuilder.append(issueNumberSuffix);
        }
        stringBuilder.append(".jpg");

        return stringBuilder.toString();
    }

    private String getIssueNumberWithXDigits(int numberOfDigits) {
        return String.format("%0" + numberOfDigits + "d", issueNumber);
    }
}
