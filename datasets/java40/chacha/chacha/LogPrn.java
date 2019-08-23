package org.chacha.log;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志功能
 * 
 * @author Fully
 * 
 */
public class LogPrn implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient Log prnLog = null;

	/**
	 * 构造函数，带调用类名
	 * 
	 * @param className
	 */
	public LogPrn(final String className) {
		prnLog = LogFactory.getLog(className);
	}

	/**
	 * 构造函数，带调用类参数构造
	 * 
	 * @param c
	 */
	public LogPrn(Class<?> c) {
		prnLog = LogFactory.getLog(c);
	}

	/**
	 * 判断是否可以输出debug级别调试信息
	 * 
	 * @return
	 */
	public boolean isDebugEnable() {
		return prnLog.isDebugEnabled();
	}

	/**
	 * 判断是否可以输出info级别调试信息
	 * 
	 * @return
	 */
	public boolean isInfoEnable() {
		return prnLog.isInfoEnabled();
	}

	/**
	 * 判断是否可以输出warn级别调试信息
	 * 
	 * @return
	 */
	public boolean isWarnEnable() {
		return prnLog.isWarnEnabled();
	}

	/**
	 * 判断是否可以输出error级别调试信息
	 * 
	 * @return
	 */
	public boolean isErrorEnable() {
		return prnLog.isErrorEnabled();
	}

	/**
	 * 判断是否可以输出fatal级别调试信息
	 * 
	 * @return
	 */
	public boolean isFatalEnable() {
		return prnLog.isFatalEnabled();
	}

	/**
	 * 判断是否可以输出trace级别调试信息
	 * 
	 * @return
	 */
	public boolean isTraceEnable() {
		return prnLog.isTraceEnabled();
	}

	/**
	 * 输出debug级别的调试信息
	 * 
	 * @param message
	 */
	public void debug(Object message) {
		prnLog.debug(message);
	}

	/**
	 * 输出debug级别的调试信息
	 * 
	 * @param message
	 * @param t
	 *            异常对象参数，将打印该异常对象堆栈信息
	 */
	public void debug(Object message, Throwable t) {
		prnLog.debug(message, t);
	}

	/**
	 * 输出INFO级别的调试信息
	 * 
	 * @param message
	 */
	public void info(Object message) {
		prnLog.info(message);
	}

	/**
	 * 输出INFO级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 * @param t
	 *            异常对象参数，将打印该异常对象堆栈信息
	 */
	public void info(Object message, Throwable t) {
		prnLog.info(message, t);
	}

	/**
	 * 输出WARN级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 */
	public void warn(Object message) {
		prnLog.warn(message);
	}

	/**
	 * 输出WARN级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 * @param t
	 *            异常对象参数，将打印该异常对象堆栈信息
	 */
	public void warn(Object message, Throwable t) {
		prnLog.warn(message, t);
	}

	/**
	 * 输出ERROR级别的调试信息
	 * 
	 * @param message
	 */
	public void error(Object message) {
		prnLog.error(message);
	}

	/**
	 * 输出ERROR级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 * @param t
	 *            异常对象参数，将打印该异常对象堆栈信息
	 */
	public void error(Object message, Throwable t) {
		prnLog.error(message, t);
	}

	/**
	 * 输出FATAL级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 */
	public void fatal(Object message) {
		prnLog.fatal(message);
	}

	/**
	 * 输出FATAL级别的调试信息
	 * 
	 * @param message
	 *            调试信息
	 * @param t
	 *            异常对象参数，将打印该异常对象堆栈信息
	 */
	public void fatal(Object message, Throwable t) {
		prnLog.fatal(message, t);
	}

}
