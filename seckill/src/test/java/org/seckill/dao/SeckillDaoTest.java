package org.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 *spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit,spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//注入Dao实现类的依赖
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	
	 
	public void testQueryById() {
		long id=1000;
		Seckill seckill=seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		/**
		 * 1000元秒杀iphone6
           Seckill [seckillId=1000, 
           name=1000元秒杀iphone6, 
           number=100, 
           startTime=Fri Dec 30 00:00:00 CST 2016, 
           endTime=Sat Dec 31 00:00:00 CST 2016, 
           createTime=Fri Dec 30 16:23:42 CST 2016]

		 */
		
	}
	

	@Test
	public void testQueryAll() {
		/**
		 * java没有保存形参的记录queryAll(int offet,int limit)-->queryAll(arg0,arg1)
		 * 多个参数传进来java用arg0和arg1代替,所以在sql文件中无法识别参数的对应关系，所以在接口中用@param指定
		 * 第一个参数就叫offet,第二个参数就叫limit,在SeckillDao.xml中mybatis能识别
		 */
		List<Seckill> lists=seckillDao.queryAll(0, 10);
		for (Seckill seckill : lists) {
			System.out.println(seckill.getName());
			System.out.println(seckill);
			
			/**
1000元秒杀iphone6
Seckill [seckillId=1000, name=1000元秒杀iphone6, number=100, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
100元秒杀ipad2
Seckill [seckillId=1001, name=100元秒杀ipad2, number=200, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
400元秒杀小米5
Seckill [seckillId=1002, name=400元秒杀小米5, number=300, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
600元秒杀红米note
Seckill [seckillId=1003, name=600元秒杀红米note, number=400, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]

			 */
		}
	}
	
	

	@Test
	public void testReduceNumber() {
		Date killDate=new Date();
		int updateCount=seckillDao.reduceNumber(1000L, killDate);
		System.out.println(updateCount);
	}

	

}
