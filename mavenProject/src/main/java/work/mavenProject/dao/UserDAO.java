package work.mavenProject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import work.mavenProject.entity.User;
import work.mavenProject.util.DBUtil;

public class UserDAO {

    public int addUser(User u) {
        // Let database auto-generate userId - don't specify it
        String sql = "INSERT INTO users (name, email) VALUES(?,?)";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());

            int result = ps.executeUpdate();
            
            // Get the generated ID
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("[DB] User created with ID: " + generatedId);
                    u.setUserId(generatedId);
                }
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("User Insert Error: " + e.getMessage());
            e.printStackTrace();
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
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