package work.mavenProject.dao;

import java.sql.*;
import work.mavenProject.entity.ExamSlot;
import work.mavenProject.util.DBUtil;

public class ExamSlotDAO {

    // ✅ ADD SLOT (with status)
	public int addSlot(ExamSlot s) {
	    try {
	        Connection con = DBUtil.getConnection();
	        String sql = "INSERT INTO examslot (slotId, date, time, status, location) VALUES (?,?,?,?,?)";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, s.getSlotId());
	        ps.setString(2, s.getDate());
	        ps.setString(3, s.getTime());
	        ps.setString(4, s.getStatus());
	        ps.setString(5, s.getLoc());

	        return ps.executeUpdate();

	    } catch (Exception e) {
	        System.out.println("Slot Error: " + e.getMessage());
	    }
	    return 0;
	}

    // 🔍 VIEW ALL SLOTS (with status)
	public void viewSlots() {
	    try {
	        Connection con = DBUtil.getConnection();
	        Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("SELECT * FROM examslot");

	        System.out.println("\n📅 Slots:");
	        System.out.println("ID | Date | Time | Status | Location");

	        while (rs.next()) {
	            System.out.println(
	                rs.getInt(1) + " | " +
	                rs.getString(2) + " | " +
	                rs.getString(3) + " | " +
	                rs.getString(4) + " | " +
	                rs.getString(5)
	            );
	        }

	    } catch (Exception e) {
	        System.out.println("View Error");
	    }
	}
    // ❌ DELETE SLOT
    public int deleteSlot(int id) {
        try {
            Connection con = DBUtil.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM examslot WHERE slotId=?"
            );

            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Delete Error");
        }
        return 0;
    }
    
    public void suggestSlot() {
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM examslot WHERE status='Available' LIMIT 1"
            );

            if (rs.next()) {
                System.out.println("💡 Suggested Slot:");
                System.out.println(
                    rs.getInt(1) + " | " +
                    rs.getString(2) + " | " +
                    rs.getString(3)
                );
            } else {
                System.out.println("No slots available!");
            }

        } catch (Exception e) {
            System.out.println("Suggestion error");
        }
    }

    // 🔥 UNIQUE FEATURE → ONLY AVAILABLE SLOTS
    public void viewAvailableSlots() {
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM examslot WHERE status='Available'"
            );

            System.out.println("\n🟢 Available Slots:");
            System.out.println("ID | Date | Time | Location");

            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + " | " +
                    rs.getString(2) + " | " +
                    rs.getString(3) + " | " +
                    rs.getString(5)
                );
            }

        } catch (Exception e) {
            System.out.println("Error loading available slots");
        }
    }
    public void removeExpiredSlots() {
        try {
            Connection con = DBUtil.getConnection();

            String sql = "DELETE FROM examslot WHERE date < CURDATE()";
            PreparedStatement ps = con.prepareStatement(sql);

            int count = ps.executeUpdate();
            System.out.println(count + " expired slots removed");

        } catch (Exception e) {
            System.out.println("Error removing expired slots");
        }
    }
    

		
	}