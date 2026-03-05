package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
import org.example.springboot.entity.Class;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ScoreService {
    @Resource
    private ScoreMapper scoreMapper;
    
    @Resource
    private StudentMapper studentMapper;
    
    @Resource
    private CourseMapper courseMapper;
    
    @Resource
    private TeacherMapper teacherMapper;
    
    @Resource
    private StudentCourseMapper studentCourseMapper;
    
    @Resource
    private ClassMapper classMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询成绩列表
     */
    public Page<Score> getScoresByPage(Page<Score> page, Score score) {
        // 构建查询条件
        LambdaQueryWrapper<Score> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(score.getStudentId() != null, Score::getStudentId, score.getStudentId())
                .eq(score.getCourseId() != null, Score::getCourseId, score.getCourseId())
                .eq(score.getTeacherId() != null, Score::getTeacherId, score.getTeacherId())
                .like(StringUtils.isNotBlank(score.getSemester()), Score::getSemester, score.getSemester())
                .orderByDesc(Score::getCreateTime);
        
        // 执行分页查询
        Page<Score> scorePage = scoreMapper.selectPage(page, wrapper);
        List<Score> records = scorePage.getRecords();
        
        // 填充关联信息
        for (Score s : records) {
            // 获取学生信息
            Student student = studentMapper.selectById(s.getStudentId());
            if (student != null) {
                User user = userMapper.selectById(student.getUserId());
                if(user != null) {
                    s.setStudentName(user.getName());
                }

                s.setStudentNo(student.getStudentNo());
                if(student.getClassId()!=null){
                    s.setClassName(classMapper.selectById(student.getClassId()).getClassName());
                }

            }
            
            // 获取课程信息
            Course course = courseMapper.selectById(s.getCourseId());
            if (course != null) {
                s.setCourseName(course.getCourseName());
                s.setCourseCode(course.getCourseCode());
                s.setCredit(course.getCredit());
                s.setCourseType(course.getCourseType());
            }
            
            // 获取教师信息
            Teacher teacher = teacherMapper.selectById(s.getTeacherId());
            if (teacher != null) {
                User user = userMapper.selectById(teacher.getUserId());
                if(user != null) {
                    s.setTeacherName(user.getName());
                }
            }
        }
        
        return scorePage;
    }
    
    /**
     * 根据ID获取成绩详情
     */
    public Score getScoreById(Long id) {
        Score score = scoreMapper.selectById(id);
        if (score == null) {
            throw new ServiceException("成绩记录不存在");
        }
        
        // 填充关联信息
        fillScoreRelatedInfo(score);
        
        return score;
    }
    
    /**
     * 添加成绩记录
     */
    @Transactional
    public void addScore(Score score) {
        // 检查学生是否存在
        Student student = studentMapper.selectById(score.getStudentId());
        if (student == null) {
            throw new ServiceException("学生不存在");
        }
        
        // 检查课程是否存在
        Course course = courseMapper.selectById(score.getCourseId());
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        
        // 检查教师是否存在
        Teacher teacher = teacherMapper.selectById(score.getTeacherId());
        if (teacher == null) {
            throw new ServiceException("教师不存在");
        }
        
        // 检查学生是否选修了该课程
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getStudentId, score.getStudentId())
                   .eq(StudentCourse::getCourseId, score.getCourseId())
                   .eq(StudentCourse::getSemester, score.getSemester());
        if (studentCourseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该学生未选修此课程");
        }
        
        // 检查是否已存在成绩记录
        LambdaQueryWrapper<Score> scoreWrapper = new LambdaQueryWrapper<>();
        scoreWrapper.eq(Score::getStudentId, score.getStudentId())
                   .eq(Score::getCourseId, score.getCourseId())
                   .eq(Score::getSemester, score.getSemester());
        if (scoreMapper.selectCount(scoreWrapper) > 0) {
            throw new ServiceException("该学生在此学期已有该课程的成绩记录");
        }
        
        // 设置等级
        if (score.getScore() != null && score.getGrade() == null) {
            score.setGrade(calculateGrade(score.getScore()));
        }
        
        // 插入成绩记录
        scoreMapper.insert(score);
    }
    
    /**
     * 更新成绩记录
     */
    @Transactional
    public void updateScore(Score score) {
        // 检查成绩记录是否存在
        Score existingScore = scoreMapper.selectById(score.getId());
        if (existingScore == null) {
            throw new ServiceException("成绩记录不存在");
        }
        
        // 设置等级
        if (score.getScore() != null && !score.getScore().equals(existingScore.getScore())) {
            score.setGrade(calculateGrade(score.getScore()));
        }
        
        // 更新成绩记录
        scoreMapper.updateById(score);
    }
    
    /**
     * 删除成绩记录
     */
    @Transactional
    public void deleteScore(Long id) {
        // 检查成绩记录是否存在
        if (scoreMapper.selectById(id) == null) {
            throw new ServiceException("成绩记录不存在");
        }
        
        // 删除成绩记录
        scoreMapper.deleteById(id);
    }
    
    /**
     * 获取所有学期列表
     */
    public List<String> getAllSemesters() {
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Score::getSemester).groupBy(Score::getSemester);
        
        return scoreMapper.selectList(queryWrapper).stream()
                .map(Score::getSemester)
                .distinct()
                .toList();
    }
    
    /**
     * 填充成绩关联信息
     */
    private void fillScoreRelatedInfo(Score score) {
        // 填充学生信息
        Student student = studentMapper.selectById(score.getStudentId());
        if (student != null) {
            User user = userMapper.selectById(student.getUserId());
            if(user != null) {
                score.setStudentName(user.getName());
            }

            score.setStudentNo(student.getStudentNo());
            
            // 获取班级信息并设置班级名称
            Class clazz = classMapper.selectById(student.getClassId());
            if (clazz != null) {
                score.setClassName(clazz.getClassName());
            }
        }
        
        // 填充课程信息
        Course course = courseMapper.selectById(score.getCourseId());
        if (course != null) {
            score.setCourseName(course.getCourseName());
        }
        
        // 填充教师信息
        Teacher teacher = teacherMapper.selectById(score.getTeacherId());
        if (teacher != null) {
            User user = userMapper.selectById(teacher.getUserId());
            if(user != null) {
                score.setStudentName(user.getName());
            }

        }
    }
    
    /**
     * 根据分数计算等级
     */
    private String calculateGrade(BigDecimal score) {
        if (score == null) {
            return null;
        }
        
        double value = score.doubleValue();
        
        if (value >= 90) {
            return "A";
        } else if (value >= 80) {
            return "B";
        } else if (value >= 70) {
            return "C";
        } else if (value >= 60) {
            return "D";
        } else {
            return "E";
        }
    }

    /**
     * 获取学生成绩统计信息
     */
    public Map<String, Object> getStudentStatistics(Long studentId) {
        // 查询学生所有成绩
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Score::getStudentId, studentId);
        List<Score> scores = scoreMapper.selectList(queryWrapper);

        // 统计结果
        Map<String, Object> statistics = new HashMap<>();
        
        if (scores.isEmpty()) {
            statistics.put("averageScore", null);
            statistics.put("averageGPA", null);
            statistics.put("totalCredits", 0);
            statistics.put("totalCourses", 0);
            return statistics;
        }

        // 计算总分和总学分
        BigDecimal totalScore = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal totalGPA = BigDecimal.ZERO;
        int passedCourses = 0;

        for (Score score : scores) {
            // 获取课程信息
            Course course = courseMapper.selectById(score.getCourseId());
            if (course != null && score.getScore() != null) {
                BigDecimal credit =course.getCredit();
                totalScore = totalScore.add(score.getScore().multiply(credit));
                totalCredits = totalCredits.add(credit);
                
                // 计算GPA
                double gpa = calculateGPA(score.getScore().doubleValue());
                totalGPA = totalGPA.add(BigDecimal.valueOf(gpa).multiply(credit));
                
                // 统计及格课程数
                if (score.getScore().doubleValue() >= 60) {
                    passedCourses++;
                }
            }
        }

        // 计算平均分
        BigDecimal averageScore = totalCredits.compareTo(BigDecimal.ZERO) > 0 
            ? totalScore.divide(totalCredits, 2, RoundingMode.HALF_UP) 
            : null;

        // 计算平均学分绩点
        BigDecimal averageGPA = totalCredits.compareTo(BigDecimal.ZERO) > 0 
            ? totalGPA.divide(totalCredits, 2, RoundingMode.HALF_UP)
            : null;

        // 设置统计结果
        statistics.put("averageScore", averageScore);
        statistics.put("averageGPA", averageGPA);
        statistics.put("totalCredits", totalCredits);
        statistics.put("totalCourses", scores.size());
        statistics.put("passedCourses", passedCourses);
        statistics.put("failedCourses", scores.size() - passedCourses);

        return statistics;
    }

    /**
     * 根据分数计算GPA
     */
    private double calculateGPA(double score) {
        if (score >= 90) {
            return 4.0;
        } else if (score >= 85) {
            return 3.7;
        } else if (score >= 80) {
            return 3.3;
        } else if (score >= 75) {
            return 3.0;
        } else if (score >= 70) {
            return 2.7;
        } else if (score >= 65) {
            return 2.3;
        } else if (score >= 60) {
            return 2.0;
        } else {
            return 0.0;
        }
    }
} 