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

import static org.junit.Assert.assertEquals;

/**
 * Created by marcelsteffen on 27.08.16.
 */
public class CharactersTest extends WithApplication {
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
    public void testCharacterTeamsSize() {
        Character character = Character.findByCharacterName("Spider-Man");
        assertEquals(0, character.teams.size());

        character = Character.findByCharacterName("Mr. Fantastic");
        assertEquals(1, character.teams.size());
    }


    @Test
    public void testToString() {
        Character character = Character.findByCharacterName("Mr. Fantastic");
        assertEquals("Mr. Fantastic (Reed Richards)", character.toString());

        character = Character.findByCharacterName("Thor");
        assertEquals("Thor (Thor Odinson)", character.toString());

        character = Character.findByCharacterName("Henry Pym");
        assertEquals("Henry Pym", character.toString());
    }

    @Test
    public void testCharactersImagePath() {
        Character character = Character.findByCharacterName("Mr. Fantastic");
        assertEquals("/assets/images/characters/mr_fantastic_(reed_richards).jpg", character.getImagePath());

        character = Character.findByCharacterName("Hulk");
        assertEquals("/assets/images/characters/hulk_(bruce_banner).jpg", character.getImagePath());

        character = Character.findByCharacterName("Henry Pym");
        assertEquals("/assets/images/characters/henry_pym.jpg", character.getImagePath());
    }
}
