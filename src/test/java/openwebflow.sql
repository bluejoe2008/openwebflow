/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50016
Source Host           : localhost:3306
Source Database       : openwebflow

Target Server Type    : MYSQL
Target Server Version : 50016
File Encoding         : 65001

Date: 2014-12-14 17:48:18
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
-- Table structure for `activity_creation_tab`
-- ----------------------------
DROP TABLE IF EXISTS `activity_creation_tab`;
CREATE TABLE `activity_creation_tab` (
  `ID` int(11) NOT NULL auto_increment,
  `FACTORYNAME` varchar(255) default NULL,
  `PROCESSDEFINITIONID` varchar(255) default NULL,
  `PROCESSINSTANCEID` varchar(255) default NULL,
  `PROPERTIESTEXT` varchar(2000) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_creation_tab
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
