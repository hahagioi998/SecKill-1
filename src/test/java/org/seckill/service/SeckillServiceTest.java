package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by thRShy on 2017/4/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Test
    //Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7e5c856f]
    //不是事务操作
    public void getSeckillList() throws Exception {
        List<SecKill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=1000L;
        SecKill secKill=seckillService.getById(id);
        logger.info("seckill={}",secKill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1000L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone=12398745610L;
            String md5=exposer.getMd5();
            try{
                SecKillExecution secKillExecution=seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}"+secKillExecution);
            }catch (RepeatKillException e1){
                logger.error(e1.getMessage());
            }catch (SecKillCloseException e2){
                logger.error(e2.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}"+exposer);
        }
        //exposer=Exposer{exposed=false, md5='null', seckillId=1000, now=1491463284338, start=1483200000000, end=1483200060000}
        //exposer=Exposer{exposed=true, md5='959eb1444daf67507dddb2e5d82c0359', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=1000L;
        long phone=12345678910L;
        String md5="959eb1444daf67507dddb2e5d82c0359";
        try{
            SecKillExecution secKillExecution=seckillService.executeSeckill(id,phone,md5);
            logger.info("result={}"+secKillExecution);
        }catch (RepeatKillException e1){
            logger.error(e1.getMessage());
        }catch (SecKillCloseException e2){
            logger.error(e2.getMessage());
        }
    }

}