package com.andy.examapp.backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.examapp.backend.domain.Enrollment;
import com.andy.examapp.backend.service.EnrollmentService;
import com.andy.examapp.backend.web.dto.EnrollmentRequest;
import com.andy.examapp.backend.web.dto.EnrollmentStatusUpdateRequest;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @PostMapping
    public List<Enrollment> enroll(@RequestBody EnrollmentRequest request) {
        return enrollmentService.enrollMultiple(
                request.getParticipantId(),
                request.getCourseIds(),
                request.getStatus()
        );
    }

    @PatchMapping("/status")
    public Enrollment updateStatus(@RequestBody EnrollmentStatusUpdateRequest request) {
        return enrollmentService.updateStatus(
                request.getParticipantId(),
                request.getCourseId(),
                request.getStatus()
        );
    }

    @GetMapping("/by-participant/{participantId}")
    public List<Enrollment> getByParticipant(@PathVariable Long participantId) {
        return enrollmentService.getByParticipant(participantId);
    }

    @GetMapping("/by-course/{courseId}")
    public List<Enrollment> getByCourse(@PathVariable Long courseId) {
        return enrollmentService.getByCourse(courseId);
    }

    @DeleteMapping("/{participantId}/{courseId}")
    public void deleteEnrollment(
            @PathVariable Long participantId,
            @PathVariable Long courseId) {
        enrollmentService.deleteEnrollment(participantId, courseId);
    }

}
