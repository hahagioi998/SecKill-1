package org.seckill.dao;

import jdk.internal.cmm.SystemResourcePressureImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thRShy on 2017/4/6.
 * 配置Spring和Junit整合  Junit启动时加载SpringIOC容器
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit  Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    //注入DAO实现类依赖
    @Resource//看到这个注解 就会从Spring容器中查找下面这个类的实现类  注入到测试类中
    private SecKillDao secKillDao;

    @Test
    public void queryById() throws Exception {
        long id=1000;
        SecKill secKill=secKillDao.queryById(id);
        System.out.print(secKill.getName());
        System.out.print(secKill);
    }

    @Test
    public void queryAll() throws Exception {
        List<SecKill> res=secKillDao.queryAll(0,100);
        for(SecKill secKill:res){
            System.out.print(secKill);
        }

    }
    @Test
    public void reduceNumber() throws Exception {

    }



}