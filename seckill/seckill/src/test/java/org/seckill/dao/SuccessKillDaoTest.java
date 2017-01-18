package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit,spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SuccessKillDaoTest {
 
	@Resource      
	private SuccessKillDao successKillDao;

	@Test
	public void testInsertSuccessKill() {
		int insertCount=successKillDao.insertSuccessKill(1000L, 13618381267L);
		System.out.println(insertCount);
		/**
		 * 第一次insertCount=1
		 * 第二次insertCount=0(同一商品id和手机号)
		 */
	}

	@Test
	public void testQueryWithSeckill() {
		SuccessKill success=successKillDao.queryWithSeckill(1000L, 13618381267L);
		System.out.println(success);
		System.out.println(success.getSeckill());
		
		/**
		 * SuccessKill [seckillId=1000, userPhone=13618381267, state=0, createTime=Sat Dec 31 22:28:02 CST 2016, seckill=Seckill [seckillId=1000, name=1000元秒杀iphone6, number=99, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sun Jan 01 00:00:00 CST 2017, createTime=Fri Dec 30 16:23:42 CST 2016]]
           Seckill [seckillId=1000, name=1000元秒杀iphone6, number=99, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sun Jan 01 00:00:00 CST 2017, createTime=Fri Dec 30 16:23:42 CST 2016]

		 */
	}

}
