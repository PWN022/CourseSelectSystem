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
@TableName("score")
@Schema(description = "成绩实体类")
public class Score {
    @TableId(type = IdType.AUTO)
    @Schema(description = "成绩ID")
    private Long id;
    
    @Schema(description = "学生ID")
    private Long studentId;
    
    @Schema(description = "课程ID")
    private Long courseId;
    
    @Schema(description = "教师ID")
    private Long teacherId;
    
    @Schema(description = "学期")
    private String semester;
    
    @Schema(description = "分数")
    private BigDecimal score;
    
    @Schema(description = "等级(A/B/C/D/E)")
    private String grade;
    
    @Schema(description = "评语")
    private String comment;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    // 非数据库字段
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
    @Schema(description = "课程类型")
    private String courseType;
    
    @TableField(exist = false)
    @Schema(description = "教师姓名")
    private String teacherName;
    
    @TableField(exist = false)
    @Schema(description = "班级名称")
    private String className;
} 