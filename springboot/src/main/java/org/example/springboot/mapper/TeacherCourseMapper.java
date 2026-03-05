package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.TeacherCourse;

/**
 * 教师课程关联 Mapper 接口
 */
@Mapper
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {
} 