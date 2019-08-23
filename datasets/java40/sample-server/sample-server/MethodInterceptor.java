package com.lz.center;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.control.IndexController;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.OperateLogEntity;
import com.lz.center.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
/**
 * User: Teaey
 * Date: 13-8-26
 */
public class MethodInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger log                 = LoggerFactory.getLogger(MethodInterceptor.class);
    private static final String PERFORMANCE_CAL_KEY = "PERFORMANCE_CAL";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //请求资源，直接放过
        if (!(handler instanceof HandlerMethod))
        {
            //log.info("{}", handler.getClass().getSimpleName());
            return true;
        }
        //判断是否有ctx
        HttpSession session = request.getSession();
        CenterContext ctx = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, session);
        if (null == ctx)
        {
            //String s1 = request.getRequestURL().toString();
            String target = request.getRequestURI();
            String index = request.getContextPath() + IndexController.INDEX;
            String login = request.getContextPath() + IndexController.LOGIN;
            if (!target.equals(index) && !(target.equals(login)))
            {
                request.getRequestDispatcher("/").forward(request, response);
                log.info("没有登录踢回登录界面 sessionId={}", session.getId());
                return false;
            }
        }
        startProfile(request);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ACL acl = handlerMethod.getMethodAnnotation(ACL.class);
        if (null == acl)
        {
            return true;
        }
        boolean toBeHandle = true;
        Limit limitLack = null;
        if (null != ctx)
        {
            UserEntity userEntity = ctx.getUser();
            for (Limit each : acl.limit())
            {
                if (!userEntity.haveLimit(each))
                {
                    log.info("[{}]没有[{}]权限", ctx.getUser().getAccount(), each);
                    limitLack = each;
                    toBeHandle = false;
                    break;
                }
            }
        }
        if (!toBeHandle)
        {
            String errorMsg = new StringBuilder().append("您没有[").append(limitLack == null ? "" : limitLack.getDesc()).append("]权限").toString();
            if (acl.type() == RequestType.PAGE)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("/overview/?errorMsg=").append(errorMsg);
                //response.sendRedirect(sb.toString());
                request.getRequestDispatcher(sb.toString()).forward(request, response);
            }
            else if (acl.type() == RequestType.JSON)
            {
                JsonResponse resultCode = new JsonResponse();
                resultCode.setResult(JsonResponse.Result.FALSE);
                resultCode.setMsg(errorMsg);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getOutputStream().write(resultCode.toJsonBytes());
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
            else
            {
                log.warn("unknown RequestType:{}", acl.type());
            }
        }
        else
        {
            String account = ctx == null ? "未登录用户" : ctx.getUser().getAccount();
            String username = ctx == null ? "未登录用户" : ctx.getUser().getUsername();
            String operation = handlerMethod.getMethod().getDeclaringClass().getName() + "." + handlerMethod.getMethod().getName();
            log.info("[ {} ]进行[ {} ]操作", account, operation);
            OperateLogEntity operateLogEntity = new OperateLogEntity();
            operateLogEntity.setAccount(account);
            operateLogEntity.setUsername(username);
            operateLogEntity.setOperation(operation);
            DaoManager.getLocalDao().saveOrUpdate(operateLogEntity);
        }
        return toBeHandle;
    }
    /**
     * 开始计时
     * @param request
     */
    private void startProfile(HttpServletRequest request)
    {
        request.setAttribute(PERFORMANCE_CAL_KEY, System.nanoTime());
    }
    /**
     * 开始计时
     * @param request
     */
    private void endProfile(HttpServletRequest request, Object handler)
    {
        Long start = (Long) request.getAttribute(PERFORMANCE_CAL_KEY);
        if (null != start)
        {
            long end = System.nanoTime();
            String name = handler.getClass().getSimpleName();
            if (handler instanceof HandlerMethod)
            {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                name = handlerMethod.getMethod().getDeclaringClass().getName() + "." + handlerMethod.getMethod().getName();
            }
            long mills = TimeUnit.NANOSECONDS.toMillis(end - start);
            if (mills < 3000)
            {
                log.debug("处理请求[ {} ]耗时[ {} ]毫秒", name, mills);
            }
            else
            {
                log.info("处理请求[ {} ]耗时[ {} ]毫秒", name, mills);
            }
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        endProfile(request, handler);
    }
}
