package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * Created by MaYue on 2016年12月28日 下午7:46:40
 * @返回值:
 *
 */
public interface SeckillDao {
	/**
	 * 减库存
	 *@param
	 *@return
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	
	/**
	 * 根据id查询秒杀对象
	 *@param
	 *@return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀列表
	 *@param
	 *@return
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
