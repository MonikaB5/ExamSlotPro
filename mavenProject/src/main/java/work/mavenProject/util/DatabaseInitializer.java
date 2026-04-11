package work.mavenProject.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        try {
            Connection con = DBUtil.getConnection();
            Statement stmt = con.createStatement();
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "userId SERIAL PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE" +
                    ")";
            stmt.executeUpdate(createUsersTable);
            System.out.println("[DB] Users table created/verified");
            
            // Create exam_slots table
            String createSlotsTable = "CREATE TABLE IF NOT EXISTS exam_slots (" +
                    "slotId SERIAL PRIMARY KEY, " +
                    "date DATE NOT NULL, " +
                    "time VARCHAR(20) NOT NULL, " +
                    "status VARCHAR(20) DEFAULT 'Available', " +
                    "loc VARCHAR(100) NOT NULL" +
                    ")";
            stmt.executeUpdate(createSlotsTable);
            System.out.println("[DB] Exam slots table created/verified");
            
            // Create bookings table
            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                    "bookingId SERIAL PRIMARY KEY, " +
                    "userId INT NOT NULL, " +
                    "slotId INT NOT NULL, " +
                    "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE, " +
                    "FOREIGN KEY (slotId) REFERENCES exam_slots(slotId) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(createBookingsTable);
            System.out.println("[DB] Bookings table created/verified");
            
            stmt.close();
            con.close();
            System.out.println("[DB] Database initialization completed successfully");
            
        } catch (Exception e) {
            System.err.println("[DB] Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
