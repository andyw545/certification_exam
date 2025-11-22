package com.andy.examapp.backend.service;

import com.andy.examapp.backend.domain.Course;
import com.andy.examapp.backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long id, Course updated) {
        Course existing = getById(id);
        existing.setCode(updated.getCode());
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        return courseRepository.save(existing);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
