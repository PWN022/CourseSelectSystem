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
@TableName("course")
@Schema(description = "课程实体类")
public class Course {
    @TableId(type = IdType.AUTO)
    @Schema(description = "课程ID")
    private Long id;
    
    @Schema(description = "课程代码")
    private String courseCode;
    
    @Schema(description = "课程名称")
    private String courseName;
    
    @Schema(description = "学分")
    private BigDecimal credit;
    
    @Schema(description = "课时")
    private Integer hours;
    
    @Schema(description = "课程类型")
    private String courseType;
    
    @Schema(description = "课程描述")
    private String description;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    @Schema(description = "授课教师数量")
    private Integer teacherCount;
    
    @TableField(exist = false)
    @Schema(description = "选课学生数量")
    private Integer studentCount;

    @Schema(description = "最大选课人数")
    private Integer maxCapacity;

    @TableField(exist = false)
    @Schema(description = "当前页码")
    private Integer currentPage;

    @TableField(exist = false)
    @Schema(description = "每页大小")
    private Integer pageSize;

    @TableField(exist = false)
    @Schema(description = "总记录数")
    private Long total;
} 