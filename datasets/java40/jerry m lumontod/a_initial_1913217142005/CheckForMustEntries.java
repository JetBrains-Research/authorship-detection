
package com.jml.eisapp.acctg.ledger.src.process;

public class CheckForMustEntries {
	private String mstrErrMsg="";
	public void CheckForMustEntries(String tstrDate,
								String tstrDateTo,
								String tstrDocCode,
								String tstrComp,
								String tstrDivision,
								String tstrGL,
								String tstrSL,
								String tstrDept,
								String tstrSubDept,
								String tstrClass,
								String tstrSubClass,
								String tstrRef,
								String tstrRemarks,
								String tstrAmount) {
	
		if (tstrDate.toString().trim().length()<1)	{
			mstrErrMsg = "Must specify date of entry|";
		}
		if (tstrDateTo.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}		
		if (tstrDocCode.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "Must specify Document number|";
		}
		if (tstrComp.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "Must specify company code|";
		}
		if (tstrDivision.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "Must specify division code|";
		}
		if (tstrGL.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "Must specify GL account|";
		}
		if (tstrSL.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrDept.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrSubDept.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrClass.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrSubClass.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrRef.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrRemarks.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "";
		}
		if (tstrAmount.toString().trim().length()<1)	{
			mstrErrMsg = mstrErrMsg + "Must specify amount|";
		}
	}
	
	public String TheMessage() {
		return mstrErrMsg;
	}
}

