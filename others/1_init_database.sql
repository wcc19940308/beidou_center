/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/8/17 13:57:03                           */
/*==============================================================*/


drop table if exists bd_advert;

drop table if exists bd_data_site;

drop table if exists bd_device;

drop table if exists bd_fish_record;

drop table if exists bd_fish_record_detail;

drop table if exists bd_interface_log;

drop table if exists bd_interface_user;

drop table if exists bd_location;

drop table if exists bd_msg_alarm;

drop table if exists bd_msg_chat;

drop table if exists bd_msg_chat_file;

drop table if exists bd_msg_notice;

drop table if exists bd_msg_weather;

drop table if exists bd_org;

drop table if exists bd_perm;

drop table if exists bd_phone_msg;

drop table if exists bd_recv_package;

drop table if exists bd_role;

drop table if exists bd_send_package;

drop table if exists bd_ship;

drop table if exists bd_ship_user;

drop table if exists bd_user;

drop table if exists bd_user_detail;

drop table if exists bd_user_login_log;

drop table if exists bd_user_login_session;

drop table if exists bd_user_perm;

drop table if exists sys_country;

drop table if exists sys_dic;

drop table if exists sys_dic_item;

drop table if exists sys_log;

drop table if exists sys_log_trigger;

drop table if exists sys_region;

/*==============================================================*/
/* Table: bd_advert                                             */
/*==============================================================*/
create table bd_advert
(
   adv_id               int not null,
   adv_title            varchar(100) not null comment '广告标题',
   adv_type             char(1) not null comment '广告类型1文字2图片3flash',
   adv_text             varchar(500) comment '广告文字内容',
   adv_base64           mediumtext comment '广告图片、flash文件base64编码',
   adv_start            datetime not null comment '广告开始时间',
   adv_end              datetime comment '广告结束时间',
   adv_time             int comment '播放持续时间，秒',
   order_no             int comment '广告排序号',
   validity             char(1) comment '是否有效',
   primary key (adv_id)
);

alter table bd_advert comment '广告信息';

/*==============================================================*/
/* Table: bd_data_site                                          */
/*==============================================================*/
create table bd_data_site
(
   site_no              varchar(20) not null,
   site_ip              varchar(15) not null,
   site_port            int not null,
   site_url             varchar(200) not null,
   user_name            varchar(20) not null comment '请求数据的用户名',
   password             varchar(50) not null comment '请求数据的密码',
   last_time            datetime comment '最后一次通信时间',
   validity             char(1) comment '是否有效',
   primary key (site_no)
);

alter table bd_data_site comment '北斗数据站';

/*==============================================================*/
/* Table: bd_device                                             */
/*==============================================================*/
create table bd_device
(
   msid                 int not null,
   site_no              varchar(20) not null comment '数据站编号',
   master_name          varchar(20) comment '指挥机名称或编号',
   master_serial_no     varchar(20) comment '指挥机序列号',
   master_card_no       int not null comment '指挥机北斗IC卡号',
   slave_serial_no      varchar(20) comment '一体机序列号',
   slave_card_no        int not null comment '一体机北斗IC卡号',
   public_id            int not null comment '通播ID',
   primary key (msid, master_card_no, slave_card_no)
);

alter table bd_device comment '北斗设备管理';

/*==============================================================*/
/* Table: bd_fish_record                                        */
/*==============================================================*/
create table bd_fish_record
(
   record_id            int not null comment '记录id',
   ship_id              int not null comment '船舶id',
   user_id              int not null comment '用户id',
   sea_area             varchar(4) comment '海域',
   country              varchar(3) comment '国家',
   record_date          date not null comment '日期',
   weather              varchar(3) comment '天气',
   wind                 int comment '风力',
   grade                varchar(3) comment '评价等级（优良差，待定）',
   jdv1                 char(1) comment '经度_范围，东经E西经W',
   jdv2                 int comment '经度_度',
   jdv3                 int comment '经度_分',
   jdv4                 int comment '经度_秒',
   jdv5                 int comment '经度_小秒',
   wdv1                 char(1) comment '纬度_范围，北纬N南纬S',
   wdv2                 int comment '纬度_度',
   wdv3                 int comment '纬度_分',
   wdv4                 int comment '纬度_秒',
   wdv5                 int comment '纬度_小秒',
   primary key (record_id)
);

