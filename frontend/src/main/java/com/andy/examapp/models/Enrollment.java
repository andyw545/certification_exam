package com.andy.examapp.models;

public class Enrollment {

    private EnrollmentId id;
    private Participant participant;
    private Course course;
    private String status;
    private String enrolledAt;

    public EnrollmentId getId() { return id; }
    public Participant getParticipant() { return participant; }
    public Course getCourse() { return course; }
    public String getStatus() { return status; }
    public String getEnrolledAt() { return enrolledAt; }

    public void setId(EnrollmentId id) { this.id = id; }
    public void setParticipant(Participant participant) { this.participant = participant; }
    public void setCourse(Course course) { this.course = course; }
    public void setStatus(String status) { this.status = status; }
    public void setEnrolledAt(String enrolledAt) { this.enrolledAt = enrolledAt; }
}
