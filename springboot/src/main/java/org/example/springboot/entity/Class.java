package org.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("class")
@Schema(description = "班级实体类")
public class Class {
    @TableId(type = IdType.AUTO)
    @Schema(description = "班级ID")
    private Long id;
    
    @Schema(description = "班级名称")
    private String className;
    
    @Schema(description = "年级")
    private String grade;
    
    @Schema(description = "专业")
    private String major;
    
    @Schema(description = "班主任ID")
    private Long headTeacherId;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    // 非数据库字段
    @TableField(exist = false)
    @Schema(description = "班主任姓名")
    private String headTeacherName;
} 