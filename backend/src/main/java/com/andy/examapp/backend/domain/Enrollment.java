package com.andy.examapp.backend.domain;

import java.time.LocalDateTime;

import com.andy.examapp.backend.domain.enums.EnrollmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime enrolledAt = LocalDateTime.now();

    public Enrollment() {}

    public Enrollment(Participant participant, Course course) {
        this.participant = participant;
        this.course = course;
        this.id = new EnrollmentId(participant.getId(), course.getId());
    }

    // Getters & setters
    public EnrollmentId getId() { return id; }

    public void setId(EnrollmentId id) { this.id = id; }

    public Participant getParticipant() { return participant; }

    public void setParticipant(Participant participant) { this.participant = participant; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public EnrollmentStatus getStatus() { return status; }

    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
}

