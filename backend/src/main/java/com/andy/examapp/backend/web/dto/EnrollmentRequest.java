package com.andy.examapp.backend.web.dto;

public class EnrollmentRequest {
    private Long participantId;
    private Long courseId;

    public Long getParticipantId() { return participantId; }

    public void setParticipantId(Long participantId) { this.participantId = participantId; }

    public Long getCourseId() { return courseId; }

    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
