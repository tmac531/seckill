package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

/**
 * Created by MaYue on 2016��12��28�� ����7:34:02
 * @����ֵ:
 *
 */
public interface SuccessKillDao {

	
	/**
	 * ���빺����ϸ���ɹ����ظ�
	 *@param
	 *@return
	 */
	int insertSuccessKill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	
	/**
	 *����id��ѯ SuccessKill��Я����ɱ��Ʒ����ʵ��
	 *@param
	 *@return
	 */
	SuccessKill queryWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
