package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * Created by MaYue on 2016��12��28�� ����7:46:40
 * @����ֵ:
 *
 */
public interface SeckillDao {
	/**
	 * �����
	 *@param
	 *@return
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	
	/**
	 * ����id��ѯ��ɱ����
	 *@param
	 *@return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * ����ƫ������ѯ��ɱ�б�
	 *@param
	 *@return
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
