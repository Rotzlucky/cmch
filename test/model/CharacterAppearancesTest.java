package model;

import com.avaje.ebean.Ebean;
import models.ComicCharacter;
import models.CharacterAppearance;
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
 * Created by marcelsteffen on 27.08.16.
 */
public class CharacterAppearancesTest extends WithApplication {

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
    public void testAppearances() {
        ComicCharacter character = ComicCharacter.findByCharacterName("Mr. Fantastic");
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
    public void testNewestEntries() {
        List<CharacterAppearance> newestEntries = CharacterAppearance.findNewestEntries(10);
        assertEquals(10, newestEntries.size());

        newestEntries = CharacterAppearance.findNewestEntries(5);
        assertEquals(5, newestEntries.size());
//        CharacterAppearance characterAppearance = newestEntries.get(0);
//        System.out.println(characterAppearance.issue.getImagePath());
//        System.out.println(characterAppearance.character.getImagePath());
    }
}
