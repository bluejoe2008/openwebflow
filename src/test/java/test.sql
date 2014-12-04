/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50016
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50016
File Encoding         : 65001

Date: 2014-12-03 21:47:04
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `activity_acl_tab`
-- ----------------------------
DROP TABLE IF EXISTS `activity_acl_tab`;
CREATE TABLE `activity_acl_tab` (
  `ID` int(19) NOT NULL auto_increment,
  `ACTIVITY_KEY` varchar(255) default NULL,
  `ASSIGNED_USER` varchar(255) default NULL,
  `GRANTED_GROUPS` varchar(255) default NULL,
  `GRANTED_USERS` varchar(255) default NULL,
  `PROCESS_DEF_ID` varchar(255) default NULL,
  `OP_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_acl_tab
-- ----------------------------

-- ----------------------------
-- Table structure for `delegation_tab`
-- ----------------------------
DROP TABLE IF EXISTS `delegation_tab`;
CREATE TABLE `delegation_tab` (
  `ID` int(19) NOT NULL auto_increment,
  `DELEGATED` varchar(255) default NULL,
  `DELEGATE` varchar(255) default NULL,
  `OP_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of delegation_tab
-- ----------------------------

-- ----------------------------
-- Table structure for `membership_tab`
-- ----------------------------
DROP TABLE IF EXISTS `membership_tab`;
CREATE TABLE `membership_tab` (
  `id` int(11) NOT NULL auto_increment,
  `GROUPID` varchar(255) default NULL,
  `USERID` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of membership_tab
-- ----------------------------
INSERT INTO `membership_tab` VALUES ('196', 'engineering', 'bluejoe');
INSERT INTO `membership_tab` VALUES ('197', 'sales', 'gonzo');
INSERT INTO `membership_tab` VALUES ('198', 'management', 'kermit');

-- ----------------------------
-- Table structure for `notification_tab`
-- ----------------------------
DROP TABLE IF EXISTS `notification_tab`;
CREATE TABLE `notification_tab` (
  `ID` int(11) NOT NULL auto_increment,
  `TASKID` varchar(255) default NULL,
  `OPTIME` date default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notification_tab
-- ----------------------------
INSERT INTO `notification_tab` VALUES ('3', '17505', '2014-12-03');

-- ----------------------------
-- Table structure for `user_tab`
-- ----------------------------
DROP TABLE IF EXISTS `user_tab`;
CREATE TABLE `user_tab` (
  `USERID` varchar(255) NOT NULL default '',
  `EMAIL` varchar(255) default NULL,
  `NICKNAME` varchar(255) default NULL,
  `MOBILEPHONENUMBER` varchar(255) default NULL,
  PRIMARY KEY  (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_tab
-- ----------------------------
INSERT INTO `user_tab` VALUES ('bluejoe', 'bluejoe2008@gmail.com', '白乔', '13800138000');
INSERT INTO `user_tab` VALUES ('kermit', 'bluejoe@cnic.cn', '老黄', '13800138000');

-- ----------------------------
-- Table structure for `vacation_request_tab`
-- ----------------------------
DROP TABLE IF EXISTS `vacation_request_tab`;
CREATE TABLE `vacation_request_tab` (
  `MOTIVATION` text,
  `DAYS` bigint(20) default NULL,
  `PROCESSID` varchar(250) default NULL,
  `ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vacation_request_tab
-- ----------------------------

-- ----------------------------
-- Procedure structure for `fun1`
-- ----------------------------
DROP PROCEDURE IF EXISTS `fun1`;
DELIMITER ;;
CREATE PROCEDURE `fun1`(in name varchar(20), in age bigint, out Result bigint)
BEGIN
	insert into person (name,age)
 		values (name, age);
select Result=last_insert_id();
END
;;
DELIMITER ;
