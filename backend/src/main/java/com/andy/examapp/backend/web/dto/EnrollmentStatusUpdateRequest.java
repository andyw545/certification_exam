package com.andy.examapp.backend.web.dto;

import com.andy.examapp.backend.domain.enums.EnrollmentStatus;

public class EnrollmentStatusUpdateRequest {

    private Long participantId;
    private Long courseId;
    private EnrollmentStatus status;

    public Long getParticipantId() { return participantId; }

    public void setParticipantId(Long participantId) { this.participantId = participantId; }

    public Long getCourseId() { return courseId; }

    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public EnrollmentStatus getStatus() { return status; }

    public void setStatus(EnrollmentStatus status) { this.status = status; }
}
