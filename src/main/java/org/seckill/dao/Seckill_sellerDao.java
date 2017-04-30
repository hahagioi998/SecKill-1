package org.seckill.dao;

/**
 * Created by thRShy on 2017/4/17.
 */
public interface Seckill_sellerDao {

    /**
     * 插入记录到关联表
     * @param sellerId
     * @param seckillId
     * @return
     */
    int insertSeckill(String sellerId,String seckillId);

}
