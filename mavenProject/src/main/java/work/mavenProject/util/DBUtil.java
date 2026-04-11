package work.mavenProject.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection() {
        try {
            // PostgreSQL connection
            String host = System.getenv().getOrDefault("DB_HOST", "dpg-d7d4u34vikkc73dtu4tg-a.oregon-postgres.render.com");
            String port = System.getenv().getOrDefault("DB_PORT", "5432");
            String database = System.getenv().getOrDefault("DB_NAME", "project_examslotpro_1");
            String user = System.getenv().getOrDefault("DB_USER", "project_examslotpro_1_user");
            String password = System.getenv().getOrDefault("DB_PASSWORD", "rm1m4Sdqe2WGngiqYNwaNYi1Wjzu4ti8");
            
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database + "?sslmode=require";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("DB Connection Failed", e);
        }
    }
}