alter table bd_fish_record comment '捕捞日志';

/*==============================================================*/
/* Table: bd_fish_record_detail                                 */
/*==============================================================*/
create table bd_fish_record_detail
(
   detail_id            int not null comment 'id',
   record_id            int not null comment '日志主id',
   fish_type            varchar(4) not null comment '鱼的种类',
   fish_grade           varchar(3) comment '鱼的品质（大中小）',
   fish_weight          int not null comment '体量',
   weight_unit          varchar(3) not null comment '体量单位（吨、斤、箱）',
   primary key (detail_id)
);

alter table bd_fish_record_detail comment '捕捞日志详情';

/*==============================================================*/
/* Table: bd_interface_log                                      */
/*==============================================================*/
create table bd_interface_log
(
   log_id               bigint not null comment '日志id',
   log_time             datetime not null comment '日志时间',
   user_name            varchar(20) not null comment '用户名',
   request_sequence     varchar(20) comment '序列表，见接口文档定义',
   terminal_code        varchar(30) comment '通信目标代码。',
   terminal_type        varchar(3) comment '通信终端类型，见表二',
   request_type         varchar(3) comment '请求命令代码',
   business_id          varchar(20) comment '产生的业务主键',
   receipt_code         varchar(10) comment '命令执行状态码,成功执行返回0 ,失败错误码见表八。',
   description          varchar(200) comment '命令执行状态描述',
   primary key (log_id)
);

alter table bd_interface_log comment '外部接口调用日志';

/*==============================================================*/
/* Table: bd_interface_user                                     */
/*==============================================================*/
create table bd_interface_user
(
   user_name            varchar(20) not null,
   pass_word            varchar(50) not null,
   last_login_time      datetime comment '最后一次登录的时间',
   user_id              varchar(4) comment '登录后返回给调用方的4位十进制数',
   primary key (user_name)
);

alter table bd_interface_user comment '外部接口用户表';

/*==============================================================*/
/* Table: bd_location                                           */
/*==============================================================*/
create table bd_location
(
   recv_time            datetime,
   loc_from             varchar(10) comment '定位来源的北斗设备IC卡号',
   ship_id              int,
   jdv1                 char(1) comment '经度_范围，东经E西经W',
   jdv2                 int comment '经度_度',
   jdv3                 int comment '经度_分',
   jdv4                 int comment '经度_秒',
   jdv5                 int comment '经度_小秒',
   wdv1                 char(1) comment '纬度_范围，北纬N南纬S',
   wdv2                 int comment '纬度_度',
   wdv3                 int comment '纬度_分',
   wdv4                 int comment '纬度_秒',
   wdv5                 int comment '纬度_小秒',
   sd                   int comment '速度',
   fx                   int comment '方向',
   wxs                  int comment '卫星数'
);

alter table bd_location comment '船舶定位信息';

/*==============================================================*/
/* Table: bd_msg_alarm                                          */
/*==============================================================*/
create table bd_msg_alarm
(
   msg_id               int not null,
   send_time            datetime comment '发送时间',
   recv_time            datetime comment '接收时间',
   msg_type             varchar(3) comment '报警类型',
   msg_txt              varchar(200) comment '报警信息',
   msg_from             int comment '发送者',
   msg_to               int comment '接收者',
   is_recv              char(1) comment '对方是否已经接收',
   recv_confirm_time    datetime comment '接收确认时间',
   is_exclude           char(1) comment '是否排除',
   exclude_confirm_time datetime comment '排除确认时间',
   primary key (msg_id)
);

alter table bd_msg_alarm comment '报警信息';

/*==============================================================*/
/* Table: bd_msg_chat                                           */
/*==============================================================*/
create table bd_msg_chat
(
   msg_id               int not null,
   send_time            datetime comment '发送时间',
   recv_time            datetime comment '接收时间',
   msg_type             char(1) not null comment '1文本2图片3语音4视频',
   msg_txt              varchar(200) comment '文本消息内容',
   msg_from             int comment '发送者北斗设备IC卡号',
   from_phone           varchar(20) comment '发送者手机号',
   msg_to               int comment '接收者北斗设备IC卡号',
   to_phone             varchar(20) comment '接受者手机号',
   is_recv              char(1) comment '对方是否已经接收',
   recv_confirm_time    datetime comment '接收确认时间',
   primary key (msg_id)
);

