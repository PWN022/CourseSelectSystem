package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
import org.example.springboot.entity.Class;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.mapper.TeacherCourseMapper;
import org.example.springboot.mapper.ClassMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    
    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherCourseMapper teacherCourseMapper;

    @Resource
    private ClassMapper classMapper;
    
    /**
     * 分页查询教师信息
     * @param teacherNo 教师编号
     * @param name 教师姓名
     * @param title 职称
     * @param currentPage 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    public Page<Teacher> getTeachersByPage(String teacherNo, String name, String title, Integer currentPage, Integer size) {
        Page<Teacher> page = new Page<>(currentPage, size);
        
        // Use MyBatis-Plus constructor for query
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(teacherNo)) {
            queryWrapper.like(Teacher::getTeacherNo, teacherNo);
        }
        if (StringUtils.isNotBlank(name)) {
            List<Long> ids = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getName, name)).stream().map(User::getId).toList();
            if(ids.isEmpty()){
                return page;
            }else{
                queryWrapper.in(Teacher::getUserId, ids);
            }
        }
        if (StringUtils.isNotBlank(title)) {
            queryWrapper.like(Teacher::getTitle, title);
        }

        // 改为按教师编号升序排列（从小到大），与学生列表保持一致
        queryWrapper.orderByAsc(Teacher::getTeacherNo);
        
        Page<Teacher> resultPage = teacherMapper.selectPage(page, queryWrapper);

        // 批量查询用户信息，避免N+1查询
        List<Teacher> teachers = resultPage.getRecords();
        if (!teachers.isEmpty()) {
            List<Long> userIds = teachers.stream().map(Teacher::getUserId).collect(Collectors.toList());
            List<User> users = userMapper.selectBatchIds(userIds);
            Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

            // Fill in username field from User entity
            for (Teacher teacher : teachers) {
                User user = userMap.get(teacher.getUserId());
                if (user != null) {
                    teacher.setUsername(user.getUsername());
                }
            }
        }
        
        return resultPage;
    }
    
    /**
     * 获取所有教师列表
     * @return 教师列表
     */
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = teacherMapper.selectList(null);
        
        // 填充用户名
        for (Teacher teacher : teachers) {
            User user = userMapper.selectById(teacher.getUserId());
            if (user != null) {
                teacher.setUsername(user.getUsername());
                teacher.setName(user.getName());
            }
        }
        
        return teachers;
    }

    /**
     * 根据ID获取教师信息
     * @param id 教师ID
     * @return 教师信息
     */
    public Teacher getTeacherById(Long id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new ServiceException("教师不存在");
        }
        
        // 填充用户名
        User user = userMapper.selectById(teacher.getUserId());
        if (user != null) {
            teacher.setUsername(user.getUsername());
        }
        
        return teacher;
    }
    
    /**
     * 根据教师编号获取教师信息
     * @param teacherNo 教师编号
     * @return 教师信息
     */
    public Teacher getByTeacherNo(String teacherNo) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getTeacherNo, teacherNo);
        
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (teacher == null) {
            throw new ServiceException("教师不存在");
        }
        
        // 填充用户名
        User user = userMapper.selectById(teacher.getUserId());
        if (user != null) {
            teacher.setUsername(user.getUsername());
        }
        
        return teacher;
    }
    
    /**
     * 添加教师信息
     * @param teacher 教师信息
     */
    @Transactional
    public void addTeacher(Teacher teacher) {
        // 检查教师编号是否已存在
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getTeacherNo, teacher.getTeacherNo());
        if (teacherMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("教师编号已存在");
        }
        
        // 检查用户是否存在
        if (userMapper.selectById(teacher.getUserId()) == null) {
            throw new ServiceException("关联用户不存在");
        }
        
        // 添加教师信息
        if (teacherMapper.insert(teacher) <= 0) {
            throw new ServiceException("添加教师失败");
        }
    }
    
    /**
     * 更新教师信息
     * @param teacher 教师信息
     */
    @Transactional
    public void updateTeacher(Teacher teacher) {
        // 检查教师是否存在
        if (teacherMapper.selectById(teacher.getId()) == null) {
            throw new ServiceException("教师不存在");
        }
        
        // 检查教师编号是否已被其他教师使用
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getTeacherNo, teacher.getTeacherNo())
                   .ne(Teacher::getId, teacher.getId());
        if (teacherMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("教师编号已被其他教师使用");
        }
        
        // 更新教师信息
        if (teacherMapper.updateById(teacher) <= 0) {
            throw new ServiceException("更新教师失败");
        }
    }
    
    /**
     * 删除教师信息（包括级联删除教师课程关联，并验证是否为班主任）
     * @param id 教师ID
     */
    @Transactional
    public void deleteTeacher(Long id) {
        // 检查教师是否存在
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new ServiceException("教师不存在");
        }

        // 检查是否为班主任
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.eq(Class::getHeadTeacherId, id);
        if (classMapper.exists(classQueryWrapper)) {
            throw new ServiceException("该教师是班主任，无法删除");
        }
        
        // 删除教师的课程关联记录
        LambdaQueryWrapper<TeacherCourse> courseQueryWrapper = new LambdaQueryWrapper<>();
        courseQueryWrapper.eq(TeacherCourse::getTeacherId, id);
        teacherCourseMapper.delete(courseQueryWrapper);
        
        // 删除教师信息
        if (teacherMapper.deleteById(id) <= 0) {
            throw new ServiceException("删除教师失败");
        }
    }
    
    /**
     * 批量删除教师（包括级联删除教师课程关联，并验证是否为班主任）
     * @param ids 教师ID列表
     */
    @Transactional
    public void batchDeleteTeachers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("未选择要删除的教师");
        }

        // 检查是否有教师是班主任
        LambdaQueryWrapper<Class> classQueryWrapper = new LambdaQueryWrapper<>();
        classQueryWrapper.in(Class::getHeadTeacherId, ids);
        if (classMapper.exists(classQueryWrapper)) {
            throw new ServiceException("选中的教师中包含班主任，无法删除");
        }
        
        // 批量删除教师的课程关联记录
        LambdaQueryWrapper<TeacherCourse> courseQueryWrapper = new LambdaQueryWrapper<>();
        courseQueryWrapper.in(TeacherCourse::getTeacherId, ids);
        teacherCourseMapper.delete(courseQueryWrapper);
        
        // 批量删除教师信息
        if (teacherMapper.deleteBatchIds(ids) <= 0) {
            throw new ServiceException("批量删除教师失败");
        }
    }
    
    /**
     * 获取所有职称列表（用于下拉选择）
     * @return 职称列表
     */
    public List<String> getAllTitles() {
        // 使用SQL查询不同的职称值
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Teacher::getTitle).groupBy(Teacher::getTitle);
        List<Teacher> teachers = teacherMapper.selectList(queryWrapper);

        return teachers.stream()
                .map(Teacher::getTitle)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }

    /**
     * 根据用户ID获取教师信息
     */
    public Teacher getTeacherByUserId(Long userId) {
        if (userId == null) {
            throw new ServiceException("用户ID不能为空");
        }

        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getUserId, userId);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);

        if (teacher == null) {
            throw new ServiceException("教师信息不存在");
        }

        // 填充用户信息
        User user = userMapper.selectById(teacher.getUserId());
        if (user != null) {
            teacher.setUsername(user.getUsername());
        }

        return teacher;
    }
}