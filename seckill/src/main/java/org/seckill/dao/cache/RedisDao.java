/**
 * 
 */
package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;

/**
 * Created by MaYue on 2017年1月17日 下午5:26:06
 * @返回值:
 *
 */
public class RedisDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final JedisPool jedisPool;
	
	public RedisDao(String ip,int port){
		jedisPool=new JedisPool(ip,port);
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);

		}
		
	}
	
	public Seckill getSeckill(long seckillId){
		return null;
	}
	public String pushSeckill(Seckill seckill){
		return null;
		
	}
}
