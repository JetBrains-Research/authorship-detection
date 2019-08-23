

package com.jml.eisapp.acctg.ledger.src.process;

import com.jml.eisapp.acctg.base.*;
import com.jml.eisapp.acctg.interfaces.*;

public class CheckGenJournalEntries implements CheckEntries{

	private CompCodeIsValid moCompCodeIsValid = new CompCodeIsValid();
	private DivisionCodeIsValid moDivisionCodeIsValid = new DivisionCodeIsValid();
	private GLCodeIsValid moGLCodeIsValid = new GLCodeIsValid();
	private SLCodeIsValid moSLCodeIsValid = new SLCodeIsValid();
	private DeptCodeIsValid moDeptCodeIsValid = new DeptCodeIsValid();
	private SubDeptCodeIsValid moSubDeptCodeIsValid = new SubDeptCodeIsValid();
	private ClassCodeIsValid moClassCodeIsValid = new ClassCodeIsValid();
	private SubClassIsValid moSubClassIsValid = new SubClassIsValid();
	private DateFormatIsValid moDateFormatIsValid = new DateFormatIsValid();
	private CheckForMustEntries moCheckForMustEntries = new CheckForMustEntries();
	
	
	public boolean CompCodeIsValid(String tstrCompCode) {
		boolean mbln=true;
		moCompCodeIsValid.CompCodeIsValid(tstrCompCode);
		if (moCompCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return mbln;		
	}
	public boolean DivisionCodeIsValid(String tstrDivisionCode) {
		boolean mbln=true;
		moDivisionCodeIsValid.DivisionCodeIsValid(tstrDivisionCode);
		if (moDivisionCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return mbln;
	}
	public boolean GLCodeIsValid(String tstrCompCode,String tstrGLCode) {
		boolean mbln=true;
		moGLCodeIsValid.GLCodeIsValid(tstrCompCode,tstrGLCode);
		if (moGLCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return mbln;
	}
	public boolean SLCodeIsValid(String tstrSLCode) {
		boolean mbln=true;
		
		moSLCodeIsValid.SLCodeIsValid(tstrSLCode);
		if (moSLCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return true; //temporary, we dont have business policies to apply yet...
	}
	public boolean DeptCodeIsValid(String tstrCompCode,String tstrDeptCode) {
		boolean mbln=true;
		
		moDeptCodeIsValid.DeptCodeIsValid(tstrCompCode,tstrDeptCode);
		if (moDeptCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}

		return true; //temporary, we dont have business policies to apply yet...
	}
	public boolean SubDeptCodeIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode) {
		boolean mbln=true;
		
		moSubDeptCodeIsValid.SubDeptCodeIsValid(tstrCompCode,tstrDeptCode,tstrSubDeptCode);
		if (moSubDeptCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return true; //temporary, we dont have business policies to apply yet...
	}
	public boolean ClassCodeIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode,String tstrClassCode) {
		boolean mbln=true;
		
		moClassCodeIsValid.ClassCodeIsValid(tstrCompCode,tstrDeptCode,tstrSubDeptCode,tstrClassCode);
		if (moClassCodeIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return true; //temporary, we dont have business policies to apply yet...
	}
	public boolean SubClassIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode,String tstrClassCode,String tstrSubClassCode) {
		boolean mbln=true;
		
		moSubClassIsValid.SubClassIsValid(tstrCompCode,tstrDeptCode,tstrSubDeptCode,tstrClassCode,tstrSubClassCode);
		if (moSubClassIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return true; //temporary, we dont have business policies to apply yet...
	}
	public boolean DateFormatIsValid(String tstrDate) {
		boolean mbln=true;

		moDateFormatIsValid.DateFormatIsValid(tstrDate);
		if (moDateFormatIsValid.TheMessage().length()>0) {
			mbln=false;
		}
		return mbln;
	}
	
	public boolean DocCodeIsValid(String tstrDocCode) {
		boolean mbln=true;


		if (tstrDocCode.length()<1) {
			mbln=false;
		}
		return mbln;
	}

	public boolean AmountIsValid(String tstrAmount) {
		boolean mbln=true;
		double mdbl;
		mdbl=0;
		
		if (tstrAmount.length()<1) {
			mbln=false;
			return mbln;
		}
		
		try {
			mdbl = Double.parseDouble(tstrAmount);
		}catch (Exception e) {
			mbln=false;	
		}

		
		return mbln;
	}
	
			
	public boolean CheckForMustEntries(String tstrDate,
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
									
		boolean mbln=true;
		moCheckForMustEntries.CheckForMustEntries(tstrDate,tstrDateTo,tstrDocCode,tstrComp,tstrDivision,tstrGL,tstrSL,tstrDept,tstrSubDept,tstrClass,tstrSubClass,tstrRef,tstrRemarks,tstrAmount);
		if (moCheckForMustEntries.TheMessage().length()>0) {
			mbln=false;
		}
		return mbln;
	}
}