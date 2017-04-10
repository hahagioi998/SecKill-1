-- 秒杀执行存储过程
DELIMITER $$

-- 定义存储过程
-- row_count();放回上一条修改类型SQL的影响行数
-- 0：影响未修改数据 >0 :修改的行数 <0:SQL错误或未执行
create PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id bigint,in v_phone bigint,
    in v_kill_time TIMESTAMP , out r_result INT )
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    start TRANSACTION ;
    insert ignore into success_killed
      (seckill_id,user_phone,create_time)
      VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT  row_count() into insert_count;
    if (insert_count=0) THEN
      ROLLBACK ;
      set r_result=-1;
    elseif(insert_count<0) THEN
      ROLLBACK;
      set r_result=-2;
    ELSE
      update seckill
      set number=number-1
      where seckill_id=v_seckill_id
        and end_time>v_kill_time
        and start_time<v_kill_time
        and number>0;
      SELECT row_count() into insert_count;
      if(insert_count=0) THEN
        ROLLBACK ;
        set r_result=0;
      elseif(insert_count=1) THEN
        COMMIT ;
        set r_result=1;
      ELSE
        ROLLBACK ;
        set r_result=-2;
      END if;
    END if;
  END;
$$
-- 存储过程结束

DELIMITER ;
set @r_result=-3;
call execute_seckill(1000,18838903314,now(),@r_result);
SELECT r_result;
-- 存储过程
-- 1、存储过程优化：事务行级锁持有的时间
-- 2、不要过度依赖存储过程
-- 3、简单的逻辑可以运用存储过程
-- 4、QPS：一个商品/6000/qps