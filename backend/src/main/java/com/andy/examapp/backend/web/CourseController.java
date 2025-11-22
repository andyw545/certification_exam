package com.andy.examapp.backend.web;

import com.andy.examapp.backend.domain.Course;
import com.andy.examapp.backend.service.CourseService;
import com.andy.examapp.backend.web.dto.CourseDto;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        CourseService.delete(id);
    }
}
