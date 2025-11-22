package com.andy.examapp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.andy.examapp.backend.domain.Course;
import com.andy.examapp.backend.repository.CourseRepository;

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
        existing.setActive(updated.isActive());
        return courseRepository.save(existing);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
