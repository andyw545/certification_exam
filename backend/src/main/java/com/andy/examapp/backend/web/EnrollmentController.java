package com.andy.examapp.backend.web;

import com.andy.examapp.backend.domain.Enrollment;
import com.andy.examapp.backend.service.EnrollmentService;
import com.andy.examapp.backend.web.dto.EnrollmentRequest;
import com.andy.examapp.backend.web.dto.EnrollmentStatusUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
