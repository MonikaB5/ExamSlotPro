package work.mavenProject.main;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import work.mavenProject.dao.BookingDAO;
import work.mavenProject.dao.ExamSlotDAO;
import work.mavenProject.dao.UserDAO;
import work.mavenProject.entity.Booking;
import work.mavenProject.entity.ExamSlot;
import work.mavenProject.entity.User;

public class WebServer {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        ExamSlotDAO slotDAO = new ExamSlotDAO();
        BookingDAO bookingDAO = new BookingDAO();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
            });
        }).start(7070);

        System.out.println("Web server started at http://localhost:7070");

        // UI Routes (Handled by static files, but API routes below)

        // User Routes
        app.get("/api/users", ctx -> ctx.json(userDAO.getAllUsers()));
        app.post("/api/users", ctx -> {
            User u = ctx.bodyAsClass(User.class);
            int res = userDAO.addUser(u);
            if (res > 0) ctx.status(201).result("User created");
            else ctx.status(400).result("Failed to create user");
        });
        app.delete("/api/users/{id}", ctx -> {
            int res = userDAO.deleteUser(Integer.parseInt(ctx.pathParam("id")));
            if (res > 0) ctx.status(200).result("User deleted");
            else ctx.status(404).result("User not found");
        });

        // Slot Routes
        app.get("/api/slots", ctx -> ctx.json(slotDAO.getAllSlots()));
        app.get("/api/slots/available", ctx -> ctx.json(slotDAO.fetchAvailableSlots()));
        app.post("/api/slots", ctx -> {
            ExamSlot s = ctx.bodyAsClass(ExamSlot.class);
            s.setStatus("Available");
            int res = slotDAO.addSlot(s);
            if (res > 0) ctx.status(201).result("Slot created");
            else ctx.status(400).result("Failed to create slot");
        });
        app.delete("/api/slots/{id}", ctx -> {
            int res = slotDAO.deleteSlot(Integer.parseInt(ctx.pathParam("id")));
            if (res > 0) ctx.status(200).result("Slot deleted");
            else ctx.status(404).result("Slot not found");
        });

        // Booking Routes
        app.get("/api/bookings", ctx -> ctx.json(bookingDAO.getAllBookings()));
        app.post("/api/bookings", ctx -> {
            Booking b = ctx.bodyAsClass(Booking.class);
            int res = bookingDAO.addBooking(b);
            if (res > 0) ctx.status(201).result("Booking created");
            else ctx.status(400).result("Failed to create booking");
        });
        app.delete("/api/bookings/{id}", ctx -> {
            int res = bookingDAO.deleteBooking(Integer.parseInt(ctx.pathParam("id")));
            if (res > 0) ctx.status(200).result("Booking deleted");
            else ctx.status(404).result("Booking not found");
        });
    }
}
