package com.andy.examapp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.andy.examapp.backend.domain.Course;
import com.andy.examapp.backend.domain.Enrollment;
import com.andy.examapp.backend.domain.EnrollmentId;
import com.andy.examapp.backend.domain.Participant;
import com.andy.examapp.backend.domain.enums.EnrollmentStatus;
import com.andy.examapp.backend.repository.CourseRepository;
import com.andy.examapp.backend.repository.EnrollmentRepository;
import com.andy.examapp.backend.repository.ParticipantRepository;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ParticipantRepository participantRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
            ParticipantRepository participantRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.participantRepository = participantRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment enroll(Long participantId, Long courseId) {
        return enroll(participantId, courseId, null);
    }

    public Enrollment enroll(Long participantId, Long courseId, String status) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + participantId));
        if (!participant.isActive()) {
            throw new RuntimeException("Participant is disabled and cannot be enrolled.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        if (!course.isActive()) {
            throw new RuntimeException("Course is disabled and cannot accept enrollments.");
        }
        EnrollmentStatus effectiveStatus = resolveStatus(status);
        EnrollmentId id = new EnrollmentId(participantId, courseId);
        return enrollmentRepository.findById(id).orElseGet(() -> {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(id);
            enrollment.setParticipant(participant);
            enrollment.setCourse(course);
            enrollment.setStatus(effectiveStatus);
            return enrollmentRepository.save(enrollment);
        });
    }

    private EnrollmentStatus resolveStatus(String status) {
        if (status == null || status.isBlank()) {
            return EnrollmentStatus.PENDING;
        }
        try {
            return EnrollmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return EnrollmentStatus.PENDING;
        }
    }

    public Enrollment updateStatus(Long participantId, Long courseId, EnrollmentStatus newStatus) {
        EnrollmentId id = new EnrollmentId(participantId, courseId);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        enrollment.setStatus(newStatus);
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getByParticipant(Long participantId) {
        return enrollmentRepository.findByParticipant_Id(participantId);
    }

    public List<Enrollment> getByCourse(Long courseId) {
        return enrollmentRepository.findByCourse_Id(courseId);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> enrollMultiple(Long participantId, List<Long> courseIds, String status) {
        return courseIds.stream()
                .map(courseId -> enroll(participantId, courseId, status))
                .toList();
    }

    public void deleteEnrollment(Long participantId, Long courseId) {
        EnrollmentId id = new EnrollmentId(participantId, courseId);

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            throw new RuntimeException("Completed enrollments cannot be deleted.");
        }
        if (!enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Enrollment not found for participant: "
                    + participantId + " and course: " + courseId);
        }
        enrollmentRepository.deleteById(id);
    }

}
