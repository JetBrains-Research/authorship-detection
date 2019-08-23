package org.chacha.log;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��־����
 * 
 * @author Fully
 * 
 */
public class LogPrn implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient Log prnLog = null;

	/**
	 * ���캯��������������
	 * 
	 * @param className
	 */
	public LogPrn(final String className) {
		prnLog = LogFactory.getLog(className);
	}

	/**
	 * ���캯�������������������
	 * 
	 * @param c
	 */
	public LogPrn(Class<?> c) {
		prnLog = LogFactory.getLog(c);
	}

	/**
	 * �ж��Ƿ�������debug���������Ϣ
	 * 
	 * @return
	 */
	public boolean isDebugEnable() {
		return prnLog.isDebugEnabled();
	}

	/**
	 * �ж��Ƿ�������info���������Ϣ
	 * 
	 * @return
	 */
	public boolean isInfoEnable() {
		return prnLog.isInfoEnabled();
	}

	/**
	 * �ж��Ƿ�������warn���������Ϣ
	 * 
	 * @return
	 */
	public boolean isWarnEnable() {
		return prnLog.isWarnEnabled();
	}

	/**
	 * �ж��Ƿ�������error���������Ϣ
	 * 
	 * @return
	 */
	public boolean isErrorEnable() {
		return prnLog.isErrorEnabled();
	}

	/**
	 * �ж��Ƿ�������fatal���������Ϣ
	 * 
	 * @return
	 */
	public boolean isFatalEnable() {
		return prnLog.isFatalEnabled();
	}

	/**
	 * �ж��Ƿ�������trace���������Ϣ
	 * 
	 * @return
	 */
	public boolean isTraceEnable() {
		return prnLog.isTraceEnabled();
	}

	/**
	 * ���debug����ĵ�����Ϣ
	 * 
	 * @param message
	 */
	public void debug(Object message) {
		prnLog.debug(message);
	}

	/**
	 * ���debug����ĵ�����Ϣ
	 * 
	 * @param message
	 * @param t
	 *            �쳣�������������ӡ���쳣�����ջ��Ϣ
	 */
	public void debug(Object message, Throwable t) {
		prnLog.debug(message, t);
	}

	/**
	 * ���INFO����ĵ�����Ϣ
	 * 
	 * @param message
	 */
	public void info(Object message) {
		prnLog.info(message);
	}

	/**
	 * ���INFO����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 * @param t
	 *            �쳣�������������ӡ���쳣�����ջ��Ϣ
	 */
	public void info(Object message, Throwable t) {
		prnLog.info(message, t);
	}

	/**
	 * ���WARN����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 */
	public void warn(Object message) {
		prnLog.warn(message);
	}

	/**
	 * ���WARN����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 * @param t
	 *            �쳣�������������ӡ���쳣�����ջ��Ϣ
	 */
	public void warn(Object message, Throwable t) {
		prnLog.warn(message, t);
	}

	/**
	 * ���ERROR����ĵ�����Ϣ
	 * 
	 * @param message
	 */
	public void error(Object message) {
		prnLog.error(message);
	}

	/**
	 * ���ERROR����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 * @param t
	 *            �쳣�������������ӡ���쳣�����ջ��Ϣ
	 */
	public void error(Object message, Throwable t) {
		prnLog.error(message, t);
	}

	/**
	 * ���FATAL����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 */
	public void fatal(Object message) {
		prnLog.fatal(message);
	}

	/**
	 * ���FATAL����ĵ�����Ϣ
	 * 
	 * @param message
	 *            ������Ϣ
	 * @param t
	 *            �쳣�������������ӡ���쳣�����ջ��Ϣ
	 */
	public void fatal(Object message, Throwable t) {
		prnLog.fatal(message, t);
	}

}
