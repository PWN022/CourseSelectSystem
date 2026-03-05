package org.example.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Class;
import org.example.springboot.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@Tag(name = "班级管理", description = "班级信息管理相关接口")
public class ClassController {
    @Resource
    private ClassService classService;
    
    @GetMapping("/page")
    @Operation(summary = "分页查询班级信息", description = "根据条件分页查询班级信息")
    public Result<?> page(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String major) {
        return Result.success(classService.getClassesByPage(className, grade, major, currentPage, size));
    }

    
    @GetMapping("/all")
    @Operation(summary = "获取所有班级列表", description = "获取所有班级列表，用于下拉选择")
    public Result<?> getAllClasses(@RequestParam(required = false) String headerTeacherId) {
        return Result.success(classService.getAllClasses(headerTeacherId));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取班级信息", description = "根据班级ID获取班级详细信息")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(classService.getClassById(id));
    }
    
    @PostMapping
    @Operation(summary = "添加班级信息", description = "添加新的班级信息")
    public Result<?> add(@RequestBody Class clazz) {
        classService.addClass(clazz);
        return Result.success();
    }
    
    @PutMapping
    @Operation(summary = "更新班级信息", description = "更新已有的班级信息")
    public Result<?> update(@RequestBody Class clazz) {
        classService.updateClass(clazz);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除班级信息", description = "根据ID删除班级信息")
    public Result<?> delete(@PathVariable Long id) {
        classService.deleteClass(id);
        return Result.success();
    }
    
    @PostMapping("/batch/delete")
    @Operation(summary = "批量删除班级信息", description = "根据ID列表批量删除班级信息")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        classService.batchDeleteClasses(ids);
        return Result.success();
    }
    
    @GetMapping("/grades")
    @Operation(summary = "获取所有年级列表", description = "获取所有年级列表，用于下拉选择")
    public Result<?> getAllGrades() {
        return Result.success(classService.getAllGrades());
    }
    
    @GetMapping("/majors")
    @Operation(summary = "获取所有专业列表", description = "获取所有专业列表，用于下拉选择")
    public Result<?> getAllMajors() {
        return Result.success(classService.getAllMajors());
    }
} 