/**
 * 
 */
package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 *@description:
 *@author MaYue 2017��1��1������2:23:12
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKillDao successKillDao;
	//md5��ֵ�ַ��������ڻ���md5
	private final String salt="dfdggxcwwe44f54==";
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0,4);
	}

	
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
		{
			return new Exposer(false, seckillId,nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5=getMD5(seckillId);
		return new Exposer(true, seckillId, md5);
	}
	
	private String getMD5(long seckillId)
	{
		String base=seckillId+"/"+salt;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	@Transactional
	/**
	 * ʹ��ע��������񷽷����ŵ�:
	 * 1�������ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
	 * 2����֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ���������������RPC/HTTP������߰��뵽���񷽷��ⲿ
	 * 3���������еķ�������Ҫ������ֻ��һ���޸Ĳ�����ֻ����������Ҫ����
	 */
	
	public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5==null||!md5.equals(getMD5(seckillId)))
		{
			throw new SeckillException("seckill data rewrite");
		}
		//ִ����ɱҵ���߼�:����棬��¼������Ϊ
		Date killTime=new Date();
		try {
			//�����
			int updateCount=seckillDao.reduceNumber(seckillId, killTime);
			
			if(updateCount<=0)
			{
				//û�и��µ���¼��˵����ɱ����(�����޷��ж��ǿ�治��������ɱʱ�䲻��ָ����Χ֮�ڣ���Ϊ�������
				//sql��䲻�ᱨ��ֻ��updateCount=0)
				throw new SeckillCloseException("seckill closed");
			}
			else{
				//��¼������Ϊ
				int insertCount=successKillDao.insertSuccessKill(seckillId, userPhone);
		if(insertCount<=0)
		{
			//�ظ���ɱ
			throw new RepeatKillException("seckill repeated");
		}
		else{
			//��ɱ�ɹ�
			SuccessKill successKill=successKillDao.queryWithSeckill(seckillId, userPhone);
		return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKill);
		}
			}
		} catch(SeckillCloseException e1){
			throw e1;
		}
		catch(RepeatKillException e2){
			throw e2;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			//���еľ��g���쳣ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
		
		
	}

	
}
