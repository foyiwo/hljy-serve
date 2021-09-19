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

 Date: 19/09/2021 19:20:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for l_logs
-- ----------------------------
DROP TABLE IF EXISTS `l_logs`;
CREATE TABLE `l_logs`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `log` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `out_trade_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
