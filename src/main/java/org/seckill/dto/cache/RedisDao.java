package org.seckill.dto.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by thRShy on 2017/4/10.
 */
public class RedisDao {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }
    //RuntimeSchema通过class来制作对应的模式
    private RuntimeSchema<SecKill> schema=RuntimeSchema.createFrom(SecKill.class);

    //redis获取秒杀对象
    public SecKill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckillId;
                //jedis内部没有实现序列化操作
                // get->byte[] -> 反序列化 -> Object(Seckill)
                //反序列化的方法，类继承 Serializable 默认使用JDK自带的序列化机制
                //这里我们采用采用自定义序列化
                //protostuff:pojo. 我们只要给出.class 但是里面需要有getter和setter方法
                byte[] bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    //创建一个空对象
                    SecKill secKill=schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,secKill,schema);
                    //seckill已经被方序列化
                    return secKill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    //往redis放入对象
    public String putSeckill(SecKill secKill){
        //set Object(Seckill) -> 序列化 -> Object[]
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+secKill.getSeckillId();
                byte[] bytes=ProtobufIOUtil.toByteArray(secKill,schema,
                        //缓存器
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout=60*60;//单位为秒
                String result=jedis.setex(key.getBytes(),timeout,bytes);
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
