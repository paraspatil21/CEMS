package backend.model;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int eventId;
    private String eventName;
    private String eventType;
    private Date eventDate;
    private Time eventTime;
    private String venue;
    private String organizer;
    private int maxParticipants;
    private int registeredCount;
    private Date deadline;
    private String status;

    public Event() {
    }

    public Event(int eventId, String eventName, String eventType,
            Date eventDate, Time eventTime, String venue, String organizer,
            int maxParticipants, int registeredCount, Date deadline, String status) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.organizer = organizer;
        this.maxParticipants = maxParticipants;
        this.registeredCount = registeredCount;
        this.deadline = deadline;
        this.status = status;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int max) {
        this.maxParticipants = max;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(int count) {
        this.registeredCount = count;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
