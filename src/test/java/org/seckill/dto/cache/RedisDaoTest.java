package org.seckill.dto.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SecKillDao;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by thRShy on 2017/4/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id=1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SecKillDao secKillDao;
    @Test
    public void testSeckill() throws Exception {
        //get and set

        SecKill secKill=redisDao.getSeckill(id);
        System.out.println(secKill);
        if(secKill==null){
            secKill=secKillDao.queryById(id);
            if(secKill!=null){
                String res=redisDao.putSeckill(secKill);
                System.out.println(res);
                SecKill secKill1=redisDao.getSeckill(id);
                System.out.println(secKill);
            }
        }else{
            System.out.println("redis has the seckill");
        }
    }


}