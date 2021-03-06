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

Date: 2019-07-17 17:49:06
*/


-- ----------------------------
-- Table structure for DEPARTMENT_NUMBERS
-- ----------------------------
DROP TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS";
CREATE TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" (
"ID" NUMBER(10) NOT NULL ,
"CREATED_DATE_TIME" TIMESTAMP(6)  NOT NULL ,
"CREATED_USER_ID" NUMBER(10) NULL ,
"CREATED_USER_NAME" VARCHAR2(55 CHAR) NOT NULL ,
"DEPART_ID" NUMBER(10) NOT NULL ,
"DISPLAY_ORDER" NUMBER(10) NULL ,
"LAST_UPDATE_DATE_TIME" TIMESTAMP(6)  NOT NULL ,
"LAST_UPDATE_USER_ID" NUMBER(10) NULL ,
"LAST_UPDATE_USER_NAME" VARCHAR2(55 CHAR) NOT NULL ,
"NAME" VARCHAR2(105 CHAR) NOT NULL ,
"PERSON_NUM" NUMBER(19,2) NULL ,
"YEAR_MONTH" VARCHAR2(255 CHAR) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of DEPARTMENT_NUMBERS
-- ----------------------------

-- ----------------------------
-- Indexes structure for table DEPARTMENT_NUMBERS
-- ----------------------------

-- ----------------------------
-- Checks structure for table DEPARTMENT_NUMBERS
-- ----------------------------
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("CREATED_DATE_TIME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("CREATED_USER_NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("DEPART_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("LAST_UPDATE_DATE_TIME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("LAST_UPDATE_USER_NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK ("YEAR_MONTH" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK (depart_id>=1 AND depart_id<=999999);
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD CHECK (display_order>=1 AND display_order<=999999);

-- ----------------------------
-- Primary Key structure for table DEPARTMENT_NUMBERS
-- ----------------------------
ALTER TABLE "PERSONPAPER"."DEPARTMENT_NUMBERS" ADD PRIMARY KEY ("ID");
