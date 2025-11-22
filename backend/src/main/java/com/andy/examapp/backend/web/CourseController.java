package com.andy.examapp.backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.examapp.backend.domain.Course;
import com.andy.examapp.backend.service.CourseService;
import com.andy.examapp.backend.web.dto.CourseDto;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    private final CourseService CourseService;

    public CourseController(CourseService CourseService) {
        this.CourseService = CourseService;
    }

    @GetMapping
    public List<Course> getAll() {
        return CourseService.getAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return CourseService.getById(id);
    }

    @PostMapping
    public Course create(@RequestBody CourseDto dto) {
        Course course = new Course(dto.getCode(), dto.getTitle(), dto.getDescription());
        return CourseService.create(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course Course) {
        return CourseService.update(id, Course);
    }

    @PutMapping("/{id}/toggle-status")
    public Course toggleStatus(@PathVariable Long id) {
        Course course = CourseService.getById(id);
        course.setActive(!course.isActive());
        return CourseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        CourseService.delete(id);
    }
}
