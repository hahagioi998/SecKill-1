package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
/**
 * Created by thRShy on 2017/4/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SellerDaoTest {

    @Resource
    private SellerDao sellerDao;

    @Test
    /**
     *
     */
    public void register() throws Exception {
        String sellerName="admin";
        String sellerPassword="admin"+"/"+"aspidjfaishf;oiahdsf";
        sellerPassword= DigestUtils.md5DigestAsHex(sellerPassword.getBytes());
        long sellerPhone=17869852145L;
        String sellerAddr="北京";
        int count=sellerDao.register(sellerName,sellerPhone,sellerPassword,sellerAddr);
        System.out.print(count);
    }

}