alter table bd_msg_chat comment '普通消息（聊天信息）';

/*==============================================================*/
/* Table: bd_msg_chat_file                                      */
/*==============================================================*/
create table bd_msg_chat_file
(
   msg_id               int not null,
   msg_type             char(1) not null comment '1文本2图片3语音4视频',
   msg_base64           mediumtext,
   primary key (msg_id)
);

alter table bd_msg_chat_file comment '消息中的文件内容，如图片、音频、视频等';

/*==============================================================*/
/* Table: bd_msg_notice                                         */
/*==============================================================*/
create table bd_msg_notice
(
   msg_id               int not null,
   send_time            datetime not null comment '发送时间',
   msg_txt              varchar(200),
   msg_from             int comment '发送者',
   msg_to               int comment '接收者',
   primary key (msg_id)
);

alter table bd_msg_notice comment '公告信息';

/*==============================================================*/
/* Table: bd_msg_weather                                        */
/*==============================================================*/
create table bd_msg_weather
(
   msg_id               int not null,
   send_time            datetime not null comment '发送时间',
   msg_txt              varchar(200),
   msg_from             int comment '发送者',
   msg_to               int comment '接收者',
   primary key (msg_id)
);

alter table bd_msg_weather comment '公告信息';

/*==============================================================*/
/* Table: bd_org                                                */
/*==============================================================*/
create table bd_org
(
   org_id               int not null comment '机构id',
   org_name             varchar(100) not null comment '机构名称',
   org_type             char(2) not null comment '机构类型（01公司02街道03其他组织）',
   org_desc             varchar(400) comment '机构描述',
   primary key (org_id)
);

alter table bd_org comment '机构信息表';

/*==============================================================*/
/* Table: bd_perm                                               */
/*==============================================================*/
create table bd_perm
(
   perm_id              int not null comment '权限id',
   parent_id            int comment '父节点',
   perm_level           int not null comment '权限层级',
   perm_name            varchar(30) not null comment '权限名称',
   perm_type            char(1) not null comment '权限类型（1菜单2按钮3-9其他自定义）',
   perm_url             varchar(300) comment 'url',
   perm_icon            varchar(200) comment '图标的url',
   order_no             int comment '排序号',
   validity             char(1) not null comment '1有效0无效',
   perm_script          varchar(255) comment '个性化脚本',
   primary key (perm_id)
);

alter table bd_perm comment '权限定义表';

/*==============================================================*/
/* Table: bd_phone_msg                                          */
/*==============================================================*/
create table bd_phone_msg
(
   phone_msg_id         int not null comment '短信id',
   phone                varchar(20) not null comment '手机号码',
   msg_type             char(1) not null comment '短信类型（1验证2普通）',
   msg_code             int(6) comment '短信验证码',
   msg_txt              varchar(200) comment '短信文本内容',
   send_time            datetime not null comment '短信发送时间',
   send_uuid            varchar(32) not null comment '发送批次号',
   send_flag            char(1) comment '发送是否成功（1成功0失败）',
   primary key (phone_msg_id)
);

alter table bd_phone_msg comment '手机短信';

/*==============================================================*/
/* Table: bd_recv_package                                       */
/*==============================================================*/
create table bd_recv_package
(
   pkg_id               bigint(20) not null,
   from_site_no         varchar(20) not null comment '数据来源的数据站编号',
   from_pkg_id          bigint(20) not null comment '数据站中的pkg_id',
   msg_id               bigint(20) comment '保存到对应消息表的msg_id',
   pkg_cmd              varchar(5) not null comment '指令',
   pkg_type             char(1) comment '报文类型',
   pkg_msg              varchar(200) comment '消息内容',
   master_from          int not null comment '报文来源的指挥机设备号',
   pkg_from             int not null comment '报文来源的北斗设备号',
   phone_from           varchar(11) comment '报文来源的手机号码',
   master_to            int comment '报文要去的指挥机设备号',
   pkg_to               int comment '报文要去的北斗设备号',
   phone_to             varchar(11) comment '报文要去的手机号码',
   recv_time            datetime not null comment '接收时间',
   recv_timestamp       bigint(15) not null comment '接收时间戳',
   primary key (pkg_id)
);

alter table bd_recv_package comment '接收到的数据包';

