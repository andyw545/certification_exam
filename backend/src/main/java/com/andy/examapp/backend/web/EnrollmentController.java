package com.andy.examapp.backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin // so JavaFX (different origin) can access if needed
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public Enrollment enroll(@RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(request.getParticipantId(), request.getCourseId());
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
}
