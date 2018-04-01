package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() {
        long id = 1;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception{
        long id = 1;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception{
        long seckillId = 1;
        long phone = 13545678913L;
        String md5 = "cb3b2bc900b4fc0da858a62426d46339";
        SeckillExecution seckillExecution = null;
        try {
            seckillExecution = seckillService.executeSeckill(seckillId,phone,md5);
            logger.info("seckillExecution={}",seckillExecution);
        } catch (RepeatKillException e) {
            logger.info(e.getMessage());
        } catch (SecKillCloseException e) {
            logger.error(e.getMessage());
        }
    }

    //测试代码完整逻辑，需要满足可以重复执行
    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long seckillId = 1;
            long phone = 13545678913L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,phone,md5);
                logger.info("seckillExecution={}",seckillExecution);
            } catch (RepeatKillException e) {
                logger.info(e.getMessage());
            } catch (SecKillCloseException e) {
                logger.error(e.getMessage());
            }
        }else {
            logger.warn("exposer={}",exposer);
        }

    }
}