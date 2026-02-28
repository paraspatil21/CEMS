package backend.model;

import java.sql.Timestamp;

public class Registration {
    private int registrationId;
    private int studentId;
    private int eventId;
    private Timestamp registrationDate;
    private String approvalStatus;

    // Extra display fields (populated via JOIN queries)
    private String studentName;
    private String rollNumber;
    private String eventName;

    public Registration() {
    }

    public Registration(int registrationId, int studentId, int eventId,
            Timestamp registrationDate, String approvalStatus) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
        this.approvalStatus = approvalStatus;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp d) {
        this.registrationDate = d;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    // Display helpers
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
