package cn.abovesky.shopping.dwr;

import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.Merchant;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.impl.DefaultScriptSessionManager;

import javax.servlet.http.HttpSession;

/**
 * Created by snow on 2014/5/8.
 */
public class DwrScriptSessionManager extends DefaultScriptSessionManager {
    public DwrScriptSessionManager(){
        //绑定一个ScriptSession增加销毁事件的监听器
        ScriptSessionListener listener = new ScriptSessionListener() {
            public void sessionCreated(ScriptSessionEvent ev) {
                HttpSession session = WebContextFactory.get().getSession();
                String merchantId = String.valueOf(((Merchant) session.getAttribute(Constants.LOGIN_MERCHANT)).getId());
                ev.getSession().setAttribute("merchantId", merchantId);
            }

            public void sessionDestroyed(ScriptSessionEvent ev) {
            }
        };
        this.addScriptSessionListener(listener);
    }

}
