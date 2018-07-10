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


insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('10','0','否','2','1');
insert into `sys_dic_item` (`dic_id`, `item_key`, `item_value`, `item_order`, `validity`) values('10','1','是','1','1');

insert into `sys_country` (`country_id`, `country_name`, `country_no`, `country_en`, `order_no`) values('100','中华人民共和国','中国','China','100');

