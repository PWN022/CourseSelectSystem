package org.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.StudentCourse;


import org.example.springboot.exception.ServiceException;
import org.example.springboot.service.StudentCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.springboot.util.ValidationUtils;

@Tag(name = "学生选课接口")
@RestController
@RequestMapping("/student-course")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;
    
    @Operation(summary = "分页查询学生选课信息")
    @GetMapping("/page")
    public Result<?> getStudentCoursesByPage(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String semester,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(studentCourseService.getStudentCoursesByPage(studentId, courseId, teacherId, semester, currentPage, size));
    }
    
    @Operation(summary = "根据学生ID获取其选课信息")
    @GetMapping("/student/{studentId}")
    public Result<?> getCoursesByStudentId(
            @PathVariable Long studentId,
            @RequestParam(required = false) String semester) {
        return Result.success(studentCourseService.getCoursesByStudentId(studentId, semester));
    }
    
    @Operation(summary = "根据课程ID获取选课学生")
    @GetMapping("/course/{courseId}")
    public Result<?> getStudentsByCourseId(
            @PathVariable Long courseId,
            @RequestParam(required = false) String semester) {
        return Result.success(studentCourseService.getStudentsByCourseId(courseId, semester));
    }
    
    @Operation(summary = "学生选课")
    @PostMapping
    public Result<?> addStudentCourse(@RequestBody StudentCourse studentCourse) {
        studentCourseService.applyCourse(studentCourse);
        return Result.success();
    }
    
    @Operation(summary = "学生退课")
    @DeleteMapping("/{id}")
    public Result<?> deleteStudentCourse(@PathVariable Long id) {
        studentCourseService.deleteStudentCourse(id);
        return Result.success();
    }
    
    @Operation(summary = "批量选课")
    @PostMapping("/batch")
    public Result<?> batchAddStudentCourses(@RequestBody Map<String, Object> params) {
        try {
            // 验证必要参数
            if (params.get("studentId") == null) {
                return Result.error("学生ID不能为空");
            }
            if (params.get("courseIds") == null) {
                return Result.error("课程ID列表不能为空");
            }
            if (params.get("teacherIds") == null) {
                return Result.error("教师ID列表不能为空");
            }
            if (params.get("semester") == null) {
                return Result.error("学期不能为空");
            }

            Long studentId = Long.valueOf(params.get("studentId").toString());

            // 处理可能是Integer类型的courseIds，转换为Long类型
            @SuppressWarnings("unchecked")
            List<Number> rawCourseIds = (List<Number>) params.get("courseIds");
            List<Long> courseIds = rawCourseIds.stream()
                .map(Number::longValue)
                .collect(Collectors.toList());

            // 处理可能是Integer类型的teacherIds，转换为Long类型
            @SuppressWarnings("unchecked")
            List<Number> rawTeacherIds = (List<Number>) params.get("teacherIds");
            List<Long> teacherIds = rawTeacherIds.stream()
                .map(Number::longValue)
                .collect(Collectors.toList());

            String semester = (String) params.get("semester");

            studentCourseService.batchApplyCourses(studentId, courseIds, teacherIds, semester);
            return Result.success();
        } catch (NumberFormatException e) {
            return Result.error("参数格式错误：" + e.getMessage());
        } catch (ClassCastException e) {
            return Result.error("参数类型错误：" + e.getMessage());
        } catch (Exception e) {
            return Result.error("批量选课失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "分页查询学生的课程信息")
    @GetMapping("/student/courses")
    public Result<?> getStudentCoursesByPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String semester) {
        
        // 创建分页对象
        Page<StudentCourse> page =
            new Page<>(currentPage, size);
        
        // 调用Service方法获取分页数据
        return Result.success(studentCourseService.getStudentCoursesWithCourseName(page, studentId, courseName, semester));
    }

    /**
     * 学生申请选课
     */
    @Operation(summary = "学生申请选课")
    @PostMapping("/apply")
    public Result<?> applyCourse(@RequestBody StudentCourse studentCourse) {
        studentCourseService.applyCourse(studentCourse);
        return Result.success();
    }

    /**
     * 批量申请选课
     */
    @Operation(summary = "批量申请选课")
    @PostMapping("/batch-apply")
    public Result<?> batchApplyCourses(@RequestBody Map<String, Object> params) {
        try {
            Long studentId = ValidationUtils.getLongFromMap(params, "studentId", "学生ID不能为空");
            String semester = ValidationUtils.getStringFromMap(params, "semester", "学期不能为空");

            List<Number> rawCourseIds = ValidationUtils.getListFromMap(params, "courseIds", "课程ID列表不能为空");
            List<Long> courseIds = rawCourseIds.stream()
                .map(Number::longValue)
                .collect(Collectors.toList());

            List<Number> rawTeacherIds = ValidationUtils.getListFromMap(params, "teacherIds", "教师ID列表不能为空");
            List<Long> teacherIds = rawTeacherIds.stream()
                .map(Number::longValue)
                .collect(Collectors.toList());

            ValidationUtils.validateNotEmpty(courseIds, "课程ID列表不能为空");
            ValidationUtils.validateNotEmpty(teacherIds, "教师ID列表不能为空");

            studentCourseService.batchApplyCourses(studentId, courseIds, teacherIds, semester);
            return Result.success();
        } catch (ServiceException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("批量申请选课失败：" + e.getMessage());
        }
    }

    /**
     * 审批学生选课申请
     */
    @Operation(summary = "审批学生选课申请")
    @PutMapping("/approve/{id}")
    public Result<?> approveStudentCourse(@PathVariable Long id, @RequestParam String status) {
        studentCourseService.approveStudentCourse(id, status);
        return Result.success();
    }



    /**
     * 获取学生的选课申请列表
     */
    @Operation(summary = "获取学生的选课申请列表")
    @GetMapping("/applications")
    public Result<List<StudentCourse>> getStudentCourseApplications(@RequestParam Long studentId,
                                                                  @RequestParam(required = false) String status,
                                                                  @RequestParam(required = false) String semester) {
        List<StudentCourse> applications = studentCourseService.getStudentCourseApplications(studentId, status, semester);
        return Result.success(applications);
    }

    /**
     * 获取所有待审批的选课申请
     */
    @Operation(summary = "获取所有待审批的选课申请")
    @GetMapping("/pending")
    public Result<Page<StudentCourse>> getPendingApplications(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Long teacherId) {
        
        Page<StudentCourse> page = studentCourseService.getPendingApplications(currentPage, size, studentId, courseId, semester, teacherId);
        return Result.success(page);
    }

    /**
     * 申请退课
     */
    @Operation(summary = "申请退课")
    @PostMapping("/drop-apply/{id}")
    public Result<?> applyDropCourse(@PathVariable Long id) {
        studentCourseService.applyDropCourse(id);
        return Result.success();
    }
    
    /**
     * 直接退课（针对未审批的课程）
     */
    @Operation(summary = "直接退课（针对未审批的课程）")
    @DeleteMapping("/drop-unapproved/{id}")
    public Result<?> dropUnapprovedCourse(@PathVariable Long id) {
        studentCourseService.dropUnapprovedCourse(id);
        return Result.success();
    }
} 