package soft_uni.migrationexe.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class V9__Add_github_url_to_developers extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Connection conn = context.getConnection();

        try (Statement st = conn.createStatement()) {
            st.execute("ALTER TABLE developers ADD COLUMN github_profile_url VARCHAR(200)");
        }
        String select = "SELECT id, github_username FROM developers";
        String update = "UPDATE developers SET github_profile_url = ? WHERE id = ?";

        try (Statement st = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement(update);
             ResultSet rs = st.executeQuery(select)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("github_username");
                String url = "https://github.com/" + username;
                ps.setString(1, url);
                ps.setLong(2, id);
                ps.executeUpdate();

            }
        }

        try (Statement st = conn.createStatement()) {
            st.execute("ALTER TABLE developers MODIFY COLUMN github_profile_url VARCHAR(200) NOT NULL");
        }
    }
}