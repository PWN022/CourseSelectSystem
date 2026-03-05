package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.StudentCourse;

/**
 * 学生选课 Mapper 接口
 */
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
} 