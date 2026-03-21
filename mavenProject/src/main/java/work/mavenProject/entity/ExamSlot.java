package work.mavenProject.entity;

public class ExamSlot {

    private int slotId;
    private String date;
    private String time;
    private String status;
    private String location;

    public ExamSlot() {}

    public ExamSlot(int slotId, String date, String time, String status, String location) {
        this.slotId = slotId;
        this.date = date;
        this.time = time;
        this.status = status;
        this.location = location;
    }

    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLoc() { return location; }
    public void setLoc(String location) { this.location = location; }
}