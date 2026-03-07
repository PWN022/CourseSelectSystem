/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : course_select

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 07/03/2026 16:49:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考勤ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `attendance_date` date NOT NULL COMMENT '考勤日期',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '考勤状态(出席/缺席/请假)',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_date`(`student_id`, `course_id`, `attendance_date`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  CONSTRAINT `fk_attendance_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_attendance_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '考勤信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (1, 1, 1, '2024-09-15', '出席', NULL, '2024-09-15 08:30:00');
INSERT INTO `attendance` VALUES (2, 1, 1, '2024-09-22', '出席', NULL, '2024-09-22 08:30:00');
INSERT INTO `attendance` VALUES (3, 1, 1, '2024-09-29', '出席', NULL, '2024-09-29 08:30:00');
INSERT INTO `attendance` VALUES (4, 1, 1, '2024-10-13', '缺席', '迟到', '2024-10-13 08:35:00');
INSERT INTO `attendance` VALUES (5, 1, 1, '2024-10-20', '出席', NULL, '2024-10-20 08:30:00');
INSERT INTO `attendance` VALUES (6, 1, 2, '2024-09-16', '出席', NULL, '2024-09-16 10:00:00');
INSERT INTO `attendance` VALUES (7, 1, 2, '2024-09-23', '出席', NULL, '2024-09-23 10:00:00');
INSERT INTO `attendance` VALUES (8, 1, 2, '2024-10-14', '请假', '生病', '2024-10-14 09:55:00');
INSERT INTO `attendance` VALUES (9, 1, 2, '2024-10-21', '出席', NULL, '2024-10-21 10:00:00');
INSERT INTO `attendance` VALUES (10, 2, 1, '2024-09-15', '出席', NULL, '2024-09-15 08:30:00');
INSERT INTO `attendance` VALUES (11, 2, 1, '2024-09-22', '出席', NULL, '2024-09-22 08:30:00');
INSERT INTO `attendance` VALUES (12, 2, 1, '2024-09-29', '缺席', '无故缺席', '2024-09-29 08:40:00');
INSERT INTO `attendance` VALUES (13, 2, 1, '2024-10-13', '出席', NULL, '2024-10-13 08:30:00');
INSERT INTO `attendance` VALUES (14, 2, 1, '2024-10-20', '出席', NULL, '2024-10-20 08:30:00');
INSERT INTO `attendance` VALUES (15, 2, 6, '2024-09-17', '出席', NULL, '2024-09-17 14:00:00');
INSERT INTO `attendance` VALUES (16, 2, 6, '2024-09-24', '出席', NULL, '2024-09-24 14:00:00');
INSERT INTO `attendance` VALUES (17, 2, 6, '2024-10-15', '出席', NULL, '2024-10-15 14:00:00');
INSERT INTO `attendance` VALUES (18, 3, 2, '2024-09-16', '出席', NULL, '2024-09-16 10:00:00');
INSERT INTO `attendance` VALUES (19, 3, 2, '2024-09-23', '请假', '事假', '2024-09-23 09:55:00');
INSERT INTO `attendance` VALUES (20, 3, 2, '2024-10-14', '出席', NULL, '2024-10-14 10:00:00');
INSERT INTO `attendance` VALUES (21, 3, 4, '2024-09-18', '出席', NULL, '2024-09-18 13:30:00');
INSERT INTO `attendance` VALUES (22, 3, 4, '2024-09-25', '出席', NULL, '2024-09-25 13:30:00');
INSERT INTO `attendance` VALUES (23, 3, 4, '2024-10-16', '缺席', '迟到', '2024-10-16 13:45:00');
INSERT INTO `attendance` VALUES (24, 3, 8, '2024-09-19', '出席', NULL, '2024-09-19 15:30:00');
INSERT INTO `attendance` VALUES (25, 3, 8, '2024-09-26', '出席', NULL, '2024-09-26 15:30:00');
INSERT INTO `attendance` VALUES (26, 3, 8, '2024-10-17', '出席', NULL, '2024-10-17 15:30:00');
INSERT INTO `attendance` VALUES (27, 4, 3, '2024-09-15', '出席', NULL, '2024-09-15 09:30:00');
INSERT INTO `attendance` VALUES (28, 4, 3, '2024-09-22', '缺席', '无故缺席', '2024-09-22 09:40:00');
INSERT INTO `attendance` VALUES (29, 4, 3, '2024-09-29', '出席', NULL, '2024-09-29 09:30:00');
INSERT INTO `attendance` VALUES (30, 4, 3, '2024-10-13', '出席', NULL, '2024-10-13 09:30:00');
INSERT INTO `attendance` VALUES (31, 6, 1, '2024-09-15', '出席', NULL, '2024-09-15 08:30:00');
INSERT INTO `attendance` VALUES (32, 6, 1, '2024-09-22', '出席', NULL, '2024-09-22 08:30:00');
INSERT INTO `attendance` VALUES (33, 6, 1, '2024-09-29', '出席', NULL, '2024-09-29 08:30:00');
INSERT INTO `attendance` VALUES (34, 6, 1, '2024-10-13', '出席', NULL, '2024-10-13 08:30:00');
INSERT INTO `attendance` VALUES (35, 6, 5, '2024-09-16', '出席', NULL, '2024-09-16 16:00:00');
INSERT INTO `attendance` VALUES (36, 6, 5, '2024-09-23', '出席', NULL, '2024-09-23 16:00:00');
INSERT INTO `attendance` VALUES (37, 6, 5, '2024-10-14', '请假', '感冒', '2024-10-14 15:55:00');
INSERT INTO `attendance` VALUES (38, 6, 7, '2024-09-18', '出席', NULL, '2024-09-18 10:30:00');
INSERT INTO `attendance` VALUES (39, 6, 7, '2024-09-25', '出席', NULL, '2024-09-25 10:30:00');
INSERT INTO `attendance` VALUES (40, 6, 7, '2024-10-16', '缺席', '旷课', '2024-10-16 10:45:00');
INSERT INTO `attendance` VALUES (41, 7, 2, '2024-09-16', '出席', NULL, '2024-09-16 10:00:00');
INSERT INTO `attendance` VALUES (42, 7, 2, '2024-09-23', '出席', NULL, '2024-09-23 10:00:00');
INSERT INTO `attendance` VALUES (43, 7, 2, '2024-10-14', '缺席', '无故缺席', '2024-10-14 10:10:00');
INSERT INTO `attendance` VALUES (44, 7, 6, '2024-09-17', '出席', NULL, '2024-09-17 14:00:00');
INSERT INTO `attendance` VALUES (45, 7, 6, '2024-09-24', '请假', '事假', '2024-09-24 13:55:00');
INSERT INTO `attendance` VALUES (46, 7, 6, '2024-10-15', '出席', NULL, '2024-10-15 14:00:00');
INSERT INTO `attendance` VALUES (47, 8, 1, '2024-09-15', '出席', NULL, '2024-09-15 08:30:00');
INSERT INTO `attendance` VALUES (48, 8, 1, '2024-09-22', '出席', NULL, '2024-09-22 08:30:00');
INSERT INTO `attendance` VALUES (49, 8, 1, '2024-09-29', '出席', NULL, '2024-09-29 08:30:00');
INSERT INTO `attendance` VALUES (50, 8, 3, '2024-09-15', '出席', NULL, '2024-09-15 09:30:00');
INSERT INTO `attendance` VALUES (51, 8, 3, '2024-09-22', '出席', NULL, '2024-09-22 09:30:00');
INSERT INTO `attendance` VALUES (52, 8, 3, '2024-09-29', '出席', NULL, '2024-09-29 09:30:00');
INSERT INTO `attendance` VALUES (53, 8, 4, '2024-09-18', '出席', NULL, '2024-09-18 13:30:00');
INSERT INTO `attendance` VALUES (54, 8, 4, '2024-09-25', '出席', NULL, '2024-09-25 13:30:00');
INSERT INTO `attendance` VALUES (55, 8, 4, '2024-10-16', '出席', NULL, '2024-10-16 13:30:00');
INSERT INTO `attendance` VALUES (56, 8, 8, '2024-09-19', '出席', NULL, '2024-09-19 15:30:00');
INSERT INTO `attendance` VALUES (57, 8, 8, '2024-09-26', '出席', NULL, '2024-09-26 15:30:00');
INSERT INTO `attendance` VALUES (58, 8, 8, '2024-10-17', '出席', NULL, '2024-10-17 15:30:00');
INSERT INTO `attendance` VALUES (59, 9, 5, '2024-09-16', '出席', NULL, '2024-09-16 16:00:00');
INSERT INTO `attendance` VALUES (60, 9, 5, '2024-09-23', '出席', NULL, '2024-09-23 16:00:00');
INSERT INTO `attendance` VALUES (61, 9, 5, '2024-10-14', '出席', NULL, '2024-10-14 16:00:00');
INSERT INTO `attendance` VALUES (62, 10, 2, '2024-09-16', '出席', NULL, '2024-09-16 10:00:00');
INSERT INTO `attendance` VALUES (63, 10, 2, '2024-09-23', '出席', NULL, '2024-09-23 10:00:00');
INSERT INTO `attendance` VALUES (64, 10, 2, '2024-10-14', '出席', NULL, '2024-10-14 10:00:00');
INSERT INTO `attendance` VALUES (65, 10, 4, '2024-09-18', '出席', NULL, '2024-09-18 13:30:00');
INSERT INTO `attendance` VALUES (66, 10, 4, '2024-09-25', '缺席', '迟到', '2024-09-25 13:45:00');
INSERT INTO `attendance` VALUES (67, 10, 4, '2024-10-16', '出席', NULL, '2024-10-16 13:30:00');
INSERT INTO `attendance` VALUES (68, 12, 3, '2024-09-15', '出席', NULL, '2024-09-15 09:30:00');
INSERT INTO `attendance` VALUES (69, 12, 3, '2024-09-22', '出席', NULL, '2024-09-22 09:30:00');
INSERT INTO `attendance` VALUES (70, 12, 3, '2024-09-29', '请假', '病假', '2024-09-29 09:25:00');
INSERT INTO `attendance` VALUES (71, 13, 1, '2024-09-15', '出席', NULL, '2024-09-15 08:30:00');
INSERT INTO `attendance` VALUES (72, 13, 1, '2024-09-22', '缺席', '无故缺席', '2024-09-22 08:40:00');
INSERT INTO `attendance` VALUES (73, 13, 1, '2024-09-29', '出席', NULL, '2024-09-29 08:30:00');
INSERT INTO `attendance` VALUES (74, 13, 5, '2024-09-16', '出席', NULL, '2024-09-16 16:00:00');
INSERT INTO `attendance` VALUES (75, 13, 5, '2024-09-23', '出席', NULL, '2024-09-23 16:00:00');
INSERT INTO `attendance` VALUES (76, 13, 5, '2024-10-14', '出席', NULL, '2024-10-14 16:00:00');
INSERT INTO `attendance` VALUES (77, 13, 8, '2024-09-19', '出席', NULL, '2024-09-19 15:30:00');
INSERT INTO `attendance` VALUES (78, 13, 8, '2024-09-26', '出席', NULL, '2024-09-26 15:30:00');
INSERT INTO `attendance` VALUES (79, 13, 8, '2024-10-17', '出席', NULL, '2024-10-17 15:30:00');
INSERT INTO `attendance` VALUES (80, 14, 4, '2024-09-18', '出席', NULL, '2024-09-18 13:30:00');
INSERT INTO `attendance` VALUES (81, 14, 4, '2024-09-25', '出席', NULL, '2024-09-25 13:30:00');
INSERT INTO `attendance` VALUES (82, 14, 4, '2024-10-16', '请假', '事假', '2024-10-16 13:25:00');
INSERT INTO `attendance` VALUES (83, 14, 5, '2025-03-18', '出席', '', '2026-03-07 16:06:25');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级名称',
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '年级',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业',
  `head_teacher_id` bigint(20) NULL DEFAULT NULL COMMENT '班主任ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_head_teacher`(`head_teacher_id`) USING BTREE,
  CONSTRAINT `fk_class_teacher` FOREIGN KEY (`head_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班级信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '计算机科学与技术2024级1班', '2024级', '计算机科学与技术', 1, '2024-06-01 09:00:00', '2024-06-01 09:00:00');
INSERT INTO `class` VALUES (2, '计算机科学与技术2024级2班', '2024级', '计算机科学与技术', 2, '2024-06-01 09:05:00', '2024-06-01 09:05:00');
INSERT INTO `class` VALUES (3, '软件工程2024级1班', '2024级', '软件工程', 3, '2024-06-01 09:10:00', '2024-06-01 09:10:00');
INSERT INTO `class` VALUES (4, '软件工程2024级2班', '2024级', '软件工程', 10, '2024-06-01 09:15:00', '2026-03-07 16:48:59');
INSERT INTO `class` VALUES (5, '数据科学与大数据2024级1班', '2024级', '数据科学与大数据技术', 9, '2024-06-01 09:20:00', '2026-03-07 16:43:02');
INSERT INTO `class` VALUES (6, '人工智能2024级1班', '2024级', '人工智能', 4, '2024-06-01 09:25:00', '2026-03-07 16:42:46');
INSERT INTO `class` VALUES (7, '计算机科学与技术2025级1班', '2025级', '计算机科学与技术', 1, '2025-06-02 09:50:57', '2026-03-07 16:36:48');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程代码',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `credit` decimal(5, 2) NOT NULL COMMENT '学分',
  `hours` int(11) NOT NULL COMMENT '课时',
  `course_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '课程描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `max_capacity` int(11) NULL DEFAULT 50 COMMENT '最大选课人数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_course_code`(`course_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'CS301', 'Python数据分析', 2.50, 40, '选修课', '使用Python进行数据分析和可视化', '2024-06-01 09:00:00', '2024-06-01 09:00:00', 50);
