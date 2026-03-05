/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : campus_management_platform

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 07/10/2025 10:15:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '考勤ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `attendance_date` date NOT NULL COMMENT '考勤日期',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '考勤状态(出席/缺席/请假)',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_date`(`student_id` ASC, `course_id` ASC, `attendance_date` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_attendance_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_attendance_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '考勤信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (1, 1, 1, '2023-09-15', '出席', NULL, '2023-09-15 08:00:00');
INSERT INTO `attendance` VALUES (2, 1, 1, '2023-09-22', '出席', NULL, '2023-09-22 08:00:00');
INSERT INTO `attendance` VALUES (3, 1, 1, '2023-09-29', '请假', '生病请假', '2023-09-29 08:00:00');
INSERT INTO `attendance` VALUES (4, 2, 1, '2023-09-15', '出席', NULL, '2023-09-15 08:00:00');
INSERT INTO `attendance` VALUES (5, 2, 1, '2023-09-22', '缺席', '无故缺席', '2023-09-22 08:00:00');
INSERT INTO `attendance` VALUES (6, 2, 1, '2023-09-29', '出席', NULL, '2023-09-29 08:00:00');
INSERT INTO `attendance` VALUES (7, 3, 2, '2023-09-18', '出席', NULL, '2023-09-18 10:00:00');
INSERT INTO `attendance` VALUES (8, 3, 2, '2023-09-25', '出席', NULL, '2023-10-02 10:00:00');
INSERT INTO `attendance` VALUES (9, 3, 2, '2023-10-02', '出席', NULL, '2023-10-02 10:00:00');
INSERT INTO `attendance` VALUES (10, 4, 1, '2023-09-15', '出席', NULL, '2023-09-15 08:00:00');
INSERT INTO `attendance` VALUES (11, 4, 1, '2023-09-22', '出席', NULL, '2023-09-22 08:00:00');
INSERT INTO `attendance` VALUES (12, 4, 1, '2023-09-29', '缺席', '无故缺席', '2023-09-29 08:00:00');
INSERT INTO `attendance` VALUES (13, 4, 3, '2023-09-20', '出席', NULL, '2023-09-20 10:00:00');
INSERT INTO `attendance` VALUES (14, 4, 3, '2023-09-27', '出席', NULL, '2023-09-27 10:00:00');
INSERT INTO `attendance` VALUES (15, 4, 3, '2023-10-04', '出席', NULL, '2023-10-04 10:00:00');
INSERT INTO `attendance` VALUES (16, 5, 2, '2023-09-19', '出席', NULL, '2023-09-19 08:00:00');
INSERT INTO `attendance` VALUES (17, 5, 2, '2023-09-26', '请假', '生病请假', '2023-09-26 08:00:00');
INSERT INTO `attendance` VALUES (18, 5, 2, '2023-10-03', '出席', NULL, '2023-10-03 08:00:00');
INSERT INTO `attendance` VALUES (19, 5, 4, '2023-09-21', '出席', NULL, '2023-09-21 14:00:00');
INSERT INTO `attendance` VALUES (20, 5, 4, '2023-09-28', '出席', NULL, '2023-09-28 14:00:00');
INSERT INTO `attendance` VALUES (21, 5, 4, '2023-10-05', '缺席', '无故缺席', '2023-10-05 14:00:00');
INSERT INTO `attendance` VALUES (22, 9, 1, '2023-02-20', '出席', NULL, '2023-02-20 08:00:00');
INSERT INTO `attendance` VALUES (23, 9, 1, '2023-02-27', '出席', NULL, '2023-02-27 08:00:00');
INSERT INTO `attendance` VALUES (24, 9, 1, '2023-03-06', '出席', NULL, '2023-03-06 08:00:00');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级名称',
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '年级',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业',
  `head_teacher_id` bigint NULL DEFAULT NULL COMMENT '班主任ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_head_teacher`(`head_teacher_id` ASC) USING BTREE,
  CONSTRAINT `fk_class_teacher` FOREIGN KEY (`head_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班级信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '计算机科学2023级1班', '2023级', '计算机科学与技术', 1, '2023-09-01 11:00:00', '2023-09-01 11:00:00');
INSERT INTO `class` VALUES (2, '软件工程2023级1班', '2023级', '软件工程', 2, '2023-09-01 11:10:00', '2023-09-01 11:10:00');
INSERT INTO `class` VALUES (3, '数据科学2023级1班', '2023级', '数据科学与大数据技术', 3, '2023-09-01 11:20:00', '2023-09-01 11:20:00');
INSERT INTO `class` VALUES (4, '人工智能2023级1班', '2023级', '人工智能', 4, '2023-09-01 11:30:00', '2023-09-01 11:30:00');
INSERT INTO `class` VALUES (5, '计算机科学2022级1班', '2022级', '计算机科学与技术', 1, '2022-09-01 11:00:00', '2022-09-01 11:00:00');
INSERT INTO `class` VALUES (6, '软件工程2022级1班', '2022级', '软件工程', 2, '2022-09-01 11:10:00', '2022-09-01 11:10:00');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程代码',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `credit` decimal(5, 2) NOT NULL COMMENT '学分',
  `hours` int NOT NULL COMMENT '课时',
  `course_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '课程描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_course_code`(`course_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1533 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'CS101', '计算机导论', 3.00, 48, '必修课', '计算机科学的基础入门课程', '2023-09-01 13:00:00', '2023-09-01 13:00:00');
INSERT INTO `course` VALUES (2, 'CS102', '程序设计基础(Java)', 4.00, 64, '必修课', 'Java语言程序设计基础', '2023-09-01 13:10:00', '2023-09-01 13:10:00');
INSERT INTO `course` VALUES (3, 'CS201', '数据结构', 4.00, 64, '必修课', '计算机数据结构与算法基础', '2023-09-01 13:20:00', '2023-09-01 13:20:00');
INSERT INTO `course` VALUES (4, 'SE101', '软件工程导论', 3.00, 48, '必修课', '软件工程的基本概念和方法', '2023-09-01 13:30:00', '2023-09-01 13:30:00');
INSERT INTO `course` VALUES (5, 'CS301', '操作系统', 4.00, 64, '必修课', '操作系统原理与设计', '2023-09-01 13:40:00', '2023-09-01 13:40:00');
INSERT INTO `course` VALUES (6, 'CS302', '计算机网络', 4.00, 64, '必修课', '计算机网络原理与应用', '2023-09-01 13:50:00', '2023-09-01 13:50:00');
INSERT INTO `course` VALUES (7, 'CS401', '人工智能导论', 3.00, 48, '选修课', '人工智能基础理论与应用', '2023-09-01 14:00:00', '2023-09-01 14:00:00');
INSERT INTO `course` VALUES (8, 'CS402', '机器学习', 4.00, 64, '选修课', '机器学习算法与应用', '2023-09-01 14:10:00', '2023-09-01 14:10:00');
INSERT INTO `course` VALUES (9, 'SE201', '数据库系统', 4.00, 64, '必修课', '数据库设计与应用', '2023-09-01 14:20:00', '2023-09-01 14:20:00');
INSERT INTO `course` VALUES (10, 'SE301', 'Web应用开发', 3.00, 48, '必修课', '基于Java的Web应用开发', '2023-09-01 14:30:00', '2023-09-01 14:30:00');
INSERT INTO `course` VALUES (1512, 'XTX105395', '运动与健康 (湖北大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1513, 'XTX3917', '生活英语听说 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1514, 'XTX442', '科研伦理与学术规范 (北京师范大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1515, 'XTX1934', '中国共产党与中华民族伟大复兴 (中央社会主义学院)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1516, 'XTX4399', '如何写好科研论文 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1517, 'XTX131078', '体育与社会 (武汉体育学院)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1518, 'XTX5031', '军事理论 (中国人民解放军国防大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1519, 'XTX392', '大国航母与舰载机 (中国人民解放军海军航空大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1520, 'XTX1322', '公共管理学 (国防科技大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1521, 'XTX526', '心理学概论 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1522, 'XTX4333', 'C++语言程序设计基础 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1523, 'XTX4397', '《资治通鉴》导读 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1524, 'XTX3509', '大学生心理健康 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1525, 'XTX113954', '数据结构(上) (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1526, 'XTX270', '不朽的艺术：走进大师与经典 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1527, 'XTX736', '中国近现代史纲要 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1528, 'XTX74126', '马克思主义基本原理 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1529, 'XTX504', '习近平新时代中国特色社会主义思想 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1530, 'XTX4468', '中国建筑史——元明清与民居 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');
INSERT INTO `course` VALUES (1531, 'XTX667760', '工程伦理2.0 (清华大学)', 1.25, 20, '通识课程', '', '2025-06-17 21:32:46', '2025-06-17 21:32:46');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '分数',
  `grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '等级(A/B/C/D/E)',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评语',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id` ASC, `course_id` ASC, `semester` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `fk_score_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '成绩信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (1, 1, 1, 1, '2023-2024-1', 85.50, 'B', '良好', '2024-01-15 10:00:00', '2024-01-15 10:00:00');
INSERT INTO `score` VALUES (2, 1, 2, 1, '2023-2024-1', 92.00, 'A', '优秀', '2024-01-15 10:10:00', '2024-01-15 10:10:00');
INSERT INTO `score` VALUES (3, 2, 1, 1, '2023-2024-1', 78.50, 'C', '中等', '2024-01-15 10:20:00', '2024-01-15 10:20:00');
INSERT INTO `score` VALUES (4, 2, 3, 2, '2023-2024-1', 88.00, 'B', '良好', '2024-01-15 10:30:00', '2024-01-15 10:30:00');
INSERT INTO `score` VALUES (5, 3, 2, 1, '2023-2024-1', 95.50, 'A', '优秀', '2024-01-15 10:40:00', '2024-01-15 10:40:00');
INSERT INTO `score` VALUES (6, 3, 4, 2, '2023-2024-1', 82.00, 'B', '良好', '2024-01-15 10:50:00', '2024-01-15 10:50:00');
INSERT INTO `score` VALUES (7, 4, 1, 1, '2023-2024-1', 76.50, 'C', '中等', '2024-01-15 11:00:00', '2024-01-15 11:00:00');
INSERT INTO `score` VALUES (8, 4, 3, 2, '2023-2024-1', 81.00, 'B', '良好', '2024-01-15 11:10:00', '2024-01-15 11:10:00');
INSERT INTO `score` VALUES (9, 5, 2, 1, '2023-2024-1', 93.50, 'A', '优秀', '2024-01-15 11:20:00', '2024-01-15 11:20:00');
INSERT INTO `score` VALUES (10, 5, 4, 2, '2023-2024-1', 87.00, 'B', '良好', '2024-01-15 11:30:00', '2024-01-15 11:30:00');
INSERT INTO `score` VALUES (11, 6, 5, 3, '2023-2024-1', 79.50, 'C', '中等', '2024-01-15 11:40:00', '2024-01-15 11:40:00');
INSERT INTO `score` VALUES (12, 6, 9, 1, '2023-2024-1', 84.00, 'B', '良好', '2024-01-15 11:50:00', '2024-01-15 11:50:00');
INSERT INTO `score` VALUES (13, 7, 6, 3, '2023-2024-1', 91.50, 'A', '优秀', '2024-01-15 12:00:00', '2024-01-15 12:00:00');
INSERT INTO `score` VALUES (14, 7, 10, 2, '2023-2024-1', 86.00, 'B', '良好', '2024-01-15 12:10:00', '2024-01-15 12:10:00');
INSERT INTO `score` VALUES (15, 8, 7, 4, '2023-2024-1', 94.00, 'A', '优秀', '2024-01-15 12:20:00', '2024-01-15 12:20:00');
INSERT INTO `score` VALUES (16, 8, 8, 4, '2023-2024-1', 90.50, 'A', '良好', '2024-01-15 12:30:00', '2025-06-25 10:56:22');
INSERT INTO `score` VALUES (17, 9, 1, 1, '2022-2023-2', 88.00, 'B', '良好', '2023-06-15 10:00:00', '2023-06-15 10:00:00');
INSERT INTO `score` VALUES (18, 9, 2, 1, '2022-2023-2', 92.50, 'A', '优秀', '2023-06-15 10:10:00', '2023-06-15 10:10:00');
INSERT INTO `score` VALUES (20, 10, 4, 2, '-3', 77.00, 'C', '中等', '2023-06-15 10:30:00', '2023-06-15 10:30:00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `class_id` bigint NOT NULL COMMENT '所属班级ID',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_no`(`student_no` ASC) USING BTREE,
  INDEX `idx_class_id`(`class_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'S202301001', 'M', '2003-01-15', '330101200301150011', '浙江省杭州市西湖区', 1, 4, '2023-09-01 12:00:00', '2025-05-28 21:05:59');
INSERT INTO `student` VALUES (2, 'S202301002', 'F', '2003-05-20', '330101200305200024', '浙江省杭州市拱墅区', 1, 5, '2023-09-01 12:10:00', '2025-05-28 21:06:02');
INSERT INTO `student` VALUES (3, 'S202302001', 'M', '2003-08-10', '330101200308100035', '浙江省杭州市滨江区', 2, 6, '2023-09-01 12:20:00', '2025-05-28 21:06:04');
INSERT INTO `student` VALUES (4, 'S202301003', 'M', '2003-03-25', '330101200303250047', '浙江省杭州市上城区', 1, 9, '2023-09-01 12:30:00', '2025-05-28 21:06:07');
INSERT INTO `student` VALUES (5, 'S202301004', 'F', '2003-07-12', '330101200307120056', '浙江省杭州市江干区', 1, 10, '2023-09-01 12:40:00', '2025-05-28 21:06:10');
INSERT INTO `student` VALUES (6, 'S202302002', 'M', '2003-09-18', '330101200309180068', '浙江省杭州市萧山区', 2, 11, '2023-09-01 12:50:00', '2025-05-28 21:06:13');
INSERT INTO `student` VALUES (7, 'S202303001', 'M', '2003-11-05', '330101200311050079', '浙江省杭州市余杭区', 3, 12, '2023-09-01 13:00:00', '2025-05-28 21:06:17');
INSERT INTO `student` VALUES (8, 'S202304001', 'M', '2003-04-30', '330101200304300081', '浙江省杭州市临平区', 4, 13, '2023-09-01 13:10:00', '2025-05-28 21:06:22');
INSERT INTO `student` VALUES (9, 'S202201001', 'M', '2002-06-22', '330101200206220092', '浙江省宁波市海曙区', 5, 14, '2022-09-01 12:00:00', '2025-05-28 21:06:25');
INSERT INTO `student` VALUES (10, 'S202202001', 'M', '2002-12-15', '330101200212150103', '浙江省宁波市江北区', 6, 15, '2022-09-01 12:10:00', '2025-05-28 21:06:29');
INSERT INTO `student` VALUES (11, 'S456', '男', '2005-05-03', '3208212002020202', 'TEST', 6, 18, '2025-05-28 21:30:06', '2025-05-28 21:30:06');
INSERT INTO `student` VALUES (12, '123456654', '男', '2001-05-31', '320821200105050505', 'test', 1, 22, '2025-06-24 23:02:58', '2025-06-24 23:02:58');
INSERT INTO `student` VALUES (13, '1324854685', 'M', '2025-06-23', '320821200101010506', 'tets', 6, 25, '2025-06-25 10:30:37', '2025-06-25 10:30:37');

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '已通过' COMMENT '审批状态(待审批/已通过/已拒绝)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id` ASC, `course_id` ASC, `semester` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `fk_sc_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sc_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sc_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生选课表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (1, 1, 1, 1, '2023-2024-1', '已通过', '2023-09-10 09:00:00');
INSERT INTO `student_course` VALUES (3, 2, 1, 1, '2023-2024-1', '已通过', '2023-09-10 09:20:00');
INSERT INTO `student_course` VALUES (4, 2, 3, 2, '2023-2024-1', '已通过', '2023-09-10 09:30:00');
INSERT INTO `student_course` VALUES (5, 3, 2, 1, '2023-2024-1', '已通过', '2023-09-10 09:40:00');
INSERT INTO `student_course` VALUES (6, 3, 4, 2, '2023-2024-1', '已通过', '2023-09-10 09:50:00');
INSERT INTO `student_course` VALUES (7, 4, 1, 1, '2023-2024-1', '已通过', '2023-09-10 10:00:00');
INSERT INTO `student_course` VALUES (8, 4, 3, 2, '2023-2024-1', '已拒绝', '2023-09-10 10:10:00');
INSERT INTO `student_course` VALUES (9, 5, 2, 1, '2023-2024-1', '已通过', '2023-09-10 10:20:00');
INSERT INTO `student_course` VALUES (10, 5, 4, 2, '2023-2024-1', '已通过', '2023-09-10 10:30:00');
INSERT INTO `student_course` VALUES (11, 6, 5, 3, '2023-2024-1', '已通过', '2023-09-10 10:40:00');
INSERT INTO `student_course` VALUES (12, 6, 9, 1, '2023-2024-1', '已通过', '2023-09-10 10:50:00');
INSERT INTO `student_course` VALUES (13, 7, 6, 3, '2023-2024-1', '已通过', '2023-09-10 11:00:00');
INSERT INTO `student_course` VALUES (14, 7, 10, 2, '2023-2024-1', '已通过', '2023-09-10 11:10:00');
INSERT INTO `student_course` VALUES (15, 8, 7, 4, '2023-2024-1', '已通过', '2023-09-10 11:20:00');
INSERT INTO `student_course` VALUES (16, 8, 8, 4, '2023-2024-1', '已通过', '2023-09-10 11:30:00');
INSERT INTO `student_course` VALUES (17, 9, 1, 1, '2022-2023-2', '已通过', '2023-02-10 09:40:00');
INSERT INTO `student_course` VALUES (18, 9, 2, 1, '2022-2023-2', '已通过', '2023-02-10 09:50:00');
INSERT INTO `student_course` VALUES (19, 10, 3, 2, '2022-2023-2', '已通过', '2023-02-10 09:50:00');
INSERT INTO `student_course` VALUES (20, 10, 4, 2, '2022-2023-2', '已通过', '2023-02-10 09:50:00');
INSERT INTO `student_course` VALUES (21, 1, 10, 2, '2023-2024-1', '已拒绝', '2025-05-28 20:47:12');
INSERT INTO `student_course` VALUES (22, 1, 3, 2, '2023-2024-1', '已拒绝', '2025-06-15 09:47:30');
INSERT INTO `student_course` VALUES (23, 1, 5, 3, '2023-2024-1', '已拒绝', '2025-06-16 11:02:32');
INSERT INTO `student_course` VALUES (25, 1, 9, 1, '2023-2024-1', '已通过', '2025-06-25 10:40:47');
INSERT INTO `student_course` VALUES (28, 13, 2, 1, '2022-2023-2', '已通过', '2025-06-25 10:41:04');
INSERT INTO `student_course` VALUES (29, 13, 1, 1, '2022-2023-2', '已通过', '2025-06-25 10:41:04');
INSERT INTO `student_course` VALUES (30, 13, 2, 1, '2025-2026-1', '已通过', '2025-07-17 22:44:32');
INSERT INTO `student_course` VALUES (31, 1, 6, 3, '2023-2024-1', '退课待审批', '2025-07-17 22:45:26');
INSERT INTO `student_course` VALUES (32, 11, 8, 4, '2023-2024-1', '待审批', '2025-10-07 09:48:40');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `teacher_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '教师编号',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_teacher_no`(`teacher_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'T2023001', 'M', '教授', 2, '2023-09-01 10:00:00', '2025-05-28 21:37:27');
INSERT INTO `teacher` VALUES (2, 'T2023002', 'F', '副教授', 3, '2023-09-01 10:10:00', '2025-05-28 21:37:24');
INSERT INTO `teacher` VALUES (3, 'T2023003', 'M', '教授', 7, '2023-09-01 10:20:00', '2025-05-28 21:37:21');
INSERT INTO `teacher` VALUES (4, 'T2023004', 'M', '副教授', 8, '2023-09-01 10:30:00', '2025-05-28 21:37:19');
INSERT INTO `teacher` VALUES (5, 'T345545', 'F', '老师', 19, '2025-05-28 21:30:56', '2025-05-28 21:37:17');
INSERT INTO `teacher` VALUES (6, 'T567890', '女', '老师', 23, '2025-06-24 23:05:05', '2025-06-24 23:05:05');
INSERT INTO `teacher` VALUES (7, '23', '女', '老师', 24, '2025-06-24 23:05:47', '2025-06-24 23:05:47');
INSERT INTO `teacher` VALUES (8, '15631563', 'M', '副教授', 26, '2025-06-25 10:31:15', '2025-06-25 10:31:15');

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course`;
CREATE TABLE `teacher_course`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_teacher_course_semester`(`teacher_id` ASC, `course_id` ASC, `semester` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_tc_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tc_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师课程关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher_course
-- ----------------------------
INSERT INTO `teacher_course` VALUES (1, 1, 1, '2023-2024-1', '2023-09-01 14:00:00');
INSERT INTO `teacher_course` VALUES (2, 1, 2, '2023-2024-1', '2023-09-01 14:10:00');
INSERT INTO `teacher_course` VALUES (3, 2, 3, '2023-2024-1', '2023-09-01 14:20:00');
INSERT INTO `teacher_course` VALUES (4, 2, 4, '2023-2024-1', '2023-09-01 14:30:00');
INSERT INTO `teacher_course` VALUES (5, 3, 5, '2023-2024-1', '2023-09-01 14:40:00');
INSERT INTO `teacher_course` VALUES (6, 3, 6, '2023-2024-1', '2023-09-01 14:50:00');
INSERT INTO `teacher_course` VALUES (7, 4, 7, '2023-2024-1', '2023-09-01 15:00:00');
INSERT INTO `teacher_course` VALUES (8, 4, 8, '2023-2024-1', '2023-09-01 15:10:00');
INSERT INTO `teacher_course` VALUES (9, 1, 9, '2023-2024-1', '2023-09-01 15:20:00');
INSERT INTO `teacher_course` VALUES (10, 2, 10, '2023-2024-1', '2023-09-01 15:30:00');
INSERT INTO `teacher_course` VALUES (11, 1, 1, '2022-2023-2', '2023-02-01 14:00:00');
INSERT INTO `teacher_course` VALUES (12, 1, 2, '2022-2023-2', '2023-02-01 14:10:00');
INSERT INTO `teacher_course` VALUES (13, 2, 3, '2022-2023-2', '2023-02-01 14:20:00');
INSERT INTO `teacher_course` VALUES (14, 2, 4, '2022-2023-2', '2023-02-01 14:30:00');
INSERT INTO `teacher_course` VALUES (15, 5, 4, '2023-2024-1', '2025-06-25 10:39:34');
INSERT INTO `teacher_course` VALUES (17, 1, 2, '2025-2026-1', '2025-06-25 10:43:12');
INSERT INTO `teacher_course` VALUES (18, 1, 8, '2025-2026-1', '2025-07-17 22:44:17');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码(加密存储)',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色code(ADMIN/TEACHER/STUDENT)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(0:禁用,1:正常)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'admin@school.edu', '13800138000', 'ADMIN', '系统管理员', '/img/1748439373391.jpg', 1, '2023-09-01 08:00:00', '2025-06-25 10:53:35');
INSERT INTO `user` VALUES (2, 'teacher1', '$2a$10$RrtI/wqXr8H0pvLlsysgouiX4VB4cSCcGBa/.CY5iBFH/doKLGuS6', 'teacher1@school.edu', '13800138001', 'TEACHER', '张教授', '/img/1759803238955.jpg', 1, '2023-09-01 08:10:00', '2025-10-07 10:13:58');
INSERT INTO `user` VALUES (3, 'teacher2', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'teacher2@school.edu', '13800138002', 'TEACHER', '李教授', '/img/avatar/teacher2.png', 1, '2023-09-01 08:20:00', '2023-09-01 08:20:00');
INSERT INTO `user` VALUES (4, 'student1', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student1@school.edu', '13800138003', 'STUDENT', '王同学', '/img/1748434128520.jpg', 1, '2023-09-01 09:00:00', '2025-05-28 20:08:48');
INSERT INTO `user` VALUES (5, 'student2', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student2@school.edu', '13800138004', 'STUDENT', '赵同学', '/img/avatar/student2.png', 1, '2023-09-01 09:10:00', '2023-09-01 09:10:00');
INSERT INTO `user` VALUES (6, 'student3', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student3@school.edu', '13800138005', 'STUDENT', '陈同学', '/img/avatar/student3.png', 1, '2023-09-01 09:20:00', '2023-09-01 09:20:00');
INSERT INTO `user` VALUES (7, 'teacher3', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'teacher3@school.edu', '13800138006', 'TEACHER', '王教授', '/img/avatar/teacher3.png', 1, '2023-09-01 08:30:00', '2023-09-01 08:30:00');
INSERT INTO `user` VALUES (8, 'teacher4', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'teacher4@school.edu', '13800138007', 'TEACHER', '刘教授', '/img/avatar/teacher4.png', 1, '2023-09-01 08:40:00', '2023-09-01 08:40:00');
INSERT INTO `user` VALUES (9, 'student4', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student4@school.edu', '13800138008', 'STUDENT', '林同学', '/img/avatar/student4.png', 1, '2023-09-01 09:30:00', '2023-09-01 09:30:00');
INSERT INTO `user` VALUES (10, 'student5', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student5@school.edu', '13800138009', 'STUDENT', '黄同学', '/img/avatar/student5.png', 1, '2023-09-01 09:40:00', '2023-09-01 09:40:00');
INSERT INTO `user` VALUES (11, 'student6', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student6@school.edu', '13800138010', 'STUDENT', '张同学', '/img/avatar/student6.png', 1, '2023-09-01 09:50:00', '2023-09-01 09:50:00');
INSERT INTO `user` VALUES (12, 'student7', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student7@school.edu', '13800138011', 'STUDENT', '吴同学', '/img/avatar/student7.png', 1, '2023-09-01 10:00:00', '2023-09-01 10:00:00');
INSERT INTO `user` VALUES (13, 'student8', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student8@school.edu', '13800138012', 'STUDENT', '郑同学', '/img/avatar/student8.png', 1, '2023-09-01 10:10:00', '2023-09-01 10:10:00');
INSERT INTO `user` VALUES (14, 'student9', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student9@school.edu', '13800138013', 'STUDENT', '李同学', '/img/avatar/student9.png', 1, '2023-09-01 10:20:00', '2023-09-01 10:20:00');
INSERT INTO `user` VALUES (15, 'student10', '$2a$10$iul6jocLsH.A4gN1QUpgDexDq6KO89syHjUkRD3NbA1L6CTVrNRMO', 'student10@school.edu', '13800138014', 'STUDENT', '周同学', '/img/avatar/student10.png', 1, '2023-09-01 10:30:00', '2023-09-01 10:30:00');
INSERT INTO `user` VALUES (18, 'stest', '$2a$10$hMl.E6zc6WxSWykxFvZFJektLfpCH7Qvd.VbD1wgv4sRza.RfX9z.', '21561561@qq.com', '15212345678', 'STUDENT', 'steat', NULL, 1, '2025-05-28 21:30:06', '2025-05-28 21:30:06');
INSERT INTO `user` VALUES (19, 'ttest', '$2a$10$BsqLc/sz5.Wb4yi6QqYZnONIgNyhmbrR2OrItYQDWiBKnCBoUz0.e', '12125896@qq.com', '13345678900', 'TEACHER', 'ttest', NULL, 1, '2025-05-28 21:30:56', '2025-05-28 21:30:56');
INSERT INTO `user` VALUES (22, 'teststudnet', '$2a$10$varkB2Zpu2HPq9voSrsFxeKyYr0RJU5fivR3QxvHOIjSuu6hhiKlC', '13459485616@qq.com', '13123456789', 'STUDENT', '李彤', NULL, 1, '2025-06-24 23:02:58', '2025-06-24 23:02:58');
INSERT INTO `user` VALUES (23, 'teachertest', '$2a$10$zJ0lQB/Zlkm9BDUIFOH1MOR6GcKKe3UrsOt.Fhr5UlKNlMyEG03Q.', '1111331@qq.com', '13123456789', 'TEACHER', '朝霞', NULL, 1, '2025-06-24 23:05:05', '2025-06-24 23:05:05');
INSERT INTO `user` VALUES (24, '223', '$2a$10$qzE2JrzypwOxBi23qWgtkOt1nd7vVtoSvWaIX6JwVi2tucP3PHLuq', '1516315615641@qq.com', '19803328316', 'TEACHER', '姚', NULL, 1, '2025-06-24 23:05:47', '2025-06-24 23:05:47');
INSERT INTO `user` VALUES (25, 'studenttest000', '$2a$10$cZU3Bu8iuyW1W6tfW8V0x.jAqUCLQcf1XfDaiwiF8VHU7M4UUnlSG', '7151465@qq.com', '13161561561', 'STUDENT', 'jx', NULL, 1, '2025-06-25 10:30:37', '2025-06-25 10:30:37');
INSERT INTO `user` VALUES (26, 'resr', '$2a$10$TWi45ItNao4tnxRitXi6UeeykDnnAgJT1scyI1BpD/tJ/lcNEEGVu', '5616515@qq.com', '13123465789', 'TEACHER', 'huygbhy', NULL, 1, '2025-06-25 10:31:15', '2025-06-25 10:31:15');

SET FOREIGN_KEY_CHECKS = 1;
