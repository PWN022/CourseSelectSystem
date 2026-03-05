package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.entity.Student;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    
    /**
     * 分页查询学生信息，带班级名称
     * @param page 分页参数
     * @param studentNo 学号
     * @param name 姓名
     * @param classId 班级ID
     * @return 分页结果
     */
    Page<Student> selectStudentPage(Page<Student> page, @Param("studentNo") String studentNo, 
                                   @Param("name") String name, @Param("classId") Long classId);
} 