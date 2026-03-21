package work.mavenProject.entity;

public class Booking {
    private int bookingId;
    private int userId;
    private int slotId;

    public Booking() {}

    public Booking(int bookingId, int userId, int slotId) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.slotId = slotId;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }
}