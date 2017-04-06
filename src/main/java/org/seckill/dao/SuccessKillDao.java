package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

/**
 * Created by thRShy on 2017/4/5.
 */
public interface SuccessKillDao {

    /**
     * 插入购买明细 ， 可过滤重复（联合主键）
     * @param seckillId
     * @param userPhone
     * @return 插入的数据行数
     */
    int insertSuccessKill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带产品对象实体。
     * @param seckillId
     * @return
     */
    SuccessKill queryByIdWithSecKill(long seckillId);

}
