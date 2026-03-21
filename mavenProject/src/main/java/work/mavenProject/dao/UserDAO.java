package work.mavenProject.dao;

import java.sql.*;
import work.mavenProject.entity.User;
import work.mavenProject.util.DBUtil;

public class UserDAO {

    public int addUser(User u) {
        String sql = "INSERT INTO users VALUES(?,?,?)";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, u.getUserId());
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void viewUsers() {
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            
	        System.out.println(" User ID  |   Name  |   Email  ");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " +
                                   rs.getString(2) + " " +
                                   rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int updateUser(User u) {
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET name=?, email=? WHERE userId=?"
            );

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setInt(3, u.getUserId());

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int deleteUser(int id) {
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM users WHERE userId=?"
            );

            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}