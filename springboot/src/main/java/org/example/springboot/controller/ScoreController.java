package org.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Score;
import org.example.springboot.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "成绩管理接口")
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    
    @GetMapping("/page")
    @Operation(summary = "分页查询成绩")
    public Result<?> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer currentPage,
            @Parameter(description = "每页显示记录数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "学生ID") @RequestParam(required = false) Long studentId,
            @Parameter(description = "课程ID") @RequestParam(required = false) Long courseId,
            @Parameter(description = "教师ID") @RequestParam(required = false) Long teacherId,
            @Parameter(description = "学期") @RequestParam(required = false) String semester) {
        
        Page<Score> page = new Page<>(currentPage, pageSize);
        Score score = new Score();
        score.setStudentId(studentId);
        score.setCourseId(courseId);
        score.setTeacherId(teacherId);
        score.setSemester(semester);
        
        return Result.success(scoreService.getScoresByPage(page, score));
    }
    
    @Operation(summary = "根据ID获取成绩详情")
    @GetMapping("/{id}")
    public Result<?> getScoreById(@PathVariable Long id) {
        Score score = scoreService.getScoreById(id);
        return Result.success(score);
    }
    
    @Operation(summary = "添加成绩记录")
    @PostMapping
    public Result<?> addScore(@RequestBody Score score) {
        scoreService.addScore(score);
        return Result.success();
    }
    
    @Operation(summary = "更新成绩记录")
    @PutMapping("/{id}")
    public Result<?> updateScore(@PathVariable Long id, @RequestBody Score score) {
        score.setId(id);
        scoreService.updateScore(score);
        return Result.success();
    }
    
    @Operation(summary = "删除成绩记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return Result.success();
    }
    
    @Operation(summary = "获取所有学期列表")
    @GetMapping("/semesters")
    public Result<?> getAllSemesters() {
        List<String> semesters = scoreService.getAllSemesters();
        return Result.success(semesters);
    }

    @Operation(summary = "获取学生成绩统计信息")
    @GetMapping("/statistics/{studentId}")
    public Result<?> getStudentStatistics(@PathVariable Long studentId) {
        return Result.success(scoreService.getStudentStatistics(studentId));
    }
} 