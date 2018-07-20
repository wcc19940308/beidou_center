/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.11 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('10','是否','sys_dic_item','item_key','item_value','dic_id=10 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('11','国家','sys_country','country_id','country_name','ORDER BY order_no DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('12','省份','sys_region','reg_id','reg_name','LEVEL=1 ORDER BY LEVEL,order_no DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('13','行政区划','sys_region','reg_id','reg_name','ORDER BY LEVEL,order_no DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('14','角色','bd_role','role_id','role_name','ORDER BY role_id;','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('15','报警类型','sys_dic_item','item_key','item_value','dic_id=15 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('16','网络类型','sys_dic_item','item_key','item_value','dic_id=16 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('17','设备类型','sys_dic_item','item_key','item_value','dic_id=17 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('18','短信类型','sys_dic_item','item_key','item_value','dic_id=18 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('19','短信发送状态','sys_dic_item','item_key','item_value','dic_id=19 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('20','聊天消息类型','sys_dic_item','item_key','item_value','dic_id=20 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('21','广告类型','sys_dic_item','item_key','item_value','dic_id=21 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('22','鱼类','sys_dic_item','item_key','item_value','dic_id=22 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('100','短报文类型','sys_dic_item','item_key','item_value','dic_id=100 order by item_order DESC','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('101','APP使用的角色列表','bd_role','role_id','role_name','ORDER BY role_id;','1');
insert into `sys_dic` (`dic_id`, `dic_name`, `table_name`, `key_column`, `value_column`, `dic_sql`, `validity`) values('102','数据站列表','bd_data_site','site_no','site_no','ORDER BY site_no;','1');



insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('10','0','否','2','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('10','1','是','1','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','1','碰撞预警','99','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','2','沉船','98','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','3','船体侧翻','97','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','4','船体火警','96','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','5','船员受伤','95','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('15','99','其他','0','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('16','1','有线网络','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('16','2','移动数据','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('16','3','宽带WIFI','7','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('16','4','船舶WIFI','6','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('17','1','WEB登录','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('17','2','APP登录','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('18','1','验证短信','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('18','2','普通短信','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('18','3','平台短信','7','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('19','1','待发送','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('19','2','发送成功','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('19','3','发送失败','7','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('20','1','文本','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('20','2','图片','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('20','3','音频','7','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('20','4','视频','6','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('21','1','文本','9','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('21','2','图片','8','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('21','3','FLASH','7','1');


insert into `sys_country` (`country_id`, `country_name`, `country_no`, `country_en`, `order_no`) values('100','中华人民共和国','中国','China','100');

