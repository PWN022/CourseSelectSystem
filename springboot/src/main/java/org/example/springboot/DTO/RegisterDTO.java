package org.example.springboot.DTO;

import lombok.Data;
import org.example.springboot.entity.User;
import org.example.springboot.entity.Student;
import org.example.springboot.entity.Teacher;

@Data
public class RegisterDTO {
    private User user;
    private Student student;
    private Teacher teacher;
} 