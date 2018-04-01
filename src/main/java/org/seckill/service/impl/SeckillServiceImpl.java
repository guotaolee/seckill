package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
@Service
public class SeckillServiceImpl implements SeckillService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String slat = "fmiou398jpkmp23^*&^#fesagsef";//增加md5难度

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        //优化点：缓存优化
        //1:访问Redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (null == seckill){
            //2：访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (null == seckill){
                return new Exposer(false,seckillId);
            }else {
                //3:放入Redis
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() ||
                nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = this.getMD5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    @Transactional
    /**
     * 使用注解控制事物方法的优点：
     * 1：别人看到这个上面这个注解后，明确了是注入事务的编码风格
     * 2：保证事务方法执行的时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事物方法外部
     * 3：不是所有的方法都需要事物，如只有一条修改操作，只读操作不需要事物控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        if (null == md5 || !md5.equals(getMD5(seckillId))){
            throw new SecKillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();


        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
            //唯一性验证：seckillId,userPhone
            if (insertCount <= 0){
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            }else {
                //减库存,热点商品竞争
                int updateCount = seckillDao.reduceNumer(seckillId,nowTime);

                if (updateCount <= 0){
                    //没有更新到记录，秒杀结束,rollback
                    throw new SecKillCloseException("seckill is closed");
                }else {
                    //秒杀成功 commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        } catch (SecKillCloseException e1){
            throw e1;
        } catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常 转化为运行期异常
            throw new SecKillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
