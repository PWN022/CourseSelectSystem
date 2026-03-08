package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
import org.example.springboot.entity.Class; // 确保导入的是你自定义的实体类 Class
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private AttendanceMapper attendanceMapper;

    /**
     * 分页查询学生信息
     */
    public Page<Student> getStudentsByPage(String studentNo, String name, Long classId, String headerTeacherId, Integer currentPage, Integer size) {
        Page<Student> page = new Page<>(currentPage, size);

        try {
            return studentMapper.selectStudentPage(page, studentNo, name, classId);
        } catch (Exception e) {
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(studentNo)) {
                queryWrapper.like(Student::getStudentNo, studentNo);
            }
            if (StringUtils.isNotBlank(name)) {
                List<Long> ids = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getName, name)).stream().map(User::getId).toList();
                if(ids.isEmpty()){
                    return page;
                }else{
                    queryWrapper.in(Student::getUserId, ids);
                }
            }
            if (classId != null) {
                queryWrapper.eq(Student::getClassId, classId);
            }

            if(StringUtils.isNotBlank(headerTeacherId)){
                try {
                    Long teacherId = Long.valueOf(headerTeacherId);
                    Set<Long> studentIds = new HashSet<>();

                    // 查询该教师担任班主任的班级学生
                    List<Long> clazzIds = classMapper.selectList(
                            new LambdaQueryWrapper<Class>().eq(Class::getHeadTeacherId, teacherId)
                    ).stream().map(Class::getId).toList();

                    if(!clazzIds.isEmpty()){
                        List<Student> studentsFromClass = studentMapper.selectList(
                                new LambdaQueryWrapper<Student>().in(Student::getClassId, clazzIds)
                        );
                        studentIds.addAll(studentsFromClass.stream().map(Student::getId).toList());
                    }

                    // 查询选修了“该教师”课程且状态为“已通过”的学生
                    List<Long> courseStudentIds = studentCourseMapper.selectList(
                            new LambdaQueryWrapper<StudentCourse>()
                                    .eq(StudentCourse::getTeacherId, teacherId)
                                    .eq(StudentCourse::getStatus, "已通过")
                    ).stream().map(StudentCourse::getStudentId).toList();

                    if (!courseStudentIds.isEmpty()) {
                        studentIds.addAll(courseStudentIds);
                    }

                    if (!studentIds.isEmpty()) {
                        queryWrapper.in(Student::getId, studentIds);
                    } else {
                        return page;
                    }
                } catch (NumberFormatException exception) {
                    return page;
                }
            }

            Page<Student> resultPage = studentMapper.selectPage(page, queryWrapper);
            resultPage.getRecords().forEach(this::fillInfo);
            return resultPage;
        }
    }

    /**
     * 获取所有学生列表（用于下拉选择）
     * 【修复越权漏洞并统一参数类型为 Long】
     * @return 学生列表
     */
    public List<Student> getAllStudents(Long teacherId) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (teacherId != null) {
            Set<Long> studentIds = new HashSet<>(); // 使用Set来存储学生ID，自动去重

            // 1. 查询该老师作为班主任所带的班级的学生
            List<Long> classIds = classMapper.selectList(
                    new LambdaQueryWrapper<Class>().eq(Class::getHeadTeacherId, teacherId)
            ).stream().map(Class::getId).toList();

            if (!classIds.isEmpty()) {
                List<Student> studentsFromClass = studentMapper.selectList(
                        new LambdaQueryWrapper<Student>().in(Student::getClassId, classIds)
                );
                studentIds.addAll(studentsFromClass.stream().map(Student::getId).toList());
            }

            // 2. 直接去选课表查绑定了该老师且状态为“已通过”的学生
            List<Long> courseStudentIds = studentCourseMapper.selectList(
                    new LambdaQueryWrapper<StudentCourse>()
                            .eq(StudentCourse::getTeacherId, teacherId)
                            .eq(StudentCourse::getStatus, "已通过")
            ).stream().map(StudentCourse::getStudentId).toList();

            if (!courseStudentIds.isEmpty()) {
                studentIds.addAll(courseStudentIds);
            }

            // 汇总检查
            List<Long> uniqueStudentIds = new ArrayList<>(studentIds);
            if (!uniqueStudentIds.isEmpty()) {
                studentLambdaQueryWrapper.in(Student::getId, uniqueStudentIds);
            } else {
                // 如果没有找到任何相关的学生ID，直接返回空列表
                return new ArrayList<>();
            }
        }

        // 执行最终查询
        List<Student> students = studentMapper.selectList(studentLambdaQueryWrapper);
        students.forEach(this::fillInfo);

        return students;
    }

    void fillInfo(Student student) {
        Class clazz = classMapper.selectById(student.getClassId());
        if (clazz != null) {
            student.setClassName(clazz.getClassName());
        }

        User user = userMapper.selectById(student.getUserId());
        if (user != null) {
            student.setUsername(user.getUsername());
            student.setName(user.getName());
        }
        if(clazz != null && clazz.getHeadTeacherId() != null) {
            Teacher headTeacher = teacherMapper.selectById(clazz.getHeadTeacherId());
            if(headTeacher != null) {
                User headTeacherUser = userMapper.selectById(headTeacher.getUserId());
                if (headTeacherUser != null) {
                    student.setHeadTeacherId(headTeacher.getId());
                    student.setHeadTeacherName(headTeacherUser.getName());
                    student.setHeadTeacherTitle(headTeacher.getTitle());
                    student.setHeadTeacherPhone(headTeacherUser.getPhone());
                    student.setHeadTeacherEmail(headTeacherUser.getEmail());
                }
            }
        }
    }

    public Student getStudentById(Long id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new ServiceException("学生不存在");
        }
        fillInfo(student);
        return student;
    }

    public Student getByStudentNo(String studentNo) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentNo, studentNo);
        Student student = studentMapper.selectOne(queryWrapper);
        if (student == null) {
            throw new ServiceException("学生不存在");
        }
        fillInfo(student);
        return student;
    }

    @Transactional
    public void addStudent(Student student) {
        if (student == null) throw new ServiceException("学生信息不能为空");
        if (StringUtils.isBlank(student.getStudentNo())) throw new ServiceException("学号不能为空");
        if (student.getClassId() == null) throw new ServiceException("班级不能为空");
        if (student.getUserId() == null) throw new ServiceException("关联用户不能为空");

        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentNo, student.getStudentNo());
        if (studentMapper.selectCount(queryWrapper) > 0) throw new ServiceException("学号已存在");

        if (StringUtils.isNotBlank(student.getIdCard())) {
            LambdaQueryWrapper<Student> idCardWrapper = new LambdaQueryWrapper<>();
            idCardWrapper.eq(Student::getIdCard, student.getIdCard());
            if (studentMapper.selectCount(idCardWrapper) > 0) throw new ServiceException("该身份证号已存在");
        }

        if (classMapper.selectById(student.getClassId()) == null) throw new ServiceException("班级不存在");
        if (userMapper.selectById(student.getUserId()) == null) throw new ServiceException("关联用户不存在");
        if (studentMapper.insert(student) <= 0) throw new ServiceException("添加学生失败");
    }

    @Transactional
    public void updateStudent(Student student) {
        if (studentMapper.selectById(student.getId()) == null) throw new ServiceException("学生不存在");

        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentNo, student.getStudentNo()).ne(Student::getId, student.getId());
        if (studentMapper.selectCount(queryWrapper) > 0) throw new ServiceException("学号已被其他学生使用");

        if (StringUtils.isNotBlank(student.getIdCard())) {
            LambdaQueryWrapper<Student> idCardWrapper = new LambdaQueryWrapper<>();
            idCardWrapper.eq(Student::getIdCard, student.getIdCard()).ne(Student::getId, student.getId());
            if (studentMapper.selectCount(idCardWrapper) > 0) throw new ServiceException("该身份证号已被其他学生使用");
        }

        if (classMapper.selectById(student.getClassId()) == null) throw new ServiceException("班级不存在");
        if (studentMapper.updateById(student) <= 0) throw new ServiceException("更新学生失败");
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (studentMapper.selectById(id) == null) throw new ServiceException("学生不存在");

        LambdaQueryWrapper<StudentCourse> courseQueryWrapper = new LambdaQueryWrapper<>();
        courseQueryWrapper.eq(StudentCourse::getStudentId, id);
        studentCourseMapper.delete(courseQueryWrapper);

        LambdaQueryWrapper<Attendance> attendanceQueryWrapper = new LambdaQueryWrapper<>();
        attendanceQueryWrapper.eq(Attendance::getStudentId, id);
        attendanceMapper.delete(attendanceQueryWrapper);

        if (studentMapper.deleteById(id) <= 0) throw new ServiceException("删除学生失败");
    }

    @Transactional
    public void batchDeleteStudents(List<Long> ids) {
        if (ids == null || ids.isEmpty()) throw new ServiceException("未选择要删除的学生");

        LambdaQueryWrapper<StudentCourse> courseQueryWrapper = new LambdaQueryWrapper<>();
        courseQueryWrapper.in(StudentCourse::getStudentId, ids);
        studentCourseMapper.delete(courseQueryWrapper);

        LambdaQueryWrapper<Attendance> attendanceQueryWrapper = new LambdaQueryWrapper<>();
        attendanceQueryWrapper.in(Attendance::getStudentId, ids);
        attendanceMapper.delete(attendanceQueryWrapper);

        if (studentMapper.deleteByIds(ids) <= 0) throw new ServiceException("批量删除学生失败");
    }

    public List<Student> getStudentsByClassId(Long classId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getClassId, classId);
        return studentMapper.selectList(queryWrapper);
    }

    public Student getByUserId(Long userId){
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserId, userId);
        Student student = studentMapper.selectOne(queryWrapper);
        fillInfo(student);
        return student;
    }

    public Student getStudentByUserId(Long userId) {
        if (userId == null) throw new ServiceException("用户ID不能为空");
        Student student = getByUserId(userId);
        if (student == null) throw new ServiceException("学生信息不存在");
        return student;
    }
}