package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.Course;

/**
 * 课程信息 Mapper 接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
} 