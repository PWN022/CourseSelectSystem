package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentCourseService  {
    @Resource
    private StudentCourseMapper studentCourseMapper;
    
    @Resource
    private StudentMapper studentMapper;
    
    @Resource
    private CourseMapper courseMapper;
    
    @Resource
    private TeacherMapper teacherMapper;
    
    @Resource
    private TeacherCourseMapper teacherCourseMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 分页查询学生选课信息
     */

    public Page<StudentCourse> getStudentCoursesByPage(Long studentId, Long courseId, Long teacherId, String semester, Integer currentPage, Integer size) {
        Page<StudentCourse> page = new Page<>(currentPage, size);
        
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        
        if (studentId != null) {
            queryWrapper.eq(StudentCourse::getStudentId, studentId);
        }
        if (courseId != null) {
            queryWrapper.eq(StudentCourse::getCourseId, courseId);
        }
        if (teacherId != null) {
            queryWrapper.eq(StudentCourse::getTeacherId, teacherId);
        }
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }
        queryWrapper.eq(StudentCourse::getStatus,"已通过");
        
        queryWrapper.orderByDesc(StudentCourse::getCreateTime);
        
        Page<StudentCourse> resultPage = studentCourseMapper.selectPage(page, queryWrapper);
        resultPage.getRecords().forEach(this::fillInfo);

        
        return resultPage;
    }
    
    /**
     * 根据学生ID获取其选课信息
     */

    public List<StudentCourse> getCoursesByStudentId(Long studentId, String semester) {
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, studentId);
        
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }
        
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        queryWrapper.eq(StudentCourse::getStatus,"已通过");
        studentCourses.forEach(this::fillInfo);
        
        return studentCourses;
    }
    
    /**
     * 根据课程ID获取选课学生
     */

    public List<StudentCourse> getStudentsByCourseId(Long courseId, String semester) {
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getCourseId, courseId);
        queryWrapper.eq(StudentCourse::getStatus,"已通过");
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }
        
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        
        // 填充关联信息
        studentCourses.forEach(this::fillInfo);

        
        return studentCourses;
    }

    void fillInfo(StudentCourse studentCourse) {
        Student student = studentMapper.selectById(studentCourse.getStudentId());
        if (student != null) {
            User user = userMapper.selectById(student.getUserId());

            if(user != null) {
                studentCourse.setStudentName(user.getName());
            }

            studentCourse.setStudentNo(student.getStudentNo());
        }
        // 填充课程信息
        Course course = courseMapper.selectById(studentCourse.getCourseId());
        if (course != null) {
            System.out.println("Course: " + course);
            studentCourse.setCourseName(course.getCourseName());
            studentCourse.setCourseCode(course.getCourseCode());
            studentCourse.setCredit(course.getCredit());
            studentCourse.setCourseType(course.getCourseType());
            studentCourse.setHours(course.getHours());
        } else {
            System.out.println("Course not found for ID: " + studentCourse.getCourseId());
        }

        Teacher teacher = teacherMapper.selectById(studentCourse.getTeacherId());
        if (teacher != null) {
            User user = userMapper.selectById(teacher.getUserId());
            if (user != null) {
                studentCourse.setTeacherName(user.getName());
            }
        }
    }

    
    /**
     * 学生退课
     */

    @Transactional
    public void deleteStudentCourse(Long id) {
        // 检查选课记录是否存在
        if (studentCourseMapper.selectById(id) == null) {
            throw new ServiceException("选课记录不存在");
        }
        
        // 删除选课记录
        if (studentCourseMapper.deleteById(id) <= 0) {
            throw new ServiceException("退课失败");
        }
    }

    /**
     * 分页查询学生课程信息，支持按课程名称筛选
     */

    public Page<StudentCourse> getStudentCoursesWithCourseName(Page<StudentCourse> page, Long studentId, String courseName, String semester) {
        // 如果提供了课程名称，需要先查询符合条件的课程ID
        List<Long> courseIds = null;
        if (StringUtils.isNotBlank(courseName)) {
            LambdaQueryWrapper<Course> courseWrapper = new LambdaQueryWrapper<>();
            courseWrapper.like(Course::getCourseName, courseName);
            List<Course> courses = courseMapper.selectList(courseWrapper);
            if (courses.isEmpty()) {
                // 如果没有找到匹配的课程，返回空结果
                return new Page<>(page.getCurrent(), page.getSize());
            }
            courseIds = courses.stream().map(Course::getId).toList();
        }
        
        // 构建学生选课查询条件
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(studentId != null, StudentCourse::getStudentId, studentId);
        queryWrapper.eq(StudentCourse::getStatus,"已通过");
        // 如果有课程名称筛选，添加课程ID条件
        if (courseIds != null) {
            queryWrapper.in(StudentCourse::getCourseId, courseIds);
        }
        
        // 添加学期条件
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(StudentCourse::getCreateTime);
        
        // 执行分页查询
        Page<StudentCourse> resultPage = studentCourseMapper.selectPage(page, queryWrapper);
        System.out.println("Found student courses: " + resultPage.getRecords().size());
        
        // 填充关联信息
        resultPage.getRecords().forEach(this::fillInfo);

        
        return resultPage;
    }

    /**
     * 学生申请选课
     */

    @Transactional
    public void applyCourse(StudentCourse studentCourse) {
        // 检查学生是否存在
        if (studentMapper.selectById(studentCourse.getStudentId()) == null) {
            throw new ServiceException("学生不存在");
        }
        
        // 检查课程是否存在
        if (courseMapper.selectById(studentCourse.getCourseId()) == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查教师是否存在
        if (teacherMapper.selectById(studentCourse.getTeacherId()) == null) {
            throw new ServiceException("教师不存在");
        }
        
        // 检查教师是否教授该课程
        LambdaQueryWrapper<TeacherCourse> teacherCourseQuery = new LambdaQueryWrapper<>();
        teacherCourseQuery.eq(TeacherCourse::getTeacherId, studentCourse.getTeacherId())
                         .eq(TeacherCourse::getCourseId, studentCourse.getCourseId())
                         .eq(TeacherCourse::getSemester, studentCourse.getSemester());
        if (teacherCourseMapper.selectCount(teacherCourseQuery) == 0) {
            throw new ServiceException("该教师在该学期未教授此课程");
        }

        // 检查是否已选择相同的课程
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, studentCourse.getStudentId())
                   .eq(StudentCourse::getCourseId, studentCourse.getCourseId())
                   .eq(StudentCourse::getSemester, studentCourse.getSemester());
        if (studentCourseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该学生在该学期已选择此课程");
        }

        // ================== 【修改单科申请容量校验】 ==================
        Course course = courseMapper.selectById(studentCourse.getCourseId());
        if (course.getMaxCapacity() != null && course.getMaxCapacity() > 0) {
            LambdaQueryWrapper<StudentCourse> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.eq(StudentCourse::getCourseId, studentCourse.getCourseId())
                    // 修复：将容量精确到具体的授课教师
                    .eq(StudentCourse::getTeacherId, studentCourse.getTeacherId())
                    .eq(StudentCourse::getSemester, studentCourse.getSemester())
                    // 锁定占用名额的状态
                    .in(StudentCourse::getStatus, "已通过", "待审批", "退课待审批");

            Long currentSelectedCount = studentCourseMapper.selectCount(countWrapper);
            if (currentSelectedCount >= course.getMaxCapacity()) {
                throw new ServiceException("抱歉，该课程名额已满（上限：" + course.getMaxCapacity() + "人）");
            }
        }
        // ====================================================================

        // 设置状态为待审批
        studentCourse.setStatus("待审批");
        
        // 添加选课信息
        if (studentCourseMapper.insert(studentCourse) <= 0) {
            throw new ServiceException("申请选课失败");
        }
    }

    /**
     * 批量申请选课
     */

    @Transactional
    public void batchApplyCourses(Long studentId, List<Long> courseIds, List<Long> teacherIds, String semester) {
        if (courseIds == null || courseIds.isEmpty() || teacherIds == null || teacherIds.isEmpty() || courseIds.size() != teacherIds.size()) {
            throw new ServiceException("选课参数错误");
        }
        
        // 检查学生是否存在
        if (studentMapper.selectById(studentId) == null) {
            throw new ServiceException("学生不存在");
        }
        
        for (int i = 0; i < courseIds.size(); i++) {
            Long courseId = courseIds.get(i);
            Long teacherId = teacherIds.get(i);
            
            // 检查课程是否存在
            if (courseMapper.selectById(courseId) == null) {
                throw new ServiceException("课程不存在");
            }
            
            // 检查教师是否存在
            if (teacherMapper.selectById(teacherId) == null) {
                throw new ServiceException("教师不存在");
            }
            
            // 检查教师是否教授该课程
            LambdaQueryWrapper<TeacherCourse> teacherCourseQuery = new LambdaQueryWrapper<>();
            teacherCourseQuery.eq(TeacherCourse::getTeacherId, teacherId)
                             .eq(TeacherCourse::getCourseId, courseId)
                             .eq(TeacherCourse::getSemester, semester);
            if (teacherCourseMapper.selectCount(teacherCourseQuery) == 0) {
                throw new ServiceException("教师未教授此课程");
            }
            
            // 检查是否已选择相同的课程
            LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StudentCourse::getStudentId, studentId)
                       .eq(StudentCourse::getCourseId, courseId)
                       .eq(StudentCourse::getSemester, semester);
            if (studentCourseMapper.selectCount(queryWrapper) > 0) {
                continue; // 已选择此课程，跳过
            }

            // ================== 【批量申请的容量校验】 ==================
            Course course = courseMapper.selectById(courseId);
            if (course.getMaxCapacity() != null && course.getMaxCapacity() > 0) {
                LambdaQueryWrapper<StudentCourse> countWrapper = new LambdaQueryWrapper<>();
                countWrapper.eq(StudentCourse::getCourseId, courseId)
                        // 同样修复
                        .eq(StudentCourse::getTeacherId, teacherId)
                        .eq(StudentCourse::getSemester, semester)
                        .ne(StudentCourse::getStatus, "已拒绝");

                Long currentSelectedCount = studentCourseMapper.selectCount(countWrapper);
                if (currentSelectedCount >= course.getMaxCapacity()) {
                    throw new ServiceException("课程《" + course.getCourseName() + "》名额已满（上限：" + course.getMaxCapacity() + "人），批量申请被阻断！");
                }
            }
            // ======================================================================
            
            // 添加选课记录
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudentId(studentId);
            studentCourse.setCourseId(courseId);
            studentCourse.setTeacherId(teacherId);
            studentCourse.setSemester(semester);
            studentCourse.setStatus("待审批");
            
            if (studentCourseMapper.insert(studentCourse) <= 0) {
                throw new ServiceException("批量申请选课失败");
            }
        }
    }

    /**
     * 根据学生ID查询选课记录
     */
    public List<StudentCourse> getStudentCoursesByStudentId(Long studentId, String semester) {
        if (studentId == null) {
            throw new ServiceException("学生ID不能为空");
        }

        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, studentId);
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }

        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);

        // 填充课程和教师信息
        for (StudentCourse studentCourse : studentCourses) {
            fillInfo(studentCourse);
        }

        return studentCourses;
    }

    /**
     * 审批学生选课申请
     */

    @Transactional
    public void approveStudentCourse(Long id, String status) {
        // 检查选课记录是否存在
        StudentCourse studentCourse = studentCourseMapper.selectById(id);
        if (studentCourse == null) {
            throw new ServiceException("选课记录不存在");
        }

        // 如果是同意通过，进行容量复核！
        if ("已通过".equals(status)) {
            Course course = courseMapper.selectById(studentCourse.getCourseId());
            if (course.getMaxCapacity() != null && course.getMaxCapacity() > 0) {
                LambdaQueryWrapper<StudentCourse> countWrapper = new LambdaQueryWrapper<>();
                countWrapper.eq(StudentCourse::getCourseId, studentCourse.getCourseId())
                        // 修复
                        .eq(StudentCourse::getTeacherId, studentCourse.getTeacherId())
                        .eq(StudentCourse::getSemester, studentCourse.getSemester())
                        .eq(StudentCourse::getStatus, "已通过"); // 注意：审批这里只查真正通过的人数

                Long currentApprovedCount = studentCourseMapper.selectCount(countWrapper);
                if (currentApprovedCount >= course.getMaxCapacity()) {
                    throw new ServiceException("审批失败：该课程正式录取名额已满（上限：" + course.getMaxCapacity() + "人）！");
                }
            }
        }
        
        // 检查是否为待审批状态或退课待审批状态
        if (!"待审批".equals(studentCourse.getStatus()) && !"退课待审批".equals(studentCourse.getStatus())) {
            throw new ServiceException("该申请已经被审批过");
        }
        
        // 更新状态
        if (!("已通过".equals(status) || "已拒绝".equals(status))) {
            throw new ServiceException("无效的审批状态");
        }
        
        // 如果是退课待审批且被拒绝，恢复为已通过状态
        if ("退课待审批".equals(studentCourse.getStatus()) && "已拒绝".equals(status)) {
            studentCourse.setStatus("已通过");
        } else {
            studentCourse.setStatus(status);
        }
        
        if (studentCourseMapper.updateById(studentCourse) <= 0) {
            throw new ServiceException("审批失败");
        }
    }

    /**
     * 获取学生的选课申请列表
     */

    public List<StudentCourse> getStudentCourseApplications(Long studentId, String status, String semester) {
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, studentId);
        
        if (StringUtils.isNotBlank(status)) {
            queryWrapper.eq(StudentCourse::getStatus, status);
        }
        
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }
        
        queryWrapper.orderByDesc(StudentCourse::getCreateTime);
        
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        studentCourses.forEach(this::fillInfo);

        
        return studentCourses;
    }

    /**
     * 获取所有待审批的选课申请
     */

    public Page<StudentCourse> getPendingApplications(Integer currentPage, Integer size, Long studentId, Long courseId, String semester, Long teacherId) {
        Page<StudentCourse> page = new Page<>(currentPage, size);
        
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        // 查询待审批和退课待审批状态的记录
        queryWrapper.in(StudentCourse::getStatus, "待审批", "退课待审批");
        
        if (studentId != null) {
            queryWrapper.eq(StudentCourse::getStudentId, studentId);
        }
        
        if (courseId != null) {
            queryWrapper.eq(StudentCourse::getCourseId, courseId);
        }
        
        if (StringUtils.isNotBlank(semester)) {
            queryWrapper.eq(StudentCourse::getSemester, semester);
        }

        // 如果指定了教师ID，只查询该教师教授的课程的申请
        if (teacherId != null) {
            // 获取该教师教授的所有课程ID
            LambdaQueryWrapper<TeacherCourse> teacherCourseQuery = new LambdaQueryWrapper<>();
            teacherCourseQuery.eq(TeacherCourse::getTeacherId, teacherId);
            List<TeacherCourse> teacherCourses = teacherCourseMapper.selectList(teacherCourseQuery);
            List<Long> courseIds = teacherCourses.stream()
                    .map(TeacherCourse::getCourseId)
                    .distinct()
                    .toList();
            
            if (!courseIds.isEmpty()) {
                queryWrapper.in(StudentCourse::getCourseId, courseIds);
            } else {
                // 如果教师没有教授任何课程，返回空结果
                return new Page<>(currentPage, size);
            }
        }
        
        queryWrapper.orderByDesc(StudentCourse::getCreateTime);
        
        Page<StudentCourse> resultPage = studentCourseMapper.selectPage(page, queryWrapper);
        
        // 填充学生姓名、课程名称和教师姓名
        for (StudentCourse studentCourse : resultPage.getRecords()) {
            fillInfo(studentCourse);
        }
        
        return resultPage;
    }


    // 检查
    private void checkCourseCapacity(Long courseId, String semester) {
        Course course = courseMapper.selectById(courseId);
        if (course != null && course.getMaxCapacity() != null && course.getMaxCapacity() > 0) {
            LambdaQueryWrapper<StudentCourse> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.eq(StudentCourse::getCourseId, courseId)
                    .eq(StudentCourse::getSemester, semester)
                    .in(StudentCourse::getStatus, "已通过", "待审批", "退课待审批"); // 只要没拒绝就占位

            if (studentCourseMapper.selectCount(countWrapper) >= course.getMaxCapacity()) {
                throw new ServiceException("课程《" + course.getCourseName() + "》名额已满");
            }
        }
    }

    /**
     * 申请退课
     * @param id 选课记录ID
     */

    @Transactional
    public void applyDropCourse(Long id) {
        // 检查选课记录是否存在
        StudentCourse studentCourse = studentCourseMapper.selectById(id);
        if (studentCourse == null) {
            throw new ServiceException("选课记录不存在");
        }
        
        // 检查状态是否为已通过
        if (!"已通过".equals(studentCourse.getStatus())) {
            throw new ServiceException("只有已通过的课程才能申请退课");
        }
        
        // 将状态更改为"退课待审批"
        studentCourse.setStatus("退课待审批");
        if (studentCourseMapper.updateById(studentCourse) <= 0) {
            throw new ServiceException("申请退课失败");
        }
    }
    
    /**
     * 直接退课（针对未审批的课程）
     * @param id 选课记录ID
     */

    @Transactional
    public void dropUnapprovedCourse(Long id) {
        // 检查选课记录是否存在
        StudentCourse studentCourse = studentCourseMapper.selectById(id);
        if (studentCourse == null) {
            throw new ServiceException("选课记录不存在");
        }
        
        // 检查状态是否为待审批
        if (!"待审批".equals(studentCourse.getStatus())) {
            throw new ServiceException("只有待审批的课程才能直接退课");
        }
        
        // 直接删除选课记录
        if (studentCourseMapper.deleteById(id) <= 0) {
            throw new ServiceException("退课失败");
        }
    }
} 