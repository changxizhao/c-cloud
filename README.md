/*
 Navicat Premium Data Transfer

 Source Server         : virtual_centos
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.0.143:13306
 Source Schema         : rbac-server

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 14/08/2020 07:00:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_model_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '权限模块id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求url',
  `type` int(11) NOT NULL COMMENT '类型，1菜单，2按钮，3其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1正常 0冻结',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_acl_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_model`;
CREATE TABLE `sys_acl_model`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级权限模块id',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级下的顺序',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '权限模块状态，1正常，0停用',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门id',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '上级列表',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '部门在当前层级下的顺序',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 0, '0', 1, '技术部', 'changxizhao', '2020-08-01 14:45:00', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (2, '后端开发小组', 1, '0.1', 1, '', 'changxizhao', '2020-08-02 10:48:10', '192.168.0.108');
INSERT INTO `sys_dept` VALUES (3, '前端开发组', 1, '0.1', 2, '技术部-前端开发组', 'admin', '2020-07-06 07:23:26', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (4, '人事部', 0, '0', 2, '人事部', 'admin', '2020-07-14 13:54:59', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (5, '招聘组', 4, '0.4', 1, '招聘组', 'admin', '2020-08-01 10:02:48', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (6, '行政部', 0, '0', 3, '行政部', 'admin', '2020-07-14 14:27:49', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (7, '财务部', 0, '0', 4, '财务部', 'changxizhao', '2020-08-02 10:44:42', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_dept` VALUES (8, '综合部', 0, '0', 5, '综合部', 'changxizhao', '2020-08-02 02:23:37', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_dept` VALUES (9, '培训组', 4, '0.4', 2, '培训组', 'changxizhao', '2020-08-02 02:01:08', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (10, '档案管理组', 4, '0.4', 3, '档案管理组', 'changxizhao', '2020-08-02 02:04:32', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (11, '综合四部', 8, '0.8', 4, '', 'admin', '2020-07-15 12:59:25', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (12, '综合五部', 8, '0.8', 5, '', 'admin', '2020-07-15 13:01:48', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (13, '综合一部', 8, '0.8', 1, '综合一部', 'admin', '2020-07-15 13:05:52', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (14, '综合二部', 8, '0.8', 2, '', 'admin', '2020-07-15 12:58:10', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (15, '综合六部', 8, '0.8', 6, '', 'admin', '2020-07-15 12:58:24', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (16, '综合三部', 8, '0.8', 3, '综合三部', 'admin', '2020-07-15 12:59:10', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (17, '财务一部', 7, '0.7', 1, '', 'admin', '2020-07-14 12:36:47', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (18, '行政一部', 6, '0.6', 1, '行政一部', 'changxizhao', '2020-08-02 10:46:12', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (19, '事业一部', 0, '0', 7, '', 'admin', '2020-07-14 12:53:39', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (20, '事业二部', 0, '0', 9, '', 'admin', '2020-07-14 12:53:56', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (21, '事业三部', 0, '0', 10, '', 'admin', '2020-07-14 12:54:07', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (24, '财务二部', 7, '0.7', 13, '财务二部', 'admin', '2020-07-15 12:49:30', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (25, '综合七部', 8, '0.8', 7, '', 'admin', '2020-07-16 07:15:40', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (26, '综合八部', 8, '0.8', 8, '', 'admin', '2020-07-16 07:15:51', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (27, '综合九部', 8, '0.8', 9, '', 'admin', '2020-07-16 07:15:59', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (28, '综合十部', 8, '0.8', 10, '', 'admin', '2020-07-16 07:16:11', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (29, '行政二部', 6, '0.6', 1, '行政二部', 'admin', '2020-07-17 14:46:37', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (30, '行政三部', 6, '0.6', 3, '行政三部', 'admin', '2020-07-17 14:57:00', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (31, '财务三部', 7, '0.7', 3, '财务三部', 'admin', '2020-07-18 14:00:11', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (32, '财务四部', 7, '0.7', 4, '', 'admin', '2020-07-18 14:01:54', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (33, '财务五部', 7, '0.7', 5, '', 'admin', '2020-07-18 14:04:28', '127.0.0.1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` bigint(20) NOT NULL COMMENT '权限更新类型，1部门，2用户，3权限模块，4权限，5角色，6角色用户关系，7角色权限关系',
  `target_id` bigint(20) NOT NULL COMMENT '基于type指定的对象id，比如用户、角色、权限的id',
  `old_value` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '旧值',
  `new_value` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '新值',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '当前是否被复原过，0没有，1有',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限码',
  `type` int(1) NOT NULL COMMENT '类型，1菜单，2按钮，3其他',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求url',
  `parent_id` bigint(20) NOT NULL COMMENT '上级id',
  `seq` int(11) NOT NULL COMMENT '显示顺序',
  `status` int(1) NOT NULL COMMENT '权限状态，1正常，0停用',
  `visible` int(1) DEFAULT NULL COMMENT '菜单是否可见（0显示 1隐藏）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '操作者',
  `operate_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 'sys', 0, 'null', 0, 1, 1, 1, '', 'changxizhao', '2020-08-11 14:09:55', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 'user', 0, 'sys/users.html', 1, 1, 1, 1, '', 'changxizhao', '2020-08-11 14:10:15', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (3, '部门管理', 'dept', 0, 'sys/depts.html', 1, 2, 1, 1, '', 'changxizhao', '2020-08-11 14:10:19', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (6, '系统设置', 'sys', 0, 'null', 0, 2, 1, 1, '', 'changxizhao', '2020-08-11 14:10:21', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (9, '菜单管理', 'menu', 0, 'sys/menu.html', 1, 3, 1, 1, '', 'changxizhao', '2020-08-11 14:10:22', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (10, '角色管理', 'role', 0, 'sys/role.html', 1, 4, 1, 1, '', 'changxizhao', '2020-08-11 14:10:23', '127.0.0.1');
INSERT INTO `sys_menu` VALUES (11, '接口管理', 'url', 0, '/swagger-ui.html', 6, 1, 1, 1, '', 'changxizhao', '2020-08-11 14:10:26', '127.0.0.1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '类型，1管理员角色，2其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1正常，0停用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 1, 1, '管理员角色', 'changxizhao', '2020-08-07 13:02:04', '127.0.0.1');

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `acl_id` bigint(20) NOT NULL COMMENT '权限id',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '人员id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '别名',
  `sex` int(255) NOT NULL DEFAULT 2 COMMENT '性别，0女，1男，2保密',
  `telephone` varchar(13) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `mail` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `dept_id` bigint(20) NOT NULL COMMENT '部门id',
  `status` int(255) NOT NULL DEFAULT 1 COMMENT '用户状态，1正常，0停用，2删除',
  `is_admin` int(11) NOT NULL DEFAULT 0 COMMENT '1是，0否',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '371aeb9fc35dd56c7a7fd157aa413924', '管理员', 2, '15701000000', NULL, 'admin@qq.com', 0, 1, 1, '管理员', 'admin', '2020-08-11 11:40:57', '127.0.0.1');
INSERT INTO `sys_user` VALUES (2, 'zhangsan', '371aeb9fc35dd56c7a7fd157aa413924', '张三', 2, '12500000000', NULL, 'qq@qq.com', 8, 1, 0, '', 'admin', '2020-07-18 14:13:04', '127.0.0.1');
INSERT INTO `sys_user` VALUES (3, 'lisi', '371aeb9fc35dd56c7a7fd157aa413924', '李四', 1, '13500000000', NULL, 'lisi@qq.com', 11, 1, 0, '', 'admin', '2020-08-01 10:22:09', '127.0.0.1');
INSERT INTO `sys_user` VALUES (4, 'changxizhao', '371aeb9fc35dd56c7a7fd157aa413924', '常希钊', 1, '15701010101', NULL, '1000@qq.com', 2, 1, 0, '', 'admin', '2020-07-16 14:05:22', '127.0.0.1');
INSERT INTO `sys_user` VALUES (8, 'liming', '371aeb9fc35dd56c7a7fd157aa413924', '李明', 1, '18700000000', NULL, 'liming@163.com', 18, 1, 0, '行政一部李明', 'admin', '2020-07-18 13:59:20', '127.0.0.1');
INSERT INTO `sys_user` VALUES (9, 'email', '371aeb9fc35dd56c7a7fd157aa413924', '邮箱', 2, '15701010109', NULL, '1803336551@qq.com', 2, 1, 0, '', 'changxizhao', '2020-08-03 03:35:57', '127.0.0.1');

SET FOREIGN_KEY_CHECKS = 1;
