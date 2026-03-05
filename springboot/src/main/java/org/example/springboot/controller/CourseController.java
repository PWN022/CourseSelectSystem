package org.example.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Course;
import org.example.springboot.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "课程管理接口")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    
    @Operation(summary = "分页查询课程信息")
    @GetMapping("/page")
    public Result<?> getCoursesByPage(
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseType,

            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(courseService.getCoursesByPage(courseCode, courseName, courseType, currentPage, size));
    }
    
    @Operation(summary = "获取所有课程列表")
    @GetMapping("/all")
    public Result<?> getAllCourses(@RequestParam(required = false) String teacherId) {
        return Result.success(courseService.getAllCourses(teacherId));
    }
    
    @Operation(summary = "根据ID获取课程信息")
    @GetMapping("/{id}")
    public Result<?> getCourseById(@PathVariable Long id) {
        return Result.success(courseService.getCourseById(id));
    }
    
    @Operation(summary = "根据课程代码获取课程信息")
    @GetMapping("/code/{courseCode}")
    public Result<?> getByCourseCode(@PathVariable String courseCode) {
        return Result.success(courseService.getByCourseCode(courseCode));
    }
    
    @Operation(summary = "添加课程")
    @PostMapping
    public Result<?> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return Result.success();
    }
    
    @Operation(summary = "更新课程信息")
    @PutMapping
    public Result<?> updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
        return Result.success();
    }
    
    @Operation(summary = "删除课程")
    @DeleteMapping("/{id}")
    public Result<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Result.success();
    }
    
    @Operation(summary = "批量删除课程")
    @PostMapping("/batch/delete")
    public Result<?> batchDeleteCourses(@RequestBody List<Long> ids) {
        courseService.batchDeleteCourses(ids);
        return Result.success();
    }
    
    @Operation(summary = "获取所有课程类型")
    @GetMapping("/types")
    public Result<?> getAllCourseTypes() {
        return Result.success(courseService.getAllCourseTypes());
    }
} 