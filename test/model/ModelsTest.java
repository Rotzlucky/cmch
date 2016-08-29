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
public class ModelsTest extends WithApplication {

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
}
