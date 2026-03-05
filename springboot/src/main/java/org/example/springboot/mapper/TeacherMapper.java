package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.Teacher;

/**
 * 教师信息 Mapper 接口
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
} 