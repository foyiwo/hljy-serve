/*
 Navicat Premium Data Transfer

 Source Server         : 47.112.155.84
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 47.112.155.84:3306
 Source Schema         : hljy_mall

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 19/09/2021 19:21:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for l_order
-- ----------------------------
DROP TABLE IF EXISTS `l_order`;
CREATE TABLE `l_order`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bill_date` datetime NULL DEFAULT NULL,
  `member_id` int(11) NULL DEFAULT NULL,
  `order_status` int(8) NULL DEFAULT NULL,
  `pay_status` int(8) NULL DEFAULT NULL,
  `order_amount` decimal(16, 2) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `end_date` datetime NULL DEFAULT NULL,
  `payment_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 269 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
