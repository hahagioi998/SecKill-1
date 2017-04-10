package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by thRShy on 2017/4/5.
 */
public interface SecKillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果返回的行数大于1，那么表示更新的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 根据ID查询秒杀商品列表
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 用存储过程来执行
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
