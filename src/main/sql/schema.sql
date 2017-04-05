

-- 数据库初始化脚本文件


create DATABASE seckill;

use seckill;
-- 秒杀表
create table seckill(
`seckill_id` bigint not null auto_increment comment '商品库存ID',
`name` VARCHAR(120) not null comment'商品名称',
`number` int not NULL comment'库存数量',
`start_time` TIMESTAMP not null comment '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL comment '秒杀结束时间',
`create_time` TIMESTAMP not NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT ='秒杀库存表';

-- 初始化数据
INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('500元秒杀iPhone',100,'2017-01-01 00:00:00','2017-01-01 00:01:00'),
  ('200元秒杀iPhone7',200,'2017-01-01 00:00:00','2017-01-01 00:01:00'),
  ('200元秒杀小米',300,'2017-01-02 00:00:00','2017-01-02 00:01:00'),
  ('100元秒杀红米',100,'2017-01-03 00:00:00','2017-01-03 00:01:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息

CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品ID',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` BIGINT NOT NULL DEFAULT -1 COMMENT '状态标识：-1 无效 0：成功 1：已付款',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  key idx_create_time(create_time)
)ENGINE =InnoDB DEFAULT CHAR SET =utf8 COMMENT ='秒杀成功明细表';
