package com.andy.examapp.models;

import java.util.List;

public class EnrollmentRequest {

    private Long participantId;
    private List<Long> courseIds;
    private String status;

    public EnrollmentRequest(Long participantId, List<Long> courseIds, String status) {
        this.participantId = participantId;
        this.courseIds = courseIds;
        this.status = status;
    }

    public Long getParticipantId() { return participantId; }
    public List<Long> getCourseIds() { return courseIds; }
    public String getStatus() { return status; }

    public void setParticipantId(Long participantId) { this.participantId = participantId; }
    public void setCourseId(List<Long> courseIds) { this.courseIds = courseIds; }
    public void setStatus(String status) { this.status = status; }
}
