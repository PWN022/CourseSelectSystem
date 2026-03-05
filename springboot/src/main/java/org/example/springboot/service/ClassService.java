package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.Class;
import org.example.springboot.entity.Student;
import org.example.springboot.entity.Teacher;
import org.example.springboot.entity.User;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.ClassMapper;
import org.example.springboot.mapper.StudentMapper;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassService {
    @Resource
    private ClassMapper classMapper;
    
    @Resource
    private StudentMapper studentMapper;
    
    @Resource
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 分页查询班级信息
     * @param className 班级名称
     * @param grade 年级
     * @param major 专业
     * @param currentPage 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    public Page<Class> getClassesByPage(String className, String grade, String major, Integer currentPage, Integer size) {
        Page<Class> page = new Page<>(currentPage, size);
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(className)) {
            queryWrapper.like(Class::getClassName, className);
        }
        if (StringUtils.isNotBlank(grade)) {
            queryWrapper.eq(Class::getGrade, grade);
        }
        if (StringUtils.isNotBlank(major)) {
            queryWrapper.eq(Class::getMajor, major);
        }
        
        Page<Class> resultPage = classMapper.selectPage(page, queryWrapper);
        
        // 填充班主任姓名
        for (Class clazz : resultPage.getRecords()) {
            if (clazz.getHeadTeacherId() != null) {
                Teacher headTeacher = teacherMapper.selectById(clazz.getHeadTeacherId());
                if (headTeacher != null) {
                    User user = userMapper.selectById(headTeacher.getUserId());
                    if(user != null) {
                        clazz.setHeadTeacherName(user.getName());
                    }

                }
            }
        }
        
        return resultPage;
    }
    
    /**
     * 获取所有班级列表（用于下拉选择）
     * @return 班级列表
     */
    public List<Class> getAllClasses(String headerTeacherId) {
        LambdaQueryWrapper<Class> classLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(headerTeacherId)) {
            classLambdaQueryWrapper.eq(Class::getHeadTeacherId, headerTeacherId);
        }
        List<Class> classes = classMapper.selectList(classLambdaQueryWrapper);
        
        // 填充班主任姓名
        for (Class clazz : classes) {
            if (clazz.getHeadTeacherId() != null) {
                Teacher headTeacher = teacherMapper.selectById(clazz.getHeadTeacherId());
                User user = userMapper.selectById(headTeacher.getUserId());
                if(user != null) {
                    clazz.setHeadTeacherName(user.getName());
                }
            }
        }
        
        return classes;
    }
    
    /**
     * 根据ID获取班级信息
     * @param id 班级ID
     * @return 班级信息
     */
    public Class getClassById(Long id) {
        Class clazz = classMapper.selectById(id);
        if (clazz == null) {
            throw new ServiceException("班级不存在");
        }
        
        // 填充班主任姓名
        if (clazz.getHeadTeacherId() != null) {
            Teacher headTeacher = teacherMapper.selectById(clazz.getHeadTeacherId());
            User user = userMapper.selectById(headTeacher.getUserId());
            if(user != null) {
                clazz.setHeadTeacherName(user.getName());
            }
        }
        
        return clazz;
    }
    
    /**
     * 添加班级信息
     * @param clazz 班级信息
     */
    @Transactional
    public void addClass(Class clazz) {
        // 检查班级名称是否已存在
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getClassName, clazz.getClassName());
        if (classMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("班级名称已存在");
        }
        
        // 检查班主任是否存在
        if (clazz.getHeadTeacherId() != null && teacherMapper.selectById(clazz.getHeadTeacherId()) == null) {
            throw new ServiceException("班主任不存在");
        }
        
        // 添加班级信息
        if (classMapper.insert(clazz) <= 0) {
            throw new ServiceException("添加班级失败");
        }
    }
    
    /**
     * 更新班级信息
     * @param clazz 班级信息
     */
    @Transactional
    public void updateClass(Class clazz) {
        // 检查班级是否存在
        if (classMapper.selectById(clazz.getId()) == null) {
            throw new ServiceException("班级不存在");
        }
        
        // 检查班级名称是否已被其他班级使用
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getClassName, clazz.getClassName())
                   .ne(Class::getId, clazz.getId());
        if (classMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("班级名称已被其他班级使用");
        }
        
        // 检查班主任是否存在
        if (clazz.getHeadTeacherId() != null && teacherMapper.selectById(clazz.getHeadTeacherId()) == null) {
            throw new ServiceException("班主任不存在");
        }
        
        // 更新班级信息
        if (classMapper.updateById(clazz) <= 0) {
            throw new ServiceException("更新班级失败");
        }
    }
    
    /**
     * 删除班级信息
     * @param id 班级ID
     */
    @Transactional
    public void deleteClass(Long id) {
        // 检查班级是否存在
        if (classMapper.selectById(id) == null) {
            throw new ServiceException("班级不存在");
        }
        
        // 检查班级是否有关联的学生
        LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.eq(Student::getClassId, id);
        if (studentMapper.selectCount(studentQueryWrapper) > 0) {
            throw new ServiceException("班级下存在学生，无法删除");
        }
        
        // 删除班级信息
        if (classMapper.deleteById(id) <= 0) {
            throw new ServiceException("删除班级失败");
        }
    }
    
    /**
     * 批量删除班级
     * @param ids 班级ID列表
     */
    @Transactional
    public void batchDeleteClasses(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("未选择要删除的班级");
        }
        
        // 检查是否有班级关联了学生
        for (Long id : ids) {
            LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
            studentQueryWrapper.eq(Student::getClassId, id);
            if (studentMapper.selectCount(studentQueryWrapper) > 0) {
                Class clazz = classMapper.selectById(id);
                throw new ServiceException("班级 " + (clazz != null ? clazz.getClassName() : id) + " 下存在学生，无法删除");
            }
        }
        
        if (classMapper.deleteBatchIds(ids) <= 0) {
            throw new ServiceException("批量删除班级失败");
        }
    }
    
    /**
     * 获取所有年级列表（用于下拉选择）
     * @return 年级列表
     */
    public List<String> getAllGrades() {
        // 使用SQL查询不同的年级值
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Class::getGrade).groupBy(Class::getGrade);
        List<Class> classes = classMapper.selectList(queryWrapper);
        
        return classes.stream()
                .map(Class::getGrade)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }
    
    /**
     * 获取所有专业列表（用于下拉选择）
     * @return 专业列表
     */
    public List<String> getAllMajors() {
        // 使用SQL查询不同的专业值
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Class::getMajor).groupBy(Class::getMajor);
        List<Class> classes = classMapper.selectList(queryWrapper);
        
        return classes.stream()
                .map(Class::getMajor)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }
} 