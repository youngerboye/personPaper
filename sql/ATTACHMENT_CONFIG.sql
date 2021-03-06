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

Date: 2019-07-17 17:48:49
*/


-- ----------------------------
-- Table structure for ATTACHMENT_CONFIG
-- ----------------------------
DROP TABLE "PERSONPAPER"."ATTACHMENT_CONFIG";
CREATE TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" (
"ID" NUMBER(10) NOT NULL ,
"ALLOW_FILES" VARCHAR2(2000 CHAR) NULL ,
"COMPRESS_MAX_WIDTH" NUMBER(10) NULL ,
"CONFIG_NAME" VARCHAR2(55 CHAR) NOT NULL ,
"CONFIG_TYPE" NUMBER(10) NOT NULL ,
"CREATED_DATE_TIME" TIMESTAMP(6)  NULL ,
"CREATED_USER_ID" NUMBER(10) NULL ,
"CREATED_USER_NAME" VARCHAR2(55 CHAR) NULL ,
"DESCRIPTION" VARCHAR2(255 CHAR) NULL ,
"LAST_UPDATE_DATE_TIME" TIMESTAMP(6)  NULL ,
"LAST_UPDATE_USER_ID" NUMBER(10) NULL ,
"LAST_UPDATE_USER_NAME" VARCHAR2(55 CHAR) NULL ,
"MAX_SIZE" NUMBER(10) NULL ,
"MIN_SIZE" NUMBER(10) NULL ,
"STATE" NUMBER(10) NOT NULL ,
"URL_PREFIX" VARCHAR2(105 CHAR) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ATTACHMENT_CONFIG
-- ----------------------------
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('3', '.png.jpg.jpeg.doc.docx.txt.text.JPG', '10240000', '会议预约员工头像', '1', TO_TIMESTAMP(' 2018-09-20 20:28:56:367000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:28:56:367000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('4', '.doc.docx.txt.text', '10240000', '请假申请', '2', TO_TIMESTAMP(' 2018-09-20 20:29:30:028000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:29:30:028000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('5', null, '10240000', '员工加减分', '3', TO_TIMESTAMP(' 2018-09-20 20:30:27:136000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:30:27:136000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('6', null, '10240000', '考核申述', '4', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('7', null, '10240000', '离职', '5', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('8', null, '10240000', '下载中心', '6', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('9', null, '10240000', '投诉建议', '7', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('10', null, '10240000', '反馈附件', '8', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('11', null, '10240000', '材料清单示例表', '9', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('12', null, '10240000', '材料清单空白表', '10', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', 'string', TO_TIMESTAMP(' 2018-09-20 20:31:09:955000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');
INSERT INTO "PERSONPAPER"."ATTACHMENT_CONFIG" VALUES ('13', null, '10240000', '咨询问题', '14', TO_TIMESTAMP(' 2019-05-17 13:34:19:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '咨询问题', TO_TIMESTAMP(' 2019-05-17 13:34:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1', 'admin', '10240000', '0', '0', 'D://nginx/nginx/html');

-- ----------------------------
-- Checks structure for table ATTACHMENT_CONFIG
-- ----------------------------
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("CONFIG_NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("CONFIG_TYPE" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("STATE" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("URL_PREFIX" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("CONFIG_NAME" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("CONFIG_TYPE" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("STATE" IS NOT NULL);
ALTER TABLE "PERSONPAPER"."ATTACHMENT_CONFIG" ADD CHECK ("URL_PREFIX" IS NOT NULL);
