package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.example.springboot.entity.Course;
import org.example.springboot.entity.Teacher;
import org.example.springboot.entity.TeacherCourse;
import org.example.springboot.entity.User;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.CourseMapper;
import org.example.springboot.mapper.TeacherCourseMapper;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherCourseService {
    @Resource
    private TeacherCourseMapper teacherCourseMapper;
    
    @Resource
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询教师课程信息
     * @param teacherId 教师ID
     * @param courseId 课程ID
     * @param courseName 课程名称
     * @param semester 学期
     * @param courseType 课程类型
     * @param currentPage 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    public Page<TeacherCourse> getTeacherCoursesByPage(Long teacherId, Long courseId, String courseName, String semester, String courseType, Integer currentPage, Integer size) {
        Page<TeacherCourse> page = new Page<>(currentPage, size);
        
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        
        if (teacherId != null) {
            queryWrapper.eq(TeacherCourse::getTeacherId, teacherId);
        }
        if (courseId != null) {
            queryWrapper.eq(TeacherCourse::getCourseId, courseId);
        }
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(TeacherCourse::getSemester, semester);
        }
        
        // 如果提供了课程名称或课程类型，需要先查询符合条件的课程ID
        if (StringUtils.isNotBlank(courseName) || StringUtils.isNotBlank(courseType)) {
            LambdaQueryWrapper<Course> courseWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(courseName)) {
                courseWrapper.like(Course::getCourseName, courseName);
            }
            if (StringUtils.isNotBlank(courseType)) {
                courseWrapper.eq(Course::getCourseType, courseType);
            }
            List<Course> courses = courseMapper.selectList(courseWrapper);
            if (courses.isEmpty()) {
                // 如果没有找到匹配的课程，返回空结果
                return new Page<>(currentPage, size);
            }
            List<Long> courseIds = courses.stream().map(Course::getId).collect(Collectors.toList());
            queryWrapper.in(TeacherCourse::getCourseId, courseIds);
        }
        
        queryWrapper.orderByDesc(TeacherCourse::getCreateTime);
        
        Page<TeacherCourse> resultPage = teacherCourseMapper.selectPage(page, queryWrapper);
        
        // 填充教师姓名和课程名称
        for (TeacherCourse teacherCourse : resultPage.getRecords()) {
            fillTeacherCourseInfo(teacherCourse);
        }
        
        return resultPage;
    }
    
    /**
     * 根据教师ID获取其授课课程详细信息
     */
    public List<TeacherCourse> getCoursesByTeacherId(Long teacherId) {
        if (teacherId == null) {
            throw new ServiceException("教师ID不能为空");
        }
        
        // 查询教师课程关联
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherCourse::getTeacherId, teacherId)
                   .orderByDesc(TeacherCourse::getSemester);
        
        List<TeacherCourse> teacherCourses = teacherCourseMapper.selectList(queryWrapper);
        
        // 填充课程详细信息
        for (TeacherCourse teacherCourse : teacherCourses) {
            fillTeacherCourseInfo(teacherCourse);
        }
        
        return teacherCourses;
    }
    
    /**
     * 根据课程ID获取授课教师
     */
    public List<TeacherCourse> getTeachersByCourseId(Long courseId) {
        if (courseId == null) {
            throw new ServiceException("课程ID不能为空");
        }
        
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherCourse::getCourseId, courseId)
                   .orderByDesc(TeacherCourse::getCreateTime);
        
        List<TeacherCourse> teacherCourses = teacherCourseMapper.selectList(queryWrapper);
        
        // 填充关联信息
        teacherCourses.forEach(this::fillTeacherCourseInfo);

        
        return teacherCourses;
    }
    
    /**
     * 添加教师课程关联
     */
    public void addTeacherCourse(TeacherCourse teacherCourse) {
        // 检查是否已存在相同的关联
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherCourse::getTeacherId, teacherCourse.getTeacherId())
                   .eq(TeacherCourse::getCourseId, teacherCourse.getCourseId())
                   .eq(TeacherCourse::getSemester, teacherCourse.getSemester());
        
        if (teacherCourseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该教师在此学期已关联此课程");
        }
        
        if (teacherCourseMapper.insert(teacherCourse) <= 0) {
            throw new ServiceException("添加教师课程关联失败");
        }
    }
    
    /**
     * 删除教师课程关联
     */
    public void deleteTeacherCourse(Long id) {
        if (teacherCourseMapper.deleteById(id) <= 0) {
            throw new ServiceException("删除教师课程关联失败");
        }
    }
    
    /**
     * 获取所有学期列表
     */
    public List<String> getAllSemesters() {
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TeacherCourse::getSemester)
                   .groupBy(TeacherCourse::getSemester)
                   .orderByDesc(TeacherCourse::getSemester);
        
        return teacherCourseMapper.selectList(queryWrapper)
                .stream()
                .map(TeacherCourse::getSemester)
                .toList();
    }
    
    /**
     * 填充教师课程关联信息
     * @param teacherCourse 教师课程关联对象
     */
    private void fillTeacherCourseInfo(TeacherCourse teacherCourse) {
        // 填充教师信息
        Teacher teacher = teacherMapper.selectById(teacherCourse.getTeacherId());
        if (teacher != null) {
            User user = userMapper.selectById(teacher.getUserId());
            if(user != null) {
                teacherCourse.setTeacherName(user.getName());
            }
        }
        
        // 填充课程信息
        Course course = courseMapper.selectById(teacherCourse.getCourseId());
        if (course != null) {
            teacherCourse.setCourseName(course.getCourseName());
            teacherCourse.setCourseCode(course.getCourseCode());
            teacherCourse.setCourseType(course.getCourseType());
            teacherCourse.setCredit(course.getCredit());
            teacherCourse.setHours(course.getHours());
            // 设置courseId字段，与id字段保持一致，用于前端兼容
            teacherCourse.setCourseId(course.getId());
        }
    }

    /**
     * 根据课程ID和教师ID获取授课教师信息
     */
    public List<TeacherCourse> getTeachersByCourseIdAndTeacherId(Long courseId, Long teacherId) {
        if (courseId == null || teacherId == null) {
            throw new ServiceException("课程ID和教师ID不能为空");
        }
        
        LambdaQueryWrapper<TeacherCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherCourse::getCourseId, courseId)
                   .eq(TeacherCourse::getTeacherId, teacherId)
                   .orderByDesc(TeacherCourse::getCreateTime);
        
        List<TeacherCourse> teacherCourses = teacherCourseMapper.selectList(queryWrapper);
        
        // 填充关联信息
        teacherCourses.forEach(this::fillTeacherCourseInfo);

        
        return teacherCourses;
    }
} 