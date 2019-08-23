package ipower.wechat.servlets;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet代理类,配合Spring进行管理。
 * @author yangyong.
 * @since 2014-02-20.
 * */
public class ServletProxy extends GenericServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ServletProxy.class);
	private Servlet proxy;
	
	@Override
	public void init() throws ServletException{
		String err = null;
		logger.info("初始化Servlet代理类...");
		String targetServletBean = this.getInitParameter("targetServletBean");
		if(targetServletBean == null || targetServletBean.trim().isEmpty()){
			logger.error(err = "未配置参数：targetServletBean");
			throw new ServletException(err);
		}
		logger.info("装载[servlet:" + targetServletBean + "]");
		this.proxy = this.loadServletBean(targetServletBean);
		if(this.proxy == null){
			logger.error(err = "装载[servlet:" + targetServletBean + "]失败，未在Spring配置中找到对象！");
			throw new ServletException(err);
		}
		this.proxy.init(this.getServletConfig());
		logger.info("成功装载[servlet:" + targetServletBean + "]对象:[" + this.proxy.getClass().getName() + "]");
		logger.info("初始化Servlet代理类完成。");
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		this.proxy.service(req, res);
	}
	/**
	 * 加载目标Servlet对象。
	 * @param targetServletBean
	 * 	在Spring中配置的Bean对象。
	 * */
	protected synchronized Servlet loadServletBean(String targetServletBean){
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return (Servlet)wac.getBean(targetServletBean);
	}
}
