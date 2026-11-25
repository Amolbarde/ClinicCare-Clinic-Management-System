package clinic901;

public class DoctorAvailability {
    private int doctorId;
    private String availableDate;
    private String availableTime;

    public DoctorAvailability() {
        // Default constructor
    }

    public DoctorAvailability(int doctorId, String availableDate, String availableTime) {
        this.doctorId = doctorId;
        this.availableDate = availableDate;
        this.availableTime = availableTime;
    }

    // Getters and Setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }
}