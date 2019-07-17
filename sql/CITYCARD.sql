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

Date: 2019-07-17 17:48:58
*/


-- ----------------------------
-- Table structure for CITYCARD
-- ----------------------------
DROP TABLE "PERSONPAPER"."CITYCARD";
CREATE TABLE "PERSONPAPER"."CITYCARD" (
"ID" NUMBER(10) NOT NULL ,
"CITY_CARD_NO" VARCHAR2(55 CHAR) NOT NULL ,
"CREATED_DATE_TIME" TIMESTAMP(6)  NULL ,
"LAST_UPDATE_DATE_TIME" TIMESTAMP(6)  NULL ,
"NAME" VARCHAR2(55 CHAR) NOT NULL ,
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
-- Records of CITYCARD
-- ----------------------------

-- ----------------------------
-- Indexes structure for table CITYCARD
-- ----------------------------

-- ----------------------------
-- Checks structure for table CITYCARD
-- ----------------------------
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD CHECK ("CITY_CARD_NO" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD CHECK ("PERSON_ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD CHECK ("PERSON_NO" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table CITYCARD
-- ----------------------------
ALTER TABLE "PERSONPAPER"."CITYCARD" ADD PRIMARY KEY ("ID");
