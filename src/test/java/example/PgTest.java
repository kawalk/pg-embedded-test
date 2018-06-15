package example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PgTest {

    @Rule
    public TemporaryFolder tf = new TemporaryFolder();

    @Test
    public void testEmbeddedPg() throws Exception {
        try (EmbeddedPostgres pg = EmbeddedPostgres.start();
             Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT 1");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
            assertFalse(rs.next());
        }
    }

    @Test
    public void testEmbeddedPgCreationWithNestedDataDirectory() throws Exception {
        try (EmbeddedPostgres pg = EmbeddedPostgres.builder()
                .setDataDirectory(tf.newFolder("data-dir-parent") + "/data-dir")
                .start()) {
            // nothing to do
        }
    }

}
