/**
 * 
 */
package org.seckill.exception;

/**
 *@description:�ظ���ɱ�쳣���������쳣
 *@author MaYue 2017��1��1������1:58:28
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
