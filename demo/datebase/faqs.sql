CREATE DATABASE /*!32312 IF NOT EXISTS*/`faqsDate208` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `faqsDate208`;

/*Table structure for table `faqs_category` */

DROP TABLE IF EXISTS `faqs_category`;

CREATE TABLE `faqs_category` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cat_lang_id` int(11) NOT NULL COMMENT 'faq语言ID',
  `cat_title` varchar(500) NOT NULL COMMENT '类别名称',
  `cat_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `cat_updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `cat_createuser` int(11) DEFAULT NULL COMMENT '创建人',
  `cat_updateuser` int(11) DEFAULT NULL COMMENT '修改人',
  `cat_status` int(11) DEFAULT '0' COMMENT '状态（1发布，0未发布）',
  `cat_ordertopdate` datetime DEFAULT NULL COMMENT '置顶排序（按时间）',
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `faqs_category` */

/*Table structure for table `faqs_detailed` */

DROP TABLE IF EXISTS `faqs_detailed`;

CREATE TABLE `faqs_detailed` (
  `dl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dl_lang_id` int(11) NOT NULL COMMENT 'faq语言ID',
  `dl_cat_id` int(11) NOT NULL COMMENT 'faq类别ID',
  `dl_title` varchar(500) NOT NULL COMMENT '详情标题',
  `dl_content` text COMMENT '详情内容,带html',
  `dl_contenttxt` text COMMENT '详情内容,纯文本',
  `dl_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `dl_updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `dl_createuser` int(11) DEFAULT NULL COMMENT '创建人',
  `dl_updateuser` int(11) DEFAULT NULL COMMENT '修改人',
  `dl_status` int(11) DEFAULT '0' COMMENT '状态（1发布，0未发布）',
  `dl_ordertopdate` datetime DEFAULT NULL COMMENT '置顶排序（按时间）',
  PRIMARY KEY (`dl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;

/*Data for the table `faqs_detailed` */

/*Table structure for table `faqs_dl_hotspot` */

DROP TABLE IF EXISTS `faqs_dl_hotspot`;

