package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        long id = 1;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
    }

    @Test
    public void queryAll() {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumer() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumer(1,killTime);
        System.out.println("变化数量--------------"+updateCount);
    }


}