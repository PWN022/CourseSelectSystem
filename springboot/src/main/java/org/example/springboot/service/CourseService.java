package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.Course;
import org.example.springboot.entity.StudentCourse;
import org.example.springboot.entity.TeacherCourse;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.CourseMapper;
import org.example.springboot.mapper.StudentCourseMapper;
import org.example.springboot.mapper.TeacherCourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;
    
    @Resource
    private TeacherCourseMapper teacherCourseMapper;
    
    @Resource
    private StudentCourseMapper studentCourseMapper;
    
    /**
     * 分页查询课程信息
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param courseType 课程类型
     * @param currentPage 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    public Page<Course> getCoursesByPage(String courseCode, String courseName, String courseType, Integer currentPage, Integer size) {
        Page<Course> page = new Page<>(currentPage, size);
        
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(courseCode)) {
            queryWrapper.like(Course::getCourseCode, courseCode);
        }
        if (StringUtils.isNotBlank(courseName)) {
            queryWrapper.like(Course::getCourseName, courseName);
        }
        if (StringUtils.isNotBlank(courseType)) {
            queryWrapper.eq(Course::getCourseType, courseType);
        }
        
        queryWrapper.orderByDesc(Course::getCreateTime);
        
        Page<Course> resultPage = courseMapper.selectPage(page, queryWrapper);
        
        // 填充教师数量和学生数量
        for (Course course : resultPage.getRecords()) {
            // 查询授课教师数量
            LambdaQueryWrapper<TeacherCourse> teacherQueryWrapper = new LambdaQueryWrapper<>();
            teacherQueryWrapper.eq(TeacherCourse::getCourseId, course.getId());
            int teacherCount = teacherCourseMapper.selectCount(teacherQueryWrapper).intValue();
            course.setTeacherCount(teacherCount);
            
            // 查询选课学生数量
            LambdaQueryWrapper<StudentCourse> studentQueryWrapper = new LambdaQueryWrapper<>();
            studentQueryWrapper.eq(StudentCourse::getCourseId, course.getId());
            int studentCount = studentCourseMapper.selectCount(studentQueryWrapper).intValue();
            course.setStudentCount(studentCount);
        }
        
        return resultPage;
    }
    
    /**
     * 获取所有课程列表
     * @return 课程列表
     */
    public List<Course> getAllCourses(String teacherId) {
        LambdaQueryWrapper<Course> courseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(teacherId)){
            List<Long> courseIds = teacherCourseMapper.selectList(new LambdaQueryWrapper<TeacherCourse>().eq(TeacherCourse::getTeacherId, teacherId)).stream().map(TeacherCourse::getCourseId).toList();
            if(!courseIds.isEmpty()){
                courseLambdaQueryWrapper.in(Course::getId, courseIds);
            }else{
                return null;
            }

        }
        return courseMapper.selectList(courseLambdaQueryWrapper);
    }
    
    /**
     * 根据ID获取课程信息
     * @param id 课程ID
     * @return 课程信息
     */
    public Course getCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 查询授课教师数量
        LambdaQueryWrapper<TeacherCourse> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.eq(TeacherCourse::getCourseId, course.getId());
        int teacherCount = teacherCourseMapper.selectCount(teacherQueryWrapper).intValue();
        course.setTeacherCount(teacherCount);
        
        // 查询选课学生数量
        LambdaQueryWrapper<StudentCourse> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.eq(StudentCourse::getCourseId, course.getId())
                .and(w -> w.isNull(StudentCourse::getStatus).or().ne(StudentCourse::getStatus, "已拒绝"));

        Long count = studentCourseMapper.selectCount(countWrapper);
        course.setStudentCount(count != null ? count.intValue() : 0);
        
        return course;
    }
    
    /**
     * 根据课程代码获取课程信息
     * @param courseCode 课程代码
     * @return 课程信息
     */
    public Course getByCourseCode(String courseCode) {
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseCode, courseCode);
        
        Course course = courseMapper.selectOne(queryWrapper);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        
        return course;
    }
    
    /**
     * 添加课程信息
     * @param course 课程信息
     */
    @Transactional
    public void addCourse(Course course) {
        // 检查课程代码是否已存在
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseCode, course.getCourseCode());
        if (courseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("课程代码已存在");
        }
        
        // 添加课程信息
        if (courseMapper.insert(course) <= 0) {
            throw new ServiceException("添加课程失败");
        }
    }
    
    /**
     * 更新课程信息
     * @param course 课程信息
     */
    @Transactional
    public void updateCourse(Course course) {
        // 检查课程是否存在
        if (courseMapper.selectById(course.getId()) == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查课程代码是否已被其他课程使用
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseCode, course.getCourseCode())
                   .ne(Course::getId, course.getId());
        if (courseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("课程代码已被其他课程使用");
        }
        
        // 更新课程信息
        if (courseMapper.updateById(course) <= 0) {
            throw new ServiceException("更新课程失败");
        }
    }
    
    /**
     * 删除课程信息
     * @param id 课程ID
     */
    @Transactional
    public void deleteCourse(Long id) {
        // 检查课程是否存在
        if (courseMapper.selectById(id) == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查是否有教师关联此课程
        LambdaQueryWrapper<TeacherCourse> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.eq(TeacherCourse::getCourseId, id);
        if (teacherCourseMapper.selectCount(teacherQueryWrapper) > 0) {
            throw new ServiceException("该课程已有教师授课，无法删除");
        }
        
        // 检查是否有学生选择此课程
        LambdaQueryWrapper<StudentCourse> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.eq(StudentCourse::getCourseId, id);
        if (studentCourseMapper.selectCount(studentQueryWrapper) > 0) {
            throw new ServiceException("该课程已有学生选课，无法删除");
        }
        
        // 删除课程信息
        if (courseMapper.deleteById(id) <= 0) {
            throw new ServiceException("删除课程失败");
        }
    }
    
    /**
     * 批量删除课程
     * @param ids 课程ID列表
     */
    @Transactional
    public void batchDeleteCourses(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("未选择要删除的课程");
        }
        
        // 检查是否有教师关联这些课程
        LambdaQueryWrapper<TeacherCourse> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.in(TeacherCourse::getCourseId, ids);
        if (teacherCourseMapper.selectCount(teacherQueryWrapper) > 0) {
            throw new ServiceException("选中的课程中有已分配教师的课程，无法删除");
        }
        
        // 检查是否有学生选择这些课程
        LambdaQueryWrapper<StudentCourse> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.in(StudentCourse::getCourseId, ids);
        if (studentCourseMapper.selectCount(studentQueryWrapper) > 0) {
            throw new ServiceException("选中的课程中有已被学生选择的课程，无法删除");
        }
        
        if (courseMapper.deleteBatchIds(ids) <= 0) {
            throw new ServiceException("批量删除课程失败");
        }
    }
    
    /**
     * 获取所有课程类型列表（用于下拉选择）
     * @return 课程类型列表
     */
    public List<String> getAllCourseTypes() {
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Course::getCourseType).groupBy(Course::getCourseType);
        List<Course> courses = courseMapper.selectList(queryWrapper);
        
        return courses.stream()
                .map(Course::getCourseType)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }
} 