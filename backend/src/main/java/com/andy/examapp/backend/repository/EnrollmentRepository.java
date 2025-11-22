package com.andy.examapp.backend.repository;

import com.andy.examapp.backend.domain.Enrollment;
import com.andy.examapp.backend.domain.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    List<Enrollment> findByParticipant_Id(Long participantId);

    List<Enrollment> findByCourse_Id(Long courseId);
}
