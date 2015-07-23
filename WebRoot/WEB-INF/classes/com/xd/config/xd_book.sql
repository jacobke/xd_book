/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : xd_book

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2015-07-23 21:32:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_book`
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book` (
  `number` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '图书编码,唯一性',
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '书名',
  `author` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '作者',
  `price` float(11,2) DEFAULT '0.00' COMMENT '价格',
  `publisher` varchar(50) COLLATE utf8_bin DEFAULT '出版社',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`number`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES ('T123', 'java编程思想', '诸葛亮', '28.50', '机械工业出版社', '1');

-- ----------------------------
-- Table structure for `tb_department`
-- ----------------------------
DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_department
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_manager`
-- ----------------------------
DROP TABLE IF EXISTS `tb_manager`;
CREATE TABLE `tb_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`) USING BTREE,
  CONSTRAINT `tb_manager_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_manager
-- ----------------------------
INSERT INTO `tb_manager` VALUES ('4', '3');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `departId` int(50) DEFAULT NULL,
  `job` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `birth` datetime DEFAULT NULL,
  `entry` datetime DEFAULT NULL COMMENT '入职时间',
  `password` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `loginName` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `登录名` (`loginName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('3', '郭延思', '1', '架构师', '1990-09-07 00:00:00', '2014-06-23 00:00:00', '123456', '思思博士1');
