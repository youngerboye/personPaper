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

Date: 2019-07-17 17:51:11
*/


-- ----------------------------
-- Table structure for ROLE_ORGAN
-- ----------------------------
DROP TABLE "PERSONPAPER"."ROLE_ORGAN";
CREATE TABLE "PERSONPAPER"."ROLE_ORGAN" (
"ID" NUMBER(10) NOT NULL ,
"ORGAN_ID" NUMBER(10) NOT NULL ,
"ROLE_ID" NUMBER(10) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ROLE_ORGAN
-- ----------------------------
INSERT INTO "PERSONPAPER"."ROLE_ORGAN" VALUES ('2582', '160', '343');
INSERT INTO "PERSONPAPER"."ROLE_ORGAN" VALUES ('2581', '168', '343');

-- ----------------------------
-- Checks structure for table ROLE_ORGAN
-- ----------------------------
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ORGAN_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ROLE_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ORGAN_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ROLE_ORGAN" ADD CHECK ("ROLE_ID" IS NOT NULL);
