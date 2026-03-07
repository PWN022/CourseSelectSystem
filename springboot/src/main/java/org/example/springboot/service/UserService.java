package org.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;

import org.example.springboot.entity.User;
import org.example.springboot.entity.Student;
import org.example.springboot.entity.Teacher;
import org.example.springboot.DTO.UserPasswordUpdateDTO;
import org.example.springboot.DTO.RegisterDTO;
import org.example.springboot.enumClass.AccountStatus;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.mapper.StudentMapper;
import org.example.springboot.mapper.TeacherMapper;
import org.example.springboot.mapper.ClassMapper;
import org.example.springboot.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private ClassMapper classMapper;

    @Value("${user.defaultPassword}")
    private String DEFAULT_PWD;

    @Resource
    private PasswordEncoder bCryptPasswordEncoder;

    public User getByEmail(String email) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null) {
            throw new ServiceException("邮箱不存在");
        }
        return user;
    }

    public User login(User user) {
        User dbUser = getByUsername(user.getUsername());
        // 用户存在性检查已经在 getByUsername 中处理
        if (dbUser.getStatus().equals(AccountStatus.PENDING_REVIEW.getValue())) {
            throw new ServiceException("账号正在审核");
        }
        if (dbUser.getStatus().equals(AccountStatus.REVIEW_FAILED.getValue())) {
            throw new ServiceException("账号审核不通过，请修改个人信息");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        

        String token = JwtTokenUtils.genToken(String.valueOf(dbUser.getId()), dbUser.getPassword());
        

        dbUser.setToken(token);
        return dbUser;
    }

    public List<User> getUserByRole(String roleCode) {
        List<User> users = userMapper.selectList(
            new LambdaQueryWrapper<User>()
                .eq(User::getRoleCode, roleCode)
        );
        if (users.isEmpty()) {
            throw new ServiceException("未找到该角色的用户");
        }
        return users;
    }

    public void updateUser(Long id, User user) {
        // 检查用户是否存在
        if (userMapper.selectById(id) == null) {
            throw new ServiceException("要更新的用户不存在");
        }
        
        // 检查新用户名是否与其他用户重复
        if (user.getUsername() != null) {
            User existUser = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, user.getUsername())
            );
            if (existUser != null && !existUser.getId().equals(id)) {
                throw new ServiceException("新用户名已被使用");
            }
        }
        
        user.setId(id);
        if (userMapper.updateById(user) <= 0) {
            throw new ServiceException("用户更新失败");
        }
    }

    public User getByUsername(String username) {
        User user = userMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return user;
    }

    /**
     * 删除用户（检查学生和教师关联）
     * @param id 用户ID
     */
    @Transactional
    public void deleteUserById(Long id) {
        // 检查用户是否存在
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 检查是否有关联的学生信息
        LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.eq(Student::getUserId, id);
        if (studentMapper.exists(studentQueryWrapper)) {
            throw new ServiceException("该用户关联了学生信息，无法删除");
        }

        // 检查是否有关联的教师信息
        LambdaQueryWrapper<Teacher> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.eq(Teacher::getUserId, id);
        if (teacherMapper.exists(teacherQueryWrapper)) {
            throw new ServiceException("该用户关联了教师信息，无法删除");
        }

        // 删除用户
        if (userMapper.deleteById(id) <= 0) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 批量删除用户（检查学生和教师关联）
     * @param ids 用户ID列表
     */
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("未选择要删除的用户");
        }

        // 检查是否有关联的学生信息
        LambdaQueryWrapper<Student> studentQueryWrapper = new LambdaQueryWrapper<>();
        studentQueryWrapper.in(Student::getUserId, ids);
        if (studentMapper.exists(studentQueryWrapper)) {
            throw new ServiceException("选中的用户中包含已关联学生信息的用户，无法删除");
        }

        // 检查是否有关联的教师信息
        LambdaQueryWrapper<Teacher> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.in(Teacher::getUserId, ids);
        if (teacherMapper.exists(teacherQueryWrapper)) {
            throw new ServiceException("选中的用户中包含已关联教师信息的用户，无法删除");
        }

        // 批量删除用户
        if (userMapper.deleteByIds(ids) <= 0) {
            throw new ServiceException("批量删除失败");
        }
    }

    public List<User> getUserList() {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<>());
        if (users.isEmpty()) {
            throw new ServiceException("未找到任何用户");
        }
        return users;
    }

    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return user;
    }

    public Page<User> getUsersByPage(String username,  String name, String roleCode, Integer currentPage, Integer size) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(User::getUsername, username);
        }

        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(roleCode)) {
            queryWrapper.eq(User::getRoleCode, roleCode);
        }
        
        return userMapper.selectPage(new Page<>(currentPage, size), queryWrapper);
    }

    public void updatePassword(Long id, UserPasswordUpdateDTO update) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        
        // 验证旧密码
        if (!bCryptPasswordEncoder.matches(update.getOldPassword(), user.getPassword())) {
            throw new ServiceException("原密码错误");
        }
        
        // 更新新密码
        user.setPassword(bCryptPasswordEncoder.encode(update.getNewPassword()));
        if (userMapper.updateById(user) <= 0) {
            throw new ServiceException("密码修改失败");
        }
    }

    public void forgetPassword(String email, String newPassword) {
        User user = userMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
        );
        if (user == null) {
            throw new ServiceException("邮箱不存在");
        }
        
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        if (userMapper.updateById(user) <= 0) {
            throw new ServiceException("密码重置失败");
        }
    }

    /**
     * 注册用户（支持学生/教师）
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO registerDTO) {
        if (registerDTO == null || registerDTO.getUser() == null) {
            throw new ServiceException("注册信息不能为空");
        }

        User user = registerDTO.getUser();

        // 验证必填字段
        if (StringUtils.isBlank(user.getUsername())) {
            throw new ServiceException("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            throw new ServiceException("邮箱不能为空");
        }
        if (StringUtils.isBlank(user.getRoleCode())) {
            throw new ServiceException("角色不能为空");
        }

        // 检查用户名和邮箱唯一性
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername())) != null) {
            throw new ServiceException("用户名已存在");
        }
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, user.getEmail())) != null) {
            throw new ServiceException("邮箱已被使用");
        }

        // 设置密码和默认状态
        user.setPassword(StringUtils.isNotBlank(user.getPassword()) ? user.getPassword() : DEFAULT_PWD);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // 设置默认状态为正常
        if (user.getStatus() == null) {
            user.setStatus(AccountStatus.NORMAL.getValue());
        }

        // 插入用户信息
        if (userMapper.insert(user) <= 0) {
            throw new ServiceException("用户创建失败");
        }

        // 插入学生或教师信息
        if ("STUDENT".equals(user.getRoleCode())) {
            Student student = registerDTO.getStudent();
            if (student == null) {
                throw new ServiceException("学生信息不能为空");
            }

            // 验证学生必填字段
            if (StringUtils.isBlank(student.getStudentNo())) {
                throw new ServiceException("学号不能为空");
            }
            if (StringUtils.isBlank(student.getGender())) {
                throw new ServiceException("性别不能为空");
            }
            if (student.getClassId() == null) {
                throw new ServiceException("班级不能为空");
            }

            // 检查学号是否已存在
            if (studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentNo, student.getStudentNo())) != null) {
                throw new ServiceException("学号已存在");
            }

            // 检查身份证是否存在
            if (StringUtils.isNotBlank(student.getIdCard())) {
                if (studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getIdCard, student.getIdCard())) != null) {
                    throw new ServiceException("该身份证号已被注册");
                }
            }

            // 检查班级是否存在
            if (classMapper.selectById(student.getClassId()) == null) {
                throw new ServiceException("班级不存在");
            }

            student.setUserId(user.getId());
            if (studentMapper.insert(student) <= 0) {
                throw new ServiceException("学生信息创建失败");
            }
        } else if ("TEACHER".equals(user.getRoleCode())) {
            Teacher teacher = registerDTO.getTeacher();
            if (teacher == null) {
                throw new ServiceException("教师信息不能为空");
            }

            // 验证教师必填字段
            if (StringUtils.isBlank(teacher.getTeacherNo())) {
                throw new ServiceException("教师编号不能为空");
            }
            if (StringUtils.isBlank(teacher.getGender())) {
                throw new ServiceException("性别不能为空");
            }

            // 检查教师编号是否已存在
            if (teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getTeacherNo, teacher.getTeacherNo())) != null) {
                throw new ServiceException("教师编号已存在");
            }

            teacher.setUserId(user.getId());
            if (teacherMapper.insert(teacher) <= 0) {
                throw new ServiceException("教师信息创建失败");
            }
        } else {
            throw new ServiceException("不支持的用户角色: " + user.getRoleCode());
        }
    }
}