/*==============================================================*/
/* Table: bd_role                                               */
/*==============================================================*/
create table bd_role
(
   role_id              int not null comment '角色id',
   role_name            varchar(40) comment '角色名称',
   role_desc            varchar(200) comment '角色描述',
   validity             char(1) comment '是否有效（1有效0无效）',
   primary key (role_id)
);

alter table bd_role comment '用户角色表';

/*==============================================================*/
/* Table: bd_send_package                                       */
/*==============================================================*/
create table bd_send_package
(
   pkg_id               bigint(20) not null,
   center_no            varchar(20) not null comment '数据来源的服务器编号',
   pkg_type             char(1) not null comment '报文类型',
   msg_id               int comment '中心平台中消息id',
   pkg_msg              varchar(200) not null comment '报文数据',
   master_from          int comment '报文来源的指挥机设备号',
   pkg_from             int not null comment '报文来源的北斗设备号',
   phone_from           varchar(11) comment '报文来源的手机号码',
   master_to            int not null comment '报文要去的指挥机设备号',
   pkg_to               int not null comment '报文要去的北斗设备号',
   phone_to             varchar(11) comment '报文要去的手机号码',
   create_time          datetime not null comment '创建时间',
   send_time            datetime comment '发送给指挥机的时间',
   send_site_no         varchar(20) comment '发送的数据站编号',
   primary key (pkg_id)
);

alter table bd_send_package comment '需要发送的数据包';

/*==============================================================*/
/* Table: bd_ship                                               */
/*==============================================================*/
create table bd_ship
(
   ship_id              int not null comment '船舶id',
   ship_no              varchar(30) comment '船舶编号',
   ship_name            varchar(40) not null comment '船舶名称',
   ship_owner           varchar(40) not null comment '船东',
   ship_owner_sfz       varchar(18) comment '船东身份证',
   ship_desc            varchar(400) comment '船舶描述',
   org_id               int comment '所属机构id',
   card_no1             int comment '北斗IC卡号1',
   serial_no1           varchar(10) comment '设备序列号1',
   card_no2             int comment '北斗IC卡号2',
   serial_no2           varchar(10) comment '设备序列号2',
   mmsi                 varchar(20) comment 'AIS系统设备号',
   ship_length          float(4,2) comment '船长',
   ship_width           float(4,2) comment '船宽',
   ship_water           float(4,2) comment '吃水',
   ship_type            varchar(3) comment '船舶类型',
   ship_type_new        varchar(3) comment '船舶类型(新)',
   ship_owner_tel       varchar(20) comment '联系方式',
   ship_tons            int comment '船舶吨位',
   country              int comment '国家',
   province             int comment '省份',
   city                 int comment '城市',
   city_area            int comment '区县',
   town                 int comment '乡镇街道',
   village              int comment '村/服务站',
   radio_call_no        varchar(20) comment '无线电呼号',
   type_other           varchar(3) comment '类别',
   validity             char(1) comment '1有效0无效',
   primary key (ship_id)
);

alter table bd_ship comment '船舶信息';

/*==============================================================*/
/* Table: bd_ship_user                                          */
/*==============================================================*/
create table bd_ship_user
(
   ship_id              int not null,
   user_id              int not null,
   primary key (ship_id, user_id)
);

alter table bd_ship_user comment '船舶和用户对应关系';

/*==============================================================*/
/* Table: bd_user                                               */
/*==============================================================*/
create table bd_user
(
   user_id              int not null comment '用户id',
   password             varchar(50) not null comment '密码',
   phone                varchar(20) not null comment '手机号码',
   role_id              int not null comment '用户角色',
   validity             char(1),
   primary key (user_id)
);

alter table bd_user comment '用户表';

/*==============================================================*/
/* Table: bd_user_detail                                        */
/*==============================================================*/
create table bd_user_detail
(
   user_id              int not null,
   user_code            varchar(20),
   user_name            varchar(40),
   qq                   varchar(20),
   weixin               varchar(30),
   addr                 varchar(200),
   primary key (user_id)
);

alter table bd_user_detail comment '用户详细信息';