CREATE TABLE `faqs_dl_hotspot` (
  `dlh_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dlh_dl_id` int(11) NOT NULL COMMENT 'faq详情ID',
  `dlh_search_count` int(11) NOT NULL DEFAULT '0' COMMENT '搜索数量',
  PRIMARY KEY (`dlh_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `faqs_dl_hotspot` */

insert  into `faqs_dl_hotspot`(`dlh_id`,`dlh_dl_id`,`dlh_search_count`) values (4,13,1);

/*Table structure for table `faqs_language` */

DROP TABLE IF EXISTS `faqs_language`;

CREATE TABLE `faqs_language` (
  `lang_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lang_title` varchar(100) NOT NULL COMMENT '语言名称',
  `lang_problem` varchar(100) DEFAULT NULL COMMENT '语言问题',
  `lang_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `lang_updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `lang_createuser` int(11) DEFAULT NULL COMMENT '创建人',
  `lang_updateuser` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`lang_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `faqs_language` */

insert  into `faqs_language`(`lang_id`,`lang_title`,`lang_problem`,`lang_createdate`,`lang_updatedate`,`lang_createuser`,`lang_updateuser`) values (1,'香港 (繁體)','常見問題','2018-10-30 09:55:32','2018-10-30 09:55:32',1,1),(2,'中国 (简体)','常见问题','2018-10-30 09:55:32','2018-10-30 09:55:32',1,1),(3,'中國台灣 (繁體)','常見問題','2018-10-30 09:55:32','2018-11-19 14:12:25',1,1),(4,'日本 (日本語))','よくあるご質問','2018-10-30 09:55:32','2018-10-30 09:55:32',1,1),(5,'대한민국(한국어)','자주묻는 질문','2018-10-30 09:55:32','2018-10-30 09:55:32',1,1),(6,'Hong Kong (EN)','FAQ','2018-10-30 09:55:32','2018-10-30 09:55:32',1,1);

/*Table structure for table `faqs_monitor` */

DROP TABLE IF EXISTS `faqs_monitor`;

CREATE TABLE `faqs_monitor` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_clientip` varchar(200) DEFAULT NULL COMMENT 'ip',
  `m_lang_id` int(11) DEFAULT NULL COMMENT 'faq语言ID',
  `m_cat_id` int(11) DEFAULT NULL COMMENT 'faq类别ID',
  `m_dl_id` int(11) DEFAULT NULL COMMENT 'faq答案ID',
  `m_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=963 DEFAULT CHARSET=utf8;


/*Data for the table `faqs_monitor` */


/*Table structure for table `faqs_user` */

DROP TABLE IF EXISTS `faqs_user`;

CREATE TABLE `faqs_user` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `usr_login_id` varchar(100) NOT NULL COMMENT '账号',
  `usr_password` varchar(100) NOT NULL COMMENT '密码',
  `usr_role` varchar(100) NOT NULL COMMENT '角色（admin管理员用户，ordinary普通用户）',
  PRIMARY KEY (`usr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `faqs_user` */

insert  into `faqs_user`(`usr_id`,`usr_login_id`,`usr_password`,`usr_role`) values (1,'admin','admin','admin');

-- 标签表

DROP TABLE IF EXISTS `faqs_detailed_tags`;
CREATE TABLE `faqs_detailed_tags` (
 `dt_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `dt_title` VARCHAR(200) NULL COMMENT 'tags名',
 `dt_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`dt_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 标签和问题的关系表

DROP TABLE IF EXISTS `faqs_dtags_relation`;
CREATE TABLE `faqs_dtags_relation` (
 `dr_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `dr_dt_id` INT NULL COMMENT '详细标签ID',
 `dr_dl_id` INT NULL COMMENT 'faq详细ID',
 `dr_order` INT NULL COMMENT '排序序号',
 `dr_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`dr_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `faqs_detailed_feedback`;
CREATE TABLE `faqs_detailed_feedback` (
 `df_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `df_type` INT NULL COMMENT '类别(1支持,2反对)',
 `df_dl_id` INT NULL COMMENT 'faq详细ID',
 `df_ip` VARCHAR(100) NULL COMMENT 'ip',
 `df_nay_content` VARCHAR(100) NULL COMMENT '反对内容',
 `df_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`df_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

--------------- 2.2版

DROP TABLE IF EXISTS `faqs_no_tags`;
CREATE TABLE `faqs_no_tags` (
 `nt_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `nt_lang_id` INT NULL COMMENT '语言Id',
 `nt_ip` VARCHAR(100) NULL COMMENT 'IP',
 `nt_title` VARCHAR(100) NULL COMMENT '标题',
 `nt_count` INT NULL COMMENT '数量',
 `nt_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`nt_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


-- faqs_detailed_feedback表添加,email收集,电话收集
ALTER TABLE faqs_detailed_feedback ADD df_nay_email VARCHAR(100) NULL;
ALTER TABLE faqs_detailed_feedback ADD df_nay_number VARCHAR(100) NULL;



DROP TABLE IF EXISTS `faqs_librabry`;
CREATE TABLE `faqs_librabry` (
 `fl_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `fl_title` VARCHAR(100) NULL COMMENT '标题',
 `fl_remarks` VARCHAR(200) NULL COMMENT '备注',
 `fl_createdate` DATETIME COMMENT '创建时间',
 `fl_updatedate` DATETIME COMMENT '更新时间',
  PRIMARY KEY (`fl_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 添加父级字段
ALTER TABLE faqs_detailed ADD dl_fl_id INT DEFAULT 0;
--  修改faqs_detailed表, dl_cat_id可以为空 , 防止出错
ALTER TABLE faqs_detailed MODIFY COLUMN dl_cat_id INT DEFAULT NULL COMMENT '类别可以为空,字段已不用';

-- 添加权重, 用搜索排序
ALTER TABLE faqs_detailed ADD dl_weights INT DEFAULT 0;


-- 父级数据错乱
UPDATE faqs_librabry SET fl_title = CONCAT('Q',fl_id);

SET @rownum=0;
UPDATE faqs_librabry
SET fl_title = CONCAT('Q',(
SELECT @rownum := @rownum +1 AS nid)
);

-- 反对内容字段变大
ALTER TABLE faqs_detailed_feedback MODIFY COLUMN df_nay_content VARCHAR(500) NULL COMMENT '反对内容';

-- 反馈表,添加状态(有反馈信息case)
ALTER TABLE faqs_detailed_feedback ADD df_nay_status INT DEFAULT 0;

