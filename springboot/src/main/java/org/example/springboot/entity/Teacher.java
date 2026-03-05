package org.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("teacher")
@Schema(description = "教师实体类")
public class Teacher {
    @TableId(type = IdType.AUTO)
    @Schema(description = "教师ID")
    private Long id;
    
    @Schema(description = "教师编号")
    private String teacherNo;
    
    @Schema(description = "性别")
    private String gender;
    
    @Schema(description = "职称")
    private String title;

    @Schema(description = "关联用户ID")
    private Long userId;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    @Schema(description = "用户名")
    private String username;

    @TableField(exist = false)
    @Schema(description = "姓名")
    private String name;
} 