package model;

import com.avaje.ebean.Ebean;
import models.Character;
import models.CharacterAppearance;
import models.Issue;
import models.Order;
import models.Team;
import models.Title;
import models.enums.OrderType;
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
        Character character = Character.findByCharacterName("Spider-Man");
        assertEquals(0, character.teams.size());

        character = Character.findByCharacterName("Mr. Fantastic");
        assertEquals(1, character.teams.size());
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
    public void testAppearances() {
        Character character = Character.findByCharacterName("Mr. Fantastic");
        Title title = Title.findByNameAndNumber("Fantastic Four", "v1");

        List<CharacterAppearance> byCharacterAndOrder = CharacterAppearance.findByCharacterAndOrder(character.id, OrderType.MAIN);
        assertEquals(4, byCharacterAndOrder.size());

        byCharacterAndOrder = CharacterAppearance.findByCharacterAndOrder(character.id, OrderType.CORE);
        assertEquals(0, byCharacterAndOrder.size());

        List<CharacterAppearance> appearances = CharacterAppearance.findByCharacterAndTitleAndOrder(character.id, title.id, OrderType.MAIN);
        assertEquals(3, appearances.size());
        assertEquals("Mr. Fantastic", appearances.get(0).character.characterName);
        assertEquals("Fantastic Four", appearances.get(0).issue.title.titleName);
        assertEquals(OrderType.MAIN, appearances.get(0).issue.orders.get(0).orderType);
    }

    @Test
    public void testToString() {
        Character character = Character.findByCharacterName("Mr. Fantastic");
        assertEquals("Mr. Fantastic (Reed Richards)", character.toString());

        character = Character.findByCharacterName("Thor");
        assertEquals("Thor (Thor Odinson)", character.toString());

        character = Character.findByCharacterName("Henry Pym");
        assertEquals("Henry Pym", character.toString());

        Issue issue = Issue.findByIssueName("The Avengers meet Sub-Mariner!");
        assertEquals("Avengers (v1) #3", issue.getTitleString());

        issue = Issue.findByIssueName("The Return of the Mole Man!");
        assertEquals("Fantastic Four (v1) #22", issue.getTitleString());

        issue = Issue.findByIssueName("The Man Called Electro!");
        assertEquals("Amazing Spider-Man (v1) #9", issue.getTitleString());
    }

    @Test
    public void testNewestEntries() {
        List<CharacterAppearance> newestEntries = CharacterAppearance.findNewestEntries(10);
        assertEquals(10, newestEntries.size());

        newestEntries = CharacterAppearance.findNewestEntries(5);
        assertEquals(5, newestEntries.size());
    }
}
