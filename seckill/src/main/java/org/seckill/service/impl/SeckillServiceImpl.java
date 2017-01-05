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
 *@author MaYue 2017年1月1日下午2:23:12
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKillDao successKillDao;
	//md5盐值字符串，用于混淆md5
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
	 * 使用注解控制事务方法的优点:
	 * 1、开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
	 * 3、不是所有的方法都需要事务，如只有一条修改操作或只读操作不需要事务
	 */
	
	public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5==null||!md5.equals(getMD5(seckillId)))
		{
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀业务逻辑:减库存，记录购买行为
		Date killTime=new Date();
		try {
			//减库存
			int updateCount=seckillDao.reduceNumber(seckillId, killTime);
			
			if(updateCount<=0)
			{
				//没有更新到记录，说明秒杀结束(但是无法判断是库存不够还是秒杀时间不在指定范围之内，因为两种情况
				//sql语句不会报错，只是updateCount=0)
				throw new SeckillCloseException("seckill closed");
			}
			else{
				//记录购买行为
				int insertCount=successKillDao.insertSuccessKill(seckillId, userPhone);
		if(insertCount<=0)
		{
			//重复秒杀
			throw new RepeatKillException("seckill repeated");
		}
		else{
			//秒杀成功
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
			//所有的編譯期异常转化为运行期异常
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
		
		
	}

	
}
