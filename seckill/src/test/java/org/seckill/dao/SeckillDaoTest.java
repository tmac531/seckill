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
 * ����spring��junit���ϣ�junit����ʱ����springIOC����
 *spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit,spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//ע��Daoʵ���������
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	
	 
	public void testQueryById() {
		long id=1000;
		Seckill seckill=seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		/**
		 * 1000Ԫ��ɱiphone6
           Seckill [seckillId=1000, 
           name=1000Ԫ��ɱiphone6, 
           number=100, 
           startTime=Fri Dec 30 00:00:00 CST 2016, 
           endTime=Sat Dec 31 00:00:00 CST 2016, 
           createTime=Fri Dec 30 16:23:42 CST 2016]

		 */
		
	}
	

	@Test
	public void testQueryAll() {
		/**
		 * javaû�б����βεļ�¼queryAll(int offet,int limit)-->queryAll(arg0,arg1)
		 * �������������java��arg0��arg1����,������sql�ļ����޷�ʶ������Ķ�Ӧ��ϵ�������ڽӿ�����@paramָ��
		 * ��һ�������ͽ�offet,�ڶ��������ͽ�limit,��SeckillDao.xml��mybatis��ʶ��
		 */
		List<Seckill> lists=seckillDao.queryAll(0, 10);
		for (Seckill seckill : lists) {
			System.out.println(seckill.getName());
			System.out.println(seckill);
			
			/**
1000Ԫ��ɱiphone6
Seckill [seckillId=1000, name=1000Ԫ��ɱiphone6, number=100, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
100Ԫ��ɱipad2
Seckill [seckillId=1001, name=100Ԫ��ɱipad2, number=200, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
400Ԫ��ɱС��5
Seckill [seckillId=1002, name=400Ԫ��ɱС��5, number=300, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]
600Ԫ��ɱ����note
Seckill [seckillId=1003, name=600Ԫ��ɱ����note, number=400, startTime=Fri Dec 30 00:00:00 CST 2016, endTime=Sat Dec 31 00:00:00 CST 2016, createTime=Fri Dec 30 16:23:42 CST 2016]

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
