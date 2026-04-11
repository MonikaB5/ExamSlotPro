package work.mavenProject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import work.mavenProject.entity.ExamSlot;
import work.mavenProject.util.DBUtil;

public class ExamSlotDAO {

    // ✅ ADD SLOT (with status) - AUTO-INCREMENT slotId - RETURNS generated ID
	public int addSlot(ExamSlot s) {
	    try {
	        Connection con = DBUtil.getConnection();
	        // Let database auto-generate slotId - don't specify it
	        String sql = "INSERT INTO examslot (date, time, status, location) VALUES (?,?,?,?)";
	        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	        ps.setString(1, s.getDate());
	        ps.setString(2, s.getTime());
	        ps.setString(3, s.getStatus());
	        ps.setString(4, s.getLoc());

	        int result = ps.executeUpdate();
	        
	        // Get the generated ID
	        if (result > 0) {
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                int generatedId = rs.getInt(1);
	                System.out.println("[DB] Slot created with ID: " + generatedId);
	                s.setSlotId(generatedId);
	            }
	        }
	        
	        return result;

	    } catch (Exception e) {
	        System.out.println("Slot Error: " + e.getMessage());
	        e.printStackTrace();
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

    public List<ExamSlot> getAllSlots() {
        List<ExamSlot> slots = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM examslot");
            while (rs.next()) {
                slots.add(new ExamSlot(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
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

    public List<ExamSlot> fetchAvailableSlots() {
        List<ExamSlot> slots = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM examslot WHERE status='Available'");
            while (rs.next()) {
                slots.add(new ExamSlot(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
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