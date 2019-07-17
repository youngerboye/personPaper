/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.1.0

Source Server         : local
Source Server Version : 110200
Source Host           : 127.0.0.1:1521
Source Schema         : PERSONPAPER

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2019-07-17 17:49:23
*/


-- ----------------------------
-- Table structure for DEPT
-- ----------------------------
DROP TABLE "PERSONPAPER"."DEPT";
CREATE TABLE "PERSONPAPER"."DEPT" (
"ID" NUMBER(10) NOT NULL ,
"DEPT_NAME" VARCHAR2(255 CHAR) NULL ,
"DEPT_NO" VARCHAR2(50 CHAR) NULL ,
"DEPT_PATH" VARCHAR2(50 CHAR) NULL ,
"DEPT_PINYIN" VARCHAR2(50 CHAR) NULL ,
"DEPT_UUID" VARCHAR2(50 CHAR) NOT NULL ,
"PARENT_DEPT_UUID" VARCHAR2(50 CHAR) NULL ,
"UPD_TIME" NUMBER(19) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of DEPT
-- ----------------------------

-- ----------------------------
-- Indexes structure for table DEPT
-- ----------------------------

-- ----------------------------
-- Checks structure for table DEPT
-- ----------------------------
ALTER TABLE "PERSONPAPER"."DEPT" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPT" ADD CHECK ("DEPT_UUID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table DEPT
-- ----------------------------
ALTER TABLE "PERSONPAPER"."DEPT" ADD PRIMARY KEY ("ID");
