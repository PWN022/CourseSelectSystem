package org.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("student_course")
@Schema(description = "学生选课实体类")
public class StudentCourse {
    @TableId(type = IdType.AUTO)
    @Schema(description = "关联ID")
    private Long id;
    
    @Schema(description = "学生ID")
    private Long studentId;
    
    @Schema(description = "课程ID")
    private Long courseId;
    
    @Schema(description = "教师ID")
    private Long teacherId;
    
    @Schema(description = "学期")
    private String semester;
    
    @Schema(description = "审批状态(待审批/已通过/已拒绝)")
    private String status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    @Schema(description = "学生姓名")
    private String studentName;
    
    @TableField(exist = false)
    @Schema(description = "学号")
    private String studentNo;
    
    @TableField(exist = false)
    @Schema(description = "课程名称")
    private String courseName;
    
    @TableField(exist = false)
    @Schema(description = "课程代码")
    private String courseCode;
    
    @TableField(exist = false)
    @Schema(description = "学分")
    private BigDecimal credit;
    
    @TableField(exist = false)
    @Schema(description = "课时")
    private Integer hours;
    
    @TableField(exist = false)
    @Schema(description = "课程类型")
    private String courseType;
    
    @TableField(exist = false)
    @Schema(description = "教师姓名")
    private String teacherName;
    
    @TableField(exist = false)
    @Schema(description = "成绩")
    private BigDecimal score;
    
    @TableField(exist = false)
    @Schema(description = "等级")
    private String grade;
} 