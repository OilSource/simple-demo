-- --------------------------------------------------------
-- 主机:                           192.168.85.40
-- 服务器版本:                        8.0.23 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 simple-demo.t_constant 结构
CREATE TABLE IF NOT EXISTS `t_constant` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `const_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '常量key',
  `const_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '常量vlaue',
  `const_status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '0无效1有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `creator` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `updater` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新人',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_constant 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_constant` DISABLE KEYS */;
INSERT INTO `t_constant` (`id`, `const_key`, `const_value`, `const_status`, `create_time`, `update_time`, `creator`, `updater`, `remark`) VALUES
	(1, 'test', '123', 1, '2021-10-09 19:59:35', '2021-10-16 21:42:40', 'admin', 'casual', '111'),
	(5, 'ignore.url', '/user/doLogin===*,/user/logout===*,/swagger-resources/**===*,/v2/api-docs===*,/doc.html===*,/webjars/**===*,/favicon.ico===*', 1, '2021-10-09 21:02:25', '2021-10-16 22:26:04', 'admin', 'admin', '消息'),
	(7, 'test3', 'fffffff2', 1, '2021-10-09 23:05:03', '2021-10-09 23:38:59', 'admin', 'admin', 'v3');
/*!40000 ALTER TABLE `t_constant` ENABLE KEYS */;

-- 导出  表 simple-demo.t_menu 结构
CREATE TABLE IF NOT EXISTS `t_menu` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '图标',
  `order_num` int unsigned NOT NULL DEFAULT '0' COMMENT '排序序号',
  `parent_id` int unsigned NOT NULL DEFAULT '0' COMMENT '上级菜单id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `perms` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `url` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单url',
  `updater` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单/按钮名称',
  `available` tinyint unsigned NOT NULL COMMENT '0不可用1可用',
  `is_open` tinyint unsigned NOT NULL COMMENT '0不展开1展开',
  `menu_type` tinyint unsigned NOT NULL COMMENT '0菜单1按钮',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_menu 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `t_menu` DISABLE KEYS */;
INSERT INTO `t_menu` (`id`, `icon`, `order_num`, `parent_id`, `create_time`, `creator`, `update_time`, `perms`, `url`, `updater`, `label`, `available`, `is_open`, `menu_type`) VALUES
	(11, 'el-icon-lx-home', 1, 0, '2021-10-03 06:06:07', 'admin', '2021-10-03 06:06:07', '', '/dashboard', 'admin', '系统首页', 1, 1, 0),
	(12, 'el-icon-lx-cascades', 1, 0, '2021-10-03 06:06:45', 'admin', '2021-10-03 06:06:45', '', '/system-manage', 'admin', '系统管理', 1, 1, 0),
	(13, 'el-icon-lx-cascades', 1, 12, '2021-10-03 06:07:01', 'admin', '2021-10-03 06:07:01', '', '/user-manage', 'admin', '用户管理', 1, 1, 0),
	(14, 'el-icon-lx-cascades', 2, 12, '2021-10-03 06:07:13', 'admin', '2021-10-10 21:12:17', '', '/role-manage', 'admin', '角色管理', 1, 1, 0),
	(15, 'el-icon-lx-cascades', 3, 12, '2021-10-03 06:07:26', 'admin', '2021-10-10 21:12:22', '', '/menu-manage', 'admin', '菜单管理', 1, 1, 0),
	(16, 'el-icon-lx-cascades', 4, 12, '2021-10-09 18:08:25', 'admin', '2021-10-10 21:12:45', '', '/constant-manage', 'admin', '常量管理', 1, 1, 0);
/*!40000 ALTER TABLE `t_menu` ENABLE KEYS */;

-- 导出  表 simple-demo.t_role 结构
CREATE TABLE IF NOT EXISTS `t_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `role_state` tinyint unsigned NOT NULL COMMENT '状态',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_role 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`id`, `role_name`, `create_time`, `updater`, `role_state`, `creator`, `update_time`, `remark`) VALUES
	(1, 'test', '2021-10-03 01:21:21', 'admin', 1, 'admin', '2021-10-03 17:25:47', '123'),
	(3, 'test2', '2021-10-10 21:13:07', 'admin', 1, 'admin', '2021-10-10 21:13:07', '11');
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;

-- 导出  表 simple-demo.t_role_menu 结构
CREATE TABLE IF NOT EXISTS `t_role_menu` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int unsigned NOT NULL COMMENT '角色id',
  `menu_id` int unsigned NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_role_menu 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `t_role_menu` DISABLE KEYS */;
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(4, 1, 11),
	(8, 3, 11),
	(15, 3, 14),
	(20, 3, 12),
	(21, 3, 16),
	(22, 1, 12),
	(23, 1, 13),
	(24, 1, 14),
	(25, 1, 15),
	(26, 1, 16);
/*!40000 ALTER TABLE `t_role_menu` ENABLE KEYS */;

-- 导出  表 simple-demo.t_user 结构
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `creator` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `updater` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `pwd` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `user_state` tinyint unsigned NOT NULL COMMENT '状态',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `username`, `create_time`, `update_time`, `creator`, `updater`, `pwd`, `user_state`, `remark`) VALUES
	(1, 'admin', '2021-10-02 18:25:37', '2022-01-25 15:51:55', 'admin', 'admin', '123456', 1, ''),
	(2, 'casual', '2021-10-03 00:04:59', '2021-10-03 00:04:59', 'admin', 'admin', '123456', 1, ''),
	(4, 'young', '2021-10-03 16:45:50', '2021-10-10 21:19:26', 'admin', 'admin', '123qwe', 1, '2');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

-- 导出  表 simple-demo.t_user_role 结构
CREATE TABLE IF NOT EXISTS `t_user_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `role_id` int unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  simple-demo.t_user_role 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES
	(2, 1, 1),
	(3, 2, 1),
	(4, 4, 3);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
