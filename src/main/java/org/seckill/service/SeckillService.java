package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 *
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 秒杀开始时输出秒杀接口地址，否则输出系统时间和秒杀结束时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5);
}
