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
@TableName("teacher_course")
@Schema(description = "教师课程关联实体类")
public class TeacherCourse {
    @TableId(type = IdType.AUTO)
    @Schema(description = "关联ID")
    private Long id;
    
    @Schema(description = "教师ID")
    private Long teacherId;
    
    @Schema(description = "课程ID")
    private Long courseId;
    
    @Schema(description = "学期")
    private String semester;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    @Schema(description = "教师姓名")
    private String teacherName;
    
    @TableField(exist = false)
    @Schema(description = "课程名称")
    private String courseName;
    
    @TableField(exist = false)
    @Schema(description = "课程代码")
    private String courseCode;
    
    @TableField(exist = false)
    @Schema(description = "课程类型")
    private String courseType;
    
    @TableField(exist = false)
    @Schema(description = "学分")
    private BigDecimal credit;
    
    @TableField(exist = false)
    @Schema(description = "课时")
    private Integer hours;

    @TableField(exist = false)
    @Schema(description = "最大选课人数")
    private Integer maxCapacity;

    @TableField(exist = false)
    @Schema(description = "已选学生数量")
    private Integer studentCount;
} 