package com.andy.examapp.models;

public class EnrollmentId {

    private Long participantId;
    private Long courseId;

    public Long getParticipantId() { return participantId; }
    public Long getCourseId() { return courseId; }

    public void setParticipantId(Long participantId) { this.participantId = participantId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    @Override
    public String toString() {
        return participantId + "-" + courseId;
    }
}
