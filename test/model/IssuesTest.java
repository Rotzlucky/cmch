package model;

import com.avaje.ebean.Ebean;
import models.Issue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.db.Database;
import play.db.Databases;
import play.libs.Yaml;
import play.test.WithApplication;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcelsteffen on 27.08.16.
 */
public class IssuesTest extends WithApplication {
    Database database;

    @Before
    public void setUp() {
        database = Databases.inMemory();
        Ebean.saveAll((Collection<?>) Yaml.load("test-data.yml"));
    }

    @After
    public void tearDown() {
        database.shutdown();
    }

    @Test
    public void testIssueTeamSize() {
        Issue issue = Issue.findByIssueName("The Avengers meet Sub-Mariner!");
        assertEquals(2, issue.teams.size());

        issue = Issue.findByIssueName("The Return of the Mole Man!");
        assertEquals(1, issue.teams.size());

        issue = Issue.findByIssueName("The Man Called Electro!");
        assertEquals(0, issue.teams.size());
    }

    @Test
    public void testToString() {
        Issue issue = Issue.findByIssueName("The Avengers meet Sub-Mariner!");
        assertEquals("Avengers (v1) #3", issue.getTitleString());

        issue = Issue.findByIssueName("The Return of the Mole Man!");
        assertEquals("Fantastic Four (v1) #22", issue.getTitleString());

        issue = Issue.findByIssueName("The Man Called Electro!");
        assertEquals("Amazing Spider-Man (v1) #9", issue.getTitleString());
    }

    @Test
    public void testIssueImagePath() {
        Issue issue = Issue.findByIssueName("The Avengers meet Sub-Mariner!");
        assertEquals("/assets/images/issues/avengers_v1/003.jpg", issue.getImagePath());

        issue = Issue.findByIssueName("The Return of the Mole Man!");
        assertEquals("/assets/images/issues/fantastic_four_v1/022.jpg", issue.getImagePath());

        issue = Issue.findByIssueName("The Man Called Electro!");
        assertEquals("/assets/images/issues/amazing_spider-man_v1/009.jpg", issue.getImagePath());
    }

}