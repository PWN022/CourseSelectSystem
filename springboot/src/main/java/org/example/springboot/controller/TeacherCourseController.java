package org.example.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.TeacherCourse;

import org.example.springboot.service.TeacherCourseService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "教师课程关联接口")
@RestController
@RequestMapping("/teacher-course")
public class TeacherCourseController {
    @Resource
    private TeacherCourseService teacherCourseService;
    
    @Operation(summary = "分页查询教师课程信息")
    @GetMapping("/page")
    public Result<?> getTeacherCoursesByPage(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String courseType,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,

            @RequestParam(required = false) Long userId) {

        return Result.success(teacherCourseService.getTeacherCoursesByPage(teacherId, courseId, courseName, semester, courseType, currentPage, size));
    }
    
    @Operation(summary = "根据教师ID获取其授课课程")
    @GetMapping("/teacher/{teacherId}")
    public Result<?> getCoursesByTeacherId(
            @PathVariable Long teacherId,

            @RequestParam(required = false) Long userId) {

        return Result.success(teacherCourseService.getCoursesByTeacherId(teacherId));
    }
    
    @Operation(summary = "根据课程ID获取授课教师")
    @GetMapping("/course/{courseId}")
    public Result<?> getTeachersByCourseId(
            @PathVariable Long courseId,

            @RequestParam(required = false) Long userId) {

        return Result.success(teacherCourseService.getTeachersByCourseId(courseId));
    }
    
    @Operation(summary = "添加教师课程关联")
    @PostMapping
    public Result<?> addTeacherCourse(@RequestBody TeacherCourse teacherCourse) {
        teacherCourseService.addTeacherCourse(teacherCourse);
        return Result.success();
    }
    
    @Operation(summary = "删除教师课程关联")
    @DeleteMapping("/{id}")
    public Result<?> deleteTeacherCourse(@PathVariable Long id) {
        teacherCourseService.deleteTeacherCourse(id);
        return Result.success();
    }
    
    @Operation(summary = "获取所有学期列表")
    @GetMapping("/semesters")
    public Result<?> getAllSemesters() {
        return Result.success(teacherCourseService.getAllSemesters());
    }
} 