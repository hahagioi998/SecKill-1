package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by thRShy on 2017/4/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {

    @Resource
    private SuccessKillDao successKillDao;
    @Test

    //can use
    public void insertSuccessKill() throws Exception {
        long userPhone=13964775481L;
        long id=1001L;
        int res=successKillDao.insertSuccessKill(id,userPhone);
        System.out.print("updateCount="+res);
    }

    @Test
    //can use
    public void queryByIdWithSecKill() throws Exception {
        long id=1000L;
        SuccessKill successKill=successKillDao.queryByIdWithSecKill(id);
        System.out.print(successKill);
        System.out.print(successKill.getSecKill().getName());
    }

    @Test
    public void queryByIdaAndPhoneWithSecKill() throws Exception{
        long id=1000L;
        long userPhone=13964775481L;
        SuccessKill successKill=successKillDao.queryByIdaAndPhoneWithSecKill(id,userPhone);
        System.out.print(successKill);
        System.out.println(successKill.getSecKill());
    }
}