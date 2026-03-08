package org.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Attendance;
import org.example.springboot.entity.Teacher;
import org.example.springboot.entity.User;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.service.AttendanceService;
import org.example.springboot.util.JwtTokenUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "考勤管理接口")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Resource
    private AttendanceService attendanceService;

    @Resource
    private TeacherMapper teacherMapper;// 新增:用于查询教师ID

    /**
     * 新增私有方法：获取当前登录的教师ID
     */
    private Long getCurrentTeacherId() {
        User currentUser = JwtTokenUtils.getCurrentUser();
        // 如果当前是教师登录，去 teacher 表查出对应的 teacher_id
        if (currentUser != null && "TEACHER".equals(currentUser.getRoleCode())) {
            LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teacher::getUserId, currentUser.getId());
            Teacher teacher = teacherMapper.selectOne(queryWrapper);
            if (teacher != null) {
                return teacher.getId();
            }
        }
        // 如果是管理员(ADMIN)或者未查询到，返回 null。Service 层遇到 null 会跳过越权校验
        return null;
    }

    @Operation(summary = "分页查询考勤记录")
    @GetMapping("/page")
    public Result<?> getAttendanceByPage(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Attendance> page = attendanceService.getAttendanceByPage(
                studentId, courseId, teacherId,status, startDate, endDate, currentPage, size);
        return Result.success(page);
    }

    @Operation(summary = "获取考勤状态选项")
    @GetMapping("/statuses")
    public Result<?> getAttendanceStatuses() {
        List<String> statuses = attendanceService.getAttendanceStatuses();
        return Result.success(statuses);
    }

    @Operation(summary = "根据ID获取考勤记录")
    @GetMapping("/{id}")
    public Result<?> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return Result.success(attendance);
    }

    @Operation(summary = "添加考勤记录")
    @PostMapping
    public Result<?> addAttendance(@RequestBody Attendance attendance) {
        // 1. 动态获取当前登录的 teacherId
        Long currentTeacherId = getCurrentTeacherId();
        // 2. 传入第二个实参
        attendanceService.addAttendance(attendance, currentTeacherId);
        return Result.success();
    }

    @Operation(summary = "更新考勤记录")
    @PutMapping("/{id}")
    public Result<?> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        attendance.setId(id);
        attendanceService.updateAttendance(attendance);
        return Result.success();
    }

    @Operation(summary = "删除考勤记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return Result.success();
    }

    @Operation(summary = "批量添加考勤记录")
    @PostMapping("/batch")
    public Result<?> batchAddAttendance(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Long> studentIds = (List<Long>) params.get("studentIds");
        Long courseId = Long.valueOf(params.get("courseId").toString());
        LocalDate attendanceDate = LocalDate.parse(params.get("attendanceDate").toString());
        String status = (String) params.get("status");
        String remark = (String) params.get("remark");

        attendanceService.batchAddAttendance(studentIds, courseId, attendanceDate, status, remark);
        return Result.success();
    }
    
    @Operation(summary = "获取考勤统计数据")
    @GetMapping("/statistics")
    public Result<?> getAttendanceStatistics(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = attendanceService.getAttendanceStatistics(courseId, teacherId,startDate, endDate);
        return Result.success(statistics);
    }

    @Operation(summary = "获取学生个人考勤统计数据")
    @GetMapping("/student/statistics")
    public Result<?> getStudentAttendanceStatistics(
            @RequestParam Long studentId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = attendanceService.getStudentAttendanceStatistics(studentId, startDate, endDate);
        return Result.success(statistics);
    }
} 