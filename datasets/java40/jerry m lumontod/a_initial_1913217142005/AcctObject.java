
package com.jml.eisapp.acctg.base;
import com.jml.eisapp.acctg.base.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AcctObject {

    private AcctType moAcctType;
    private List molisteners = new ArrayList();
    private String mstrAcctCode,mstrAcctDesc;
	private String mstrAcctType;
	
    public AcctObject() {
    	super();
    }

    public AcctObject(AcctType toAcctType) {
    	moAcctType=toAcctType;
    }
        
    public synchronized void NewAcctSelectionEventReceived(String tstrAcctCode,String tstrAcctDesc,String tstrAcctType) {
			
		mstrAcctCode = tstrAcctCode;
		mstrAcctDesc = tstrAcctDesc;
		mstrAcctType=tstrAcctType;
		moAcctType = new AcctType(mstrAcctCode,mstrAcctDesc,mstrAcctType);
		
		fireAcctSelectionEvent();
           
    }

    public synchronized void addEventListener(AcctSelectionListener toListener) {
        molisteners.add(toListener);
    }
    
    public synchronized void removeEventListener(AcctSelectionListener toListener) {
        molisteners.remove(toListener);
    }
     
    private synchronized void fireAcctSelectionEvent() {
        AcctEvent moAcctEvent = new AcctEvent(this, moAcctType,mstrAcctType);
        Iterator listeners = molisteners.iterator();
        while(listeners.hasNext()) {
            ((AcctSelectionListener)listeners.next()).AcctSelectionEventReceived(moAcctEvent);
        }
    }
}
