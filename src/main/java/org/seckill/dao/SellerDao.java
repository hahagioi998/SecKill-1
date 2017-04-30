package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;
import org.seckill.entity.Seller;

import java.util.Date;
import java.util.List;

/**
 * Created by thRShy on 2017/4/17.
 */
public interface SellerDao {

    /**
     * 商家 登录
     * @param sellerId
     * @param sellerPassword
     * @return
     */
    Seller login(@Param("sellerId") String sellerId, @Param("sellerPassword") String sellerPassword);

    /**
     * 商家注册 - 商家ID自动生成
     * @param sellerName
     * @param sellerPhone
     * @param sellerPassword
     * @return
     */
    int register(@Param("sellerName") String sellerName,@Param("sellerPhone") long sellerPhone,
                 @Param("sellerPassword") String sellerPassword,@Param("sellerAddr") String sellerAddr);


    /**
     * 修改个人信息
     * @param sellerName
     * @param sellerPhone
     * @param sellerPassword
     * @return
     */
    int update(@Param("sellerName") String sellerName,@Param("sellerPhone") long sellerPhone,
               @Param("sellerPassword") String sellerPassword);


    /**
     * 遍历所有的商家
     * @return
     */
    List<Seller> findAllSeller();


    /**
     * 发布新的商品
     * @param sellerId
     * @param seckillDes
     * @param seckillName
     * @param seckillStartDate
     * @param seckillEndDate
     * @param seckillCreateDate
     * @param seckillNum
     * @return
     */
    int newSeckill(@Param("sellerId") String sellerId, @Param("seckillDes") String seckillDes,
                   @Param("seckillName") String seckillName, @Param("seckillStartDate") Date seckillStartDate ,
                   @Param("seckillEndDate") Date seckillEndDate,@Param("seckillCreateDate") Date seckillCreateDate ,
                   @Param("seckillNum") int seckillNum);


    /**
     * 搜索所属商家的商品
     * @param sellerId
     * @return
     */
    List<SecKill> findSeckillsBySeller(String sellerId);


}
