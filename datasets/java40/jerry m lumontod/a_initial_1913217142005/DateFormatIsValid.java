

package com.jml.eisapp.acctg.base;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class DateFormatIsValid {
	
	private String mstrTheMessage="",mstrDummy[];
	private Date mdDate;
	private String mstrDate;
	
	
	public void DateFormatIsValid(String tstrDate) {
		SimpleDateFormat msdfFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat mdf = new SimpleDateFormat( "MM/dd/yyyy" );
		Calendar mgc;
		mstrDate = tstrDate;
		
		try {
			mdDate = msdfFormat.parse(mstrDate);
			mstrDummy = mstrDate.split("/");
			mgc = new GregorianCalendar((int)Double.parseDouble(mstrDummy[2]),(int)Double.parseDouble(mstrDummy[1]),(int)Double.parseDouble(mstrDummy[0]));
			//System.out.println("Date valid value is " + mdf.format(mdDate));
			//System.out.println("mgc value is " + mdf.format(mgc.getTime()));
			//System.out.println("mstrDummy value is " + mstrDummy[0] + "-" + mstrDummy[1] + "-" + mstrDummy[2]);
			mstrTheMessage="";
		}catch (Exception e) {
			mstrTheMessage="Invalid date format:  " + e.getMessage();
			//System.out.println(mstrTheMessage);
			
		}
        		
	}

	public String TheMessage() {
		return mstrTheMessage;
	}
	/**public static void main(String[] args) {
		int dummy;
		dummy=0;
		DateFormatIsValid DateFormatIsValidInst = new DateFormatIsValid("01/01/2005");
	}*/
}

