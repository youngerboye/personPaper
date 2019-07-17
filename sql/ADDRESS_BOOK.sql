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

Date: 2019-07-17 17:48:24
*/


-- ----------------------------
-- Table structure for ADDRESS_BOOK
-- ----------------------------
DROP TABLE "PERSONPAPER"."ADDRESS_BOOK";
CREATE TABLE "PERSONPAPER"."ADDRESS_BOOK" (
"ID" NUMBER(10) NOT NULL ,
"AMPUTATED" NUMBER(10) NULL ,
"CREATED_DATE_TIME" TIMESTAMP(6)  NULL ,
"CREATED_USER_ID" NUMBER(10) NULL ,
"CREATED_USER_NAME" VARCHAR2(55 CHAR) NULL ,
"LAST_UPDATE_DATE_TIME" TIMESTAMP(6)  NULL ,
"LAST_UPDATE_USER_ID" NUMBER(10) NULL ,
"LAST_UPDATE_USER_NAME" VARCHAR2(55 CHAR) NULL ,
"NAME" VARCHAR2(55 CHAR) NOT NULL ,
"PHONE_NUMBER" VARCHAR2(12 CHAR) NOT NULL ,
"PLATE_NO" VARCHAR2(255 CHAR) NULL ,
"SEX" NUMBER(10) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ADDRESS_BOOK
-- ----------------------------

-- ----------------------------
-- Indexes structure for table ADDRESS_BOOK
-- ----------------------------

-- ----------------------------
-- Checks structure for table ADDRESS_BOOK
-- ----------------------------
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD CHECK ("PHONE_NUMBER" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD CHECK ("SEX" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD CHECK (sex<=1 AND sex>=0);

-- ----------------------------
-- Primary Key structure for table ADDRESS_BOOK
-- ----------------------------
ALTER TABLE "PERSONPAPER"."ADDRESS_BOOK" ADD PRIMARY KEY ("ID");
