/**
 * 
 */
package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *@description:
 *@author MaYue 2017年1月1日下午10:21:33
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		
		List<Seckill> list=seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	
	@Test
	public void testGetById() {
		Seckill seckill=seckillService.getById(1000);
		logger.info("seckill={}", seckill);
	}

	//测试代码完整逻辑，注意可重复执行
	@Test//和下面的测试方法二合一
	public void testExportSeckillUrl() {
		
		long id=1000L;
		Exposer exposer=seckillService.exportSeckillUrl(id);
		/**秒杀开启(单独测试testExportSeckillUrl的结果)
		 * exposer=Exposer [exposed=true, 
		 * seckillId=1000, 
		 * md5=681e7e06e2fe8bc6803c15f83289c60f, 
		 * now=0, start=0, end=0]
		 */
		if(exposer.isExposed())
		{
			logger.info("exposer={}", exposer);
			long phone=13618381267L;
			String md5=exposer.getMd5();
			try {
				SeckillExecution seckillExecution= seckillService.excuteSeckill(id, phone, md5);
				logger.info("result={}", seckillExecution);

			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			}catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
			
		}
		
		else
		{
			logger.warn("exposer={}", exposer);
		}
		
	}

	
//	@Test
//	public void testExcuteSeckill() {
//		
//	}

}
