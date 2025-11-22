package com.andy.examapp.backend.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrollmentId implements Serializable {

    private Long participantId;
    private Long courseId;

    public EnrollmentId() {}

    public EnrollmentId(Long participantId, Long courseId) {
        this.participantId = participantId;
        this.courseId = courseId;
    }

    public Long getParticipantId() { return participantId; }

    public void setParticipantId(Long participantId) { this.participantId = participantId; }

    public Long getCourseId() { return courseId; }

    public void setCourseId(Long courseId) { this.courseId = courseId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(participantId, that.participantId) &&
               Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, courseId);
    }
}
