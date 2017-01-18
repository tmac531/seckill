/**
 * 
 */
package org.seckill.dto;

import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;

/**
 *@description:封装秒杀执行后结果
 *@author MaYue 2017年1月1日下午1:11:01
 *
 */
public class SeckillExecution {

	private long seckillId;
	
	private int state;
	
	private String stateInfo;
	
	private SuccessKill successKill;

	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum,SuccessKill successKill) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKill = successKill;
	}

	public SeckillExecution(long seckillId,SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKill getSuccessKill() {
		return successKill;
	}

	public void setSuccessKill(SuccessKill successKill) {
		this.successKill = successKill;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKill=" + successKill + "]";
	}
	
}