INSERT INTO `course` VALUES (2, 'CS302', 'Web前端开发', 2.50, 40, '选修课', 'HTML、CSS、JavaScript基础与实战', '2024-06-01 09:10:00', '2024-06-01 09:10:00', 50);
INSERT INTO `course` VALUES (3, 'CS303', '移动应用开发', 2.50, 40, '选修课', 'Android/iOS应用开发入门', '2024-06-01 09:20:00', '2024-06-01 09:20:00', 50);
INSERT INTO `course` VALUES (4, 'CS304', '人工智能导论', 2.00, 32, '选修课', 'AI基础概念与应用场景', '2024-06-01 09:30:00', '2024-06-01 09:30:00', 50);
INSERT INTO `course` VALUES (5, 'PE101', '健身与形体', 1.50, 32, '选修课', '科学健身方法与体型管理', '2024-06-01 10:00:00', '2024-06-01 10:00:00', 50);
INSERT INTO `course` VALUES (6, 'TE101', '无人机航拍', 1.50, 28, '选修课', '无人机操控与航拍技巧', '2024-06-01 10:10:00', '2024-06-01 10:10:00', 50);
INSERT INTO `course` VALUES (7, 'ME101', '自媒体运营', 2.00, 36, '选修课', '短视频、公众号等平台运营', '2024-06-01 10:20:00', '2024-06-01 10:20:00', 50);
INSERT INTO `course` VALUES (8, 'TA101', '红酒文化与品鉴', 1.00, 20, '选修课', '红酒基础知识与品鉴技巧', '2024-06-01 10:30:00', '2024-06-01 10:30:00', 50);
INSERT INTO `course` VALUES (9, 'ESPCE301', '高级英语', 10.00, 180, '选修课', '学术英语语言知识与阅读技能、学习策略和学术交际能力为主要内容', '2025-06-01 11:28:41', '2026-03-07 10:09:40', 5);
INSERT INTO `course` VALUES (10, 'SE202', '软件测试', 2.50, 40, '专业必修课', '测试策略、黑盒测试、白盒测试、自动化测试、性能测试、缺陷管理', '2024-06-01 10:30:00', '2026-03-07 16:48:13', 50);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '分数',
  `grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '等级(A/B/C/D/E)',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评语',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id`, `course_id`, `semester`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `fk_score_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_select` FOREIGN KEY (`student_id`, `course_id`, `semester`) REFERENCES `student_course` (`student_id`, `course_id`, `semester`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '成绩信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (1, 1, 1, 1, '2024-2025-1', 88.50, 'B', '良好', '2025-01-15 10:00:00', '2026-03-04 18:20:18');
INSERT INTO `score` VALUES (2, 1, 2, 2, '2024-2025-1', 92.00, 'A', '优秀', '2025-01-15 10:01:00', '2025-01-15 10:01:00');
INSERT INTO `score` VALUES (3, 1, 5, 5, '2024-2025-1', 85.00, 'B', '良好', '2025-01-15 10:02:00', '2025-01-15 10:02:00');
INSERT INTO `score` VALUES (4, 1, 7, 7, '2024-2025-1', 78.50, 'C', '中等', '2025-01-15 10:03:00', '2025-01-15 10:03:00');
INSERT INTO `score` VALUES (5, 2, 1, 1, '2024-2025-1', 91.00, 'A', '优秀', '2025-01-15 10:10:00', '2025-01-15 10:10:00');
INSERT INTO `score` VALUES (6, 2, 6, 6, '2024-2025-1', 84.00, 'B', '良好', '2025-01-15 10:11:00', '2025-01-15 10:11:00');
INSERT INTO `score` VALUES (7, 3, 2, 2, '2024-2025-1', 76.00, 'C', '中等', '2025-01-15 10:20:00', '2025-01-15 10:20:00');
INSERT INTO `score` VALUES (8, 3, 4, 4, '2024-2025-1', 82.50, 'B', '良好', '2025-01-15 10:21:00', '2025-01-15 10:21:00');
INSERT INTO `score` VALUES (9, 3, 8, 8, '2024-2025-1', 95.00, 'A', '优秀', '2025-01-15 10:22:00', '2025-01-15 10:22:00');
INSERT INTO `score` VALUES (10, 4, 3, 3, '2024-2025-1', 68.00, 'D', '及格', '2025-01-15 10:30:00', '2025-01-15 10:30:00');
INSERT INTO `score` VALUES (11, 6, 1, 1, '2024-2025-1', 86.00, 'B', '良好', '2025-01-15 10:40:00', '2025-01-15 10:40:00');
INSERT INTO `score` VALUES (12, 6, 5, 5, '2024-2025-1', 79.00, 'C', '中等', '2025-01-15 10:41:00', '2025-01-15 10:41:00');
INSERT INTO `score` VALUES (13, 6, 7, 7, '2024-2025-1', 88.00, 'B', '良好', '2025-01-15 10:42:00', '2025-01-15 10:42:00');
INSERT INTO `score` VALUES (14, 7, 2, 2, '2024-2025-1', 72.00, 'C', '中等', '2025-01-15 10:50:00', '2025-01-15 10:50:00');
INSERT INTO `score` VALUES (15, 7, 6, 6, '2024-2025-1', 65.00, 'D', '及格', '2025-01-15 10:51:00', '2025-01-15 10:51:00');
INSERT INTO `score` VALUES (16, 8, 1, 1, '2024-2025-1', 94.00, 'A', '优秀', '2025-01-15 11:00:00', '2025-01-15 11:00:00');
INSERT INTO `score` VALUES (17, 8, 3, 3, '2024-2025-1', 89.00, 'B', '良好', '2025-01-15 11:01:00', '2025-01-15 11:01:00');
INSERT INTO `score` VALUES (18, 8, 4, 4, '2024-2025-1', 91.50, 'A', '优秀', '2025-01-15 11:02:00', '2025-01-15 11:02:00');
INSERT INTO `score` VALUES (19, 8, 8, 8, '2024-2025-1', 87.00, 'B', '良好', '2025-01-15 11:03:00', '2025-01-15 11:03:00');
INSERT INTO `score` VALUES (20, 9, 5, 5, '2024-2025-1', 81.00, 'B', '良好', '2025-01-15 11:10:00', '2025-01-15 11:10:00');
INSERT INTO `score` VALUES (21, 10, 2, 2, '2024-2025-1', 93.00, 'A', '优秀', '2025-01-15 11:20:00', '2025-01-15 11:20:00');
INSERT INTO `score` VALUES (22, 10, 4, 4, '2024-2025-1', 77.00, 'C', '中等', '2025-01-15 11:21:00', '2025-01-15 11:21:00');
INSERT INTO `score` VALUES (23, 12, 3, 3, '2024-2025-1', 84.00, 'B', '良好', '2025-01-15 14:00:00', '2025-01-15 14:00:00');
INSERT INTO `score` VALUES (24, 13, 1, 1, '2024-2025-1', 76.50, 'C', '中等', '2025-01-15 14:10:00', '2025-01-15 14:10:00');
INSERT INTO `score` VALUES (25, 13, 5, 5, '2024-2025-1', 82.00, 'B', '良好', '2025-01-15 14:11:00', '2025-01-15 14:11:00');
INSERT INTO `score` VALUES (26, 13, 8, 8, '2024-2025-1', 90.00, 'A', '优秀', '2025-01-15 14:12:00', '2025-01-15 14:12:00');
INSERT INTO `score` VALUES (27, 14, 4, 4, '2024-2025-1', 71.00, 'C', '中等', '2025-01-15 14:20:00', '2025-01-15 14:20:00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `class_id` bigint(20) NOT NULL COMMENT '所属班级ID',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_no`(`student_no`) USING BTREE,
  INDEX `idx_class_id`(`class_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'S20240901', 'M', '2003-05-12', '110101200305120011', '北京市东城区安定门街道', 1, 10, '2024-08-20 09:30:00', '2024-08-20 09:30:00');
INSERT INTO `student` VALUES (2, 'S20240902', 'F', '2004-08-23', '110102200408230024', '北京市西城区西长安街街道', 1, 11, '2024-08-20 10:15:00', '2024-09-10 14:20:00');
INSERT INTO `student` VALUES (3, 'S20240903', 'M', '2002-11-05', '110105200211050035', '北京市朝阳区朝外街道', 2, 12, '2024-08-21 11:20:00', '2024-08-21 11:20:00');
INSERT INTO `student` VALUES (4, 'S20240904', 'F', '2004-02-18', '110106200402180042', '北京市丰台区丰台街道', 1, 13, '2024-08-21 14:45:00', '2024-10-05 16:30:00');
INSERT INTO `student` VALUES (5, 'S20240905', 'M', '2003-09-30', '110107200309300053', '北京市石景山区八宝山街道', 1, 14, '2024-08-22 09:10:00', '2024-08-22 09:10:00');
INSERT INTO `student` VALUES (6, 'S20240906', 'F', '2005-03-15', '110108200503150064', '北京市海淀区海淀街道', 2, 15, '2024-08-22 10:30:00', '2024-09-18 11:15:00');
INSERT INTO `student` VALUES (7, 'S20240907', 'M', '2002-07-08', '110109200207080075', '北京市门头沟区大峪街道', 3, 16, '2024-08-23 13:50:00', '2024-08-23 13:50:00');
INSERT INTO `student` VALUES (8, 'S20240908', 'F', '2004-12-25', '110111200412250086', '北京市房山区拱辰街道', 4, 17, '2024-08-23 15:20:00', '2024-11-02 10:40:00');
INSERT INTO `student` VALUES (9, 'S20240909', 'M', '2003-04-03', '110112200304030097', '北京市通州区中仓街道', 5, 18, '2024-08-24 08:45:00', '2024-08-24 08:45:00');
INSERT INTO `student` VALUES (10, 'S20240910', 'F', '2005-01-19', '110113200501190108', '北京市顺义区胜利街道', 6, 19, '2024-08-24 10:05:00', '2024-09-25 14:10:00');
INSERT INTO `student` VALUES (11, 'S20240911', 'M', '2002-09-14', '110114200209140119', '北京市昌平区城北街道', 1, 20, '2024-08-25 11:30:00', '2024-08-25 11:30:00');
INSERT INTO `student` VALUES (12, 'S20240912', 'F', '2004-06-27', '110115200406270120', '北京市大兴区兴丰街道', 2, 21, '2024-08-25 14:15:00', '2024-10-12 09:30:00');
INSERT INTO `student` VALUES (13, 'S20240913', 'M', '2003-10-08', '110116200310080131', '北京市怀柔区龙山街道', 3, 22, '2024-08-26 09:50:00', '2024-08-26 09:50:00');
INSERT INTO `student` VALUES (14, 'S20240914', 'F', '2005-07-22', '110117200507220142', '北京市平谷区滨河街道', 4, 23, '2024-08-26 13:40:00', '2024-09-30 16:20:00');
INSERT INTO `student` VALUES (15, 'TEST002', 'M', '2001-12-31', '110101200201010015', '北京市东城区安定门街道', 7, 29, '2026-03-06 18:04:37', '2026-03-06 18:51:23');
INSERT INTO `student` VALUES (16, 'TEST003', 'F', '2003-02-28', '110102200303010028', '北京市西城区西长安街街道', 7, 30, '2026-03-06 18:07:47', '2026-03-06 18:51:26');
INSERT INTO `student` VALUES (17, 'TEST004', 'M', '2005-07-22', '110117200507230142', '北京市平谷区滨河街道', 7, 32, '2026-03-07 12:15:28', '2026-03-07 12:15:28');

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '已通过' COMMENT '审批状态(待审批/已通过/已拒绝)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id`, `course_id`, `semester`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `fk_sc_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sc_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sc_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生选课表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (1, 1, 1, 1, '2024-2025-1', '已通过', '2024-08-25 09:00:00');
INSERT INTO `student_course` VALUES (2, 1, 2, 2, '2024-2025-1', '已通过', '2024-08-25 09:01:00');
INSERT INTO `student_course` VALUES (3, 1, 5, 5, '2024-2025-1', '已通过', '2024-08-25 09:02:00');
INSERT INTO `student_course` VALUES (4, 1, 7, 7, '2024-2025-1', '待审批', '2024-08-25 09:03:00');
INSERT INTO `student_course` VALUES (5, 2, 1, 1, '2024-2025-1', '已通过', '2024-08-25 09:10:00');
INSERT INTO `student_course` VALUES (6, 2, 6, 6, '2024-2025-1', '已通过', '2024-08-25 09:11:00');
INSERT INTO `student_course` VALUES (7, 3, 2, 2, '2024-2025-1', '已通过', '2024-08-25 09:20:00');
INSERT INTO `student_course` VALUES (8, 3, 4, 4, '2024-2025-1', '已通过', '2024-08-25 09:21:00');
INSERT INTO `student_course` VALUES (9, 3, 8, 8, '2024-2025-1', '已通过', '2024-08-25 09:22:00');
INSERT INTO `student_course` VALUES (10, 4, 3, 3, '2024-2025-1', '已通过', '2024-08-25 09:30:00');
INSERT INTO `student_course` VALUES (11, 6, 1, 1, '2024-2025-1', '已通过', '2024-08-26 10:10:00');
INSERT INTO `student_course` VALUES (12, 6, 5, 5, '2024-2025-1', '已通过', '2024-08-26 10:11:00');
INSERT INTO `student_course` VALUES (13, 6, 7, 7, '2024-2025-1', '已通过', '2024-08-26 10:12:00');
INSERT INTO `student_course` VALUES (14, 7, 2, 2, '2024-2025-1', '已通过', '2024-08-26 10:20:00');
INSERT INTO `student_course` VALUES (15, 7, 6, 6, '2024-2025-1', '待审批', '2024-08-26 10:21:00');
INSERT INTO `student_course` VALUES (16, 8, 1, 1, '2024-2025-1', '已通过', '2024-08-27 09:00:00');
INSERT INTO `student_course` VALUES (17, 8, 3, 3, '2024-2025-1', '已通过', '2024-08-27 09:01:00');
INSERT INTO `student_course` VALUES (18, 8, 4, 4, '2024-2025-1', '已通过', '2024-08-27 09:02:00');
INSERT INTO `student_course` VALUES (19, 8, 8, 8, '2024-2025-1', '已通过', '2024-08-27 09:03:00');
INSERT INTO `student_course` VALUES (20, 9, 5, 5, '2024-2025-1', '已通过', '2024-08-27 09:10:00');
INSERT INTO `student_course` VALUES (21, 10, 2, 2, '2024-2025-1', '已通过', '2024-08-27 09:20:00');
INSERT INTO `student_course` VALUES (22, 10, 4, 4, '2024-2025-1', '已通过', '2024-08-27 09:21:00');
INSERT INTO `student_course` VALUES (23, 10, 6, 6, '2024-2025-1', '已拒绝', '2024-08-27 09:22:00');
INSERT INTO `student_course` VALUES (24, 12, 3, 3, '2024-2025-1', '已通过', '2024-08-27 14:00:00');
INSERT INTO `student_course` VALUES (25, 12, 7, 7, '2024-2025-1', '待审批', '2024-08-27 14:01:00');
INSERT INTO `student_course` VALUES (26, 13, 1, 1, '2024-2025-1', '已通过', '2024-08-28 10:00:00');
INSERT INTO `student_course` VALUES (27, 13, 5, 5, '2024-2025-1', '已通过', '2024-08-28 10:01:00');
INSERT INTO `student_course` VALUES (28, 13, 8, 8, '2024-2025-1', '已通过', '2024-08-28 10:02:00');
INSERT INTO `student_course` VALUES (29, 14, 4, 4, '2024-2025-1', '已通过', '2024-08-28 10:10:00');
INSERT INTO `student_course` VALUES (30, 14, 6, 6, '2024-2025-1', '待审批', '2024-08-28 10:11:00');
INSERT INTO `student_course` VALUES (34, 14, 5, 12, '2024-2025-2', '已通过', '2026-03-06 11:57:26');
INSERT INTO `student_course` VALUES (35, 1, 9, 11, '2025-2026-1', '已通过', '2026-03-06 17:58:21');
INSERT INTO `student_course` VALUES (36, 15, 9, 11, '2025-2026-1', '已通过', '2026-03-06 18:05:39');
INSERT INTO `student_course` VALUES (37, 15, 5, 12, '2024-2025-2', '已通过', '2026-03-06 18:05:40');
INSERT INTO `student_course` VALUES (38, 16, 5, 12, '2024-2025-2', '已通过', '2026-03-06 18:08:02');
INSERT INTO `student_course` VALUES (39, 16, 3, 3, '2024-2025-1', '待审批', '2026-03-06 18:09:29');
INSERT INTO `student_course` VALUES (40, 4, 9, 11, '2025-2026-1', '已通过', '2026-03-06 22:32:05');
INSERT INTO `student_course` VALUES (45, 5, 9, 11, '2025-2026-1', '已通过', '2026-03-07 11:36:35');
INSERT INTO `student_course` VALUES (46, 16, 9, 11, '2025-2026-1', '已通过', '2026-03-07 11:37:01');
INSERT INTO `student_course` VALUES (47, 17, 5, 12, '2024-2025-2', '已通过', '2026-03-07 12:15:53');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `teacher_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '教师编号',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_teacher_no`(`teacher_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'T20240101', 'M', '教授', 2, '2024-01-15 10:30:00', '2026-03-06 11:21:20');
INSERT INTO `teacher` VALUES (2, 'T20240102', 'F', '教授', 3, '2024-01-16 09:15:00', '2026-03-06 11:21:23');
INSERT INTO `teacher` VALUES (3, 'T20240103', 'F', '副教授', 4, '2024-01-18 11:20:00', '2026-03-06 12:02:05');
INSERT INTO `teacher` VALUES (4, 'T20240104', 'M', '副教授', 5, '2024-01-20 14:45:00', '2026-03-06 12:02:07');
INSERT INTO `teacher` VALUES (5, 'T20240105', 'F', '副教授', 6, '2024-01-22 08:50:00', '2026-03-06 12:02:15');
INSERT INTO `teacher` VALUES (6, 'T20240106', 'M', '讲师', 7, '2024-01-25 13:10:00', '2026-03-06 12:02:22');
INSERT INTO `teacher` VALUES (7, 'T20240107', 'F', '讲师', 8, '2024-01-28 10:05:00', '2026-03-06 12:02:30');
INSERT INTO `teacher` VALUES (8, 'T20240201', 'M', '讲师', 9, '2024-02-01 15:30:00', '2026-03-06 12:02:49');
INSERT INTO `teacher` VALUES (9, 'T20240108', 'M', '讲师', 24, '2024-01-20 09:30:00', '2026-03-06 11:21:47');
INSERT INTO `teacher` VALUES (10, 'T20240202', 'F', '副教授', 25, '2024-02-05 14:15:00', '2026-03-06 11:21:54');
INSERT INTO `teacher` VALUES (11, 'T20250901', 'F', '教授', 26, '2025-09-01 09:11:00', '2026-03-06 11:32:18');
INSERT INTO `teacher` VALUES (12, 'T20260101', 'M', '讲师', 27, '2026-01-15 11:22:06', '2026-03-06 11:22:43');
INSERT INTO `teacher` VALUES (13, 'TEST001', 'M', '讲师', 28, '2026-03-06 18:00:32', '2026-03-06 18:00:32');
INSERT INTO `teacher` VALUES (14, 'TEST005', 'M', '教授', 33, '2026-03-07 12:17:09', '2026-03-07 12:17:09');

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course`;
CREATE TABLE `teacher_course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `semester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_teacher_course_semester`(`teacher_id`, `course_id`, `semester`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  CONSTRAINT `fk_tc_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tc_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师课程关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher_course
-- ----------------------------
INSERT INTO `teacher_course` VALUES (1, 1, 1, '2024-2025-1', '2024-06-15 09:00:00');
INSERT INTO `teacher_course` VALUES (2, 2, 2, '2024-2025-1', '2024-06-15 09:05:00');
INSERT INTO `teacher_course` VALUES (3, 3, 3, '2024-2025-1', '2024-06-15 09:10:00');
INSERT INTO `teacher_course` VALUES (4, 4, 4, '2024-2025-1', '2024-06-15 09:15:00');
INSERT INTO `teacher_course` VALUES (5, 5, 5, '2024-2025-1', '2024-06-15 09:20:00');
INSERT INTO `teacher_course` VALUES (6, 6, 6, '2024-2025-1', '2024-06-15 09:25:00');
INSERT INTO `teacher_course` VALUES (7, 7, 7, '2024-2025-1', '2024-06-15 09:30:00');
INSERT INTO `teacher_course` VALUES (8, 8, 8, '2024-2025-1', '2024-06-15 09:35:00');
INSERT INTO `teacher_course` VALUES (9, 9, 1, '2024-2025-1', '2024-06-15 09:40:00');
INSERT INTO `teacher_course` VALUES (10, 10, 10, '2024-2025-1', '2024-06-15 09:45:00');
INSERT INTO `teacher_course` VALUES (11, 12, 5, '2024-2025-2', '2025-03-15 09:00:00');
INSERT INTO `teacher_course` VALUES (12, 11, 9, '2025-2026-1', '2025-09-15 11:29:31');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码(加密存储)',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色code(ADMIN/TEACHER/STUDENT)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0:禁用,1:正常)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  UNIQUE INDEX `uk_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'admin@school.edu.cn', '13900001111', 'ADMIN', '超级管理员', '/img/1772619693217.png', 1, '2024-01-10 09:00:00', '2026-03-16 11:13:51');
INSERT INTO `user` VALUES (2, 'zhangwei', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhangwei@t.school.edu.cn', '13900001112', 'TEACHER', '张伟', NULL, 1, '2024-01-15 10:30:00', '2024-02-08 02:51:16');
INSERT INTO `user` VALUES (3, 'liujing', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'liujing@t.school.edu.cn', '13900001113', 'TEACHER', '刘静', NULL, 1, '2024-01-16 09:15:00', '2024-05-24 04:34:51');
INSERT INTO `user` VALUES (4, 'wangfang', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'wangfang@t.school.edu.cn', '13900001114', 'TEACHER', '王芳', NULL, 1, '2024-01-18 11:20:00', '2025-08-29 14:20:30');
INSERT INTO `user` VALUES (5, 'chengang', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'chengang@t.school.edu.cn', '13900001115', 'TEACHER', '陈刚', NULL, 1, '2024-01-20 14:45:00', '2024-11-10 09:58:00');
INSERT INTO `user` VALUES (6, 'lina', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'lina@t.school.edu.cn', '13900001116', 'TEACHER', '李娜', NULL, 1, '2024-01-22 08:50:00', '2025-05-29 06:48:33');
INSERT INTO `user` VALUES (7, 'zhaoming', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhaoming@t.school.edu.cn', '13900001117', 'TEACHER', '赵明', NULL, 1, '2024-01-25 13:10:00', '2024-04-14 04:08:51');
INSERT INTO `user` VALUES (8, 'liuhong', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'liuhong@t.school.edu.cn', '13900001118', 'TEACHER', '刘红', NULL, 1, '2024-01-28 10:05:00', '2025-05-17 00:18:34');
INSERT INTO `user` VALUES (9, 'zhoujie', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhoujie@t.school.edu.cn', '13900001119', 'TEACHER', '周杰', NULL, 1, '2024-02-01 15:30:00', '2025-11-04 13:06:22');
INSERT INTO `user` VALUES (10, 'zhaolei', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhaolei@s.school.edu.cn', '13900001120', 'STUDENT', '赵磊', NULL, 1, '2024-08-20 09:30:00', '2024-12-03 16:36:14');
INSERT INTO `user` VALUES (11, 'sunli', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'sunli@s.school.edu.cn', '13900001121', 'STUDENT', '孙丽', NULL, 1, '2024-08-20 10:15:00', '2025-03-05 19:42:03');
INSERT INTO `user` VALUES (12, 'zhouwei', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhouwei@s.school.edu.cn', '13900001122', 'STUDENT', '周伟', NULL, 1, '2024-08-21 11:20:00', '2025-01-09 00:41:38');
INSERT INTO `user` VALUES (13, 'wujie', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'wujie@s.school.edu.cn', '13900001123', 'STUDENT', '吴婕', NULL, 1, '2024-08-21 14:45:00', '2026-03-06 11:43:55');
INSERT INTO `user` VALUES (14, 'zhengshuang', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'zhengshuang@s.school.edu.cn', '13900001124', 'STUDENT', '郑爽', NULL, 1, '2024-08-22 09:10:00', '2024-09-14 07:45:25');
INSERT INTO `user` VALUES (15, 'wangli', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'wangli@s.school.edu.cn', '13900001125', 'STUDENT', '王丽', NULL, 1, '2024-08-22 10:30:00', '2024-11-02 13:40:58');
INSERT INTO `user` VALUES (16, 'fengtao', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'fengtao@s.school.edu.cn', '13900001126', 'STUDENT', '冯涛', NULL, 1, '2024-08-23 13:50:00', '2026-01-30 21:08:39');
INSERT INTO `user` VALUES (17, 'chenjing', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'chenjing@s.school.edu.cn', '13900001127', 'STUDENT', '陈静', NULL, 1, '2024-08-23 15:20:00', '2025-08-21 16:40:28');
INSERT INTO `user` VALUES (18, 'weijun', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'weijun@s.school.edu.cn', '13900001128', 'STUDENT', '魏军', NULL, 1, '2024-08-24 08:45:00', '2025-12-11 19:56:22');
INSERT INTO `user` VALUES (19, 'jiangli', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'jiangli@s.school.edu.cn', '13900001129', 'STUDENT', '蒋丽', NULL, 1, '2024-08-24 10:05:00', '2024-08-22 01:40:32');
INSERT INTO `user` VALUES (20, 'shenhua', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'shenhua@s.school.edu.cn', '13900001130', 'STUDENT', '沈华', NULL, 1, '2024-08-25 11:30:00', '2025-07-15 20:33:34');
INSERT INTO `user` VALUES (21, 'hanyu', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'hanyu@s.school.edu.cn', '13900001131', 'STUDENT', '韩玉', NULL, 1, '2024-08-25 14:15:00', '2026-03-06 11:43:00');
INSERT INTO `user` VALUES (22, 'qiaofeng', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'qiaofeng@s.school.edu.cn', '13900001132', 'STUDENT', '乔峰', NULL, 1, '2024-08-26 09:50:00', '2025-04-12 19:11:03');
INSERT INTO `user` VALUES (23, 'duanyu', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'duanyu@s.school.edu.cn', '13900001133', 'STUDENT', '段玉', NULL, 1, '2024-08-26 13:40:00', '2026-03-06 11:43:15');
INSERT INTO `user` VALUES (24, 'xuyang', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'xuyang@t.school.edu.cn', '13900001134', 'TEACHER', '徐阳', NULL, 1, '2024-01-20 09:30:00', '2024-01-22 11:28:15');
INSERT INTO `user` VALUES (25, 'linting', '$2a$10$Cc6gX7Jel5UtKreBdrY8SeRiqwqEuccdySNafjQRMtz30KE92sPaS', 'linting@t.school.edu.cn', '13900001135', 'TEACHER', '林婷', NULL, 1, '2024-02-05 14:15:00', '2025-09-16 01:32:25');
INSERT INTO `user` VALUES (26, 'qiyonghua', '$2a$10$Zy6TI00KNnraoJXKK/hoferWlb/WjIHx1mp7u3l2Xd4IJgYzTXGu6', 'qiyonghua@t.school.edu.cn', '13900011100', 'TEACHER', '祁永华', NULL, 1, '2025-09-01 11:15:39', '2026-03-06 11:16:52');
INSERT INTO `user` VALUES (27, 'liangliwei', '$2a$10$V12zSoYVZ3UBzFkkErUJLuUY3IW2/hjbd2E7hzsHH7sQQA5wjCsOy', 'liangliwei@t.school.edu.cn', '13900011111', 'TEACHER', '梁力伟', NULL, 1, '2026-01-15 11:22:06', '2026-03-06 11:23:22');
INSERT INTO `user` VALUES (28, 'test', '$2a$10$xVlaJGQIHhHKs.UNaxQHRu2ManpZsiEvbTxaTkf36Ke4Jy6ord4F2', 'test@school.edu.cn', '13710101010', 'TEACHER', '测试教师用户', NULL, 1, '2026-03-06 18:00:32', '2026-03-06 18:05:15');
INSERT INTO `user` VALUES (29, 'ceshi', '$2a$10$LDt4u0Woof4EOp.IklrjweikoqYQvs30jzR7LQIchx4.Sjon0g03q', 'ceshi@school.edu.cn', '13701010101', 'STUDENT', '测试学生用户', NULL, 1, '2026-03-06 18:04:37', '2026-03-06 18:05:18');
INSERT INTO `user` VALUES (30, 'ceshi1', '$2a$10$FRA9qX62dG/mZu3.rCxcz.Hisr.L/v/Lt6PPWoh8BIO4hfC6PEaLi', 'ceshi1@school.edu.cn', '13701010101', 'STUDENT', '测试学生用户1', NULL, 1, '2026-03-06 18:07:47', '2026-03-06 18:07:47');
INSERT INTO `user` VALUES (32, 'ceshi2', '$2a$10$/cvVNDll84PzdjSSuV9Alu/jnOdr4HXEVJrZF3sXpoIqwPww6DaZS', 'T@s.school.edu.cn', '13710100000', 'STUDENT', 'T', NULL, 1, '2026-03-07 12:15:28', '2026-03-07 12:15:28');
INSERT INTO `user` VALUES (33, 'ceshi3', '$2a$10$JSpxsEDHIsAFuQJwPIsMHO72OwRQiD.u6/NTdWEMyeQAlVu9wm2mG', 'TE@t.school.edu.cn', '13710100001', 'TEACHER', 'TE', NULL, 1, '2026-03-07 12:17:09', '2026-03-07 12:17:09');

SET FOREIGN_KEY_CHECKS = 1;
