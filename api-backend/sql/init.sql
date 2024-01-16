-- my_api.`interface_info`
create table if not exists my_api.`interface_info`
(
    `id`             bigint                             not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                       not null comment '名称',
    `description`    varchar(256)                       null comment '描述',
    `url`            varchar(256)                       not null comment '请求地址',
    `requestHeader`  text                               not null comment '请求头',
    `responseHeader` text                               not null comment '响应头',
    `statue`         int      default 0                 not null comment '接口状态（0-关闭 1-开启）',
    `method`         varchar(256)                       not null comment '请求方式',
    `userId`         bigint                             not null comment '创建人',
    `createTime`     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment 'my_api.`interface_info`';

insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '段子涵', '石鹏', 'www.brock-koss.name', '陶健雄', '钱鸿煊', 0, '龙瑾瑜', 1668333758085);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '丁绍辉', '阎鸿涛', 'www.rey-bednar.biz', '杜立辉', '段天翊', 0, '白笑愚', 1664156957431);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '薛昊然', '冯明杰', 'www.joella-kemmer.name', '沈涛', '袁鹭洋', 0, '钟思', 1665178122186);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '叶博文', '孔驰', 'www.alphonso-wyman.com', '沈鹭洋', '雷伟诚', 0, '熊修洁', 1664103107634);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '邵子轩', '尹楷瑞', 'www.daine-moore.org', '赖伟祺', '陆智辉', 0, '姚擎宇', 1643220278403);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '江天宇', '梁浩宇', 'www.christopher-gulgowski.com', '蔡钰轩', '姚明辉', 0, '许君浩', 1662210057999);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '叶智宸', '顾智辉', 'www.johnson-renner.io', '崔煜城', '陆凯瑞', 0, '熊文轩', 1643241537977);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '刘鸿煊', '沈钰轩', 'www.vera-cassin.co', '龚晋鹏', '龚晟睿', 0, '于展鹏', 1643646423580);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '何博涛', '戴振家', 'www.arron-wiza.info', '谢哲瀚', '汪鹏煊', 0, '江明轩', 1648162308213);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '段昊天', '陈智辉', 'www.shila-graham.io', '余烨霖', '袁睿渊', 0, '郭晓博', 1656259586484);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '宋炎彬', '万聪健', 'www.dale-kub.com', '梁立诚', '莫健雄', 0, '李立轩', 1647863652194);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '任胤祥', '于梓晨', 'www.rosetta-daniel.org', '彭致远', '白浩', 0, '段金鑫', 1663762853948);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '邱烨霖', '金天磊', 'www.suzan-hansen.org', '石绍辉', '江健雄', 0, '于潇然', 1653618963121);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '邱鹤轩', '熊熠彤', 'www.rosina-huels.name', '黎子骞', '熊健雄', 0, '袁雨泽', 1648887892649);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '罗浩轩', '吕雨泽', 'www.yessenia-hickle.net', '何昊然', '于潇然', 0, '薛晓博', 1649439311420);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '白炫明', '程鸿煊', 'www.charita-howell.name', '郭昊天', '萧晓博', 0, '许远航', 1657828245828);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '严烨华', '谭志泽', 'www.chante-kub.com', '毛昊天', '严鑫鹏', 0, '毛炎彬', 1667892618734);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '郝炎彬', '尹远航', 'www.kyle-mckenzie.co', '黎博超', '洪雨泽', 0, '戴昊强', 1667079089329);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '蔡鸿涛', '田晓博', 'www.enrique-hudson.com', '夏鹏涛', '冯瑾瑜', 0, '刘正豪', 1651892961377);
insert into my_api.`interface_info` (`isDelete`, `name`, `description`, `url`, `requestHeader`, `responseHeader`,
                                     `statue`, `method`, `userId`)
values (0, '赖煜祺', '董钰轩', 'www.roderick-monahan.net', '唐修杰', '叶明', 0, '郝鹭洋', 1649119222573);

-- 用户接口关系表
create table if not exists my_api.`user_interface_info`
(
    `id`              bigint                             not null auto_increment comment '主键' primary key,
    `userId`          bigint                             not null comment '用户 id',
    `interfaceInfoId` bigint                             not null comment '接口 id',
    `totalNum`        int      default 0                 not null comment '总调用次数',
    `leftNum`         int      default 0                 not null comment '剩余调用次数',
    `statue`          int      default 0                 not null comment '接口状态（0-关闭 1-开启）',
    `createTime`      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment 'my_api.`interface_info`';

