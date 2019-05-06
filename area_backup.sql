/*
Navicat MySQL Data Transfer

Source Server         : 3307
Source Server Version : 80012
Source Host           : localhost:3307
Source Database       : datas

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-06 21:44:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area_backup
-- ----------------------------
DROP TABLE IF EXISTS `area_backup`;
CREATE TABLE `area_backup` (
  `AREAID` varchar(128) NOT NULL,
  `AREACODE` varchar(128) DEFAULT NULL,
  `AREANAME` varchar(128) NOT NULL,
  `AREALEVEL` varchar(1) NOT NULL,
  `AREAFULLNAME` varchar(200) DEFAULT NULL,
  `PARENTID` varchar(128) NOT NULL,
  `VCHAR1` varchar(32) DEFAULT NULL,
  `VCHAR2` varchar(32) DEFAULT NULL,
  `VCHAR3` varchar(32) DEFAULT NULL,
  `ISUSED` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`AREAID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for basicinfo
-- ----------------------------
DROP TABLE IF EXISTS `basicinfo`;
CREATE TABLE `basicinfo` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `VALUE` varchar(512) NOT NULL,
  `TYPE` varchar(20) NOT NULL,
  `TAG` varchar(30) DEFAULT NULL,
  `ISALIVE` varchar(1) NOT NULL,
  `VCHAR1` varchar(32) DEFAULT NULL,
  `VCHAR3` varchar(60) DEFAULT NULL,
  `VCHAR2` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bookratings
-- ----------------------------
DROP TABLE IF EXISTS `bookratings`;
CREATE TABLE `bookratings` (
  `User-ID` int(11) NOT NULL DEFAULT '0',
  `ISBN` varchar(13) NOT NULL DEFAULT '',
  `Book-Rating` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`User-ID`,`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_area
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_area`;
CREATE TABLE `bss_sys_area` (
  `AREAID` varchar(32) NOT NULL,
  `AREANAME` varchar(128) NOT NULL,
  `AREALEVEL` int(11) NOT NULL,
  `AREAFULLNAME` varchar(200) DEFAULT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `SHORTNAME` varchar(32) DEFAULT NULL,
  `ISUNIT` varchar(1) DEFAULT NULL,
  `LASTUPDATE` varchar(14) DEFAULT NULL,
  `YZCODE` varchar(32) DEFAULT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`AREAID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_deploynode
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_deploynode`;
CREATE TABLE `bss_sys_deploynode` (
  `NODEID` varchar(32) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `SYSID` varchar(32) NOT NULL,
  `URL` varchar(200) NOT NULL,
  `ICON` varchar(500) DEFAULT NULL,
  `SHOWORDER` int(11) NOT NULL,
  `VCHAR1` varchar(200) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  `VCHAR2` varchar(200) DEFAULT NULL,
  `VCHAR3` varchar(200) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`NODEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_log`;
CREATE TABLE `bss_sys_log` (
  `LOGID` varchar(32) NOT NULL,
  `USERNAME` varchar(64) NOT NULL,
  `TRUENAME` varchar(20) NOT NULL,
  `CLIENTIP` varchar(32) DEFAULT NULL,
  `OPERTYPE` varchar(32) NOT NULL,
  `MODULENAME` varchar(32) NOT NULL,
  `OPERCONTENT` varchar(600) DEFAULT NULL,
  `OPERDATE` varchar(14) NOT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`LOGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_module
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_module`;
CREATE TABLE `bss_sys_module` (
  `MODULEID` varchar(32) NOT NULL,
  `SYSID` varchar(32) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `PARENTID` varchar(32) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `ICON` varchar(500) DEFAULT NULL,
  `SHOWORDER` int(11) NOT NULL,
  `ISUSED` varchar(1) NOT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MODULEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_operate
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_operate`;
CREATE TABLE `bss_sys_operate` (
  `OPERATEID` varchar(32) NOT NULL,
  `OPERATENAME` varchar(100) NOT NULL,
  `OPERATECODE` varchar(64) DEFAULT NULL,
  `METHOD` varchar(200) DEFAULT NULL,
  `MODULEID` varchar(32) DEFAULT NULL,
  `ICON` varchar(500) DEFAULT NULL,
  `SHOWORDER` int(11) NOT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_role`;
CREATE TABLE `bss_sys_role` (
  `ROLEID` varchar(32) NOT NULL,
  `ROLENAME` varchar(128) NOT NULL,
  `ROLEDES` varchar(200) DEFAULT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_rolemodule
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_rolemodule`;
CREATE TABLE `bss_sys_rolemodule` (
  `RMID` varchar(32) NOT NULL,
  `RNID` varchar(32) NOT NULL,
  `MODULEID` varchar(32) NOT NULL,
  `VCHAR1` varchar(100) DEFAULT NULL,
  `VCHAR2` varchar(100) DEFAULT NULL,
  `VCHAR3` varchar(200) DEFAULT NULL,
  `VCHAR4` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_rolenode
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_rolenode`;
CREATE TABLE `bss_sys_rolenode` (
  `RNID` varchar(32) NOT NULL,
  `RSID` varchar(32) NOT NULL,
  `NODEID` varchar(32) NOT NULL,
  `VCHAR1` varchar(100) DEFAULT NULL,
  `VCHAR2` varchar(100) DEFAULT NULL,
  `VCHAR3` varchar(200) DEFAULT NULL,
  `VCHAR4` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_roleoperate
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_roleoperate`;
CREATE TABLE `bss_sys_roleoperate` (
  `ROID` varchar(32) NOT NULL,
  `RMID` varchar(32) NOT NULL,
  `OPERATEID` varchar(32) NOT NULL,
  `VCHAR1` varchar(100) DEFAULT NULL,
  `VCHAR2` varchar(100) DEFAULT NULL,
  `VCHAR3` varchar(200) DEFAULT NULL,
  `VCHAR4` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ROID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_rolesys
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_rolesys`;
CREATE TABLE `bss_sys_rolesys` (
  `RSID` varchar(32) NOT NULL,
  `ROLEID` varchar(32) NOT NULL,
  `SYSID` varchar(32) NOT NULL,
  `VCHAR1` varchar(100) DEFAULT NULL,
  `VCHAR2` varchar(100) DEFAULT NULL,
  `VCHAR3` varchar(200) DEFAULT NULL,
  `VCHAR4` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bss_sys_system
-- ----------------------------
DROP TABLE IF EXISTS `bss_sys_system`;
CREATE TABLE `bss_sys_system` (
  `SYSID` varchar(32) NOT NULL,
  `SYSNAME` varchar(64) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `ICON` varchar(500) NOT NULL,
  `SHOWORDER` int(11) DEFAULT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`SYSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for china
-- ----------------------------
DROP TABLE IF EXISTS `china`;
CREATE TABLE `china` (
  `name` varchar(80) DEFAULT NULL,
  `code` varchar(12) DEFAULT NULL,
  `parentcode` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `id` varchar(64) NOT NULL,
  `category_id` varchar(64) NOT NULL,
  `title` varchar(255) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT '0',
  `weight_date` datetime DEFAULT NULL,
  `hits` int(11) DEFAULT '0',
  `posid` varchar(10) DEFAULT NULL,
  `custom_content_view` varchar(255) DEFAULT NULL,
  `view_config` text,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_article_create_by` (`create_by`),
  KEY `cms_article_title` (`title`),
  KEY `cms_article_keywords` (`keywords`),
  KEY `cms_article_del_flag` (`del_flag`),
  KEY `cms_article_weight` (`weight`),
  KEY `cms_article_update_date` (`update_date`),
  KEY `cms_article_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_article_data
-- ----------------------------
DROP TABLE IF EXISTS `cms_article_data`;
CREATE TABLE `cms_article_data` (
  `id` varchar(64) NOT NULL,
  `content` text,
  `copyfrom` varchar(255) DEFAULT NULL,
  `relation` varchar(255) DEFAULT NULL,
  `allow_comment` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `parent_ids` varchar(2000) NOT NULL,
  `site_id` varchar(64) DEFAULT '1',
  `office_id` varchar(64) DEFAULT NULL,
  `module` varchar(20) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT '30',
  `in_menu` char(1) DEFAULT '1',
  `in_list` char(1) DEFAULT '1',
  `show_modes` char(1) DEFAULT '0',
  `allow_comment` char(1) DEFAULT NULL,
  `is_audit` char(1) DEFAULT NULL,
  `custom_list_view` varchar(255) DEFAULT NULL,
  `custom_content_view` varchar(255) DEFAULT NULL,
  `view_config` text,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_category_parent_id` (`parent_id`),
  KEY `cms_category_module` (`module`),
  KEY `cms_category_name` (`name`),
  KEY `cms_category_sort` (`sort`),
  KEY `cms_category_del_flag` (`del_flag`),
  KEY `cms_category_office_id` (`office_id`),
  KEY `cms_category_site_id` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment` (
  `id` varchar(64) NOT NULL,
  `category_id` varchar(64) NOT NULL,
  `content_id` varchar(64) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `audit_user_id` varchar(64) DEFAULT NULL,
  `audit_date` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_comment_category_id` (`category_id`),
  KEY `cms_comment_content_id` (`content_id`),
  KEY `cms_comment_status` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_guestbook
-- ----------------------------
DROP TABLE IF EXISTS `cms_guestbook`;
CREATE TABLE `cms_guestbook` (
  `id` varchar(64) NOT NULL,
  `type` char(1) NOT NULL,
  `content` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `workunit` varchar(100) NOT NULL,
  `ip` varchar(100) NOT NULL,
  `create_date` datetime NOT NULL,
  `re_user_id` varchar(64) DEFAULT NULL,
  `re_date` datetime DEFAULT NULL,
  `re_content` varchar(100) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_guestbook_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_link
-- ----------------------------
DROP TABLE IF EXISTS `cms_link`;
CREATE TABLE `cms_link` (
  `id` varchar(64) NOT NULL,
  `category_id` varchar(64) NOT NULL,
  `title` varchar(255) NOT NULL,
  `color` varchar(50) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT '0',
  `weight_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_link_category_id` (`category_id`),
  KEY `cms_link_title` (`title`),
  KEY `cms_link_del_flag` (`del_flag`),
  KEY `cms_link_weight` (`weight`),
  KEY `cms_link_create_by` (`create_by`),
  KEY `cms_link_update_date` (`update_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cms_site
-- ----------------------------
DROP TABLE IF EXISTS `cms_site`;
CREATE TABLE `cms_site` (
  `id` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT 'default',
  `copyright` text,
  `custom_index_view` varchar(255) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cms_site_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `code` varchar(24) NOT NULL,
  `name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for core_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `core_admin_user`;
CREATE TABLE `core_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `deleted_flag` tinyint(3) unsigned NOT NULL,
  `sort` int(10) unsigned NOT NULL,
  `status` tinyint(3) unsigned NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) unsigned DEFAULT NULL,
  `fullname` varchar(30) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for core_user
-- ----------------------------
DROP TABLE IF EXISTS `core_user`;
CREATE TABLE `core_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `password` varchar(32) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `deleted_flag` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) unsigned DEFAULT NULL,
  `sort` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawlerurl
-- ----------------------------
DROP TABLE IF EXISTS `crawlerurl`;
CREATE TABLE `crawlerurl` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(254) NOT NULL,
  PRIMARY KEY (`id`,`url`)
) ENGINE=InnoDB AUTO_INCREMENT=72556 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crawlerurlssq
-- ----------------------------
DROP TABLE IF EXISTS `crawlerurlssq`;
CREATE TABLE `crawlerurlssq` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `issue` varchar(10) NOT NULL,
  `number1` int(2) NOT NULL,
  `number2` int(2) NOT NULL,
  `number3` int(2) NOT NULL,
  `number4` int(2) NOT NULL,
  `number5` int(2) NOT NULL,
  `number6` int(2) NOT NULL,
  `number7` int(2) NOT NULL,
  `url` varchar(254) NOT NULL,
  PRIMARY KEY (`id`,`url`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dbs
-- ----------------------------
DROP TABLE IF EXISTS `dbs`;
CREATE TABLE `dbs` (
  `id` varchar(32) NOT NULL,
  `user` varchar(32) NOT NULL,
  `dbname` varchar(200) NOT NULL,
  `type` varchar(64) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `port` int(11) NOT NULL,
  `url` varchar(254) NOT NULL,
  `username` varchar(40) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `commont` varchar(254) DEFAULT NULL,
  `driver` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dbname` (`dbname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dictinfo
-- ----------------------------
DROP TABLE IF EXISTS `dictinfo`;
CREATE TABLE `dictinfo` (
  `ID` varchar(32) NOT NULL,
  `DICTCODE` varchar(32) DEFAULT NULL,
  `TYPECODE` varchar(32) NOT NULL,
  `INFO` varchar(64) NOT NULL,
  `REMARK` varchar(256) DEFAULT NULL,
  `UPDATETIME` varchar(16) DEFAULT NULL,
  `DICTSORT` decimal(22,0) DEFAULT NULL,
  `ISENABLE` varchar(1) NOT NULL,
  `VALUETYPE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dicttype
-- ----------------------------
DROP TABLE IF EXISTS `dicttype`;
CREATE TABLE `dicttype` (
  `TYPECODE` varchar(32) NOT NULL,
  `TYPENAME` varchar(64) NOT NULL,
  `REMARK` varchar(256) DEFAULT NULL,
  `TYPESORT` decimal(22,0) DEFAULT NULL,
  `CODELENGTH` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`TYPECODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `fileinfo`;
CREATE TABLE `fileinfo` (
  `fileName` varchar(100) DEFAULT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `uploadDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for filetable
-- ----------------------------
DROP TABLE IF EXISTS `filetable`;
CREATE TABLE `filetable` (
  `id` varchar(32) DEFAULT NULL,
  `userid` varchar(32) DEFAULT NULL,
  `filename` varchar(250) DEFAULT NULL,
  `csend` tinyint(4) DEFAULT '0',
  `hidden` tinyint(4) DEFAULT '0',
  `pid` varchar(32) NOT NULL,
  `description` varchar(100) DEFAULT '',
  `urlname` varchar(254) DEFAULT '',
  `createtime` datetime DEFAULT NULL,
  `filetype` tinyint(4) DEFAULT '0',
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `str1` varchar(40) DEFAULT '',
  `str2` varchar(40) DEFAULT '',
  `str3` varchar(40) DEFAULT '',
  `str4` varchar(40) DEFAULT '',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uni_id_pid` (`id`,`pid`),
  KEY `ind_userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=16375 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_scheme
-- ----------------------------
DROP TABLE IF EXISTS `gen_scheme`;
CREATE TABLE `gen_scheme` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `category` varchar(2000) DEFAULT NULL,
  `package_name` varchar(500) DEFAULT NULL,
  `module_name` varchar(30) DEFAULT NULL,
  `sub_module_name` varchar(30) DEFAULT NULL,
  `function_name` varchar(500) DEFAULT NULL,
  `function_name_simple` varchar(100) DEFAULT NULL,
  `function_author` varchar(100) DEFAULT NULL,
  `gen_table_id` varchar(200) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gen_scheme_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  `parent_table` varchar(200) DEFAULT NULL,
  `parent_table_fk` varchar(100) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`),
  KEY `gen_table_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `id` varchar(64) NOT NULL,
  `gen_table_id` varchar(64) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `jdbc_type` varchar(100) DEFAULT NULL,
  `java_type` varchar(500) DEFAULT NULL,
  `java_field` varchar(200) DEFAULT NULL,
  `is_pk` char(1) DEFAULT NULL,
  `is_null` char(1) DEFAULT NULL,
  `is_insert` char(1) DEFAULT NULL,
  `is_edit` char(1) DEFAULT NULL,
  `is_list` char(1) DEFAULT NULL,
  `is_query` char(1) DEFAULT NULL,
  `query_type` varchar(200) DEFAULT NULL,
  `show_type` varchar(200) DEFAULT NULL,
  `dict_type` varchar(200) DEFAULT NULL,
  `settings` varchar(2000) DEFAULT NULL,
  `sort` decimal(10,0) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`),
  KEY `gen_table_column_name` (`name`),
  KEY `gen_table_column_sort` (`sort`),
  KEY `gen_table_column_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `category` varchar(2000) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `content` text,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gen_template_del_falg` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hotle
-- ----------------------------
DROP TABLE IF EXISTS `hotle`;
CREATE TABLE `hotle` (
  `name` varchar(120) DEFAULT NULL,
  `cardNo` varchar(10) DEFAULT NULL,
  `descriot` varchar(150) DEFAULT NULL,
  `ctfTp` varchar(6) DEFAULT NULL,
  `card` varchar(60) DEFAULT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `birthday` varchar(9) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  `zip` varchar(32) DEFAULT NULL,
  `dirty` varchar(56) DEFAULT NULL,
  `district1` varchar(6) DEFAULT NULL,
  `district2` varchar(12) DEFAULT NULL,
  `district3` varchar(6) DEFAULT NULL,
  `district4` varchar(18) DEFAULT NULL,
  `district5` varchar(21) DEFAULT NULL,
  `district6` varchar(19) DEFAULT NULL,
  `firstNm` varchar(40) DEFAULT NULL,
  `lastNm` varchar(40) DEFAULT NULL,
  `duty` varchar(48) DEFAULT NULL,
  `mobile` varchar(64) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `fax` varchar(70) DEFAULT NULL,
  `eMail` varchar(84) DEFAULT NULL,
  `nation` text,
  `taste` varchar(140) DEFAULT NULL,
  `education` varchar(48) DEFAULT NULL,
  `company` varchar(92) DEFAULT NULL,
  `cTel` varchar(60) DEFAULT NULL,
  `cAddress` varchar(108) DEFAULT NULL,
  `cZip` varchar(8) DEFAULT NULL,
  `family` varchar(12) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_card` (`card`),
  KEY `index_birthday` (`birthday`),
  KEY `index_address` (`address`),
  KEY `index_version` (`version`)
) ENGINE=MyISAM AUTO_INCREMENT=20050146 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `code` smallint(1) DEFAULT NULL,
  `tag` smallint(1) DEFAULT NULL,
  `appname` varchar(40) DEFAULT NULL,
  `flag` tinyint(1) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `oldurl` varchar(250) DEFAULT NULL,
  `width` int(4) DEFAULT NULL,
  `height` int(4) DEFAULT NULL,
  `uindex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iptable
-- ----------------------------
DROP TABLE IF EXISTS `iptable`;
CREATE TABLE `iptable` (
  `ipaddr` varchar(120) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `port` int(11) NOT NULL DEFAULT '-1',
  `addrtype` varchar(20) NOT NULL DEFAULT '',
  `ctime` varchar(20) NOT NULL DEFAULT '',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `address` (`address`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for memory
-- ----------------------------
DROP TABLE IF EXISTS `memory`;
CREATE TABLE `memory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(32) DEFAULT NULL,
  `data_scale` varchar(4) DEFAULT NULL,
  `data_y` varchar(6) DEFAULT NULL,
  `data_x` varchar(6) DEFAULT NULL,
  `tag` varchar(6) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `time` varchar(5) DEFAULT NULL,
  `item_title1` varchar(100) DEFAULT NULL,
  `item_title2` varchar(100) DEFAULT NULL,
  `show_img1` varchar(254) DEFAULT NULL,
  `show_img2` varchar(254) DEFAULT NULL,
  `music` varchar(254) DEFAULT NULL,
  `show_p` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `navigation` varchar(12) NOT NULL,
  `galleryphoto` varchar(254) DEFAULT NULL,
  `sid` varchar(16) DEFAULT NULL,
  `img_cns` varchar(254) DEFAULT NULL,
  `pubtime` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `source_url` varchar(254) NOT NULL,
  `url` varchar(254) NOT NULL,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_pubtime` (`pubtime`)
) ENGINE=MyISAM AUTO_INCREMENT=486811 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_leave
-- ----------------------------
DROP TABLE IF EXISTS `oa_leave`;
CREATE TABLE `oa_leave` (
  `id` varchar(64) NOT NULL,
  `process_instance_id` varchar(64) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `leave_type` varchar(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `reality_start_time` datetime DEFAULT NULL,
  `reality_end_time` datetime DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `oa_leave_create_by` (`create_by`),
  KEY `oa_leave_process_instance_id` (`process_instance_id`),
  KEY `oa_leave_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_notify
-- ----------------------------
DROP TABLE IF EXISTS `oa_notify`;
CREATE TABLE `oa_notify` (
  `id` varchar(64) NOT NULL,
  `type` char(1) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `files` varchar(2000) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `oa_notify_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_notify_record
-- ----------------------------
DROP TABLE IF EXISTS `oa_notify_record`;
CREATE TABLE `oa_notify_record` (
  `id` varchar(64) NOT NULL,
  `oa_notify_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `read_flag` char(1) DEFAULT '0',
  `read_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oa_notify_record_notify_id` (`oa_notify_id`),
  KEY `oa_notify_record_user_id` (`user_id`),
  KEY `oa_notify_record_read_flag` (`read_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_test_audit
-- ----------------------------
DROP TABLE IF EXISTS `oa_test_audit`;
CREATE TABLE `oa_test_audit` (
  `id` varchar(64) NOT NULL,
  `PROC_INS_ID` varchar(64) DEFAULT NULL,
  `USER_ID` varchar(64) DEFAULT NULL,
  `OFFICE_ID` varchar(64) DEFAULT NULL,
  `POST` varchar(255) DEFAULT NULL,
  `AGE` char(1) DEFAULT NULL,
  `EDU` varchar(255) DEFAULT NULL,
  `CONTENT` varchar(255) DEFAULT NULL,
  `OLDA` varchar(255) DEFAULT NULL,
  `OLDB` varchar(255) DEFAULT NULL,
  `OLDC` varchar(255) DEFAULT NULL,
  `NEWA` varchar(255) DEFAULT NULL,
  `NEWB` varchar(255) DEFAULT NULL,
  `NEWC` varchar(255) DEFAULT NULL,
  `ADD_NUM` varchar(255) DEFAULT NULL,
  `EXE_DATE` varchar(255) DEFAULT NULL,
  `HR_TEXT` varchar(255) DEFAULT NULL,
  `LEAD_TEXT` varchar(255) DEFAULT NULL,
  `MAIN_LEAD_TEXT` varchar(255) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `OA_TEST_AUDIT_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for personal
-- ----------------------------
DROP TABLE IF EXISTS `personal`;
CREATE TABLE `personal` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `information` text,
  `introduction` text,
  `birthday` varchar(8) DEFAULT NULL,
  `place` varchar(40) DEFAULT NULL,
  `age` tinyint(1) DEFAULT NULL,
  `nationality` varchar(40) DEFAULT NULL,
  `nativePlace` varchar(60) DEFAULT NULL,
  `flag` smallint(1) DEFAULT NULL,
  `role` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` varchar(6) DEFAULT NULL,
  `province` varchar(36) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(240) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `jd` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for prediction
-- ----------------------------
DROP TABLE IF EXISTS `prediction`;
CREATE TABLE `prediction` (
  `product_id` int(5) NOT NULL,
  `product_month` date NOT NULL,
  `ciiquantity_month` int(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(14) NOT NULL,
  `district_id1` varchar(40) DEFAULT NULL,
  `district_id2` varchar(40) DEFAULT NULL,
  `district_id3` varchar(40) DEFAULT NULL,
  `district_id4` varchar(40) DEFAULT NULL,
  `lat` varchar(40) DEFAULT NULL,
  `lon` varchar(40) DEFAULT NULL,
  `railway` varchar(40) DEFAULT NULL,
  `airport` varchar(40) DEFAULT NULL,
  `citycenter` varchar(40) DEFAULT NULL,
  `railway2` varchar(40) DEFAULT NULL,
  `airport2` varchar(40) DEFAULT NULL,
  `citycenter2` varchar(40) DEFAULT NULL,
  `eval` varchar(40) DEFAULT NULL,
  `eval2` varchar(40) DEFAULT NULL,
  `eval3` varchar(40) DEFAULT NULL,
  `eval4` varchar(40) DEFAULT NULL,
  `voters` varchar(40) DEFAULT NULL,
  `startdate` varchar(40) DEFAULT NULL,
  `upgradedate` varchar(40) DEFAULT NULL,
  `cooperatedate` varchar(40) DEFAULT NULL,
  `maxstock` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product_quantity
-- ----------------------------
DROP TABLE IF EXISTS `product_quantity`;
CREATE TABLE `product_quantity` (
  `product_id` varchar(20) NOT NULL,
  `product_date` varchar(30) DEFAULT NULL,
  `orderattribute1` varchar(14) DEFAULT NULL,
  `orderattribute2` varchar(14) DEFAULT NULL,
  `orderattribute3` varchar(14) DEFAULT NULL,
  `orderattribute4` varchar(14) DEFAULT NULL,
  `ciiquantity` varchar(14) DEFAULT NULL,
  `ordquantity` varchar(14) DEFAULT NULL,
  `price` varchar(14) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for region_station
-- ----------------------------
DROP TABLE IF EXISTS `region_station`;
CREATE TABLE `region_station` (
  `name` varchar(20) NOT NULL,
  `code` varchar(10) DEFAULT NULL,
  `west` double NOT NULL,
  `east` double NOT NULL,
  `south` double NOT NULL,
  `north` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for relationship
-- ----------------------------
DROP TABLE IF EXISTS `relationship`;
CREATE TABLE `relationship` (
  `auth` varchar(80) DEFAULT NULL,
  `relation` varchar(80) DEFAULT NULL,
  `target` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reports
-- ----------------------------
DROP TABLE IF EXISTS `reports`;
CREATE TABLE `reports` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alt` varchar(100) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `src1` varchar(254) DEFAULT NULL,
  `src2` text,
  `classify` varchar(20) DEFAULT NULL,
  `ctime` varchar(20) DEFAULT NULL,
  `heat` varchar(32) DEFAULT NULL,
  `content` text,
  `lastupdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42789 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_address
-- ----------------------------
DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `area_id` varchar(64) DEFAULT NULL,
  `is_default` char(1) DEFAULT NULL,
  `cookie_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `cookie_id` varchar(64) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `app_cart_cookie_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart_item`;
CREATE TABLE `shop_cart_item` (
  `id` varchar(64) NOT NULL,
  `cart_id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` tinyint(3) unsigned NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `count` int(10) unsigned NOT NULL DEFAULT '0',
  `product_id` varchar(64) NOT NULL,
  `is_ordered` char(1) NOT NULL DEFAULT '0',
  `is_selected` char(1) NOT NULL DEFAULT '1',
  `user_id` char(64) DEFAULT NULL,
  `cookie_id` char(64) DEFAULT NULL,
  `app_cart_cookie_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`,`cart_id`),
  KEY `FKCADACD250FB5F39` (`cart_id`),
  KEY `FKCADACD25CDF5619` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_cart_item_attribute
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart_item_attribute`;
CREATE TABLE `shop_cart_item_attribute` (
  `id` varchar(64) NOT NULL,
  `item_id` varchar(64) DEFAULT NULL,
  `attribute_item_id` varchar(64) DEFAULT NULL,
  `attribute_item_value_id` varchar(64) DEFAULT NULL,
  `attribute_idstring` varchar(130) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category` (
  `id` varchar(64) NOT NULL,
  `featured` char(1) DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  `featured_image` varchar(255) DEFAULT NULL,
  `image_small` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` varchar(64) NOT NULL DEFAULT '0',
  `sort` int(10) unsigned NOT NULL DEFAULT '999',
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `create_date` date DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `app_featured_home` char(1) DEFAULT '0',
  `app_featured_home_sort` int(10) unsigned DEFAULT '999',
  `parent_ids` varchar(2000) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `is_audit` char(1) DEFAULT '1',
  `meta_keywords` varchar(255) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `href_target` char(7) DEFAULT '_blank',
  `image_medium` varchar(1000) DEFAULT NULL,
  `image_large` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_collect_product
-- ----------------------------
DROP TABLE IF EXISTS `shop_collect_product`;
CREATE TABLE `shop_collect_product` (
  `id` varchar(64) NOT NULL,
  `product_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_cookie
-- ----------------------------
DROP TABLE IF EXISTS `shop_cookie`;
CREATE TABLE `shop_cookie` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_coupon
-- ----------------------------
DROP TABLE IF EXISTS `shop_coupon`;
CREATE TABLE `shop_coupon` (
  `id` varchar(64) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `duration_day` int(10) unsigned DEFAULT NULL,
  `duration_day_desc` varchar(20) DEFAULT NULL,
  `count_per_user` int(10) unsigned DEFAULT NULL,
  `used_type` char(1) DEFAULT NULL,
  `used_type_desc` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `type_desc` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_coupon_user
-- ----------------------------
DROP TABLE IF EXISTS `shop_coupon_user`;
CREATE TABLE `shop_coupon_user` (
  `id` varchar(64) NOT NULL,
  `coupon_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `has_used` char(1) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `type_desc` varchar(255) DEFAULT NULL,
  `duration_day` int(11) DEFAULT NULL,
  `duration_day_desc` varchar(255) DEFAULT NULL,
  `used_type` char(1) DEFAULT NULL,
  `used_type_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_history_product
-- ----------------------------
DROP TABLE IF EXISTS `shop_history_product`;
CREATE TABLE `shop_history_product` (
  `id` varchar(64) NOT NULL,
  `product_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `count` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_order
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  `address_fullname` varchar(255) NOT NULL,
  `address_telephone` varchar(255) NOT NULL,
  `area_id` varchar(64) NOT NULL,
  `area_name` varchar(255) NOT NULL,
  `area_parent_id` varchar(64) NOT NULL,
  `area_path_ids` varchar(2000) NOT NULL,
  `area_path_names` varchar(2000) NOT NULL,
  `area_simple_name` varchar(2000) DEFAULT NULL,
  `area_zip_code` varchar(255) DEFAULT NULL,
  `cart_id` varchar(64) DEFAULT NULL,
  `cookie_id` varchar(64) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `serial_no` varchar(255) DEFAULT NULL,
  `preorder_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `total_count` int(10) unsigned NOT NULL,
  `print_count` int(10) unsigned NOT NULL DEFAULT '0',
  `coupon_user_id` varchar(64) DEFAULT NULL,
  `coupon_user_total_price` decimal(10,2) DEFAULT NULL,
  `origin_total_price` decimal(10,2) DEFAULT NULL,
  `address_id` varchar(64) DEFAULT NULL,
  `has_paid` char(1) DEFAULT NULL,
  `pay_type` char(1) DEFAULT NULL,
  `notice` varchar(1000) DEFAULT NULL,
  `rough_pay_type` char(1) DEFAULT NULL,
  `status_id` varchar(64) DEFAULT NULL,
  `op_transaction_id` varchar(64) DEFAULT NULL,
  `status_union` varchar(255) DEFAULT NULL,
  `min_total_price_label` varchar(50) DEFAULT NULL,
  `paid_date` datetime DEFAULT NULL,
  `store_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8B9C26AE8C28AB7B` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_order_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_item`;
CREATE TABLE `shop_order_item` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `count` int(10) unsigned NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `featured_image` char(1) DEFAULT NULL,
  `image_small` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `product_id` varchar(64) NOT NULL,
  `cart_item_id` varchar(64) DEFAULT NULL,
  `order_id` varchar(64) DEFAULT NULL,
  `preorder_item_id` varchar(64) DEFAULT NULL,
  `subtotal_price` decimal(10,2) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2D110D643AE5AF1C` (`cart_item_id`),
  KEY `FK2D110D64D4FAFCF2` (`preorder_item_id`),
  KEY `FK2D110D645206E979` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_order_item_attribute
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_item_attribute`;
CREATE TABLE `shop_order_item_attribute` (
  `id` varchar(64) NOT NULL,
  `item_id` varchar(64) DEFAULT NULL,
  `attribute_item_id` varchar(64) DEFAULT NULL,
  `attribute_item_value_id` varchar(64) DEFAULT NULL,
  `attribute_idstring` varchar(130) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `attribute_item_name` varchar(20) DEFAULT NULL,
  `attribute_item_print_name` varchar(20) DEFAULT NULL,
  `attribute_item_sort` int(10) unsigned DEFAULT NULL,
  `attribute_item_value_name` varchar(20) DEFAULT NULL,
  `attribute_item_value_print_name` varchar(20) DEFAULT NULL,
  `attribute_item_value_sort` int(10) unsigned DEFAULT NULL,
  `attribute_item_value_price` decimal(10,0) DEFAULT NULL,
  `attribute_item_value_is_standard` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_order_status
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_status`;
CREATE TABLE `shop_order_status` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `order_id` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `rough_pay_type` char(1) DEFAULT NULL,
  `status_union` varchar(20) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `pending_label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_order_status_process
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_status_process`;
CREATE TABLE `shop_order_status_process` (
  `id` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `status_union` char(10) DEFAULT NULL,
  `label` varchar(20) DEFAULT NULL,
  `is_finished` char(1) DEFAULT NULL,
  `step` tinyint(3) unsigned DEFAULT NULL,
  `css_class` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_preorder
-- ----------------------------
DROP TABLE IF EXISTS `shop_preorder`;
CREATE TABLE `shop_preorder` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `cart_id` varchar(64) DEFAULT NULL,
  `cookie_id` varchar(64) DEFAULT NULL,
  `total_count` int(10) unsigned NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `is_ordered` char(1) NOT NULL DEFAULT '0',
  `coupon_user_id` varchar(64) DEFAULT NULL,
  `coupon_user_total_price` decimal(10,2) DEFAULT NULL,
  `origin_total_price` decimal(10,2) DEFAULT NULL,
  `area_id` varchar(64) DEFAULT NULL,
  `area_name` varchar(50) DEFAULT NULL,
  `area_parent_id` varchar(64) DEFAULT NULL,
  `area_path_ids` varchar(1000) DEFAULT NULL,
  `area_path_names` varchar(1000) DEFAULT NULL,
  `area_simple_name` varchar(50) DEFAULT NULL,
  `area_zip_code` varchar(10) DEFAULT NULL,
  `address_fullname` varchar(255) DEFAULT NULL,
  `address_telephone` char(11) DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  `address_id` varchar(64) DEFAULT NULL,
  `pay_type` char(1) DEFAULT NULL,
  `product_type` char(1) DEFAULT NULL,
  `rough_pay_type` char(1) DEFAULT NULL,
  `min_total_price_label` varchar(50) DEFAULT NULL,
  `store_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB3B5028B8C28AB7B` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_preorder_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_preorder_item`;
CREATE TABLE `shop_preorder_item` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `count` int(10) unsigned NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `image_small` varchar(255) DEFAULT NULL,
  `featured_image` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `product_id` varchar(64) NOT NULL,
  `cart_item_id` varchar(64) DEFAULT NULL,
  `preorder_id` varchar(64) DEFAULT NULL,
  `subtotal_price` decimal(10,2) NOT NULL,
  `type` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB1BA10473AE5AF1C` (`cart_item_id`),
  KEY `FKB1BA10473CCC7AFB` (`preorder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_preorder_item_attribute
-- ----------------------------
DROP TABLE IF EXISTS `shop_preorder_item_attribute`;
CREATE TABLE `shop_preorder_item_attribute` (
  `id` varchar(64) NOT NULL,
  `item_id` varchar(64) DEFAULT NULL,
  `attribute_item_id` varchar(64) DEFAULT NULL,
  `attribute_item_value_id` varchar(64) DEFAULT NULL,
  `attribute_idstring` varchar(130) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `attribute_item_name` varchar(20) DEFAULT NULL,
  `attribute_item_print_name` varchar(20) DEFAULT NULL,
  `attribute_item_sort` int(10) unsigned DEFAULT NULL,
  `attribute_item_value_name` varchar(20) DEFAULT NULL,
  `attribute_item_value_print_name` varchar(20) DEFAULT NULL,
  `attribute_item_value_sort` int(10) unsigned DEFAULT NULL,
  `attribute_item_value_price` decimal(10,2) DEFAULT NULL,
  `attribute_item_value_is_standard` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_product
-- ----------------------------
DROP TABLE IF EXISTS `shop_product`;
CREATE TABLE `shop_product` (
  `id` varchar(64) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `featured_image` varchar(255) DEFAULT NULL,
  `image_small` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(10) unsigned DEFAULT '999',
  `del_flag` char(1) DEFAULT '0',
  `create_date` date DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `featured_price` decimal(10,2) DEFAULT NULL,
  `featured_position` varchar(255) DEFAULT NULL,
  `featured_position_sort` int(10) unsigned DEFAULT '999',
  `app_featured_home` char(1) DEFAULT '0',
  `app_featured_home_sort` int(10) unsigned DEFAULT '999',
  `app_featured_image` varchar(255) DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `meta_keywords` varchar(255) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `is_audit` char(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `featured` char(1) DEFAULT '0',
  `description` text,
  `category_id` varchar(64) DEFAULT NULL,
  `image_medium` varchar(1000) DEFAULT NULL,
  `image_large` varchar(1000) DEFAULT NULL,
  `app_featured_topic` char(1) DEFAULT NULL,
  `app_featured_topic_sort` int(10) unsigned DEFAULT '99',
  `app_long_image1` varchar(1000) DEFAULT NULL,
  `app_long_image2` varchar(1000) DEFAULT NULL,
  `app_long_image3` varchar(1000) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `app_long_image4` varchar(255) DEFAULT NULL,
  `app_long_image5` varchar(255) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `shop_product_attribute`;
CREATE TABLE `shop_product_attribute` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `status` char(1) NOT NULL DEFAULT '1',
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `item_id` varchar(64) NOT NULL,
  `product_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC1C0E2AC5CDF5619` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_product_attribute_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_product_attribute_item`;
CREATE TABLE `shop_product_attribute_item` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `sort` int(10) unsigned NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `print_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_product_attribute_item_value
-- ----------------------------
DROP TABLE IF EXISTS `shop_product_attribute_item_value`;
CREATE TABLE `shop_product_attribute_item_value` (
  `id` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL,
  `sort` int(10) unsigned NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `item_id` varchar(64) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `print_name` varchar(20) DEFAULT NULL,
  `is_standard` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_sezi
-- ----------------------------
DROP TABLE IF EXISTS `shop_sezi`;
CREATE TABLE `shop_sezi` (
  `id` varchar(64) NOT NULL,
  `num` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `user_id` varchar(64) DEFAULT NULL,
  `is_max_in_day` char(1) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_store
-- ----------------------------
DROP TABLE IF EXISTS `shop_store`;
CREATE TABLE `shop_store` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sort` int(11) NOT NULL DEFAULT '999',
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(96) NOT NULL,
  `aliasname` varchar(96) NOT NULL,
  `urlFunctionName` varchar(96) NOT NULL,
  `urlName` varchar(254) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`,`name`),
  UNIQUE KEY `uq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `simple_name` varchar(30) DEFAULT NULL,
  `zip_code` varchar(20) DEFAULT NULL,
  `area_number` varchar(10) DEFAULT NULL,
  `level` tinyint(3) unsigned NOT NULL,
  `path_ids` varchar(2000) DEFAULT NULL,
  `path_names` varchar(2000) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `parent_ids` varchar(2000) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `sort` int(11) DEFAULT '9999',
  `shipping_group` char(1) DEFAULT NULL,
  `store_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_area_origin
-- ----------------------------
DROP TABLE IF EXISTS `sys_area_origin`;
CREATE TABLE `sys_area_origin` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `parent_ids` varchar(2000) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sort` decimal(10,0) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `key` varchar(64) NOT NULL,
  `value` varchar(1000) NOT NULL,
  `code` varchar(64) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL DEFAULT '10',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `name` varchar(32) NOT NULL,
  `code` varchar(128) DEFAULT NULL,
  `sort` int(11) DEFAULT '0',
  `linkman` varchar(64) DEFAULT NULL,
  `linkman_no` varchar(32) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(256) NOT NULL,
  `dict_type` varchar(64) NOT NULL,
  `dict_remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `UK_SYS_DICT_TYPE` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(64) NOT NULL,
  `detail_name` varchar(256) DEFAULT NULL,
  `detail_code` varchar(32) DEFAULT NULL,
  `detail_sort` varchar(32) DEFAULT NULL,
  `detail_type` varchar(32) DEFAULT NULL,
  `detail_state` varchar(32) DEFAULT NULL,
  `detail_content` varchar(256) DEFAULT NULL,
  `detail_remark` varchar(256) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_element
-- ----------------------------
DROP TABLE IF EXISTS `sys_element`;
CREATE TABLE `sys_element` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(96) NOT NULL,
  `pid` varchar(96) NOT NULL,
  `createtime` datetime NOT NULL,
  `code` varchar(96) DEFAULT NULL,
  PRIMARY KEY (`id`,`name`,`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=14364 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_file_upload
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_upload`;
CREATE TABLE `sys_file_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL DEFAULT '',
  `path` varchar(512) NOT NULL DEFAULT '',
  `factpath` varchar(512) NOT NULL,
  `ext` varchar(32) NOT NULL,
  `originalname` varchar(256) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '9',
  `size` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(256) DEFAULT NULL,
  `business_type` int(11) NOT NULL DEFAULT '1',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) NOT NULL,
  `oper_object` varchar(64) DEFAULT NULL,
  `oper_table` varchar(64) NOT NULL,
  `oper_id` int(11) DEFAULT '0',
  `oper_type` varchar(64) DEFAULT NULL,
  `oper_remark` varchar(100) DEFAULT NULL,
  `create_time` varchar(64) NOT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` int(11) NOT NULL DEFAULT '0',
  `name` varchar(200) NOT NULL DEFAULT '',
  `urlkey` varchar(256) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `type` int(11) DEFAULT '1',
  `sort` int(11) DEFAULT '1',
  `icon` varchar(254) DEFAULT '',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `parent_ids` varchar(2000) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sort` decimal(10,0) NOT NULL,
  `area_id` varchar(64) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `type` char(1) NOT NULL,
  `grade` char(1) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zip_code` varchar(100) DEFAULT NULL,
  `master` varchar(100) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `fax` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `USEABLE` varchar(64) DEFAULT NULL,
  `PRIMARY_PERSON` varchar(64) DEFAULT NULL,
  `DEPUTY_PERSON` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`),
  KEY `sys_office_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organize
-- ----------------------------
DROP TABLE IF EXISTS `sys_organize`;
CREATE TABLE `sys_organize` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(64) NOT NULL,
  `brief` varchar(254) NOT NULL,
  `instruction` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `parentid` varchar(64) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organize_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_organize_relation`;
CREATE TABLE `sys_organize_relation` (
  `id` varchar(64) NOT NULL,
  `relationid` varchar(64) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '',
  `status` int(11) DEFAULT '1',
  `sort` int(11) DEFAULT '1',
  `remark` text,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `menuid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` varchar(64) NOT NULL,
  `office_id` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms` (
  `id` varchar(64) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `msg` varchar(1000) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `expired_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `is_received` char(1) DEFAULT '0',
  `sync_return_result` varchar(1000) DEFAULT NULL,
  `code` char(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_social`;
CREATE TABLE `sys_social` (
  `id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(64) NOT NULL,
  `x` float DEFAULT NULL,
  `y` float DEFAULT NULL,
  `z` float DEFAULT NULL,
  `size` double DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_source`;
CREATE TABLE `sys_source` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` varchar(32) NOT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `type` varchar(32) NOT NULL,
  `name` varchar(96) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`,`cid`),
  UNIQUE KEY `uq_cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(70) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `realname` varchar(32) DEFAULT NULL,
  `departid` int(11) DEFAULT '0',
  `usertype` int(11) DEFAULT '2',
  `state` int(11) DEFAULT '10',
  `thirdid` varchar(200) DEFAULT NULL,
  `endtime` varchar(32) DEFAULT NULL,
  `email` varchar(64) NOT NULL,
  `tel` varchar(42) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `title_url` varchar(200) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `theme` varchar(64) DEFAULT 'default',
  `back_site_id` int(11) DEFAULT '0',
  `create_site_id` int(11) DEFAULT '1',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  `birthday` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=17425729 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for syslog
-- ----------------------------
DROP TABLE IF EXISTS `syslog`;
CREATE TABLE `syslog` (
  `ID` varchar(64) NOT NULL,
  `USERID` varchar(32) DEFAULT NULL,
  `OPERATEDATE` datetime DEFAULT NULL,
  `USERIP` varchar(20) DEFAULT NULL,
  `USERNAME` varchar(64) DEFAULT NULL,
  `LOGTYPE` varchar(1) DEFAULT NULL,
  `MESSAGES` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sysmodule
-- ----------------------------
DROP TABLE IF EXISTS `sysmodule`;
CREATE TABLE `sysmodule` (
  `MODULEID` varchar(32) NOT NULL,
  `MODULENAME` varchar(64) NOT NULL,
  `PARENTID` varchar(32) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `ICON` varchar(128) DEFAULT NULL,
  `SHOWORDER` int(11) NOT NULL,
  `ISUSED` varchar(1) NOT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MODULEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sysoperation
-- ----------------------------
DROP TABLE IF EXISTS `sysoperation`;
CREATE TABLE `sysoperation` (
  `OPERATEID` varchar(32) NOT NULL,
  `MODULEID` varchar(32) DEFAULT NULL,
  `OPERATENAME` varchar(64) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `ICON` varchar(128) DEFAULT NULL,
  `SHOWORDER` int(11) NOT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `VCHAR6` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATEID`),
  KEY `FK7F1D553A97B2F638` (`MODULEID`),
  CONSTRAINT `FK7F1D553A97B2F638` FOREIGN KEY (`MODULEID`) REFERENCES `sysmodule` (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sysrole
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `ID` varchar(32) NOT NULL,
  `ROLENAME` varchar(64) NOT NULL,
  `GROUPID` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sysrolemodule
-- ----------------------------
DROP TABLE IF EXISTS `sysrolemodule`;
CREATE TABLE `sysrolemodule` (
  `ROLEID` varchar(32) NOT NULL,
  `MODULEID` varchar(32) NOT NULL,
  PRIMARY KEY (`ROLEID`,`MODULEID`),
  KEY `FKE8CDF0CF97B2F638` (`MODULEID`),
  CONSTRAINT `FKE8CDF0CF97B2F638` FOREIGN KEY (`MODULEID`) REFERENCES `sysmodule` (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(20) NOT NULL,
  `USERNAME` varchar(128) NOT NULL,
  `GROUPID` varchar(1) NOT NULL,
  `PWD` varchar(64) NOT NULL,
  `CONTACT` varchar(128) DEFAULT NULL,
  `ADDR` varchar(256) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `USERSTATE` varchar(1) NOT NULL,
  `REMARK` varchar(256) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `SEX` varchar(4) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `MOVEPHONE` varchar(11) DEFAULT NULL,
  `FAX` varchar(20) DEFAULT NULL,
  `LASTUPDATE` varchar(14) DEFAULT NULL,
  `VCHAR1` varchar(300) DEFAULT NULL,
  `VCHAR2` varchar(300) DEFAULT NULL,
  `VCHAR3` varchar(300) DEFAULT NULL,
  `VCHAR4` varchar(300) DEFAULT NULL,
  `VCHAR5` varchar(300) DEFAULT NULL,
  `SYSID` varchar(64) DEFAULT NULL,
  `BIRTH` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERID` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `memberId` bigint(20) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` smallint(1) DEFAULT '1',
  `is_del` smallint(1) DEFAULT '0',
  `last_login_ip` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT '0',
  `addId` bigint(20) DEFAULT NULL,
  `add_name` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `area` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adminId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `adminId` (`adminId`) USING BTREE,
  KEY `roleId` (`roleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_person_base_info
-- ----------------------------
DROP TABLE IF EXISTS `t_person_base_info`;
CREATE TABLE `t_person_base_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL,
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `habit_hand` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `height` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `weight` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `waist` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_diabetes` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_hypertension` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `diabetes_year` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0',
  `diabetes_relatives` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `glycosylated_blood` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0',
  `smoking_year` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_person_friends_group
-- ----------------------------
DROP TABLE IF EXISTS `t_person_friends_group`;
CREATE TABLE `t_person_friends_group` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL,
  `group_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_default` smallint(1) DEFAULT NULL,
  `is_del` smallint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_person_friends_members
-- ----------------------------
DROP TABLE IF EXISTS `t_person_friends_members`;
CREATE TABLE `t_person_friends_members` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `groupId` bigint(11) DEFAULT NULL,
  `friend_id` bigint(11) DEFAULT NULL,
  `add_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_person_friends_right
-- ----------------------------
DROP TABLE IF EXISTS `t_person_friends_right`;
CREATE TABLE `t_person_friends_right` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL,
  `friend_id` bigint(11) DEFAULT NULL,
  `right_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` smallint(2) DEFAULT NULL,
  `add_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_person_message
-- ----------------------------
DROP TABLE IF EXISTS `t_person_message`;
CREATE TABLE `t_person_message` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `type` smallint(1) DEFAULT NULL,
  `user_id` bigint(11) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `check_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `value` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `abnormity_description` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `check_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `push_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_del` smallint(1) DEFAULT '0',
  `equipment_id` bigint(11) DEFAULT NULL,
  `is_push_success` smallint(1) DEFAULT '1',
  `blood_type` smallint(1) DEFAULT NULL,
  `friend_id` bigint(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_right
-- ----------------------------
DROP TABLE IF EXISTS `t_right`;
CREATE TABLE `t_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `action_url` varchar(255) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `enable` smallint(1) DEFAULT '1',
  `indexs` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `status` smallint(1) DEFAULT '1',
  `addId` bigint(20) DEFAULT NULL,
  `add_name` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_right
-- ----------------------------
DROP TABLE IF EXISTS `t_role_right`;
CREATE TABLE `t_role_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `rightId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`) USING BTREE,
  KEY `rightId` (`rightId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `introduce` varchar(255) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `enable` tinyint(4) DEFAULT '1',
  `content` text,
  `title` varchar(100) DEFAULT NULL,
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_login
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login`;
CREATE TABLE `t_user_login` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `status` smallint(1) DEFAULT '1',
  `login_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `last_login_ip` varchar(20) DEFAULT '',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `create_time_stamp` bigint(20) DEFAULT NULL,
  `last_login_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `equipment_number` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `app_number` varchar(100) DEFAULT '',
  `wechat` varchar(30) DEFAULT '',
  `QQ` varchar(30) DEFAULT '',
  `weblog` varchar(30) DEFAULT '',
  `is_accept_push` smallint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `id` varchar(32) NOT NULL,
  `dbname` varchar(200) NOT NULL,
  `tablename` varchar(32) NOT NULL,
  `user` varchar(32) NOT NULL,
  `type` varchar(64) NOT NULL,
  `isnull` varchar(64) NOT NULL,
  `commont` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskgroup
-- ----------------------------
DROP TABLE IF EXISTS `taskgroup`;
CREATE TABLE `taskgroup` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `disable` tinyint(1) NOT NULL DEFAULT '0',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_advice_feedback
-- ----------------------------
DROP TABLE IF EXISTS `tb_advice_feedback`;
CREATE TABLE `tb_advice_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `qq` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `telphone` varchar(32) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `folder_id` int(11) DEFAULT '1',
  `title` varchar(200) DEFAULT '',
  `content` longtext,
  `count_view` int(11) DEFAULT '0',
  `count_comment` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '1',
  `status` varchar(20) DEFAULT '1',
  `is_comment` int(11) DEFAULT '1',
  `is_recommend` int(11) DEFAULT '2',
  `sort` int(11) DEFAULT '1',
  `jump_url` varchar(256) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `image_net_url` varchar(256) DEFAULT NULL,
  `file_url` varchar(256) DEFAULT NULL,
  `file_name` varchar(256) DEFAULT NULL,
  `approve_status` int(11) DEFAULT NULL,
  `publish_time` varchar(64) DEFAULT NULL,
  `publish_user` varchar(64) DEFAULT '1',
  `start_time` varchar(64) DEFAULT NULL,
  `end_time` varchar(64) DEFAULT NULL,
  `update_time` varchar(64) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_articlelike
-- ----------------------------
DROP TABLE IF EXISTS `tb_articlelike`;
CREATE TABLE `tb_articlelike` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fatherId` int(11) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  `content` text NOT NULL,
  `status` int(11) DEFAULT '11',
  `reply_userid` int(11) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_contact
-- ----------------------------
DROP TABLE IF EXISTS `tb_contact`;
CREATE TABLE `tb_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `addr` varchar(256) DEFAULT NULL,
  `birthday` varchar(32) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_error
-- ----------------------------
DROP TABLE IF EXISTS `tb_error`;
CREATE TABLE `tb_error` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `ip` varchar(64) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `content` text,
  `remark` text,
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_folder
-- ----------------------------
DROP TABLE IF EXISTS `tb_folder`;
CREATE TABLE `tb_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `key` varchar(100) DEFAULT '',
  `path` varchar(200) DEFAULT '',
  `content` text,
  `sort` int(11) DEFAULT '1',
  `status` int(11) DEFAULT '1',
  `type` int(11) DEFAULT '1',
  `jump_url` varchar(200) DEFAULT NULL,
  `material_type` int(11) DEFAULT NULL,
  `site_id` int(11) DEFAULT NULL,
  `seo_title` varchar(200) DEFAULT NULL,
  `seo_keywords` varchar(200) DEFAULT NULL,
  `seo_description` varchar(200) DEFAULT NULL,
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=261 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_folder_notice
-- ----------------------------
DROP TABLE IF EXISTS `tb_folder_notice`;
CREATE TABLE `tb_folder_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `folder_id` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `icon` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_folder_roll_picture
-- ----------------------------
DROP TABLE IF EXISTS `tb_folder_roll_picture`;
CREATE TABLE `tb_folder_roll_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `folder_id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT '',
  `content` varchar(2000) DEFAULT NULL,
  `sort` int(11) DEFAULT '1',
  `status` int(11) DEFAULT '1',
  `image_url` varchar(256) DEFAULT NULL,
  `image_net_url` varchar(256) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT '0',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_friendlylink
-- ----------------------------
DROP TABLE IF EXISTS `tb_friendlylink`;
CREATE TABLE `tb_friendlylink` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `url` varchar(256) NOT NULL,
  `sort` int(11) NOT NULL,
  `state` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '21',
  `remark` varchar(256) DEFAULT NULL,
  `site_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_image
-- ----------------------------
DROP TABLE IF EXISTS `tb_image`;
CREATE TABLE `tb_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_id` int(11) DEFAULT '1',
  `album_name` varchar(200) DEFAULT '',
  `name` varchar(200) DEFAULT '',
  `linkurl` varchar(400) DEFAULT '',
  `cdnurl` varchar(400) DEFAULT '',
  `image_url` varchar(256) DEFAULT NULL,
  `image_net_url` varchar(256) DEFAULT NULL,
  `ext` varchar(20) DEFAULT '',
  `width` varchar(20) DEFAULT '',
  `height` varchar(20) DEFAULT '',
  `status` int(11) DEFAULT '1',
  `is_comment` int(11) DEFAULT '1',
  `is_recommend` int(11) DEFAULT '2',
  `sort` int(11) DEFAULT '1',
  `remark` varchar(400) DEFAULT NULL,
  `publish_time` varchar(64) DEFAULT NULL,
  `publish_user` varchar(64) DEFAULT '1',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_image_album
-- ----------------------------
DROP TABLE IF EXISTS `tb_image_album`;
CREATE TABLE `tb_image_album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `remark` text,
  `sort` int(11) DEFAULT '1',
  `status` int(11) DEFAULT '1',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_image_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_image_tags`;
CREATE TABLE `tb_image_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_id` int(11) DEFAULT NULL,
  `tagname` varchar(200) DEFAULT '',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_pageview
-- ----------------------------
DROP TABLE IF EXISTS `tb_pageview`;
CREATE TABLE `tb_pageview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `create_day` varchar(64) NOT NULL,
  `create_time` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_site
-- ----------------------------
DROP TABLE IF EXISTS `tb_site`;
CREATE TABLE `tb_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `template` varchar(32) DEFAULT NULL,
  `template_mobile` varchar(32) DEFAULT NULL,
  `domain_pc` varchar(64) DEFAULT NULL,
  `domain_mobile` varchar(64) DEFAULT NULL,
  `domain_others` varchar(400) DEFAULT NULL,
  `site_title` varchar(256) DEFAULT NULL,
  `site_folder_id` int(11) DEFAULT NULL,
  `site_article_id` int(11) DEFAULT NULL,
  `thumbnail` varchar(256) DEFAULT NULL,
  `db_url` varchar(200) DEFAULT NULL,
  `db_user` varchar(64) DEFAULT NULL,
  `db_pwd` varchar(64) DEFAULT NULL,
  `db_driver` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT '10',
  `status` int(2) DEFAULT '1',
  `site_defalut` int(2) DEFAULT '2',
  `remark` varchar(1000) DEFAULT NULL,
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_tags`;
CREATE TABLE `tb_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `tagname` varchar(200) DEFAULT '',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4740 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_video
-- ----------------------------
DROP TABLE IF EXISTS `tb_video`;
CREATE TABLE `tb_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_id` int(11) DEFAULT '1',
  `album_name` varchar(200) DEFAULT '',
  `name` varchar(200) DEFAULT '',
  `video_url` varchar(256) DEFAULT NULL,
  `video_net_url` varchar(256) DEFAULT NULL,
  `thumbnail` varchar(256) DEFAULT '',
  `ext` varchar(20) DEFAULT '',
  `resolution` varchar(20) DEFAULT '',
  `status` int(11) DEFAULT '1',
  `is_comment` int(11) DEFAULT '1',
  `is_recommend` int(11) DEFAULT '2',
  `sort` int(11) DEFAULT '1',
  `remark` varchar(400) DEFAULT NULL,
  `publish_time` varchar(64) DEFAULT NULL,
  `publish_user` varchar(64) DEFAULT '1',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_video_album
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_album`;
CREATE TABLE `tb_video_album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `remark` text,
  `sort` int(11) DEFAULT '1',
  `status` int(11) DEFAULT '1',
  `update_time` varchar(64) DEFAULT NULL,
  `update_id` int(11) DEFAULT '0',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_video_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_tags`;
CREATE TABLE `tb_video_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` int(11) DEFAULT NULL,
  `tagname` varchar(200) DEFAULT '',
  `create_time` varchar(64) DEFAULT NULL,
  `create_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test_data_child
-- ----------------------------
DROP TABLE IF EXISTS `test_data_child`;
CREATE TABLE `test_data_child` (
  `id` varchar(64) NOT NULL,
  `test_data_main_id` varchar(64) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `test_data_child_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test_data_main
-- ----------------------------
DROP TABLE IF EXISTS `test_data_main`;
CREATE TABLE `test_data_main` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `office_id` varchar(64) DEFAULT NULL,
  `area_id` varchar(64) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `in_date` date DEFAULT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `test_data_main_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test_tree
-- ----------------------------
DROP TABLE IF EXISTS `test_tree`;
CREATE TABLE `test_tree` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `parent_ids` varchar(2000) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sort` decimal(10,0) NOT NULL,
  `create_by` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(64) NOT NULL,
  `update_date` datetime NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `test_tree_del_flag` (`del_flag`),
  KEY `test_data_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for uploadinfo
-- ----------------------------
DROP TABLE IF EXISTS `uploadinfo`;
CREATE TABLE `uploadinfo` (
  `md5` varchar(6) DEFAULT NULL,
  `chunks` varchar(24) DEFAULT NULL,
  `chunk` varchar(36) DEFAULT NULL,
  `path` varchar(45) DEFAULT NULL,
  `fileName` varchar(240) DEFAULT NULL,
  `ext` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userarea
-- ----------------------------
DROP TABLE IF EXISTS `userarea`;
CREATE TABLE `userarea` (
  `USERID` varchar(32) NOT NULL,
  `AREAID` varchar(32) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`USERID`,`AREAID`),
  KEY `FK1EC9CED8FF20B276` (`USERID`),
  CONSTRAINT `FK1EC9CED8FF20B276` FOREIGN KEY (`USERID`) REFERENCES `sysuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usergys
-- ----------------------------
DROP TABLE IF EXISTS `usergys`;
CREATE TABLE `usergys` (
  `ID` varchar(64) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `LB` varchar(32) DEFAULT NULL,
  `XKZ` varchar(128) DEFAULT NULL,
  `XKZYXQ` varchar(128) DEFAULT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `DH` varchar(64) DEFAULT NULL,
  `JYFW` varchar(256) DEFAULT NULL,
  `ZCDZ` varchar(128) DEFAULT NULL,
  `LXDZ` varchar(128) DEFAULT NULL,
  `YZBM` varchar(32) DEFAULT NULL,
  `ZZC` varchar(32) DEFAULT NULL,
  `CZ` varchar(64) DEFAULT NULL,
  `FRMC` varchar(16) DEFAULT NULL,
  `FRSFZ` varchar(64) DEFAULT NULL,
  `ZCZJ` varchar(32) DEFAULT NULL,
  `XSE` varchar(32) DEFAULT NULL,
  `DZYX` varchar(128) DEFAULT NULL,
  `WZ` varchar(256) DEFAULT NULL,
  `DMZH` varchar(128) DEFAULT NULL,
  `DMZHYXQ` varchar(128) DEFAULT NULL,
  `YYZZ` varchar(64) DEFAULT NULL,
  `YYZZYXQ` varchar(128) DEFAULT NULL,
  `XYZ` varchar(1) DEFAULT NULL,
  `XYZBM` varchar(64) DEFAULT NULL,
  `XYZYXQ` varchar(128) DEFAULT NULL,
  `GDZC` varchar(32) DEFAULT NULL,
  `JJ` varchar(255) DEFAULT NULL,
  `BZ` varchar(200) DEFAULT NULL,
  `VCHAR1` varchar(128) DEFAULT NULL,
  `VCHAR2` varchar(128) DEFAULT NULL,
  `VCHAR3` varchar(128) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MC` (`MC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usergysarea
-- ----------------------------
DROP TABLE IF EXISTS `usergysarea`;
CREATE TABLE `usergysarea` (
  `USERGYSID` varchar(64) NOT NULL,
  `AREAID` varchar(32) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`USERGYSID`,`AREAID`),
  KEY `FK28BB7783A46DFFFF` (`USERGYSID`),
  CONSTRAINT `FK28BB7783A46DFFFF` FOREIGN KEY (`USERGYSID`) REFERENCES `usergys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userjd
-- ----------------------------
DROP TABLE IF EXISTS `userjd`;
CREATE TABLE `userjd` (
  `ID` varchar(64) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `DZ` varchar(256) DEFAULT NULL,
  `YZBM` varchar(32) DEFAULT NULL,
  `XLR` varchar(64) DEFAULT NULL,
  `DH` varchar(64) DEFAULT NULL,
  `CZ` varchar(64) DEFAULT NULL,
  `DZYX` varchar(128) DEFAULT NULL,
  `WZ` varchar(128) DEFAULT NULL,
  `VCHAR1` varchar(128) DEFAULT NULL,
  `VCHAR2` varchar(128) DEFAULT NULL,
  `VCHAR3` varchar(128) DEFAULT NULL,
  `DQ` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MC` (`MC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `USERID` varchar(32) NOT NULL,
  `USERROLE` varchar(32) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`USERID`,`USERROLE`),
  KEY `FK1ED17EC1211FA3BC` (`USERROLE`),
  CONSTRAINT `FK1ED17EC1211FA3BC` FOREIGN KEY (`USERROLE`) REFERENCES `sysrole` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for useryy
-- ----------------------------
DROP TABLE IF EXISTS `useryy`;
CREATE TABLE `useryy` (
  `ID` varchar(64) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `DZ` varchar(256) DEFAULT NULL,
  `YZBM` varchar(32) DEFAULT NULL,
  `DQ` varchar(128) DEFAULT NULL,
  `JB` varchar(32) DEFAULT NULL,
  `CWS` varchar(64) DEFAULT NULL,
  `FYLJG` varchar(1) DEFAULT NULL,
  `DH` varchar(64) DEFAULT NULL,
  `YJKDH` varchar(64) DEFAULT NULL,
  `LB` varchar(64) DEFAULT NULL,
  `YPSR` varchar(32) DEFAULT NULL,
  `YWSR` varchar(32) DEFAULT NULL,
  `CZ` varchar(64) DEFAULT NULL,
  `VCHAR1` varchar(128) DEFAULT NULL,
  `VCHAR2` varchar(128) DEFAULT NULL,
  `VCHAR3` varchar(128) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MC` (`MC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weather
-- ----------------------------
DROP TABLE IF EXISTS `weather`;
CREATE TABLE `weather` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cityname` varchar(24) DEFAULT NULL,
  `citycode` varchar(24) DEFAULT NULL,
  `date` varchar(32) DEFAULT NULL,
  `high` varchar(12) DEFAULT NULL,
  `low` varchar(12) DEFAULT NULL,
  `status` varchar(12) DEFAULT NULL,
  `wind` varchar(12) DEFAULT NULL,
  `direction1` varchar(12) DEFAULT NULL,
  `direction2` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `createtime` (`createtime`)
) ENGINE=MyISAM AUTO_INCREMENT=2272767 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yppmbackup
-- ----------------------------
DROP TABLE IF EXISTS `yppmbackup`;
CREATE TABLE `yppmbackup` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `JX` varchar(32) NOT NULL,
  `DW` varchar(64) DEFAULT NULL,
  `ZHXS` varchar(16) NOT NULL,
  `LB` varchar(32) DEFAULT NULL,
  `ZT` varchar(1) DEFAULT NULL,
  `ZL` varchar(16) DEFAULT NULL,
  `HL` varchar(16) DEFAULT NULL,
  `YB` varchar(1) DEFAULT NULL,
  `YBBM` varchar(128) DEFAULT NULL,
  `BZ` varchar(200) DEFAULT NULL,
  `VCHAR1` varchar(768) DEFAULT NULL,
  `VCHAR2` varchar(128) DEFAULT NULL,
  `VCHAR3` varchar(128) DEFAULT NULL,
  `GG` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ypxx
-- ----------------------------
DROP TABLE IF EXISTS `ypxx`;
CREATE TABLE `ypxx` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(32) NOT NULL,
  `SCQYMC` varchar(128) NOT NULL,
  `SPMC` varchar(64) NOT NULL,
  `ZBJG` double NOT NULL,
  `ZPDZ` varchar(128) DEFAULT NULL,
  `PZWH` varchar(64) DEFAULT NULL,
  `PZWHYXQ` datetime DEFAULT NULL,
  `JKY` varchar(1) DEFAULT NULL,
  `BZCZ` varchar(64) DEFAULT NULL,
  `BZDW` varchar(64) DEFAULT NULL,
  `LSJG` double DEFAULT NULL,
  `LSJGCC` varchar(64) DEFAULT NULL,
  `ZLCC` varchar(32) DEFAULT NULL,
  `ZLCCSM` varchar(200) DEFAULT NULL,
  `YPJYBG` varchar(1) DEFAULT NULL,
  `YPJYBGBM` varchar(128) DEFAULT NULL,
  `YPJYBGYXQ` datetime DEFAULT NULL,
  `CPSM` varchar(255) DEFAULT NULL,
  `JYZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(128) DEFAULT NULL,
  `VCHAR2` varchar(128) DEFAULT NULL,
  `VCHAR3` varchar(128) DEFAULT NULL,
  `DW` varchar(32) DEFAULT NULL,
  `MC` varchar(128) DEFAULT NULL,
  `JX` varchar(32) DEFAULT NULL,
  `GG` varchar(128) DEFAULT NULL,
  `ZHXS` varchar(16) DEFAULT NULL,
  `PINYIN` varchar(768) DEFAULT NULL,
  `LB` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yybusiness
-- ----------------------------
DROP TABLE IF EXISTS `yybusiness`;
CREATE TABLE `yybusiness` (
  `ID` varchar(32) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `ZBJG` double NOT NULL,
  `JYJG` double NOT NULL,
  `CGL` decimal(22,0) NOT NULL,
  `CGJE` double NOT NULL,
  `CGZT` varchar(1) NOT NULL,
  `RKL` decimal(22,0) DEFAULT NULL,
  `RKJE` double DEFAULT NULL,
  `RKDH` varchar(32) DEFAULT NULL,
  `YPPH` varchar(32) DEFAULT NULL,
  `YPYXQ` double DEFAULT NULL,
  `RKTIME` datetime DEFAULT NULL,
  `FHTIME` datetime DEFAULT NULL,
  `YYTHDID` varchar(32) DEFAULT NULL,
  `THL` varchar(32) DEFAULT NULL,
  `THJE` double DEFAULT NULL,
  `THZT` varchar(1) DEFAULT NULL,
  `THYY` varchar(100) DEFAULT NULL,
  `YYJSDID` varchar(32) DEFAULT NULL,
  `JSL` decimal(22,0) DEFAULT NULL,
  `JSJE` double DEFAULT NULL,
  `JSZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `USERGYSID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK639D9AE0D3C9AB79` (`USERYYID`),
  KEY `FK639D9AE08973E2D1` (`YPXXID`),
  CONSTRAINT `FK639D9AE08973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`),
  CONSTRAINT `FK639D9AE0D3C9AB79` FOREIGN KEY (`USERYYID`) REFERENCES `useryy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yybusiness2014
-- ----------------------------
DROP TABLE IF EXISTS `yybusiness2014`;
CREATE TABLE `yybusiness2014` (
  `ID` varchar(32) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `ZBJG` double NOT NULL,
  `JYJG` double NOT NULL,
  `CGL` decimal(22,0) NOT NULL,
  `CGJE` double NOT NULL,
  `CGZT` varchar(1) NOT NULL,
  `RKL` decimal(22,0) DEFAULT NULL,
  `RKJE` double DEFAULT NULL,
  `RKDH` varchar(32) DEFAULT NULL,
  `YPPH` varchar(32) DEFAULT NULL,
  `YPYXQ` double DEFAULT NULL,
  `RKTIME` datetime DEFAULT NULL,
  `FHTIME` datetime DEFAULT NULL,
  `YYTHDID` varchar(32) DEFAULT NULL,
  `THL` varchar(32) DEFAULT NULL,
  `THJE` double DEFAULT NULL,
  `THZT` varchar(1) DEFAULT NULL,
  `THYY` varchar(100) DEFAULT NULL,
  `YYJSDID` varchar(32) DEFAULT NULL,
  `JSL` decimal(22,0) DEFAULT NULL,
  `JSJE` double DEFAULT NULL,
  `JSZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `USERGYSID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD3AC9FC1D3C9AB79` (`USERYYID`),
  KEY `FKD3AC9FC18973E2D1` (`YPXXID`),
  CONSTRAINT `FKD3AC9FC18973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`),
  CONSTRAINT `FKD3AC9FC1D3C9AB79` FOREIGN KEY (`USERYYID`) REFERENCES `useryy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yybusiness2015
-- ----------------------------
DROP TABLE IF EXISTS `yybusiness2015`;
CREATE TABLE `yybusiness2015` (
  `ID` varchar(32) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `ZBJG` double NOT NULL,
  `JYJG` double NOT NULL,
  `CGL` decimal(22,0) NOT NULL,
  `CGJE` double NOT NULL,
  `CGZT` varchar(1) NOT NULL,
  `RKL` decimal(22,0) DEFAULT NULL,
  `RKJE` double DEFAULT NULL,
  `RKDH` varchar(32) DEFAULT NULL,
  `YPPH` varchar(32) DEFAULT NULL,
  `YPYXQ` double DEFAULT NULL,
  `RKTIME` datetime DEFAULT NULL,
  `FHTIME` datetime DEFAULT NULL,
  `YYTHDID` varchar(32) DEFAULT NULL,
  `THL` varchar(32) DEFAULT NULL,
  `THJE` double DEFAULT NULL,
  `THZT` varchar(1) DEFAULT NULL,
  `THYY` varchar(100) DEFAULT NULL,
  `YYJSDID` varchar(32) DEFAULT NULL,
  `JSL` decimal(22,0) DEFAULT NULL,
  `JSJE` double DEFAULT NULL,
  `JSZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `USERGYSID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD3AC9FC2D3C9AB79` (`USERYYID`),
  KEY `FKD3AC9FC28973E2D1` (`YPXXID`),
  CONSTRAINT `FKD3AC9FC28973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`),
  CONSTRAINT `FKD3AC9FC2D3C9AB79` FOREIGN KEY (`USERYYID`) REFERENCES `useryy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgd
-- ----------------------------
DROP TABLE IF EXISTS `yycgd`;
CREATE TABLE `yycgd` (
  `ID` varchar(32) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime NOT NULL,
  `TJTIME` datetime DEFAULT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `ZT` varchar(1) NOT NULL,
  `SHYJ` varchar(256) DEFAULT NULL,
  `SHTIME` datetime DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  `XGTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`),
  KEY `FK50FA540D3C9AB79` (`USERYYID`),
  CONSTRAINT `FK50FA540D3C9AB79` FOREIGN KEY (`USERYYID`) REFERENCES `useryy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgd2014
-- ----------------------------
DROP TABLE IF EXISTS `yycgd2014`;
CREATE TABLE `yycgd2014` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime NOT NULL,
  `TJTIME` datetime DEFAULT NULL,
  `XGTIME` datetime DEFAULT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `KSGHDATE` datetime DEFAULT NULL,
  `JSGHDATE` datetime DEFAULT NULL,
  `ZT` varchar(1) NOT NULL,
  `SHYJ` varchar(256) DEFAULT NULL,
  `SHTIME` datetime DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgdmx
-- ----------------------------
DROP TABLE IF EXISTS `yycgdmx`;
CREATE TABLE `yycgdmx` (
  `ID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `USERGYSID` varchar(64) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `ZBJG` double DEFAULT NULL,
  `JYJG` double DEFAULT NULL,
  `CGL` decimal(22,0) DEFAULT NULL,
  `CGJE` double DEFAULT NULL,
  `CGZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKFFBB5EEB8973E2D1` (`YPXXID`),
  KEY `FKFFBB5EEBA46DFFFF` (`USERGYSID`),
  CONSTRAINT `FKFFBB5EEB8973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`),
  CONSTRAINT `FKFFBB5EEBA46DFFFF` FOREIGN KEY (`USERGYSID`) REFERENCES `usergys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgdmx2014
-- ----------------------------
DROP TABLE IF EXISTS `yycgdmx2014`;
CREATE TABLE `yycgdmx2014` (
  `ID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `USERGYSID` varchar(64) NOT NULL,
  `ZBJG` double NOT NULL,
  `JYJG` double DEFAULT NULL,
  `CGL` decimal(22,0) DEFAULT NULL,
  `CGJE` double DEFAULT NULL,
  `CGZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKE3E3664C8973E2D1` (`YPXXID`),
  KEY `FKE3E3664CB0EE0AF4` (`YYCGDID`),
  CONSTRAINT `FKE3E3664C8973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`),
  CONSTRAINT `FKE3E3664CB0EE0AF4` FOREIGN KEY (`YYCGDID`) REFERENCES `yycgd2014` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgdrk
-- ----------------------------
DROP TABLE IF EXISTS `yycgdrk`;
CREATE TABLE `yycgdrk` (
  `ID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  `RKL` decimal(22,0) NOT NULL,
  `RKJE` double NOT NULL,
  `RKDH` varchar(32) NOT NULL,
  `YPPH` varchar(32) NOT NULL,
  `YPYXQ` double NOT NULL,
  `RKTIME` datetime NOT NULL,
  `CGZT` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yycgdrk2014
-- ----------------------------
DROP TABLE IF EXISTS `yycgdrk2014`;
CREATE TABLE `yycgdrk2014` (
  `ID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  `RKL` decimal(22,0) NOT NULL,
  `CGZT` varchar(1) NOT NULL,
  `RKJE` double NOT NULL,
  `RKDH` varchar(32) NOT NULL,
  `YPPH` varchar(32) NOT NULL,
  `YPYXQ` double NOT NULL,
  `RKTIME` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yyjsd
-- ----------------------------
DROP TABLE IF EXISTS `yyjsd`;
CREATE TABLE `yyjsd` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime NOT NULL,
  `XGTIME` datetime DEFAULT NULL,
  `TJTIME` datetime NOT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `ZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yyjsd2014
-- ----------------------------
DROP TABLE IF EXISTS `yyjsd2014`;
CREATE TABLE `yyjsd2014` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime NOT NULL,
  `TJTIME` datetime DEFAULT NULL,
  `XGTIME` datetime DEFAULT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `ZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yyjsdmx
-- ----------------------------
DROP TABLE IF EXISTS `yyjsdmx`;
CREATE TABLE `yyjsdmx` (
  `ID` varchar(32) NOT NULL,
  `YYJSDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `JSL` decimal(22,0) NOT NULL,
  `JSJE` double NOT NULL,
  `JSZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yyjsdmx2014
-- ----------------------------
DROP TABLE IF EXISTS `yyjsdmx2014`;
CREATE TABLE `yyjsdmx2014` (
  `ID` varchar(32) NOT NULL,
  `YYJSDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `JSL` decimal(22,0) NOT NULL,
  `JSJE` double NOT NULL,
  `JSZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yythd
-- ----------------------------
DROP TABLE IF EXISTS `yythd`;
CREATE TABLE `yythd` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) DEFAULT NULL,
  `MC` varchar(128) DEFAULT NULL,
  `USERYYID` varchar(64) DEFAULT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime DEFAULT NULL,
  `XGTIME` datetime DEFAULT NULL,
  `TJTIME` datetime DEFAULT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `ZT` varchar(1) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yythd2014
-- ----------------------------
DROP TABLE IF EXISTS `yythd2014`;
CREATE TABLE `yythd2014` (
  `ID` varchar(32) NOT NULL,
  `BM` varchar(10) NOT NULL,
  `MC` varchar(128) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `LXR` varchar(64) DEFAULT NULL,
  `LXDH` varchar(64) DEFAULT NULL,
  `CJR` varchar(64) DEFAULT NULL,
  `CJTIME` datetime NOT NULL,
  `TJTIME` datetime DEFAULT NULL,
  `XGTIME` datetime DEFAULT NULL,
  `BZ` varchar(256) DEFAULT NULL,
  `ZT` varchar(1) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BM` (`BM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yythdmx
-- ----------------------------
DROP TABLE IF EXISTS `yythdmx`;
CREATE TABLE `yythdmx` (
  `ID` varchar(32) NOT NULL,
  `YYTHDID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `THL` decimal(22,0) NOT NULL,
  `THJE` double NOT NULL,
  `THZT` varchar(1) NOT NULL,
  `THYY` varchar(100) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yythdmx2014
-- ----------------------------
DROP TABLE IF EXISTS `yythdmx2014`;
CREATE TABLE `yythdmx2014` (
  `ID` varchar(32) NOT NULL,
  `YYTHDID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `YYCGDID` varchar(32) NOT NULL,
  `THL` decimal(22,0) NOT NULL,
  `THJE` double NOT NULL,
  `THZT` varchar(1) NOT NULL,
  `THYY` varchar(100) DEFAULT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  `VCHAR3` varchar(64) DEFAULT NULL,
  `VCHAR4` varchar(128) DEFAULT NULL,
  `VCHAR5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yyypml
-- ----------------------------
DROP TABLE IF EXISTS `yyypml`;
CREATE TABLE `yyypml` (
  `ID` varchar(32) NOT NULL,
  `YPXXID` varchar(32) NOT NULL,
  `USERYYID` varchar(64) NOT NULL,
  `USERGYSID` varchar(64) NOT NULL,
  `VCHAR1` varchar(64) DEFAULT NULL,
  `VCHAR2` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9CEF26168973E2D1` (`YPXXID`),
  CONSTRAINT `FK9CEF26168973E2D1` FOREIGN KEY (`YPXXID`) REFERENCES `ypxx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
