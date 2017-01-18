package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

/**
 * Created by MaYue on 2016年12月28日 下午7:34:02
 * @返回值:
 *
 */
public interface SuccessKillDao {

	
	/**
	 * 插入购买明细，可过滤重复
	 *@param
	 *@return
	 */
	int insertSuccessKill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	
	/**
	 *根据id查询 SuccessKill并携带秒杀产品对象实体
	 *@param
	 *@return
	 */
	SuccessKill queryWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
