package cn.abovesky.shopping.dwr;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

import java.util.Collection;

public class Push {
    public static void sendMessageAuto(final String merchantId, final String message) {
        try {
            Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
                public boolean match(ScriptSession session) {
                    if (session.getAttribute("merchantId") == null)
                        return false;
                    else
                        return (session.getAttribute("merchantId")).equals(merchantId);
                }
            }, new Runnable() {
                public void run() {
                    ScriptBuffer script = new ScriptBuffer();
                    script.appendCall("showMessage", message);
                    Collection<ScriptSession> sessions = Browser.getTargetSessions();
                    for (ScriptSession scriptSession : sessions) {
                        scriptSession.addScript(script);
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
