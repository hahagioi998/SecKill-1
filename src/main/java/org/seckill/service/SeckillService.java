package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by thRShy on 2017/4/6.
 *
 *  业务接口 ： 站在 使用者 角度设计接口
 *  三个方面：
 *  1、方法定义粒度
 *  2、参数
 *  3、返回类型（return 类型/异常）
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    SecKill getById(long seckillId);

    /**
     * 返回秒杀
     * 如果秒杀没开始，返回系统时间和秒杀开始时间
     * 秒杀开始后返回秒杀地址
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SecKillCloseException,SeckillException,RepeatKillException;
}
