package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
import org.example.springboot.entity.Class;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    @Resource
    private AttendanceMapper attendanceMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ClassMapper classMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private UserMapper userMapper;

    // 新增注入：用于精确关联 老师-课程-学生
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    /**
     * 核心权限拦截器：确保老师只能看 【自己教的某门课】 下的 【选了这门课且绑定自己为老师的学生】
     */
    private void applyTeacherFilter(LambdaQueryWrapper<Attendance> wrapper, String teacherId) {
        if (StringUtils.isNotBlank(teacherId) && !"undefined".equals(teacherId)) {
            try {
                Long tid = Long.valueOf(teacherId);
                // 查出该老师名下所有的选课记录 (包含 courseId 和 studentId)
                List<StudentCourse> scList = studentCourseMapper.selectList(
                        new LambdaQueryWrapper<StudentCourse>()
                                .eq(StudentCourse::getTeacherId, tid)
                                .eq(StudentCourse::getStatus, "已通过") // 只看已通过的
                );

                if (scList.isEmpty()) {
                    wrapper.eq(Attendance::getId, -1L); // 该老师名下没学生，直接阻断
                    return;
                }

                // 将老师名下的记录转换为特定的组合查询条件
                wrapper.and(w -> {
                    for (StudentCourse sc : scList) {
                        w.or(orW -> orW.eq(Attendance::getCourseId, sc.getCourseId())
                                .eq(Attendance::getStudentId, sc.getStudentId()));
                    }
                });
            } catch (Exception e) {
                wrapper.eq(Attendance::getId, -1L); // 发生异常时阻断查询
            }
        }
    }

    /**
     * 分页查询考勤记录
     */
    public Page<Attendance> getAttendanceByPage(Long studentId, Long courseId,String teacherId, String status,
                                                LocalDate startDate, LocalDate endDate,
                                                Integer currentPage, Integer size) {
        Page<Attendance> page = new Page<>(currentPage, size);
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();

        if (studentId != null) queryWrapper.eq(Attendance::getStudentId, studentId);
        if (courseId != null) queryWrapper.eq(Attendance::getCourseId, courseId);
        if (StringUtils.isNotBlank(status)) queryWrapper.eq(Attendance::getStatus, status);
        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        // 应用教师权限隔离
        applyTeacherFilter(queryWrapper, teacherId);

        queryWrapper.orderByDesc(Attendance::getAttendanceDate);
        Page<Attendance> resultPage = attendanceMapper.selectPage(page, queryWrapper);

        for (Attendance attendance : resultPage.getRecords()) {
            fillAttendanceInfo(attendance);
        }
        return resultPage;
    }

    public List<String> getAttendanceStatuses() {
        return Arrays.asList("出席", "缺席", "请假");
    }

    public Attendance getAttendanceById(Long id) {
        Attendance attendance = attendanceMapper.selectById(id);
        if (attendance == null) throw new ServiceException("考勤记录不存在");
        fillAttendanceInfo(attendance);
        return attendance;
    }

    @Transactional
    public void addAttendance(Attendance attendance,Long currentTeacherId) {
        Student student = studentMapper.selectById(attendance.getStudentId());
        if (student == null) throw new ServiceException("学生不存在");

        Course course = courseMapper.selectById(attendance.getCourseId());
        if (course == null) throw new ServiceException("课程不存在");

        // 越权校验
        if (currentTeacherId != null) {
            LambdaQueryWrapper<StudentCourse> scWrapper = new LambdaQueryWrapper<>();
            scWrapper.eq(StudentCourse::getStudentId, attendance.getStudentId())
                    .eq(StudentCourse::getCourseId, attendance.getCourseId())
                    .eq(StudentCourse::getTeacherId, currentTeacherId)
                    .eq(StudentCourse::getStatus, "已通过");
            if (studentCourseMapper.selectCount(scWrapper) == 0) {
                throw new ServiceException("该学生并非您这门课名下的学生，无法添加考勤");
            }
        }

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, attendance.getStudentId())
                .eq(Attendance::getCourseId, attendance.getCourseId())
                .eq(Attendance::getAttendanceDate, attendance.getAttendanceDate());

        if (attendanceMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该学生在当日该课程已有考勤记录");
        }
        attendanceMapper.insert(attendance);
    }

    @Transactional
    public void updateAttendance(Attendance attendance) {
        if (attendanceMapper.selectById(attendance.getId()) == null) throw new ServiceException("考勤记录不存在");
        if (studentMapper.selectById(attendance.getStudentId()) == null) throw new ServiceException("学生不存在");
        if (courseMapper.selectById(attendance.getCourseId()) == null) throw new ServiceException("课程不存在");

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, attendance.getStudentId())
                .eq(Attendance::getCourseId, attendance.getCourseId())
                .eq(Attendance::getAttendanceDate, attendance.getAttendanceDate())
                .ne(Attendance::getId, attendance.getId());

        if (attendanceMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该学生在当日该课程已有其他考勤记录");
        }
        attendanceMapper.updateById(attendance);
    }

    @Transactional
    public void deleteAttendance(Long id) {
        if (attendanceMapper.selectById(id) == null) throw new ServiceException("考勤记录不存在");
        attendanceMapper.deleteById(id);
    }

    @Transactional
    public void batchAddAttendance(List<Long> studentIds, Long courseId, LocalDate attendanceDate, String status, String remark) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) throw new ServiceException("课程不存在");

        for (Long studentId : studentIds) {
            Student student = studentMapper.selectById(studentId);
            if (student == null) continue;

            LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Attendance::getStudentId, studentId)
                    .eq(Attendance::getCourseId, courseId)
                    .eq(Attendance::getAttendanceDate, attendanceDate);

            if (attendanceMapper.selectCount(queryWrapper) > 0) continue;

            Attendance attendance = new Attendance();
            attendance.setStudentId(studentId);
            attendance.setCourseId(courseId);
            attendance.setAttendanceDate(attendanceDate);
            attendance.setStatus(status);
            attendance.setRemark(remark);

            attendanceMapper.insert(attendance);
        }
    }

    private void fillAttendanceInfo(Attendance attendance) {
        if (attendance == null) return;
        Student student = studentMapper.selectById(attendance.getStudentId());
        if (student != null) {
            User user = userMapper.selectById(student.getUserId());
            if(user!=null) attendance.setStudentName(user.getName());
            attendance.setStudentNo(student.getStudentNo());

            Class clazz = classMapper.selectById(student.getClassId());
            if (clazz != null) attendance.setClassName(clazz.getClassName());
        }
        Course course = courseMapper.selectById(attendance.getCourseId());
        if (course != null) attendance.setCourseName(course.getCourseName());
    }

    /**
     * 获取考勤统计数据
     */
    public Map<String, Object> getAttendanceStatistics(Long courseId, String teacherId,LocalDate startDate, LocalDate endDate) {
        Map<String, Object> statistics = new HashMap<>();

        // 1. 出席记录数
        LambdaQueryWrapper<Attendance> presentWrapper = new LambdaQueryWrapper<>();
        presentWrapper.eq(Attendance::getStatus, "出席");
        if (courseId != null) presentWrapper.eq(Attendance::getCourseId, courseId);
        if (startDate != null) presentWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) presentWrapper.le(Attendance::getAttendanceDate, endDate);
        applyTeacherFilter(presentWrapper, teacherId);
        long presentCount = attendanceMapper.selectCount(presentWrapper);

        // 2. 缺席记录数
        LambdaQueryWrapper<Attendance> absentWrapper = new LambdaQueryWrapper<>();
        absentWrapper.eq(Attendance::getStatus, "缺席");
        if (courseId != null) absentWrapper.eq(Attendance::getCourseId, courseId);
        if (startDate != null) absentWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) absentWrapper.le(Attendance::getAttendanceDate, endDate);
        applyTeacherFilter(absentWrapper, teacherId);
        long absentCount = attendanceMapper.selectCount(absentWrapper);

        // 3. 请假记录数
        LambdaQueryWrapper<Attendance> leaveWrapper = new LambdaQueryWrapper<>();
        leaveWrapper.eq(Attendance::getStatus, "请假");
        if (courseId != null) leaveWrapper.eq(Attendance::getCourseId, courseId);
        if (startDate != null) leaveWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) leaveWrapper.le(Attendance::getAttendanceDate, endDate);
        applyTeacherFilter(leaveWrapper, teacherId);
        long leaveCount = attendanceMapper.selectCount(leaveWrapper);

        // 修复原代码中的 total 没加过滤条件的问题
        long realTotal = presentCount + absentCount + leaveCount;

        if (realTotal > 0) {
            double attendanceRate = (double) presentCount / realTotal * 100;

            statistics.put("total", realTotal);
            statistics.put("presentCount", presentCount);
            statistics.put("absentCount", absentCount);
            statistics.put("leaveCount", leaveCount);
            statistics.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0);

            // 加入 teacherId 一同过滤班级数据
            List<Map<String, Object>> classStatistics = getClassAttendanceStatistics(courseId, teacherId, startDate, endDate);
            statistics.put("classStatistics", classStatistics);

            List<Map<String, Object>> trendData = getAttendanceTrendData(courseId, teacherId, startDate, endDate);
            statistics.put("trendData", trendData);
        } else {
            statistics.put("total", 0);
            statistics.put("presentCount", 0);
            statistics.put("absentCount", 0);
            statistics.put("leaveCount", 0);
            statistics.put("attendanceRate", 0);
            statistics.put("classStatistics", new ArrayList<>());
            statistics.put("trendData", new ArrayList<>());
        }
        return statistics;
    }

    /**
     * 获取班级考勤统计数据
     */
    private List<Map<String, Object>> getClassAttendanceStatistics(Long courseId, String teacherId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> classStatistics = new ArrayList<>();

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        if (courseId != null) queryWrapper.eq(Attendance::getCourseId, courseId);
        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        applyTeacherFilter(queryWrapper, teacherId);

        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);

        Map<Long, List<Attendance>> classAttendances = attendances.stream()
                .collect(Collectors.groupingBy(attendance -> {
                    Student student = studentMapper.selectById(attendance.getStudentId());
                    return student != null ? student.getClassId() : -1L;
                }));

        for (Map.Entry<Long, List<Attendance>> entry : classAttendances.entrySet()) {
            Long classId = entry.getKey();
            if (classId == -1L) continue;

            List<Attendance> classAttendanceList = entry.getValue();
            Class clazz = classMapper.selectById(classId);
            if (clazz == null) continue;

            Map<String, Object> classData = new HashMap<>();
            classData.put("classId", classId);
            classData.put("className", clazz.getClassName());

            LambdaQueryWrapper<Student> studentWrapper = new LambdaQueryWrapper<>();
            studentWrapper.eq(Student::getClassId, classId);
            long totalStudents = studentMapper.selectCount(studentWrapper);
            classData.put("totalStudents", totalStudents);

            long presentCount = classAttendanceList.stream().filter(a -> "出席".equals(a.getStatus())).count();
            long absentCount = classAttendanceList.stream().filter(a -> "缺席".equals(a.getStatus())).count();
            long leaveCount = classAttendanceList.stream().filter(a -> "请假".equals(a.getStatus())).count();

            double attendanceRate = classAttendanceList.size() > 0
                    ? (double) presentCount / classAttendanceList.size() * 100 : 0;

            classData.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0);
            classData.put("presentCount", presentCount);
            classData.put("absentCount", absentCount);
            classData.put("leaveCount", leaveCount);
            classData.put("totalAttendances", classAttendanceList.size());

            classStatistics.add(classData);
        }
        return classStatistics;
    }

    /**
     * 获取考勤趋势数据
     */
    private List<Map<String, Object>> getAttendanceTrendData(Long courseId, String teacherId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> trendData = new ArrayList<>();

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        if (courseId != null) queryWrapper.eq(Attendance::getCourseId, courseId);
        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        applyTeacherFilter(queryWrapper, teacherId);

        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);

        Map<String, List<Attendance>> weeklyAttendances = attendances.stream()
                .collect(Collectors.groupingBy(attendance ->
                        "第" + (attendance.getAttendanceDate().get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear()) + "周")));

        for (Map.Entry<String, List<Attendance>> entry : weeklyAttendances.entrySet()) {
            Map<String, Object> weekData = new HashMap<>();
            List<Attendance> weekAttendances = entry.getValue();

            weekData.put("week", entry.getKey());

            double total = weekAttendances.size();
            double presentCount = weekAttendances.stream().filter(a -> "出席".equals(a.getStatus())).count();
            double absentCount = weekAttendances.stream().filter(a -> "缺席".equals(a.getStatus())).count();
            double leaveCount = weekAttendances.stream().filter(a -> "请假".equals(a.getStatus())).count();

            weekData.put("presentRate", total > 0 ? Math.round(presentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("absentRate", total > 0 ? Math.round(absentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("leaveRate", total > 0 ? Math.round(leaveCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("totalCount", (int)total);

            trendData.add(weekData);
        }
        return trendData;
    }

    /**
     * 获取学生个人考勤统计数据
     */
    public Map<String, Object> getStudentAttendanceStatistics(Long studentId, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> statistics = new HashMap<>();

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);

        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        long total = attendanceMapper.selectCount(queryWrapper);

        if (total > 0) {
            LambdaQueryWrapper<Attendance> presentWrapper = new LambdaQueryWrapper<>();
            presentWrapper.eq(Attendance::getStudentId, studentId).eq(Attendance::getStatus, "出席");
            if (startDate != null) presentWrapper.ge(Attendance::getAttendanceDate, startDate);
            if (endDate != null) presentWrapper.le(Attendance::getAttendanceDate, endDate);
            long presentCount = attendanceMapper.selectCount(presentWrapper);

            LambdaQueryWrapper<Attendance> absentWrapper = new LambdaQueryWrapper<>();
            absentWrapper.eq(Attendance::getStudentId, studentId).eq(Attendance::getStatus, "缺席");
            if (startDate != null) absentWrapper.ge(Attendance::getAttendanceDate, startDate);
            if (endDate != null) absentWrapper.le(Attendance::getAttendanceDate, endDate);
            long absentCount = attendanceMapper.selectCount(absentWrapper);

            LambdaQueryWrapper<Attendance> leaveWrapper = new LambdaQueryWrapper<>();
            leaveWrapper.eq(Attendance::getStudentId, studentId).eq(Attendance::getStatus, "请假");
            if (startDate != null) leaveWrapper.ge(Attendance::getAttendanceDate, startDate);
            if (endDate != null) leaveWrapper.le(Attendance::getAttendanceDate, endDate);
            long leaveCount = attendanceMapper.selectCount(leaveWrapper);

            double attendanceRate = (double) presentCount / total * 100;

            statistics.put("total", total);
            statistics.put("presentCount", presentCount);
            statistics.put("absentCount", absentCount);
            statistics.put("leaveCount", leaveCount);
            statistics.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0);

            List<Map<String, Object>> courseStatistics = getStudentCourseAttendanceStatistics(studentId, startDate, endDate);
            statistics.put("courseStatistics", courseStatistics);

            List<Map<String, Object>> trendData = getStudentAttendanceTrendData(studentId, startDate, endDate);
            statistics.put("trendData", trendData);
        } else {
            statistics.put("total", 0);
            statistics.put("presentCount", 0);
            statistics.put("absentCount", 0);
            statistics.put("leaveCount", 0);
            statistics.put("attendanceRate", 0);
            statistics.put("courseStatistics", new ArrayList<>());
            statistics.put("trendData", new ArrayList<>());
        }
        return statistics;
    }

    /**
     * 获取学生各课程考勤统计数据
     */
    private List<Map<String, Object>> getStudentCourseAttendanceStatistics(Long studentId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> courseStatistics = new ArrayList<>();

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);

        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);

        Map<Long, List<Attendance>> courseAttendances = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getCourseId));

        for (Map.Entry<Long, List<Attendance>> entry : courseAttendances.entrySet()) {
            Long courseId = entry.getKey();
            List<Attendance> courseAttendanceList = entry.getValue();
            Course course = courseMapper.selectById(courseId);
            if (course == null) continue;

            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseId", courseId);
            courseData.put("courseName", course.getCourseName());
            courseData.put("courseCode", course.getCourseCode());

            long presentCount = courseAttendanceList.stream().filter(a -> "出席".equals(a.getStatus())).count();
            long absentCount = courseAttendanceList.stream().filter(a -> "缺席".equals(a.getStatus())).count();
            long leaveCount = courseAttendanceList.stream().filter(a -> "请假".equals(a.getStatus())).count();

            double attendanceRate = courseAttendanceList.size() > 0
                    ? (double) presentCount / courseAttendanceList.size() * 100 : 0;

            courseData.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0);
            courseData.put("presentCount", presentCount);
            courseData.put("absentCount", absentCount);
            courseData.put("leaveCount", leaveCount);
            courseData.put("totalAttendances", courseAttendanceList.size());

            courseStatistics.add(courseData);
        }
        return courseStatistics;
    }

    /**
     * 获取学生考勤趋势数据
     */
    private List<Map<String, Object>> getStudentAttendanceTrendData(Long studentId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> trendData = new ArrayList<>();

        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);

        if (startDate != null) queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        if (endDate != null) queryWrapper.le(Attendance::getAttendanceDate, endDate);

        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);

        Map<String, List<Attendance>> weeklyAttendances = attendances.stream()
                .collect(Collectors.groupingBy(attendance ->
                        "第" + (attendance.getAttendanceDate().get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear()) + "周")));

        for (Map.Entry<String, List<Attendance>> entry : weeklyAttendances.entrySet()) {
            Map<String, Object> weekData = new HashMap<>();
            List<Attendance> weekAttendances = entry.getValue();

            weekData.put("week", entry.getKey());

            double total = weekAttendances.size();
            double presentCount = weekAttendances.stream().filter(a -> "出席".equals(a.getStatus())).count();
            double absentCount = weekAttendances.stream().filter(a -> "缺席".equals(a.getStatus())).count();
            double leaveCount = weekAttendances.stream().filter(a -> "请假".equals(a.getStatus())).count();

            weekData.put("presentRate", total > 0 ? Math.round(presentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("absentRate", total > 0 ? Math.round(absentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("leaveRate", total > 0 ? Math.round(leaveCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("totalCount", (int)total);

            trendData.add(weekData);
        }
        return trendData;
    }
}