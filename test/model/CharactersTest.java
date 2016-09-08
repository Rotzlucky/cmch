package model;

import com.avaje.ebean.Ebean;
import models.ComicCharacter;
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
        ComicCharacter character = ComicCharacter.findByCharacterName("Spider-Man");
        assertEquals(0, character.teams.size());

        character = ComicCharacter.findByCharacterName("Mr. Fantastic");
        assertEquals(1, character.teams.size());
    }


    @Test
    public void testToString() {
        ComicCharacter character = ComicCharacter.findByCharacterName("Mr. Fantastic");
        assertEquals("Mr. Fantastic (Reed Richards)", character.toString());

        character = ComicCharacter.findByCharacterName("Thor");
        assertEquals("Thor (Thor Odinson)", character.toString());

        character = ComicCharacter.findByCharacterName("Henry Pym");
        assertEquals("Henry Pym", character.toString());
    }

    @Test
    public void testCharactersImagePath() {
        ComicCharacter character = ComicCharacter.findByCharacterName("Mr. Fantastic");
        assertEquals("/assets/images/characters/mr_fantastic_(reed_richards).jpg", character.getImagePath());

        character = ComicCharacter.findByCharacterName("Hulk");
        assertEquals("/assets/images/characters/hulk_(bruce_banner).jpg", character.getImagePath());

        character = ComicCharacter.findByCharacterName("Henry Pym");
        assertEquals("/assets/images/characters/henry_pym.jpg", character.getImagePath());
    }

    @Test
    public void testFindSortedByName() {
        List<ComicCharacter> sortedByName = ComicCharacter.findSortedByName();
        assertEquals("Henry Pym", sortedByName.get(0).characterName);
        assertEquals("Wasp", sortedByName.get(sortedByName.size() - 1).characterName);

        sortedByName = ComicCharacter.findSortedByName("Hu");
        assertEquals(2, sortedByName.size());
    }
}
