package work.mavenProject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import work.mavenProject.entity.Booking;
import work.mavenProject.util.DBUtil;

public class BookingDAO {

    public int addBooking(Booking b) {

        Connection con = null;

        try {
            con = DBUtil.getConnection();

           
            String check = "SELECT status FROM examslot WHERE slotId=?";
            PreparedStatement psCheck = con.prepareStatement(check);
            psCheck.setInt(1, b.getSlotId());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String status = rs.getString(1);

                if (status.equalsIgnoreCase("Booked")) {
                    System.out.println("❌ Slot already booked!");
                    return 0;
                }
            } else {
                System.out.println("❌ Slot not found!");
                return 0;
            }

            
            // Let database auto-generate bookingId - don't specify it
            String sql = "INSERT INTO booking (userId, slotId) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getSlotId());

            int result = ps.executeUpdate();

            
            String update = "UPDATE examslot SET status='Booked' WHERE slotId=?";
            PreparedStatement ps2 = con.prepareStatement(update);
            ps2.setInt(1, b.getSlotId());
            ps2.executeUpdate();

            System.out.println("✅ Booking successful!");
            return result;

        } catch (Exception e) {
            System.out.println("❌ Booking Failed!");
        }

        return 0;
    }

    
    public void viewBookings() {

        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT b.bookingId, u.name, e.date, e.time " +
                         "FROM booking b " +
                         "JOIN users u ON b.userId = u.userId " +
                         "JOIN examslot e ON b.slotId = e.slotId";

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n📋 Booking Details:");
            System.out.println("ID | Name | Date | Time");

            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + " | " +
                    rs.getString(2) + " | " +
                    rs.getString(3) + " | " +
                    rs.getString(4)
                );
            }

        } catch (Exception e) {
            System.out.println("Error loading bookings");
        }
    }

    public List<Map<String, Object>> getAllBookings() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT b.bookingId, u.name, e.date, e.time " +
                         "FROM booking b " +
                         "JOIN users u ON b.userId = u.userId " +
                         "JOIN examslot e ON b.slotId = e.slotId";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("bookingId", rs.getInt(1));
                map.put("userName", rs.getString(2));
                map.put("date", rs.getString(3));
                map.put("time", rs.getString(4));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int deleteBooking(int id) {

        Connection con = null;

        try {
            con = DBUtil.getConnection();

            
            String get = "SELECT slotId FROM booking WHERE bookingId=?";
            PreparedStatement psGet = con.prepareStatement(get);
            psGet.setInt(1, id);
            ResultSet rs = psGet.executeQuery();

            int slotId = 0;
            if (rs.next()) {
                slotId = rs.getInt(1);
            }


            String sql = "DELETE FROM booking WHERE bookingId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();

            // Reset slot
            String update = "UPDATE examslot SET status='Available' WHERE slotId=?";
            PreparedStatement ps2 = con.prepareStatement(update);
            ps2.setInt(1, slotId);
            ps2.executeUpdate();

            System.out.println("🗑 Booking deleted!");
            return result;

        } catch (Exception e) {
            System.out.println("❌ Delete Failed!");
        }

        return 0;
    }
    public void viewUserBookings(int uid) {
        try {
            Connection con = DBUtil.getConnection();

            String sql = "SELECT b.bookingId, e.date, e.time " +
                         "FROM booking b JOIN examslot e " +
                         "ON b.slotId=e.slotId WHERE b.userId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\nBooking History:");
            System.out.println("ID | Date | Time");

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt(1) + " | " +
                    rs.getString(2) + " | " +
                    rs.getString(3)
                );
            }

            if (!found) {
                System.out.println("❌ No bookings found for this user!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}