package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑

        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "seckill:" + seckillId;
                //此时想用Redis做缓存，获得seckill对象，获取对象通过序列化的方式来获取
                //采用自定义序列化，用Protostuff（只能对有pojo.即只能对有get或者set的目标进行操作）
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到
                if (null != bytes) {
                    //用schema创建一个Seckill的空对象
                    Seckill seckill = schema.newMessage();
                    //使用protobufIOUtil反序列化出有数据seckill，即序列化后的seckill从字节码变更有数据的对象
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){

        try {
            Jedis jedis = jedisPool.getResource();
            try {
               String key = "seckill:" + seckill.getSeckillId();
               byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
               //超时缓存
                int timeout = 60 * 60;//一个小时
               String result = jedis.setex(key.getBytes(),timeout,bytes);
               return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
