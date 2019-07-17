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

Date: 2019-07-17 17:50:33
*/


-- ----------------------------
-- Table structure for PERSON
-- ----------------------------
DROP TABLE "PERSONPAPER"."PERSON";
CREATE TABLE "PERSONPAPER"."PERSON" (
"ID" NUMBER(10) NOT NULL ,
"CREATED_DATE_TIME" TIMESTAMP(6)  NULL ,
"DEPT_NAME" VARCHAR2(255 CHAR) NULL ,
"DEPT_NO" VARCHAR2(255 CHAR) NULL ,
"DEPT_UUID" VARCHAR2(255 CHAR) NULL ,
"DESCRIPTION" VARCHAR2(255 CHAR) NULL ,
"EMPLOYEE_ID" NUMBER(10) NOT NULL ,
"GENDER" NUMBER(10) NULL ,
"LAST_UPDATE_DATE_TIME" TIMESTAMP(6)  NULL ,
"NAME" VARCHAR2(55 CHAR) NOT NULL ,
"PATH" VARCHAR2(255 CHAR) NULL ,
"PERSON_ID" NUMBER(10) NOT NULL ,
"PERSON_NO" VARCHAR2(55 CHAR) NOT NULL ,
"PHONE_NO" VARCHAR2(12 CHAR) NULL ,
"STATE" NUMBER(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of PERSON
-- ----------------------------

-- ----------------------------
-- Indexes structure for table PERSON
-- ----------------------------

-- ----------------------------
-- Checks structure for table PERSON
-- ----------------------------
ALTER TABLE "PERSONPAPER"."PERSON" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."PERSON" ADD CHECK ("EMPLOYEE_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."PERSON" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."PERSON" ADD CHECK ("PERSON_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."PERSON" ADD CHECK ("PERSON_NO" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PERSON
-- ----------------------------
ALTER TABLE "PERSONPAPER"."PERSON" ADD PRIMARY KEY ("ID");
