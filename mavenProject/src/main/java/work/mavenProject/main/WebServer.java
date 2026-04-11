package work.mavenProject.main;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import work.mavenProject.dao.BookingDAO;
import work.mavenProject.dao.ExamSlotDAO;
import work.mavenProject.dao.UserDAO;
import work.mavenProject.entity.Booking;
import work.mavenProject.entity.ExamSlot;
import work.mavenProject.entity.User;
import work.mavenProject.util.DatabaseInitializer;

public class WebServer {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        ExamSlotDAO slotDAO = new ExamSlotDAO();
        BookingDAO bookingDAO = new BookingDAO();

        // Get port from environment variable or use default 7070
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
            });
        }).start(port);

        System.out.println("Web server started at http://localhost:" + port);
        
        // Initialize database tables
        DatabaseInitializer.initializeDatabase();

        // Health Check
        app.get("/api/health", ctx -> {
            ctx.json(new java.util.HashMap<String, String>() {{ put("status", "up"); }});
        });

        // UI Routes (Handled by static files, but API routes below)

        // User Routes
        app.get("/api/users", ctx -> {
            try {
                ctx.json(userDAO.getAllUsers());
            } catch (Exception e) {
                System.err.println("[API ERROR] GET /api/users: " + e.getMessage());
                ctx.status(500).json(new java.util.HashMap<String, String>() {{ put("error", e.getMessage()); }});
            }
        });
        app.post("/api/users", ctx -> {
            try {
                User u = ctx.bodyAsClass(User.class);
                int res = userDAO.addUser(u);
                if (res > 0) ctx.status(201).json(new java.util.HashMap<String, String>() {{ put("message", "User created"); }});
                else ctx.status(400).json(new java.util.HashMap<String, String>() {{ put("error", "Failed to create user"); }});
            } catch (Exception e) {
                System.err.println("[API ERROR] POST /api/users: " + e.getMessage());
                e.printStackTrace();
                ctx.status(500).json(new java.util.HashMap<String, String>() {{ put("error", e.getMessage()); }});
            }
        });
        app.delete("/api/users/{id}", ctx -> {
            try {
                int res = userDAO.deleteUser(Integer.parseInt(ctx.pathParam("id")));
                if (res > 0) ctx.status(200).result("User deleted");
                else ctx.status(404).result("User not found");
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        // Slot Routes
        app.get("/api/slots", ctx -> ctx.json(slotDAO.getAllSlots()));
        app.get("/api/slots/available", ctx -> ctx.json(slotDAO.fetchAvailableSlots()));
        app.post("/api/slots", ctx -> {
            try {
                ExamSlot s = ctx.bodyAsClass(ExamSlot.class);
                s.setStatus("Available");
                int res = slotDAO.addSlot(s);
                if (res > 0) {
                    ctx.status(201).json(new java.util.HashMap<String, String>() {{ put("message", "Slot created"); }});
                } else {
                    ctx.status(400).json(new java.util.HashMap<String, String>() {{ put("error", "Failed to create slot"); }});
                }
            } catch (Exception e) {
                System.err.println("[API ERROR] POST /api/slots: " + e.getMessage());
                e.printStackTrace();
                ctx.status(500).json(new java.util.HashMap<String, String>() {{ put("error", e.getMessage()); }});
            }
        });
        app.delete("/api/slots/{id}", ctx -> {
            try {
                int res = slotDAO.deleteSlot(Integer.parseInt(ctx.pathParam("id")));
                if (res > 0) ctx.status(200).result("Slot deleted");
                else ctx.status(404).result("Slot not found");
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        // Booking Routes
        app.get("/api/bookings", ctx -> ctx.json(bookingDAO.getAllBookings()));
        app.post("/api/bookings", ctx -> {
            try {
                Booking b = ctx.bodyAsClass(Booking.class);
                int res = bookingDAO.addBooking(b);
                if (res > 0) {
                    ctx.status(201).json(new java.util.HashMap<String, String>() {{ put("message", "Booking created"); }});
                } else {
                    ctx.status(400).json(new java.util.HashMap<String, String>() {{ put("error", "Failed to create booking"); }});
                }
            } catch (Exception e) {
                System.err.println("[API ERROR] POST /api/bookings: " + e.getMessage());
                e.printStackTrace();
                ctx.status(500).json(new java.util.HashMap<String, String>() {{ put("error", e.getMessage()); }});
            }
        });
        app.delete("/api/bookings/{id}", ctx -> {
            try {
                int res = bookingDAO.deleteBooking(Integer.parseInt(ctx.pathParam("id")));
                if (res > 0) ctx.status(200).result("Booking deleted");
                else ctx.status(404).result("Booking not found");
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });
    }
}
