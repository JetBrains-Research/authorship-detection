package com.lz.center.control;
import com.lz.center.AttributeManager;
import com.lz.center.CenterContext;
import com.lz.center.JsonResponse;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.LoginLogEntiry;
import com.lz.center.entity.UserEntity;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.listener.SpringContextUtil;
import com.lz.center.rpc.ZoneEntityManager;
import com.lz.game.rpc.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-8-23
 */
@Controller
@RequestMapping(value = "/")
//@SessionAttributes(value = {AttributeManager.SESSION_ATTR_CTX}, types = {CenterContext.class})
public class IndexController
{
    private static final Logger log    = LoggerFactory.getLogger(IndexController.class);
    public static final  String INDEX  = "/";
    public static final  String LOGIN  = "/login";
    public static final  String LOGOUT = "/logout";
    @RequestMapping(value = INDEX)
    public String index()
    {
        return "login";
    }
    /**
     * 登录操作
     * @param account
     * @param passwd
     * @return
     */
    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(required = true) String account, @RequestParam(required = true) String passwd, Locale locale, HttpServletRequest request)
    {
        JsonResponse resultCode = new JsonResponse();
        if (null == account || null == passwd || account.isEmpty() || passwd.isEmpty())
        {
            resultCode.setResult(JsonResponse.Result.FALSE);
            resultCode.setMsg(SpringContextUtil.getMessage("login.accountorpassword.empty", "帐号或密码不能为空", locale));
        }
        else
        {
            String encryptPwd = MD5Encrypt.encrypt(passwd);
            UserEntity user = DaoManager.getLocalDao().selectUser4Login(account, encryptPwd);
            if (null == user)
            {
                resultCode.setResult(JsonResponse.Result.FALSE);
                resultCode.setMsg(SpringContextUtil.getMessage("login.accountorpassword.error", "帐号或密码错误", locale));
            }
            else
            {
                AttributeManager.setAttribute(AttributeManager.SESSION_ATTR_CTX, request.getSession(), CenterContext.newContext(user));
                ZoneEntity zoneEntity = ZoneEntityManager.getInstance().getFirstZoneEntiry();
                AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, request.getSession()).setTargetZone(zoneEntity);
                resultCode.setUrl(request.getContextPath() + "/overview/");
                LoginLogEntiry loginLogEntiry = new LoginLogEntiry();
                loginLogEntiry.setUserName(user.getUsername());
                DaoManager.getLocalDao().saveOrUpdate(loginLogEntiry);
                log.info("用户[{}]登录系统", account);
            }
        }
        return resultCode.toJson();
    }
    @RequestMapping(value = LOGOUT)
    protected String handle(RedirectAttributes ra, Map<String, Object> attr, HttpSession session) throws ServletException, IOException
    {
        CenterContext cc = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, session);
        if (null != cc)
        {
            AttributeManager.setAttribute(AttributeManager.SESSION_ATTR_CTX, session, null);
            //attr.put(CenterContext.SESSION_ATTR_CTX, null);
            log.info("登出 userId={} userName={}", cc.getUser().getId(), cc.getUser().getUsername());
        }
        else
        {
            log.warn("未登录无法登出");
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }
}
