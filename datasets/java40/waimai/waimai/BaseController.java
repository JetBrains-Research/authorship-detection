package cn.abovesky.shopping.base;

import cn.abovesky.shopping.base.editor.*;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.Admin;
import cn.abovesky.shopping.domain.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

/**
 * Created by snow on 2014/4/20.
 */
public abstract class BaseController {
    @Autowired
    protected HttpServletRequest request;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(Integer.class, new IntegerEditor());
        binder.registerCustomEditor(Long.class, new LongEditor());
        binder.registerCustomEditor(Float.class, new FloatEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    protected ModelAndView ajaxDone(int statusCode, String message, String confirmMsg, String navTabId, String rel, String callbackType, String forwardUrl) {
        ModelAndView mav = new ModelAndView("ajaxDone");
        mav.addObject("statusCode", statusCode);
        mav.addObject("message", message);
        mav.addObject("confirmMsg", confirmMsg);
        mav.addObject("navTabId", navTabId);
        mav.addObject("rel", rel);
        mav.addObject("callbackType", callbackType);
        mav.addObject("forwardUrl", forwardUrl);
        return mav;
    }

    protected ModelAndView ajaxDoneSuccess(String message) {
        return ajaxDone(200, message, "", "", "", "", "");
    }

    protected ModelAndView ajaxDoneError(String message) {
        return ajaxDone(300, message, "", "", "", "", "");
    }

    protected ModelAndView ajaxDoneSuccessAndClose(String message, String navTabId) {
        return ajaxDone(200, message, "", navTabId, "", "closeCurrent", "");
    }

    private boolean isWindowSeparator() {
        Properties properties = System.getProperties();
        if ("/".equals(properties.get("file.separator"))) {
            return false;
        } else {
            return true;
        }
    }

    protected Merchant getMerchant() {
        return request.getSession().getAttribute(Constants.LOGIN_MERCHANT) != null ? (Merchant) request.getSession().getAttribute(Constants.LOGIN_MERCHANT) : null;
    }

    protected Admin getAdmin() {
        return request.getSession().getAttribute(Constants.LOGIN_ADMIN) != null ? (Admin) request.getSession().getAttribute(Constants.LOGIN_ADMIN) : null;
    }
}
