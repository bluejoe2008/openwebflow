/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50016
Source Host           : localhost:3306
Source Database       : openwebflow

Target Server Type    : MYSQL
Target Server Version : 50016
File Encoding         : 65001

Date: 2015-01-13 21:33:41
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `owf_activity_creation`
-- ----------------------------
DROP TABLE IF EXISTS `owf_activity_creation`;
CREATE TABLE `owf_activity_creation` (
  `ID` int(11) NOT NULL auto_increment,
  `FACTORY_NAME` varchar(255) default NULL,
  `PROCESS_DEFINITION_ID` varchar(255) default NULL,
  `PROCESS_INSTANCE_ID` varchar(255) default NULL,
  `PROPERTIES_TEXT` varchar(2000) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_activity_creation
-- ----------------------------

-- ----------------------------
-- Table structure for `owf_activity_permission`
-- ----------------------------
DROP TABLE IF EXISTS `owf_activity_permission`;
CREATE TABLE `owf_activity_permission` (
  `ID` int(19) NOT NULL auto_increment,
  `ACTIVITY_KEY` varchar(255) default NULL,
  `ASSIGNED_USER` varchar(255) default NULL,
  `GRANTED_GROUPS` varchar(255) default NULL,
  `GRANTED_USERS` varchar(255) default NULL,
  `PROCESS_DEFINITION_ID` varchar(255) default NULL,
  `OP_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_activity_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `owf_delegation`
-- ----------------------------
DROP TABLE IF EXISTS `owf_delegation`;
CREATE TABLE `owf_delegation` (
  `ID` int(19) NOT NULL auto_increment,
  `DELEGATED` varchar(255) default NULL,
  `DELEGATE` varchar(255) default NULL,
  `OP_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_delegation
-- ----------------------------

-- ----------------------------
-- Table structure for `owf_membership`
-- ----------------------------
DROP TABLE IF EXISTS `owf_membership`;
CREATE TABLE `owf_membership` (
  `ID` int(11) NOT NULL auto_increment,
  `GROUP_ID` varchar(255) default NULL,
  `USER_ID` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_membership
-- ----------------------------

-- ----------------------------
-- Table structure for `owf_notification`
-- ----------------------------
DROP TABLE IF EXISTS `owf_notification`;
CREATE TABLE `owf_notification` (
  `ID` int(11) NOT NULL auto_increment,
  `TASK_ID` varchar(255) default NULL,
  `OP_TIME` date default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_notification
-- ----------------------------

-- ----------------------------
-- Table structure for `owf_user`
-- ----------------------------
DROP TABLE IF EXISTS `owf_user`;
CREATE TABLE `owf_user` (
  `USER_ID` varchar(255) NOT NULL default '',
  `EMAIL` varchar(255) default NULL,
  `NICK_NAME` varchar(255) default NULL,
  `MOBILE_PHONE_NUMBER` varchar(255) default NULL,
  PRIMARY KEY  (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of owf_user
-- ----------------------------
