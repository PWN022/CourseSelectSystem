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
@TableName("student")
@Schema(description = "学生实体类")
public class Student {
    @TableId(type = IdType.AUTO)
    @Schema(description = "学生ID")
    private Long id;
    
    @Schema(description = "学号")
    private String studentNo;
    
    @Schema(description = "性别")
    private String gender;
    
    @Schema(description = "出生日期")
    private LocalDate birthDate;
    
    @Schema(description = "身份证号")
    private String idCard;
    
    @Schema(description = "家庭住址")
    private String address;
    
    @Schema(description = "所属班级ID")
    private Long classId;
    
    @Schema(description = "关联用户ID")
    private Long userId;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    // 非数据库字段
    @TableField(exist = false)
    @Schema(description = "班级名称")
    private String className;
    
    @TableField(exist = false)
    @Schema(description = "用户名")
    private String username;
    @TableField(exist = false)
    @Schema(description = "姓名")
    private String name;
    @TableField(exist = false)
    @Schema(description = "班主任ID")
    private Long headTeacherId;
    
    @TableField(exist = false)
    @Schema(description = "班主任姓名")
    private String headTeacherName;
    
    @TableField(exist = false)
    @Schema(description = "班主任职称")
    private String headTeacherTitle;
    @TableField(exist = false)
    @Schema(description = "班主任电话")
    private String headTeacherPhone;
    @TableField(exist = false)
    @Schema(description = "班主任邮箱")
    private String headTeacherEmail;

    @TableField(exist = false)
    @Schema(description = "联系电话")
    private String phone;

    @TableField(exist = false)
    @Schema(description = "电子邮箱")
    private String email;
}