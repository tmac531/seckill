/**
 * 
 */
package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 *@description:
 *@author MaYue 2016年12月31日下午11:37:17
 *
 */
public interface SeckillService {
/**
 * 
 *@description:查询所有秒杀记录
 *@return     :List<Seckill>
 */
	List<Seckill> getSeckillList();
	
	/**
	 * 
	 *@description:查询单个秒杀纪录
	 *@return     :Seckill
	 */
	Seckill getById(long seckillId);
	
	
	/**
	 *@description:秒杀开始是输出秒杀接口地址，否则输出系统时间和秒杀时间
	 *@param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 *@description:执行秒杀操作
	 *@param seckillId
	 *@param userPhone
	 *@param md5
	 */
	SeckillExecution excuteSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;

}
