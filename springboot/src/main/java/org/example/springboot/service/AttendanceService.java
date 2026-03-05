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

    /**
     * 分页查询考勤记录
     */
    public Page<Attendance> getAttendanceByPage(Long studentId, Long courseId,String teacherId, String status,
                                              LocalDate startDate, LocalDate endDate, 
                                              Integer currentPage, Integer size) {
        Page<Attendance> page = new Page<>(currentPage, size);
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (studentId != null) {
            queryWrapper.eq(Attendance::getStudentId, studentId);
        }
        if (courseId != null) {
            queryWrapper.eq(Attendance::getCourseId, courseId);
        }
        if (StringUtils.isNotBlank(status)) {
            queryWrapper.eq(Attendance::getStatus, status);
        }
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }
        if(StringUtils.isNotBlank(teacherId)){
            List<Long> allStudentIds = studentService.getAllStudents(teacherId).stream().map(Student::getId).toList();
            if(!allStudentIds.isEmpty()){
                queryWrapper.in(Attendance::getStudentId, allStudentIds);
            }else{
                return page;
            }

        }
        
        // 按日期降序排序
        queryWrapper.orderByDesc(Attendance::getAttendanceDate);
        
        Page<Attendance> resultPage = attendanceMapper.selectPage(page, queryWrapper);
        
        // 填充学生和课程信息
        for (Attendance attendance : resultPage.getRecords()) {
            fillAttendanceInfo(attendance);
        }
        
        return resultPage;
    }
    
    /**
     * 获取所有考勤状态选项
     */
    public List<String> getAttendanceStatuses() {
        return Arrays.asList("出席", "缺席", "请假");
    }
    
    /**
     * 根据ID获取考勤记录
     */
    public Attendance getAttendanceById(Long id) {
        Attendance attendance = attendanceMapper.selectById(id);
        if (attendance == null) {
            throw new ServiceException("考勤记录不存在");
        }
        fillAttendanceInfo(attendance);
        return attendance;
    }
    
    /**
     * 添加考勤记录
     */
    @Transactional
    public void addAttendance(Attendance attendance) {
        // 检查学生是否存在
        Student student = studentMapper.selectById(attendance.getStudentId());
        if (student == null) {
            throw new ServiceException("学生不存在");
        }
        
        // 检查课程是否存在
        Course course = courseMapper.selectById(attendance.getCourseId());
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查是否已存在相同学生、课程、日期的考勤记录
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, attendance.getStudentId())
                   .eq(Attendance::getCourseId, attendance.getCourseId())
                   .eq(Attendance::getAttendanceDate, attendance.getAttendanceDate());
        
        if (attendanceMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该学生在当日该课程已有考勤记录");
        }
        
        attendanceMapper.insert(attendance);
    }
    
    /**
     * 更新考勤记录
     */
    @Transactional
    public void updateAttendance(Attendance attendance) {
        // 检查考勤记录是否存在
        if (attendanceMapper.selectById(attendance.getId()) == null) {
            throw new ServiceException("考勤记录不存在");
        }
        
        // 检查学生是否存在
        if (studentMapper.selectById(attendance.getStudentId()) == null) {
            throw new ServiceException("学生不存在");
        }
        
        // 检查课程是否存在
        if (courseMapper.selectById(attendance.getCourseId()) == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查是否与其他记录冲突
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
    
    /**
     * 删除考勤记录
     */
    @Transactional
    public void deleteAttendance(Long id) {
        if (attendanceMapper.selectById(id) == null) {
            throw new ServiceException("考勤记录不存在");
        }
        attendanceMapper.deleteById(id);
    }
    
    /**
     * 批量添加考勤记录
     */
    @Transactional
    public void batchAddAttendance(List<Long> studentIds, Long courseId, LocalDate attendanceDate, String status, String remark) {
        // 检查课程是否存在
        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        
        for (Long studentId : studentIds) {
            // 检查学生是否存在
            Student student = studentMapper.selectById(studentId);
            if (student == null) {
                continue; // 跳过不存在的学生
            }
            
            // 检查是否已存在相同学生、课程、日期的考勤记录
            LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Attendance::getStudentId, studentId)
                       .eq(Attendance::getCourseId, courseId)
                       .eq(Attendance::getAttendanceDate, attendanceDate);
            
            if (attendanceMapper.selectCount(queryWrapper) > 0) {
                continue; // 跳过已有记录的学生
            }
            
            // 创建新的考勤记录
            Attendance attendance = new Attendance();
            attendance.setStudentId(studentId);
            attendance.setCourseId(courseId);
            attendance.setAttendanceDate(attendanceDate);
            attendance.setStatus(status);
            attendance.setRemark(remark);
            
            attendanceMapper.insert(attendance);
        }
    }
    
    /**
     * 填充考勤记录的学生和课程信息
     */
    private void fillAttendanceInfo(Attendance attendance) {
        if (attendance == null) {
            return;
        }
        
        // 填充学生信息
        Student student = studentMapper.selectById(attendance.getStudentId());
        if (student != null) {
            User user = userMapper.selectById(student.getUserId());
            if(user!=null){
                attendance.setStudentName(user.getName());
            }

            attendance.setStudentNo(student.getStudentNo());
            
            // 填充班级信息
            Class clazz = classMapper.selectById(student.getClassId());
            if (clazz != null) {
                attendance.setClassName(clazz.getClassName());
            }
        }
        
        // 填充课程信息
        Course course = courseMapper.selectById(attendance.getCourseId());
        if (course != null) {
            attendance.setCourseName(course.getCourseName());
        }
    }
    
    /**
     * 获取考勤统计数据
     */
    public Map<String, Object> getAttendanceStatistics(Long courseId, String teacherId,LocalDate startDate, LocalDate endDate) {
        Map<String, Object> statistics = new HashMap<>();
        
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<Attendance>();
        
        // 获取总记录数
        long total = attendanceMapper.selectCount(queryWrapper);
        
        if (total > 0) {
            // 出席记录数
            LambdaQueryWrapper<Attendance> presentWrapper = new LambdaQueryWrapper<Attendance>();
            presentWrapper.eq(Attendance::getStatus, "出席");
            // 添加原始查询条件
            if (courseId != null) {
                presentWrapper.eq(Attendance::getCourseId, courseId);
            }
            if (startDate != null) {
                presentWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                presentWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            if (StringUtils.isNotBlank(teacherId)) {
                List<Long> courseIds = teacherCourseMapper.selectList(
                    new LambdaQueryWrapper<TeacherCourse>()
                        .eq(TeacherCourse::getTeacherId, teacherId)
                ).stream().map(TeacherCourse::getCourseId).toList();
                
                if (!courseIds.isEmpty()) {
                    presentWrapper.in(Attendance::getCourseId, courseIds);
                }
            }
            long presentCount = attendanceMapper.selectCount(presentWrapper);
            
            // 缺席记录数
            LambdaQueryWrapper<Attendance> absentWrapper = new LambdaQueryWrapper<Attendance>();
            absentWrapper.eq(Attendance::getStatus, "缺席");
            // 添加原始查询条件
            if (courseId != null) {
                absentWrapper.eq(Attendance::getCourseId, courseId);
            }
            if (startDate != null) {
                absentWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                absentWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            if (StringUtils.isNotBlank(teacherId)) {
                List<Long> courseIds = teacherCourseMapper.selectList(
                    new LambdaQueryWrapper<TeacherCourse>()
                        .eq(TeacherCourse::getTeacherId, teacherId)
                ).stream().map(TeacherCourse::getCourseId).toList();
                
                if (!courseIds.isEmpty()) {
                    absentWrapper.in(Attendance::getCourseId, courseIds);
                }
            }
            long absentCount = attendanceMapper.selectCount(absentWrapper);
            
            // 请假记录数
            LambdaQueryWrapper<Attendance> leaveWrapper = new LambdaQueryWrapper<Attendance>();
            leaveWrapper.eq(Attendance::getStatus, "请假");
            // 添加原始查询条件
            if (courseId != null) {
                leaveWrapper.eq(Attendance::getCourseId, courseId);
            }
            if (startDate != null) {
                leaveWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                leaveWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            if (StringUtils.isNotBlank(teacherId)) {
                List<Long> courseIds = teacherCourseMapper.selectList(
                    new LambdaQueryWrapper<TeacherCourse>()
                        .eq(TeacherCourse::getTeacherId, teacherId)
                ).stream().map(TeacherCourse::getCourseId).toList();
                
                if (!courseIds.isEmpty()) {
                    leaveWrapper.in(Attendance::getCourseId, courseIds);
                }
            }
            long leaveCount = attendanceMapper.selectCount(leaveWrapper);
            
            // 计算出勤率
            double attendanceRate = (double) presentCount / total * 100;
            
            // 基本统计数据
            statistics.put("total", total);
            statistics.put("presentCount", presentCount);
            statistics.put("absentCount", absentCount);
            statistics.put("leaveCount", leaveCount);
            statistics.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0); // 保留两位小数
            
            // 获取班级统计数据
            List<Map<String, Object>> classStatistics = getClassAttendanceStatistics(courseId, startDate, endDate);
            statistics.put("classStatistics", classStatistics);
            
            // 获取趋势数据（按周统计）
            List<Map<String, Object>> trendData = getAttendanceTrendData(courseId, teacherId, startDate, endDate);
            statistics.put("trendData", trendData);
        } else {
            // 没有记录，返回默认值
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
    private List<Map<String, Object>> getClassAttendanceStatistics(Long courseId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> classStatistics = new ArrayList<>();
        
        // 构建查询条件
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        if (courseId != null) {
            queryWrapper.eq(Attendance::getCourseId, courseId);
        }
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }

        // 查询所有相关班级的考勤记录
        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);
        
        // 按班级分组统计
        Map<Long, List<Attendance>> classAttendances = attendances.stream()
            .collect(Collectors.groupingBy(attendance -> {
                Student student = studentMapper.selectById(attendance.getStudentId());
                return student != null ? student.getClassId() : -1L;
            }));

        // 处理每个班级的统计数据
        for (Map.Entry<Long, List<Attendance>> entry : classAttendances.entrySet()) {
            Long classId = entry.getKey();
            if (classId == -1L) continue;

            List<Attendance> classAttendanceList = entry.getValue();
            Class clazz = classMapper.selectById(classId);
            if (clazz == null) continue;

            Map<String, Object> classData = new HashMap<>();
            classData.put("classId", classId);
            classData.put("className", clazz.getClassName());

            // 统计班级学生总数
            LambdaQueryWrapper<Student> studentWrapper = new LambdaQueryWrapper<>();
            studentWrapper.eq(Student::getClassId, classId);
            long totalStudents = studentMapper.selectCount(studentWrapper);
            classData.put("totalStudents", totalStudents);

            // 统计考勤数据
            long presentCount = classAttendanceList.stream()
                .filter(a -> "出席".equals(a.getStatus()))
                .count();
            long absentCount = classAttendanceList.stream()
                .filter(a -> "缺席".equals(a.getStatus()))
                .count();
            long leaveCount = classAttendanceList.stream()
                .filter(a -> "请假".equals(a.getStatus()))
                .count();

            // 修改出勤率计算逻辑
            double attendanceRate = classAttendanceList.size() > 0 
                ? (double) presentCount / classAttendanceList.size() * 100 
                : 0;

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
        
        // 构建查询条件
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        if (courseId != null) {
            queryWrapper.eq(Attendance::getCourseId, courseId);
        }
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }
        
        // 如果是教师，只查询其教授的课程
        if (StringUtils.isNotBlank(teacherId)) {
            List<Long> courseIds = teacherCourseMapper.selectList(
                new LambdaQueryWrapper<TeacherCourse>()
                    .eq(TeacherCourse::getTeacherId, teacherId)
            ).stream().map(TeacherCourse::getCourseId).toList();
            
            if (!courseIds.isEmpty()) {
                queryWrapper.in(Attendance::getCourseId, courseIds);
            } else {
                return trendData;
            }
        }

        // 查询考勤记录
        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);
        
        // 按周分组统计
        Map<String, List<Attendance>> weeklyAttendances = attendances.stream()
            .collect(Collectors.groupingBy(attendance -> 
                "第" + (attendance.getAttendanceDate().get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear()) + "周")));

        // 处理每周的统计数据
        for (Map.Entry<String, List<Attendance>> entry : weeklyAttendances.entrySet()) {
            Map<String, Object> weekData = new HashMap<>();
            List<Attendance> weekAttendances = entry.getValue();
            
            weekData.put("week", entry.getKey());
            
            // 计算各状态的比率
            double total = weekAttendances.size();
            double presentCount = weekAttendances.stream()
                .filter(a -> "出席".equals(a.getStatus()))
                .count();
            double absentCount = weekAttendances.stream()
                .filter(a -> "缺席".equals(a.getStatus()))
                .count();
            double leaveCount = weekAttendances.stream()
                .filter(a -> "请假".equals(a.getStatus()))
                .count();

            // 修改比率计算，确保总和为100%
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
        
        // 构建查询条件
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);
        
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }
        
        // 获取总记录数
        long total = attendanceMapper.selectCount(queryWrapper);
        
        if (total > 0) {
            // 出席记录数
            LambdaQueryWrapper<Attendance> presentWrapper = new LambdaQueryWrapper<>();
            presentWrapper.eq(Attendance::getStudentId, studentId)
                         .eq(Attendance::getStatus, "出席");
            if (startDate != null) {
                presentWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                presentWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            long presentCount = attendanceMapper.selectCount(presentWrapper);
            
            // 缺席记录数
            LambdaQueryWrapper<Attendance> absentWrapper = new LambdaQueryWrapper<>();
            absentWrapper.eq(Attendance::getStudentId, studentId)
                        .eq(Attendance::getStatus, "缺席");
            if (startDate != null) {
                absentWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                absentWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            long absentCount = attendanceMapper.selectCount(absentWrapper);
            
            // 请假记录数
            LambdaQueryWrapper<Attendance> leaveWrapper = new LambdaQueryWrapper<>();
            leaveWrapper.eq(Attendance::getStudentId, studentId)
                       .eq(Attendance::getStatus, "请假");
            if (startDate != null) {
                leaveWrapper.ge(Attendance::getAttendanceDate, startDate);
            }
            if (endDate != null) {
                leaveWrapper.le(Attendance::getAttendanceDate, endDate);
            }
            long leaveCount = attendanceMapper.selectCount(leaveWrapper);
            
            // 计算出勤率
            double attendanceRate = (double) presentCount / total * 100;
            
            // 基本统计数据
            statistics.put("total", total);
            statistics.put("presentCount", presentCount);
            statistics.put("absentCount", absentCount);
            statistics.put("leaveCount", leaveCount);
            statistics.put("attendanceRate", Math.round(attendanceRate * 100) / 100.0); // 保留两位小数
            
            // 获取课程统计数据
            List<Map<String, Object>> courseStatistics = getStudentCourseAttendanceStatistics(studentId, startDate, endDate);
            statistics.put("courseStatistics", courseStatistics);
            
            // 获取趋势数据（按周统计）
            List<Map<String, Object>> trendData = getStudentAttendanceTrendData(studentId, startDate, endDate);
            statistics.put("trendData", trendData);
        } else {
            // 没有记录，返回默认值
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
        
        // 构建查询条件
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);
        
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }

        // 查询所有相关考勤记录
        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);
        
        // 按课程分组统计
        Map<Long, List<Attendance>> courseAttendances = attendances.stream()
            .collect(Collectors.groupingBy(Attendance::getCourseId));

        // 处理每个课程的统计数据
        for (Map.Entry<Long, List<Attendance>> entry : courseAttendances.entrySet()) {
            Long courseId = entry.getKey();
            List<Attendance> courseAttendanceList = entry.getValue();
            Course course = courseMapper.selectById(courseId);
            if (course == null) continue;

            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseId", courseId);
            courseData.put("courseName", course.getCourseName());
            courseData.put("courseCode", course.getCourseCode());

            // 统计考勤数据
            long presentCount = courseAttendanceList.stream()
                .filter(a -> "出席".equals(a.getStatus()))
                .count();
            long absentCount = courseAttendanceList.stream()
                .filter(a -> "缺席".equals(a.getStatus()))
                .count();
            long leaveCount = courseAttendanceList.stream()
                .filter(a -> "请假".equals(a.getStatus()))
                .count();

            // 计算出勤率
            double attendanceRate = courseAttendanceList.size() > 0 
                ? (double) presentCount / courseAttendanceList.size() * 100 
                : 0;

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
        
        // 构建查询条件
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attendance::getStudentId, studentId);
        
        if (startDate != null) {
            queryWrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Attendance::getAttendanceDate, endDate);
        }

        // 查询考勤记录
        List<Attendance> attendances = attendanceMapper.selectList(queryWrapper);
        
        // 按周分组统计
        Map<String, List<Attendance>> weeklyAttendances = attendances.stream()
            .collect(Collectors.groupingBy(attendance -> 
                "第" + (attendance.getAttendanceDate().get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear()) + "周")));

        // 处理每周的统计数据
        for (Map.Entry<String, List<Attendance>> entry : weeklyAttendances.entrySet()) {
            Map<String, Object> weekData = new HashMap<>();
            List<Attendance> weekAttendances = entry.getValue();
            
            weekData.put("week", entry.getKey());
            
            // 计算各状态的比率
            double total = weekAttendances.size();
            double presentCount = weekAttendances.stream()
                .filter(a -> "出席".equals(a.getStatus()))
                .count();
            double absentCount = weekAttendances.stream()
                .filter(a -> "缺席".equals(a.getStatus()))
                .count();
            double leaveCount = weekAttendances.stream()
                .filter(a -> "请假".equals(a.getStatus()))
                .count();

            // 计算比率
            weekData.put("presentRate", total > 0 ? Math.round(presentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("absentRate", total > 0 ? Math.round(absentCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("leaveRate", total > 0 ? Math.round(leaveCount / total * 100 * 100) / 100.0 : 0);
            weekData.put("totalCount", (int)total);
            
            trendData.add(weekData);
        }
        
        return trendData;
    }
} 