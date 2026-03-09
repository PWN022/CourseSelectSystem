package org.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Student;
import org.example.springboot.entity.Teacher;
import org.example.springboot.entity.User;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.service.StudentService;
import org.example.springboot.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "学生管理", description = "学生信息管理相关接口")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private TeacherMapper teacherMapper;// 新增注入

    /**
     * 新增：获取当前登录的教师ID
     */
    private Long getCurrentTeacherId() {
        User currentUser = JwtTokenUtils.getCurrentUser();
        if (currentUser != null && "TEACHER".equals(currentUser.getRoleCode())) {
            LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teacher::getUserId, currentUser.getId());
            Teacher teacher = teacherMapper.selectOne(queryWrapper);
            if (teacher != null) return teacher.getId();
        }
        return null;
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询学生信息", description = "根据条件分页查询学生信息")
    public Result<?> page(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String headerTeacherId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long classId) {

        // ============ 【强制拦截越权查询】 ============
        Long currentTeacherId = getCurrentTeacherId();
        if (currentTeacherId != null) {
            headerTeacherId = String.valueOf(currentTeacherId);
        }
        // =======================================================

        return Result.success(studentService.getStudentsByPage(studentNo, name, classId, headerTeacherId, currentPage, size));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有学生列表", description = "获取所有学生列表，用于下拉选择")
    public Result<?> getAllStudents(@RequestParam(required = false) Long teacherId) {
        // 如果是教师登录，强制覆盖查询参数
        Long currentTeacherId = getCurrentTeacherId();
        if (currentTeacherId != null) {
            teacherId = currentTeacherId;
        }

        return Result.success(studentService.getAllStudents(teacherId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取学生信息", description = "根据学生ID获取学生详细信息")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(studentService.getStudentById(id));
    }

    @GetMapping("/studentNo/{studentNo}")
    @Operation(summary = "根据学号获取学生信息", description = "根据学生学号获取学生详细信息")
    public Result<?> getByStudentNo(@PathVariable String studentNo) {
        return Result.success(studentService.getByStudentNo(studentNo));
    }

    @PostMapping
    @Operation(summary = "添加学生信息", description = "添加新的学生信息")
    public Result<?> add(@RequestBody Student student) {
        studentService.addStudent(student);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "更新学生信息", description = "更新已有的学生信息")
    public Result<?> update(@RequestBody Student student) {
        studentService.updateStudent(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除学生信息", description = "根据ID删除学生信息")
    public Result<?> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success();
    }

    @PostMapping("/batch/delete")
    @Operation(summary = "批量删除学生信息", description = "根据ID列表批量删除学生信息")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        studentService.batchDeleteStudents(ids);
        return Result.success();
    }

    @GetMapping("/userId/{userId}")
    @Operation(summary = "根据用户ID获取学生信息", description = "根据用户ID获取学生信息")
    public Result<?> getStudentByUserId(@PathVariable Long userId) {
        Student student = studentService.getStudentByUserId(userId);
        return Result.success(student);
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "获取班级下的所有学生", description = "根据班级ID获取该班级下的所有学生")
    public Result<?> getStudentsByClassId(@PathVariable Long classId) {
        return Result.success(studentService.getStudentsByClassId(classId));
    }


}