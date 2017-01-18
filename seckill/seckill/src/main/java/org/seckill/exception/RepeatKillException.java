/**
 * 
 */
package org.seckill.exception;

/**
 *@description:重复秒杀异常，运行期异常
 *@author MaYue 2017年1月1日下午1:58:28
 *
 */
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message) {
		super(message);
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
