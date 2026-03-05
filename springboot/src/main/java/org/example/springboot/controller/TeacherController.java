package org.example.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Teacher;
import org.example.springboot.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/teacher")
@Tag(name = "教师管理", description = "教师信息管理相关接口")
public class TeacherController {
    @Resource
    private TeacherService teacherService;
    
    @GetMapping("/page")
    @Operation(summary = "分页查询教师信息", description = "根据条件分页查询教师信息")
    public Result<?> page(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String teacherNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title) {
        return Result.success(teacherService.getTeachersByPage(teacherNo, name, title, currentPage, size));
    }
    
    @GetMapping("/all")
    @Operation(summary = "获取所有教师列表", description = "获取所有教师列表，用于下拉选择")
    public Result<?> getAllTeachers() {
        return Result.success(teacherService.getAllTeachers());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取教师信息", description = "根据教师ID获取教师详细信息")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(teacherService.getTeacherById(id));
    }


    
    @GetMapping("/teacherNo/{teacherNo}")
    @Operation(summary = "根据教师编号获取教师信息", description = "根据教师编号获取教师详细信息")
    public Result<?> getByTeacherNo(@PathVariable String teacherNo) {
        return Result.success(teacherService.getByTeacherNo(teacherNo));
    }
    
    @PostMapping
    @Operation(summary = "添加教师信息", description = "添加新的教师信息")
    public Result<?> add(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
        return Result.success();
    }
    
    @PutMapping
    @Operation(summary = "更新教师信息", description = "更新已有的教师信息")
    public Result<?> update(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除教师信息", description = "根据ID删除教师信息")
    public Result<?> delete(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return Result.success();
    }
    
    @PostMapping("/batch/delete")
    @Operation(summary = "批量删除教师信息", description = "根据ID列表批量删除教师信息")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        teacherService.batchDeleteTeachers(ids);
        return Result.success();
    }
    
    @GetMapping("/titles")
    @Operation(summary = "获取所有职称列表", description = "获取所有职称列表，用于下拉选择")
    public Result<List<String>> getAllTitles() {
        return Result.success(teacherService.getAllTitles());
    }

    @GetMapping("/userId/{userId}")
    @Operation(summary = "根据用户ID获取教师信息", description = "根据用户ID获取教师信息")
    public Result<?> getTeacherByUserId(@PathVariable Long userId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        return Result.success(teacher);
    }
    
    /**
     * 获取教师统计数据
     *
     * @return 教师统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getTeacherStatistics() {
        // 获取所有教师
        List<Teacher> teachers = teacherService.getAllTeachers();
        
        // 教师总数
        int totalTeachers = teachers.size();
        
        // 性别统计
        long maleTeachers = teachers.stream().filter(t -> "M".equals(t.getGender())).count();
        long femaleTeachers = teachers.stream().filter(t -> "F".equals(t.getGender())).count();
        
        // 获取职称列表并统计
        List<String> titles = teacherService.getAllTitles();
        int titleCount = titles.size();
        
        // 职称分布统计
        Map<String, Long> titleDistribution = teachers.stream()
                .filter(t -> t.getTitle() != null && !t.getTitle().isEmpty())
                .collect(Collectors.groupingBy(Teacher::getTitle, Collectors.counting()));
        
        // 组装结果
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalTeachers", totalTeachers);
        statistics.put("maleTeachers", maleTeachers);
        statistics.put("femaleTeachers", femaleTeachers);
        statistics.put("titleCount", titleCount);
        statistics.put("titleDistribution", titleDistribution);
        
        return Result.success(statistics);
    }
} 