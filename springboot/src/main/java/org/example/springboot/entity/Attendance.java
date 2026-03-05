package org.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("attendance")
@Schema(description = "考勤实体类")
public class Attendance {
    @TableId(type = IdType.AUTO)
    @Schema(description = "考勤ID")
    private Long id;

    @Schema(description = "学生ID")
    private Long studentId;

    @Schema(description = "课程ID")
    private Long courseId;

    @Schema(description = "考勤日期")
    private LocalDate attendanceDate;

    @Schema(description = "考勤状态(出席/缺席/请假)")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 非数据库字段，用于前端展示
    @TableField(exist = false)
    @Schema(description = "学生姓名")
    private String studentName;

    @TableField(exist = false)
    @Schema(description = "学号")
    private String studentNo;

    @TableField(exist = false)
    @Schema(description = "班级名称")
    private String className;

    @TableField(exist = false)
    @Schema(description = "课程名称")
    private String courseName;
} 