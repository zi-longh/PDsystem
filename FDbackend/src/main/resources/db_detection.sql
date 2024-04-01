/*
 Navicat Premium Data Transfer

 Source Server         : mysql01
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : db_detection

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 01/04/2024 22:49:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生、教师、管理员\r\nSTUDENT,teacher,ADMIN',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('root', 'root', 'ADMIN');
INSERT INTO `account` VALUES ('student1', '123', 'STUDENT');
INSERT INTO `account` VALUES ('student2', '123', 'STUDENT');
INSERT INTO `account` VALUES ('student3', '123', 'STUDENT');
INSERT INTO `account` VALUES ('teacher1', '123', 'TEACHER');

-- ----------------------------
-- Table structure for detect_record
-- ----------------------------
DROP TABLE IF EXISTS `detect_record`;
CREATE TABLE `detect_record`  (
  `record_id` int NOT NULL AUTO_INCREMENT COMMENT '检测编号',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号名',
  `template_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模板id',
  `detect_time` datetime(0) NULL DEFAULT NULL COMMENT '检测时间',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检测状态：0不通过',
  `paperName` varchar(59) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论文中文名',
  `paperEnglishName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论文英文名',
  `resultFileName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检测结果文件',
  `resultPDF` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检测结果文件pdf，只有当通过检测后才会有',
  `isSendToTeacher` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '1是 0否',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of detect_record
-- ----------------------------
INSERT INTO `detect_record` VALUES (5, 'student1', '2024031234', '2024-03-23 17:45:49', '2', '论文中文名2', '论文英文名2', 'resultFIleName', NULL, '0');
INSERT INTO `detect_record` VALUES (7, 'student1', '2024031234', '2024-02-23 18:23:34', '2', '论文中文名', 'englishname', 'filename', 'pdfname', '0');
INSERT INTO `detect_record` VALUES (8, 'student1', '2024031234', '2024-02-23 18:23:34', '2', '论文中文名', 'englishname', 'filename', 'pdfname', '0');
INSERT INTO `detect_record` VALUES (9, 'student1', '2024031234', '2024-02-23 18:23:34', '2', '论文中文名', 'englishname', 'filename', 'pdfname', '0');
INSERT INTO `detect_record` VALUES (10, 'student1', '2024031234', '2024-02-23 18:23:34', '2', '论文中文名', 'englishname', 'filename', 'pdfname', '0');
INSERT INTO `detect_record` VALUES (11, 'student1', '202012023', '2024-04-01 16:52:32', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]申论练习_2024-04-01T16-52-32.096988100.docx', NULL, '0');
INSERT INTO `detect_record` VALUES (12, 'student1', '202012023', '2024-04-01 16:58:18', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]wps测试论文2_2024-04-01T16-58-18.886364200.docx', NULL, '0');
INSERT INTO `detect_record` VALUES (13, 'student1', '202012023', '2024-04-01 16:58:46', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]wps测试论文2_2024-04-01T16-58-46.120955900.docx', NULL, '0');
INSERT INTO `detect_record` VALUES (14, 'student1', '202012023', '2024-04-01 17:00:08', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]wps测试论文2_2024-04-01T17-00-08.767328300.docx', 'wps测试论文2_2024-04-01T17-00-08.767328300.pdf', '0');
INSERT INTO `detect_record` VALUES (15, 'student1', '2024031234', '2024-04-01 17:33:30', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]wps测试论文2_2024-04-01T17-33-30.863999.docx', NULL, '0');
INSERT INTO `detect_record` VALUES (16, 'student1', '202012023', '2024-04-01 17:34:26', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '[待修改]wps测试论文2_2024-04-01T17-34-26.018928800.docx', NULL, '0');
INSERT INTO `detect_record` VALUES (17, 'student1', '2024031234', '2024-04-01 20:52:48', '2', '基于OOXML标准的论文检测与校正系统', 'Paper detection and correction system based on OOXML standard', '(待修改)测试论文aa_2024-04-01T20-52-48.357839900.docx', NULL, '0');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `grade` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院',
  `instructor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '指导老师，可以为空',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('student1', '黄子龙', '2020101634', '2020级', '软件工程', '信息科学技术学院', 'teacher1');
INSERT INTO `student` VALUES ('student2', '黄子龙', '2020101634', '2020级', '软件工程', '信息科学技术学院', 'teacher1');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号名',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '老师姓名',
  `teacher_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师工号',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所处单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '信息简介',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('teacher1', '张晓刚', '20112', '信息科学技术学院', '描述');
INSERT INTO `teacher` VALUES ('teacher2', '张三', '120312', '信息科学技术学院', '描述2');
INSERT INTO `teacher` VALUES ('teacher3', '李四', '123123', '信息科学技术学院', '描述3');
INSERT INTO `teacher` VALUES ('teacher4', '王五', '142342', '信息科学技术学院', '描述4');

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template`  (
  `template_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板编号',
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板名称',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最近更新时间',
  `status` int NOT NULL COMMENT '0表示未发布，1表示已发布',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板描述',
  PRIMARY KEY (`template_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('2024023456', '学术会议论文模板', 'teacher3', '2024-05-02 14:30:18', '2024-05-02 14:30:18', 0, '适用于学术会议投稿的论文模板');
INSERT INTO `template` VALUES ('2024031234', '2024本科生论文模板', 'teacher1', '2024-03-27 13:16:37', '2024-03-27 13:16:37', 1, '2024年级毕业生通用论文模板');
INSERT INTO `template` VALUES ('2024031235', '信科院本科生毕业论文模板', 'teacher2', '2024-03-13 13:24:48', '2024-03-13 13:24:48', 1, '专用于信科院的本科毕业论文模板');
INSERT INTO `template` VALUES ('2024031236', '成人教育论文通用模板', 'teacher1', '2024-03-20 13:25:48', '2024-03-20 13:25:48', 1, '专用于成人教育部门的论文模板');
INSERT INTO `template` VALUES ('2024035678', '研究生论文模板', 'teacher2', '2024-04-15 10:45:22', '2024-04-15 10:45:22', 0, '研究生毕业论文通用模板');

-- ----------------------------
-- Table structure for template_info
-- ----------------------------
DROP TABLE IF EXISTS `template_info`;
CREATE TABLE `template_info`  (
  `template_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '绑定的模板id',
  `soh_flag` int NOT NULL COMMENT '“诚信声明”标志位，0无，1有',
  `soh_seq` int NULL DEFAULT NULL COMMENT '次序，默认为1',
  `soh_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '诚信说明内容',
  `soh_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '\"诚信声明\"标题字体类别',
  `soh_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `soh_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `soh_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `soh_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `soh_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `soh_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `soh_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `soh_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '\"诚信声明\"段落字体类别',
  `soh_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `soh_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `soh_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `soh_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `soh_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `aoc_flag` int NULL DEFAULT NULL COMMENT '中文摘要是否有',
  `aoc_seq` int NULL DEFAULT NULL COMMENT '中文摘要次序',
  `aoc_prefixFont` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前置词字体',
  `aoc_isPrefixBold` int NULL DEFAULT NULL COMMENT '前置词是否加粗',
  `aoc_recommendedMaxContentLength` int NULL DEFAULT NULL COMMENT '摘要的推荐最大字数',
  `aoc_recommendedMinContentLength` int NULL DEFAULT NULL COMMENT '摘要的推荐最小字数',
  `aoc_recommendedMaxKeywordsCount` int NULL DEFAULT NULL COMMENT '关键词的推荐最大个数',
  `aoc_recommendedMinKeywordsCount` int NULL DEFAULT NULL COMMENT '关键词推荐的最小个数',
  `aoc_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `aoc_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `aoc_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `aoc_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `aoc_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `aoc_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `aoc_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `aoc_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `aoc_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `aoc_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `aoc_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `aoc_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `aoc_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `aoc_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `aoe_flag` int NULL DEFAULT NULL COMMENT '英文摘要是否有',
  `aoe_seq` int NULL DEFAULT NULL COMMENT '英文摘要次序',
  `aoe_prefixFont` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前置词字体',
  `aoe_isPrefixBold` int NULL DEFAULT NULL COMMENT '前置词是否加粗',
  `aoe_recommendedMaxContentLength` int NULL DEFAULT NULL COMMENT '摘要的推荐最大字数',
  `aoe_recommendedMinContentLength` int NULL DEFAULT NULL COMMENT '摘要的推荐最小字数',
  `aoe_recommendedMaxKeywordsCount` int NULL DEFAULT NULL COMMENT '关键词的推荐最大个数',
  `aoe_recommendedMinKeywordsCount` int NULL DEFAULT NULL COMMENT '关键词推荐的最小个数',
  `aoe_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `aoe_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `aoe_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `aoe_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `aoe_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `aoe_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `aoe_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `aoe_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `aoe_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `aoe_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `aoe_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `aoe_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `aoe_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `aoe_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `catalogue_flag` int NULL DEFAULT NULL COMMENT '目录是否有',
  `catalogue_seq` int NULL DEFAULT NULL COMMENT '目录次序',
  `catalogue_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `catalogue_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `catalogue_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `catalogue_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `catalogue_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `catalogue_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `catalogue_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `catalogue_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `mainbody_flag` int NULL DEFAULT NULL COMMENT '绪论和正文是否有',
  `mainbody_seq` int NULL DEFAULT NULL COMMENT '绪论和正文次序',
  `mainbody_h1_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `mainbody_h1_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `mainbody_h1_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `mainbody_h1_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `mainbody_h1_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `mainbody_h1_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `mainbody_h1_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `mainbody_h1_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `mainbody_h2_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `mainbody_h2_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `mainbody_h2_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `mainbody_h2_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `mainbody_h2_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `mainbody_h2_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `mainbody_h2_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `mainbody_h2_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `mainbody_h3_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `mainbody_h3_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `mainbody_h3_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `mainbody_h3_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `mainbody_h3_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `mainbody_h3_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `mainbody_h3_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `mainbody_h3_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `mainbody_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `mainbody_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `mainbody_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `mainbody_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `mainbody_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `mainbody_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `conclusion_flag` int NULL DEFAULT NULL COMMENT '结论是否有',
  `conclusion_seq` int NULL DEFAULT NULL COMMENT '结论次序',
  `conclusion_recommendedMaxContentLength` int NULL DEFAULT NULL COMMENT '推荐的结论最大字数',
  `conclusion_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `conclusion_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `conclusion_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `conclusion_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `conclusion_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `conclusion_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `conclusion_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `conclusion_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `conclusion_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `conclusion_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `conclusion_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `conclusion_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `conclusion_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `conclusion_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `thanks_flag` int NULL DEFAULT NULL COMMENT '致谢是否有',
  `thanks_seq` int NULL DEFAULT NULL COMMENT '致谢次序',
  `thanks_recommendedMaxContentLength` int NULL DEFAULT NULL COMMENT '推荐的致谢最大字数',
  `thanks_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `thanks_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `thanks_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `thanks_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `thanks_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `thanks_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `thanks_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `thanks_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `thanks_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `thanks_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `thanks_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `thanks_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `thanks_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `thanks_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `references_flag` int NULL DEFAULT NULL COMMENT '参考文献是否有',
  `references_seq` int NULL DEFAULT NULL COMMENT '参考文献次序',
  `references_recommendedMinCount` int NULL DEFAULT NULL COMMENT '推荐的参考文献最少条数',
  `references_h_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题字体类别',
  `references_h_font_english_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `references_h_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `references_h_jc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对齐方式left，right，center，\"jc\" 表示 \"justification\"',
  `references_h_bold` int NULL DEFAULT NULL COMMENT '字体加粗，0无，1有',
  `references_h_afterline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段后间隔，100为1行',
  `references_h_beforeline` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段前间隔，100为1行',
  `references_h_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距，240为1行，lineRule需要设置为auto',
  `references_p_font_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段落字体类别',
  `references_p_font_type_english` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '西文字体类别',
  `references_p_font_sz` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字体大小',
  `references_p_ind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '首行缩进',
  `references_p_line` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '段间距',
  `references_p_bold` int NULL DEFAULT NULL COMMENT '段落正文是否加粗，0无1有',
  `caption_fontName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图表注释字体',
  `caption_fontEnglishName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图表注释英文字体',
  `caption_fontSize` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图表注释字号',
  `caption_picSzWidthMin` double NULL DEFAULT NULL COMMENT '图片最小宽度',
  `caption_picSzWidthMax` double NULL DEFAULT NULL COMMENT '图片最大宽度',
  `caption_picSzHeightMin` double NULL DEFAULT NULL COMMENT '图片最小高度',
  `caption_picSzHeightMax` double NULL DEFAULT NULL COMMENT '图片最大高度',
  `caption_recommendNum` int NULL DEFAULT NULL COMMENT '推荐的图表数量，这个数量是最小值，可以多于这个数量。',
  `pageSetting_headerContent1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页眉内容1',
  `pageSetting_headerContent2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页眉内容2',
  `pageSetting_topMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页上边距',
  `pageSetting_bottomMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页下边距',
  `pageSetting_leftMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页左边距',
  `pageSetting_rightMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页右边距',
  `pageSetting_headerMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页眉边距',
  `pageSetting_footerMargin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页脚边距',
  `pageSetting_oddEvenPage` int NULL DEFAULT NULL COMMENT '奇偶页设置，0无，1有'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of template_info
-- ----------------------------
INSERT INTO `template_info` VALUES ('2024031234', 1, 1, '我声明，所呈交的毕业论文是本人在老师指导下进行的研究工作及取得的研究成果。据我查证，除了文中特别加以标注和致谢的地方外，论文中不包含其他人已经发表或撰写过的研究成果，也不包含为获得其他教育机构的学位或证书而使用过的材料。我承诺，论文中的所有内容均真实、可信。', '楷体_GB2312', 'Times New Roman', '44', 'center', 1, '200', '100', '240', '楷体_GB2312', 'Times New Roman', '30', '200', '360', 0, 1, 2, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 240, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 3, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 1, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '360', 0, 1, 4, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', 1, 5, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', 'left', 1, '30', '30', '360', '宋体', 'Times New Roman', '28', 'left', 1, '0', '0', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 6, 630, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 7, 630, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 8, 8, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '0', '360', 0, '宋体', 'Times New Roman', '21', 1, 17, 1, 26, 2, '暨南大学本科毕业设计（论文）', 'defaultPaperName', '1418', '1418', '1701', '1134', '851', '992', 1);
INSERT INTO `template_info` VALUES ('2024031235', 1, 1, '我声明，所呈交的毕业论文是本人在老师指导下进行的研究工作及取得的研究成果。据我查证，除了文中特别加以标注和致谢的地方外，论文中不包含其他人已经发表或撰写过的研究成果，也不包含为获得其他教育机构的学位或证书而使用过的材料。我承诺，论文中的所有内容均真实、可信。', '楷体_GB2312', 'Times New Roman', '44', 'center', 1, '200', '100', '240', '楷体_GB2312', 'Times New Roman', '30', '200', '360', 0, 1, 2, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 240, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 3, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 1, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '360', 0, 1, 4, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', 1, 5, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', 'left', 1, '30', '30', '360', '宋体', 'Times New Roman', '28', 'left', 1, '0', '0', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 6, 630, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 7, 630, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 8, 8, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '0', '360', 0, '宋体', 'Times New Roman', '21', 1, 17, 1, 26, 2, '信科院毕业设计（论文）', 'defaultPaperName', '1418', '1418', '1701', '1134', '851', '992', 1);
INSERT INTO `template_info` VALUES ('2024031236', 1, 1, '我声明，所呈交的毕业论文是本人在老师指导下进行的研究工作及取得的研究成果。据我查证，除了文中特别加以标注和致谢的地方外，论文中不包含其他人已经发表或撰写过的研究成果，也不包含为获得其他教育机构的学位或证书而使用过的材料。我承诺，论文中的所有内容均真实、可信。', '楷体_GB2312', 'Times New Roman', '44', 'center', 1, '200', '100', '240', '楷体_GB2312', 'Times New Roman', '30', '200', '360', 0, 1, 2, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 240, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 3, '宋体', 1, 300, 1, 5, 1, '宋体', 'Times New Roman', '36', 'center', 1, '200', '100', '240', '宋体', 'Times New Roman', '28', '200', '360', 0, 1, 4, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', 1, 5, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', 'left', 1, '30', '30', '360', '宋体', 'Times New Roman', '28', 'left', 1, '0', '0', '240', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 6, 630, '宋体', 'Times New Roman', '36', 'left', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 7, 630, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '200', '240', 0, 1, 8, 8, '宋体', 'Times New Roman', '36', 'center', 1, '50', '50', '480', '宋体', 'Times New Roman', '28', '0', '360', 0, '宋体', 'Times New Roman', '21', 1, 17, 1, 26, 2, '成人教育本科毕业设计（论文）', 'defaultPaperName', '1418', '1418', '1701', '1134', '851', '992', 1);

SET FOREIGN_KEY_CHECKS = 1;
