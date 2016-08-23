package model;

import com.avaje.ebean.Ebean;
import models.Character;
import models.CharacterAppearance;
import models.Issue;
import models.Order;
import models.Team;
import models.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.db.Database;
import play.db.Databases;
import play.libs.Yaml;
import play.test.WithApplication;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcelsteffen on 20.08.16.
 */
public class ModelsTest extends WithApplication{

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
    public void testModelRowCounts() {
        assertEquals(3, Title.find.findRowCount());
        assertEquals(2, Team.find.findRowCount());
        assertEquals(10, Character.find.findRowCount());
        assertEquals(7, Issue.find.findRowCount());
        assertEquals(7, Order.find.findRowCount());
        assertEquals(27, CharacterAppearance.find.findRowCount());
    }

    @Test
    public void testCharacterTeamsSize() {
        List<Character> characters = Character.find.where().eq("characterName", "Spider-Man").findList();
        assertEquals(1, characters.size());
        assertEquals(0, characters.get(0).teams.size());

        characters = Character.find.where().eq("characterName", "Mister Fantastic").findList();
        assertEquals(1, characters.size());
        assertEquals(1, characters.get(0).teams.size());
    }

    @Test
    public void testIssueTeamSize() {
        List<Issue> issues = Issue.find.where().eq("issueName", "The Avengers meet Sub-Mariner!").findList();
        assertEquals(1, issues.size());
        assertEquals(2, issues.get(0).teams.size());

        issues = Issue.find.where().eq("issueName", "The Return of the Mole Man!").findList();
        assertEquals(1, issues.size());
        assertEquals(1, issues.get(0).teams.size());

        issues = Issue.find.where().eq("issueName", "The Man Called Electro!").findList();
        assertEquals(1, issues.size());
        assertEquals(0, issues.get(0).teams.size());
    }
}