/*==============================================================*/
/* Table: bd_user_login_log                                     */
/*==============================================================*/
create table bd_user_login_log
(
   log_id               int not null,
   user_id              int not null comment '用户id',
   login_device         varchar(3) not null comment '登录设备(1网页登录2APP的设备类型)',
   net_type             varchar(3) comment '网络类型(1有线2移动数据3普通wifi4船舶wifi)',
   device_id            varchar(20) comment 'ip地址或北斗设备',
   login_time           datetime not null comment '登录时间',
   logout_time          datetime comment '登出时间',
   primary key (log_id)
);

alter table bd_user_login_log comment '用户登录日志';

/*==============================================================*/
/* Table: bd_user_login_session                                 */
/*==============================================================*/
create table bd_user_login_session
(
   user_id              int not null comment '用户id',
   login_device         varchar(3) not null comment '登录设备(1网页登录2APP的设备类型)',
   net_type             varchar(3) comment '网络类型(1有线2移动数据3普通wifi4船舶wifi)',
   device_id            varchar(20) comment 'ip地址或北斗设备',
   login_time           datetime not null comment '登录时间',
   session_id           varchar(50) not null,
   primary key (user_id, login_device)
);

alter table bd_user_login_session comment '用户登录信息';

/*==============================================================*/
/* Table: bd_user_perm                                          */
/*==============================================================*/
create table bd_user_perm
(
   ur_id                int not null comment '角色id或用户id',
   perm_id              int not null comment '权限id',
   perm_type            char(1) not null comment '1角色权限2用户权限',
   primary key (ur_id, perm_id, perm_type)
);

alter table bd_user_perm comment '用户权限分配表';

/*==============================================================*/
/* Table: sys_country                                           */
/*==============================================================*/
create table sys_country
(
   country_id           int not null comment 'id',
   country_name         varchar(40) not null comment '国家名称',
   country_no           varchar(20) not null comment '国家代号',
   country_en           varchar(100) not null comment '国家英文名',
   order_no             int comment '排序号',
   primary key (country_id)
);

alter table sys_country comment '国家列表';

/*==============================================================*/
/* Table: sys_dic                                               */
/*==============================================================*/
create table sys_dic
(
   dic_id               int not null,
   dic_name             varchar(30) not null comment '地点名称',
   table_name           varchar(20) not null comment '字典取值表名',
   key_column           varchar(30) not null comment '取key的列名',
   value_column         varchar(30) not null comment '取value的列名',
   dic_sql              varchar(400) comment '字典取值sql',
   validity             char(1) comment '1有效0无效',
   primary key (dic_id)
);

alter table sys_dic comment '系统字典表';

/*==============================================================*/
/* Table: sys_dic_item                                          */
/*==============================================================*/
create table sys_dic_item
(
   dic_id               int not null,
   item_key             varchar(10) not null,
   item_value           varchar(40),
   item_order           int comment '排序号',
   validity             char(1) comment '1有效0无效',
   primary key (dic_id, item_key)
);

alter table sys_dic_item comment '字典键值对';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   log_id               int not null,
   log_type             char(1) not null comment '日志类型',
   log_time             datetime not null comment '日志时间',
   log_txt              varchar(300) not null comment '日志内容（1系统日志2用户日志）',
   user_id              int comment '用户id',
   primary key (log_id)
);

alter table sys_log comment '系统日志表';

/*==============================================================*/
/* Table: sys_log_trigger                                       */
/*==============================================================*/
create table sys_log_trigger
(
   log_id               int not null comment '日志id',
   log_time             datetime not null comment '日志时间',
   table_name           varchar(30) comment '操作表名',
   columns              varchar(500) comment '操作列名',
   old_values           varchar(1000) comment '原内容',
   new_values           varchar(1000) comment '新内容',
   user_id              int comment '用户id',
   device               char(1) comment '设备类型',
   device_key           varchar(100) comment '设备标识（mac、ip等）',
   primary key (log_id)
);

alter table sys_log_trigger comment '系统触发器日志';

/*==============================================================*/
/* Table: sys_region                                            */
/*==============================================================*/
create table sys_region
(
   reg_id               int not null comment 'id',
   reg_name             varchar(100) not null comment '名称',
   parent_id            int comment '父节点id',
   level                int not null comment '层级',
   country_id           int not null comment '国家id',
   reg_code             varchar(20) comment '行政区划代码',
   public_id            int comment '通播号',
   order_no             int comment '排序号',
   primary key (reg_id)
);

alter table sys_region comment '行政区划表';

