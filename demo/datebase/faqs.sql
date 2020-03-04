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

-- 2.2版

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

-- 添加父级(用程序去跑添加 , 时间不超一天.)
ALTER TABLE faqs_monitor ADD m_dl_id_father INT DEFAULT 0;



-- e-form 地区名
DROP TABLE IF EXISTS `e_area_name`;
CREATE TABLE `e_area_name` (
 `a_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `a_group_hk` VARCHAR(200) NULL COMMENT '标题分类-繁体',
 `a_group_cn` VARCHAR(200) NULL COMMENT '标题分类-简体',
 `a_group_en` VARCHAR(200) NULL COMMENT '标题分类-英文',
 `a_key` VARCHAR(100) NULL COMMENT '表示',
 `a_title_hk` VARCHAR(200) NULL COMMENT '繁体-名',
 `a_title_cn` VARCHAR(200) NULL COMMENT '简体-名',
 `a_title_en` VARCHAR(200) NULL COMMENT '英文-名',
 `a_updatedate` TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`a_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `e_area_name`(`a_group_hk`,`a_group_cn`,`a_group_en`,`a_key`,`a_title_hk`,`a_title_cn`,`a_title_en`)
VALUES ('香港特別行政區','中国香港','Hong Kong SAR','HKG','香港(HKG)','香港(HKG)','Hong Kong(HKG)'),
('柬埔寨','柬埔寨','Cambodia','REP','暹粒(REP)','暹粒(REP)','Siem Reap(REP)'),
('中國大陸','中国大陆','Mainland China','NGB','寧波(NGB)','宁波(NGB)','Ningbo(NGB)'),
('日本','日本','Japan','FUK','福岡(FUK)','福冈(FUK)','Fukuoka(FUK)'),
('日本','日本','Japan','HIJ','廣島(HIJ)','广岛(HIJ)','Hiroshima(HIJ)'),
('日本','日本','Japan','ISG','石垣島(ISG)','石垣岛(ISG)','Ishigaki(ISG)'),
('日本','日本','Japan','KOJ','鹿兒島(KOJ)','鹿儿岛(KOJ)','Kagoshima(KOJ)'),
('日本','日本','Japan','KMJ','熊本(KMJ)','熊本(KMJ)','Kumamoto(KMJ)'),
('日本','日本','Japan','NGS','長崎(NGS)','长崎(NGS)','Nagasaki(NGS)'),
('日本','日本','Japan','NGO','名古屋(NGO)','名古屋(NGO)','Nagoya-Chubu(NGO)'),
('日本','日本','Japan','KIX','大阪-關西(KIX)','大阪-关西(KIX)','Osaka-Kansai(KIX)'),
('日本','日本','Japan','SHI','宮古 (下地島)(SHI)','宫古（下地岛）(SHI)','Miyako (Shimojishima)(SHI)'),
('日本','日本','Japan','TAK','高松(TAK)','高松(TAK)','Takamatsu(TAK)'),
('日本','日本','Japan','HND','東京-羽田(HND)','东京-羽田(HND)','Tokyo-Haneda(HND)'),
('日本','日本','Japan','NRT','東京-成田(NRT)','东京-成田(NRT)','Tokyo-Narita(NRT)'),
('韓國','韩国','South Korea','PUS','釜山(PUS)','釜山(PUS)','Busan-Gimhae(PUS)'),
('韓國','韩国','South Korea','CJU','濟州(CJU)','济州(CJU)','Jeju(CJU)'),
('韓國','韩国','South Korea','ICN','首爾-仁川(ICN)','首尔-仁川(ICN)','Seoul-Incheon(ICN)'),
('中國台灣','中国台湾','Taiwan, China','RMQ','台中(RMQ)','台中(RMQ)','Taichung(RMQ)'),
('泰國','泰国','Thailand','BKK','曼谷(BKK)','曼谷(BKK)','Bangkok(BKK)'),
('泰國','泰国','Thailand','CNX','清邁(CNX)','清迈(CNX)','Chiang Mai(CNX)'),
('泰國','泰国','Thailand','CEI','清萊(CEI)','清莱(CEI)','Chiang Rai(CEI)'),
('泰國','泰国','Thailand','HKT','布吉(HKT)','普吉(HKT)','Phuket(HKT)'),
('美國屬地','美国属地','Northern Mariana Islands','DAD','峴港(DAD)','岘港(DAD)','Da Nang(DAD)'),
('越南','越南','Vietnam','CXR','芽莊(CXR)','芽庄(CXR)','Nha Trang(CXR)'),
('越南','越南','Vietnam','SPN','塞班(SPN)','塞班(SPN)','Saipan(SPN)');




-- e_currency 货币
DROP TABLE IF EXISTS `e_currency`;
CREATE TABLE `e_currency` (
 `ec_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `ec_title` VARCHAR(200) NULL COMMENT '标题',
  PRIMARY KEY (`ec_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


-- e-form 记录表
DROP TABLE IF EXISTS `e_form`;
CREATE TABLE `e_form` (
 `e_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `e_lang_id` INT NULL COMMENT '语言Id',
 `e_type` INT(11) NOT NULL COMMENT '类型(1:Resend Itinerary重新發送行程單 , 2:Duplicate Mulitiple Booking重復訂單,
				3:Request for Certificate證明申請 , 4:Name Correction姓名修正 ,
				5:Payment Failure支付失敗 , 6:Reconfirm Flight 確認航班 )',
 `e_pnr` VARCHAR(100) NULL COMMENT 'PNR',
 `e_first_name` VARCHAR(50) NULL COMMENT '名',
 `e_last_name` VARCHAR(50) NULL COMMENT '性',
 `e_email` VARCHAR(200) NULL COMMENT '邮箱',
 `e_trip_type` INT(11) DEFAULT 0 COMMENT '旅行类型[1返往(默认)，2单程, 3多城市]',
 `e_trip_departing` VARCHAR(200) NULL COMMENT '出发地',
 `e_trip_going` VARCHAR(200) NULL COMMENT '目的地',
 `e_trip_departingdate` DATETIME COMMENT '出发日期',
 `e_trip_goingdate` DATETIME COMMENT '回程日期',
 `e_trip_departing_new` VARCHAR(200) NULL COMMENT '出发地1',
 `e_trip_going_new` VARCHAR(200) NULL COMMENT '目的地1',
 `e_relation` INT(11) DEFAULT 0 COMMENT '是否有关系(0无,1第一,2第二)',
 `e_relation_eid` INT(11) DEFAULT 0 COMMENT '关系的id',
 `e_certificate_type` INT(11) DEFAULT 0 COMMENT '证书类型表',
 `e_first_name_new` VARCHAR(50) NULL COMMENT '新-名',
 `e_last_name_new` VARCHAR(50) NULL COMMENT '新-性',
 `e_flie` VARCHAR(200) NULL COMMENT '文件名',
 `e_payment_type` INT(11) DEFAULT 0 COMMENT '支付类型(1:支付宝,2:信用卡,3:微信)',
 `e_payment_transaction` VARCHAR(200) NULL COMMENT '交易号',
 `e_payment_amount` VARCHAR(200) NULL COMMENT '支付金额',
 `e_currency_id` INT(11) DEFAULT 0 NULL COMMENT 'e_currency货币外表',
 `e_payment_accounts` VARCHAR(200) NULL COMMENT '账号',
 `e_random` INT(11) DEFAULT 0 NULL COMMENT 'e_currency货币外表',
 `e_updatedate` TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`e_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

-- e_form_result 对接口返回记录
DROP TABLE IF EXISTS `e_form_result`;
CREATE TABLE `e_form_result` (
 `er_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `er_e_id` INT NULL COMMENT 'e-form表单ID',
 `er_result` VARCHAR(100) NULL COMMENT '解析接口返回值(0:Matched;其它值表示Not Matched)',
 `er_result_xml` VARCHAR(200) NULL COMMENT '接口返回值',
  PRIMARY KEY (`er_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


-- e_form_type 分类
DROP TABLE IF EXISTS `e_form_type`;
CREATE TABLE `e_form_type` (
 `et_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `et_title_hk` VARCHAR(200) NULL COMMENT '繁体-名',
 `et_title_cn` VARCHAR(200) NULL COMMENT '简体-名',
 `et_title_en` VARCHAR(200) NULL COMMENT '英文-名',
  PRIMARY KEY (`et_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `e_form_type`(`et_title_hk`,`et_title_cn`,`et_title_en`)
VALUES ('重新發送行程單','重新发送行程单','Request for itinerary'),
('重復訂單','重复订单','Duplicate Booking'),
('證明申請','证明申请','Request for Certificate'),
('姓名修正','姓名修正','Name Correction'),
('支付失敗','支付失敗','Payment Failure'),
('確認航班','确认航班','Reconfirm Flight');

-- e-form和问题的关系表
DROP TABLE IF EXISTS `faqs_eform_relation`;
CREATE TABLE `faqs_eform_relation` (
 `er_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `er_et_id` INT NULL COMMENT 'e-form类型',
 `er_dl_id` INT NULL COMMENT 'faq详细ID',
  PRIMARY KEY (`er_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


-- e_certificate 证书类别
DROP TABLE IF EXISTS `e_certificate`;
CREATE TABLE `e_certificate` (
 `ec_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `ec_title_hk` VARCHAR(200) NULL COMMENT '繁体-名',
 `ec_title_cn` VARCHAR(200) NULL COMMENT '简体-名',
 `ec_title_en` VARCHAR(200) NULL COMMENT '英文-名',
  PRIMARY KEY (`ec_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `e_certificate`(`ec_title_hk`,`ec_title_cn`,`ec_title_en`)
VALUES ('航班延誤證明','航班延误证明','Flight Delay Certificate'),
('搭乘航班證明','搭乘航班证明','Travel Certificate'),
('無搭乘航班證明','无搭乘航班证明','Confirmation Letter of No show'),
('航班取消證明','航班取消证明','Flight Cancel Certificate');


-- e-form ,添加dlId 从哪条问题进
ALTER TABLE e_form ADD e_dl_id INT DEFAULT 0;

-- 添加E-Form7
INSERT  INTO `e_form_type`(`et_title_hk`,`et_title_cn`,`et_title_en`)
VALUES ('颱風原因更改航班','台风原因更改航班','Typhoon Move Flight');


-- 添加E-Form8
INSERT  INTO `e_form_type`(`et_title_hk`,`et_title_cn`,`et_title_en`)
VALUES ('已購買新訂單的退款','已购买新订单的退款','Refund with new booking');


-- e-form ,添加e_pnr_new,新编号
ALTER TABLE e_form ADD e_pnr_new VARCHAR(100) NULL COMMENT 'PNR-new';


-- user -> 添加权限;
-- admin最高权限,agent只可编辑
insert  into `faqs_user`(`usr_id`,`usr_login_id`,`usr_password`,`usr_role`) values (1,'Erica Yu','7d645f','admin'),(2,'Gary Lam','a855c2','admin'),(3,'Sisi Yip','9b177a','admin'),(4,'Sarsi Pablo','64f70f','admin'),(5,'Alpha Bautista','1c9f09','admin'),(6,'Able Chung','777092','agent'),(7,'KEY Wong','7895b3','agent'),(8,'Tony Leung','7bebd1','agent'),(9,'Dick Tse','42942a','agent'),(10,'Marvie Fernando','f29b5c','agent'),(11,'Cecile Agbing','903bab','agent');


-- faqs_log日志
DROP TABLE IF EXISTS `faqs_logs`;
CREATE TABLE `faqs_logs` (
 `l_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `l_user_id` INT NULL COMMENT '用户ID',
 `l_ip` VARCHAR(200) NULL COMMENT 'IP',
 `l_title` VARCHAR(250) NULL COMMENT '内容简介',
 `l_content` TEXT NULL COMMENT '内容的json',
 `l_content_other` TEXT NULL COMMENT '内容的json-other',
 `l_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`l_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

-- e_form_type_display 显示
DROP TABLE IF EXISTS `e_form_type_display`;
CREATE TABLE `e_form_type_display` (
 `d_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `d_user_id` INT NULL COMMENT '用户ID',
 `d_et_id` INT NULL COMMENT 'E-FORM type',
 `d_order` INT COMMENT '排序',
 `d_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`d_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 添加user
INSERT  INTO `faqs_user`(`usr_login_id`,`usr_password`,`usr_role`) VALUES
('Windy Tam','6da932','admin');
INSERT  INTO `faqs_user`(`usr_login_id`,`usr_password`,`usr_role`) VALUES
('Hifi Chong','d4130e','agent');
INSERT  INTO `faqs_user`(`usr_login_id`,`usr_password`,`usr_role`) VALUES
('admin','paddy','admin');



-- 添加E-Form9
INSERT  INTO `e_form_type`(`et_title_hk`,`et_title_cn`,`et_title_en`)
VALUES ('航班資訊','航班资讯','Flight information');

-- 添加E-Form10
INSERT  INTO `e_form_type`(`et_title_hk`,`et_title_cn`,`et_title_en`)
VALUES ('重要旅遊注意事項','重要旅游注意事项','Important Travel Notice');

-- 记录E-Form点击数
DROP TABLE IF EXISTS `e_form_monitor`;
CREATE TABLE `e_form_monitor` (
  `m_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_clientip` VARCHAR(200) DEFAULT NULL COMMENT 'ip',
  `m_lang_id` INT(11) DEFAULT NULL COMMENT 'faq语言ID',
  `m_et_id` INT(11) DEFAULT NULL COMMENT 'e-form-type',
  `m_createdate` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`m_id`)
) ENGINE=INNODB AUTO_INCREMENT=963 DEFAULT CHARSET=utf8;

-- 2019-8-22
-- eform9 添加航班编号，航班出发日期
ALTER TABLE e_form ADD e_flight_no VARCHAR(50) NULL COMMENT '航班编号';
ALTER TABLE e_form ADD e_flight_departuredate DATETIME COMMENT '航班出发日期';

-- 修改字段长度
alter table e_form_result MODIFY er_result_xml VARCHAR(500);

-- 2019-8-26
-- 备注faqs_detailed.dl_status 修改为（0未发布，1发布External官网，2发布Internal内部）



--2019-09-10
-- 添加记录，crm-username
ALTER TABLE e_form_monitor ADD m_crm_uid VARCHAR(50) COMMENT 'crm-name';
ALTER TABLE faqs_monitor ADD m_crm_uid VARCHAR(50) COMMENT 'crm-name';
ALTER TABLE e_form_result ADD er_crm_uid VARCHAR(50) COMMENT 'crm-name';



--2019-09-26
-- e_pdf_area pdf地区
DROP TABLE IF EXISTS `e_pdf_area`;
CREATE TABLE `e_pdf_area` (
 `epa_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `epa_key` VARCHAR(100) NULL COMMENT 'key',
 `epa_title_hk` VARCHAR(200) NULL COMMENT '繁体-名',
 `epa_title_en` VARCHAR(200) NULL COMMENT '英文-名',
  PRIMARY KEY (`epa_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT  INTO `e_pdf_area`(`epa_key`,`epa_title_hk`,`epa_title_en`)VALUES
('HKG','香港','Hong Kong'),
('REP','暹粒','Siem Reap'),
('NGB','寧波','Ningbo'),
('FUK','福岡','Fukuoka'),
('HIJ','廣島','Hiroshima'),
('ISG','石垣島','Ishigaki'),
('KOJ','鹿兒島','Kagoshima'),
('KMJ','熊本','Kumamoto'),
('NGS','長崎','Nagasaki'),
('NGO','名古屋','Nagoya'),
('OKA','沖繩','Okinawa'),
('KIX','大阪 (關西)','Osaka (Kansai)'),
('SHI','宮古 (下地島)','Miyako (Shimojishima)'),
('TAK','高松','Takamatsu'),
('HND','東京 (羽田)','Tokyo (Haneda)'),
('NRT','東京 (成田)','Tokyo (Narita)'),
('PUS','釜山','Busan'),
('CJU','濟州','Jeju'),
('ICN','首爾 (仁川)','Seoul (Incheon)'),
('RMQ','台中','Taichung'),
('BKK','曼谷(蘇凡納布米)','Bangkok (Suvarnabhumi)'),
('CNX','清邁','Chiang Mai'),
('CEI','清萊','Chiang Rai'),
('HKT','布吉','Phuket'),
('SPN','塞班','Saipan'),
('DAD','峴港','Da Nang'),
('CXR','芽莊','Nha Trang');

-- 2019/10/14
-- 修改e_form 字段长
ALTER TABLE e_form MODIFY e_flie TEXT;
ALTER TABLE e_form MODIFY e_first_name TEXT;
ALTER TABLE e_form MODIFY e_last_name TEXT;
ALTER TABLE e_form_result MODIFY er_result TEXT;
ALTER TABLE e_form_result MODIFY er_result_xml TEXT;
-- 添加eform状态（1为成功）
ALTER TABLE e_form ADD e_status INT DEFAULT 0 COMMENT 'eform状态（1为成功）';
UPDATE e_form SET e_status = 1 WHERE e_type != 3;
UPDATE e_form SET e_status = 1 WHERE e_type = 3 AND e_certificate_type NOT IN (1,4);
UPDATE e_form SET e_status = 1 WHERE e_flie IS NOT NULL;


-- 跟改语言文字
-- e_certificate 证书类别
DROP TABLE IF EXISTS `e_certificate`;
CREATE TABLE `e_certificate` (
 `ec_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `ec_title_hk` VARCHAR(200) NULL COMMENT '繁体-名',
 `ec_title_cn` VARCHAR(200) NULL COMMENT '简体-名',
 `ec_title_jp` VARCHAR(200) NULL COMMENT '日文-名',
 `ec_title_kr` VARCHAR(200) NULL COMMENT '韩文-名',
 `ec_title_en` VARCHAR(200) NULL COMMENT '英文-名',
  PRIMARY KEY (`ec_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT  INTO `e_certificate`(`ec_title_hk`,`ec_title_cn`,`ec_title_jp`,`ec_title_kr`,`ec_title_en`)
VALUES ('航班延誤證明','航班延误证明','遅延証明書','비행 지연 증명서','Flight Delay Certificate'),
('搭乘航班證明','搭乘航班证明','旅行証明書','여행 서류','Travel Certificate'),
('無搭乘航班證明','无搭乘航班证明','ノーショーの確認書','미탑승 확인 증명서','Confirmation Letter of No show'),
('航班取消證明','航班取消证明','欠航証明書','항공편 취소 증명서','Flight Cancel Certificate');


-- 2019-11-22 统一表控制
INSERT INTO e_form_monitor
(m_clientip,m_lang_id,m_et_id,m_createdate)
SELECT  '192.168.97.12',e_lang_id,e_type,e_updatedate
FROM e_form
WHERE e_updatedate  <= '2019-08-14 14:50:00'


-- 添加搜索记录
-- 2019-12-17
DROP TABLE IF EXISTS `faqs_select_monitor`;
CREATE TABLE `faqs_select_monitor` (
  `m_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_clientip` VARCHAR(200) DEFAULT NULL COMMENT 'ip',
  `m_lang_id` INT(11) DEFAULT NULL COMMENT 'faq语言ID',
  `m_selete` VARCHAR(500) NULL COMMENT '搜索值',
  `m_createdate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `m_crm_uid` VARCHAR(50) NULL COMMENT 'crm-name',
  PRIMARY KEY (`m_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- e_form_relation 关系表
DROP TABLE IF EXISTS `e_form_relation`;
CREATE TABLE `e_form_relation` (
 `er_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `er_e_id` INT NULL COMMENT 'e-form表单ID',
 `er_trip_type` INT(11) DEFAULT 0 COMMENT '[1单程,2往返]',
 `er_outbound_unchanged` INT(11) DEFAULT 0 COMMENT '[Remain unchanged: 0不选,1选择]',
 `er_outbound_one` DATETIME COMMENT '第一次',
 `er_outbound_two` DATETIME COMMENT '第二次',
 `er_outbound_three` DATETIME COMMENT '第三次',
 `er_inbound_unchanged` INT(11) DEFAULT 0 COMMENT '[Remain unchanged: 0不选,1选择]',
 `er_inbound_one` DATETIME COMMENT '第一次',
 `er_inbound_two` DATETIME COMMENT '第二次',
 `er_inbound_three` DATETIME COMMENT '第三次',
  PRIMARY KEY (`er_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE e_form_result ADD er_zoho_mail_title TEXT COMMENT '发给zoho的邮件标题';


/*Table structure for table `folder` */

DROP TABLE IF EXISTS `folder`;

CREATE TABLE `folder` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `f_title` varchar(200) DEFAULT NULL COMMENT 'title',
  `f_lang_id` int(11) NOT NULL COMMENT '语言ID',
  `f_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `f_key_random` int(11) DEFAULT '0' COMMENT 'key-同个Q',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `folder` */

/*Table structure for table `folder_display_relation` */

DROP TABLE IF EXISTS `folder_display_relation`;

CREATE TABLE `folder_display_relation` (
  `fdr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fdr_f_id` int(11) DEFAULT NULL COMMENT '文件夹ID-f_key_random',
  `fdr_parenid` int(11) DEFAULT '1' COMMENT '文件夹父级ID，默认1',
  `fdr_level` int(11) DEFAULT NULL COMMENT '层次',
  `fdr_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`fdr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `folder_display_relation` */

/*Table structure for table `folder_tags` */

DROP TABLE IF EXISTS `folder_tags`;

CREATE TABLE `folder_tags` (
  `ft_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ft_tags` varchar(200) DEFAULT NULL COMMENT 'tags名',
  PRIMARY KEY (`ft_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `folder_tags` */

/*Table structure for table `folder_tags_relation` */

DROP TABLE IF EXISTS `folder_tags_relation`;

CREATE TABLE `folder_tags_relation` (
  `ftr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ftr_f_id` int(11) DEFAULT NULL COMMENT '文件夹ID',
  `ftr_ft_id` int(11) DEFAULT NULL COMMENT '文件夹标签ID',
  `ftr_order` int(11) DEFAULT NULL COMMENT '排序序号',
  `ftr_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ftr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `folder_tags_relation` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- 文件夹与faq父级关系
DROP TABLE IF EXISTS `folder_library_relation`;
CREATE TABLE `folder_library_relation` (
 `flr_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
 `flr_fl_id` INT NULL COMMENT 'library-ID',
 `flr_parenid` INT DEFAULT 1 COMMENT '文件夹ID-f_key_random',
 `flr_createdate` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`flr_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



-- 2020-1-2
-- 搜索反馈
DROP TABLE IF EXISTS `faqs_select_feedback`;
CREATE TABLE `faqs_select_feedback` (
  `df_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `df_lang_id` INT(11) NOT NULL COMMENT '语言ID',
  `df_ip` VARCHAR(100) DEFAULT NULL COMMENT 'ip',
  `df_createdate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `df_suggest_content` VARCHAR(100) DEFAULT NULL COMMENT '建议内容',
  `df_follow_content` VARCHAR(100) DEFAULT NULL COMMENT '跟进内容',
  `df_follow` INT(11) DEFAULT '1' COMMENT '默认跟进为1',
  `df_name` VARCHAR(100) DEFAULT NULL,
  `df_email` VARCHAR(100) DEFAULT NULL,
  `df_number` VARCHAR(100) DEFAULT NULL,
  `df_status` INT(11) DEFAULT '0',
  PRIMARY KEY (`df_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;