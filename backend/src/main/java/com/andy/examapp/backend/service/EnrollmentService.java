package com.andy.examapp.backend.service;

import com.andy.examapp.backend.domain.*;
import com.andy.examapp.backend.domain.enums.EnrollmentStatus;
import com.andy.examapp.backend.repository.EnrollmentRepository;
import com.andy.examapp.backend.repository.ParticipantRepository;
import com.andy.examapp.backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + participantId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        EnrollmentId id = new EnrollmentId(participantId, courseId);

        // If already enrolled, just return existing
        return enrollmentRepository.findById(id).orElseGet(() -> {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(id);
            enrollment.setParticipant(participant);
            enrollment.setCourse(course);
            enrollment.setStatus(EnrollmentStatus.PENDING);
            return enrollmentRepository.save(enrollment);
        });
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
}
