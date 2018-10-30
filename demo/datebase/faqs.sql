-- 创建数据库faqsDate
CREATE DATABASE `faqsDate`;

USE faqsDate;

-- faq 管理用户表					
DROP TABLE IF EXISTS `faqs_user`;
CREATE TABLE `faqs_user` (
 `usr_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `usr_login_id` VARCHAR(100) NOT NULL COMMENT '账号',
 `usr_password` VARCHAR(100) NOT NULL COMMENT '密码',
 `usr_role` VARCHAR(100) NOT NULL COMMENT '角色（admin管理员用户，ordinary普通用户）',
  PRIMARY KEY (`usr_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- faq 语言表					
DROP TABLE IF EXISTS `faqs_language`;
CREATE TABLE `faqs_language` (
 `lang_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `lang_title` VARCHAR(100) NOT NULL COMMENT '语言名称',
 `lang_problem` VARCHAR(100) NULL COMMENT '语言问题',
 `lang_createdate` DATETIME COMMENT '创建时间',
 `lang_updatedate` DATETIME COMMENT '修改时间',
 `lang_createuser` INT COMMENT '创建人',
 `lang_updateuser` INT COMMENT '修改人',
  PRIMARY KEY (`lang_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- faq 类别表			
DROP TABLE IF EXISTS `faqs_category`;
CREATE TABLE `faqs_category` (
 `cat_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `cat_lang_id` INT NOT NULL COMMENT 'faq语言ID',
 `cat_title` VARCHAR(100) NOT NULL COMMENT '类别名称',
 `cat_createdate` DATETIME COMMENT '创建时间',
 `cat_updatedate` DATETIME COMMENT '修改时间',
 `cat_createuser` INT COMMENT '创建人',
 `cat_updateuser` INT COMMENT '修改人',
 `cat_status` INT NULL DEFAULT 0 COMMENT '状态（1发布，0未发布）',
 `cat_ordertopdate` DATETIME COMMENT '置顶排序（按时间）',
  PRIMARY KEY (`cat_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- faq 详情表		
DROP TABLE IF EXISTS `faqs_detailed`;
CREATE TABLE `faqs_detailed` (
 `dl_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `dl_lang_id` INT NOT NULL COMMENT 'faq语言ID',
 `dl_cat_id` INT NOT NULL COMMENT 'faq类别ID',
 `dl_title` VARCHAR(100) NOT NULL COMMENT '详情标题',
 `dl_content` TEXT NULL COMMENT '详情内容,带html',
 `dl_contenttxt` TEXT NULL COMMENT '详情内容,纯文本',
 `dl_createdate` DATETIME COMMENT '创建时间',
 `dl_updatedate` DATETIME COMMENT '修改时间',
 `dl_createuser` INT COMMENT '创建人',
 `dl_updateuser` INT COMMENT '修改人',
 `dl_status` INT NULL DEFAULT 0 COMMENT '状态（1发布，0未发布）',
 `dl_ordertopdate` DATETIME COMMENT '置顶排序（按时间）', 
  PRIMARY KEY (`dl_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- faq 详情收集表（比如热点搜索）		
DROP TABLE IF EXISTS `faqs_dl_hotspot`;
CREATE TABLE `faqs_dl_hotspot` (
 `dlh_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `dlh_dl_id` INT NOT NULL COMMENT 'faq详情ID',
 `dlh_search_count` INT NOT NULL DEFAULT 0 COMMENT '搜索数量',
  PRIMARY KEY (`dlh_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入初始化数据
insert into faqs_user(usr_login_id,usr_password,usr_role) values('admin','admin','admin');
insert into faqs_language (lang_title,lang_problem,lang_createdate,lang_updatedate,lang_createuser,lang_updateuser) values 
('香港 (繁體)','常見問題',now(),now(),1,1)
,('中国 (简体)','常见问题',now(),now(),1,1)
,('台灣 (繁體)','常見問題',now(),now(),1,1)
,('日本 (日本語))','よくあるご質問',now(),now(),1,1)
,('대한민국(한국어)','자주묻는 질문',now(),now(),1,1)
,('Hong Kong (EN)','FAQ',now(),now(),1,1);

insert into faqs_category (cat_lang_id,cat_title,cat_createdate,cat_updatedate,cat_createuser,cat_updateuser,cat_status,cat_ordertopdate) values 
(1,'主頁',now(),now(),1,1,1,now())
,(1,'訂票規則',now(),now(),1,1,1,now())
,(1,'預訂信息',now(),now(),1,1,1,now())
,(1,'在線客服',now(),now(),1,1,1,now())
,(1,'一般查詢',now(),now(),1,1,1,now())
,(2,'主页',now(),now(),1,1,1,now())
,(2,'订票规则',now(),now(),1,1,1,now())
,(2,'预订信息',now(),now(),1,1,1,now())
,(2,'在线客服',now(),now(),1,1,1,now())
,(2,'一般查询',now(),now(),1,1,1,now())
,(3,'主頁',now(),now(),1,1,1,now())
,(3,'訂票規則',now(),now(),1,1,1,now())
,(3,'預訂信息',now(),now(),1,1,1,now())
,(3,'在線客服',now(),now(),1,1,1,now())
,(3,'一般查詢',now(),now(),1,1,1,now())
,(4,'ホームページ',now(),now(),1,1,1,now())
,(4,'運賃規則',now(),now(),1,1,1,now())
,(4,'予約情報',now(),now(),1,1,1,now())
,(4,'オンラインサービス',now(),now(),1,1,1,now())
,(4,'予約検索',now(),now(),1,1,1,now())
,(5,'홈',now(),now(),1,1,1,now())
,(5,'요금규정',now(),now(),1,1,1,now())
,(5,'예약정보',now(),now(),1,1,1,now())
,(5,'온라인서비스',now(),now(),1,1,1,now())
,(5,'일반조회',now(),now(),1,1,1,now())
,(6,'Home',now(),now(),1,1,1,now())
,(6,'Fare Rules',now(),now(),1,1,1,now())
,(6,'Booking Information',now(),now(),1,1,1,now())
,(6,'Online Services',now(),now(),1,1,1,now())
,(6,'General Enquiry',now(),now(),1,1,1,now());


insert into faqs_detailed (dl_lang_id,dl_cat_id,dl_title,dl_content,dl_contenttxt,dl_createdate,dl_updatedate,dl_createuser,dl_updateuser,dl_status,dl_ordertopdate) values 
(1,2,'問: 我可否更改航班之日期/時間?','123123','123123',now(),now(),1,1,1,now())
,(1,2,'問: 我可否更改航班的出發地/目的地?','bbbbb','123123',now(),now(),1,1,1,now())
,(1,3,'問: 我怎樣才能預訂機票? ','cccc','123123',now(),now(),1,1,1,now())
,(2,7,'问: 我可否更改航班之日期/时间?','ddddd','123123',now(),now(),1,1,1,now())
,(2,7,'问: 我可否更改航班的出发地/目的地?','eeeee','123123',now(),now(),1,1,1,now())
,(2,8,'问: 我怎样才能预订机票? ','eeeee','123123',now(),now(),1,1,1,now())
,(6,27,'Q: Can I change the date/time of my flight?','eeeee','123123',now(),now(),1,1,1,now())
,(6,27,'Q: Can I change the origin/destination of my flight?','eeeee','123123',now(),now(),1,1,1,now())
,(6,28,'Q: How do I make a booking? ','eeeee','123123',now(),now(),1,1,1,now());
----- 以上基本完成