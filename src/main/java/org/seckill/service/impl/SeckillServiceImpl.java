package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.dto.cache.RedisDao;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 接口具体的实现
 * Created by thRShy on 2017/4/6.
 */
//@Component  @Service  @Dao
@Service
public class SeckillServiceImpl implements SeckillService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖
    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    @Autowired
    private RedisDao redisDao;
    //用于混淆MD5
    public final String slat="knfasdjfnjashdfoiewbfjshadfuobn,,.ahsefdnfpasdkfsoapufphwaeu";

    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0,4);
    }

    public SecKill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //通过redis 缓存优化
        //优化点：缓存优化：超时的基础上维护一致性
        /*
        * 伪代码
        * get from cache
        * if null
        *   get db
        * else
        *   put cache
        * login
        *
        * */
        SecKill secKill=redisDao.getSeckill(seckillId);
        if(secKill==null){
            //访问数据库
            secKill=secKillDao.queryById(seckillId);
            if(secKill==null)
                return new Exposer(false,seckillId);
            else{
                redisDao.putSeckill(secKill);
            }
        }
        Date startTime=secKill.getStartTime();
        Date endTime=secKill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()
                ||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定在字符串过程
        String md5=getMd5(seckillId);
        return new Exposer(true,md5,seckillId);

    }

    //md5加密方法
    private String getMd5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }



    @Transactional
    /**
     * 使用注解 控制事务方法的优点
     * 1、开发团队达成一致的约定、明确标注事务方法的编程风格
     * 2、保证事务方法的操作时间尽可能短，不要穿插其他的网络操作或者剥离到事务方法外部
     * 3、不是所有的方法都需要事务，比如只有一条操作或只读操作
     *
     */
    public SecKillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SecKillCloseException, SeckillException, RepeatKillException {
        if(md5==null||!md5.equals(getMd5(seckillId))){
            throw new SeckillException("秒杀数据被修改");
        }
        //执行秒杀逻辑
        //1、减去库存
        //2、记录秒杀行为
        Date nowTime=new Date();

        try{
            int updateCount=secKillDao.reduceNumber(seckillId,nowTime);
            if(updateCount<=0){
                //没有更新记录  即秒杀结束或其他原因
                throw new SecKillCloseException("秒杀结束");
            }else{
                //减库存成功 -> 记录购买行为
                int insertCount=successKillDao.insertSuccessKill(seckillId,userPhone);
                if(insertCount<=0){
                    throw  new RepeatKillException("重复秒杀");
                }else{
                    SuccessKill successKill=successKillDao.queryByIdaAndPhoneWithSecKill(seckillId,userPhone);
                    return new SecKillExecution(seckillId, SeckillStateEnum.SUCCESS,successKill);
                }
            }
        }catch (SecKillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常 转化为运行时异常
            throw new SeckillException("秒杀 异常"+e.getMessage());
        }

    }
}
