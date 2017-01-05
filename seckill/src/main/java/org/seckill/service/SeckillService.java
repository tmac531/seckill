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
 *@author MaYue 2016��12��31������11:37:17
 *
 */
public interface SeckillService {
/**
 * 
 *@description:��ѯ������ɱ��¼
 *@return     :List<Seckill>
 */
	List<Seckill> getSeckillList();
	
	/**
	 * 
	 *@description:��ѯ������ɱ��¼
	 *@return     :Seckill
	 */
	Seckill getById(long seckillId);
	
	
	/**
	 *@description:��ɱ��ʼ�������ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱʱ��
	 *@param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 *@description:ִ����ɱ����
	 *@param seckillId
	 *@param userPhone
	 *@param md5
	 */
	SeckillExecution excuteSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;

}
