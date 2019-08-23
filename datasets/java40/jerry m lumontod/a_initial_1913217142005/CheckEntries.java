

package com.jml.eisapp.acctg.interfaces;
public interface CheckEntries {

	public boolean CompCodeIsValid(String tstrCompCode);
	public boolean DivisionCodeIsValid(String tstrDivisionCode);
	public boolean GLCodeIsValid(String tstrCompCode,String tstrGLCode);
	public boolean SLCodeIsValid(String tstrSLCode);
	public boolean DeptCodeIsValid(String tstrCompCode,String tstrDeptCode);
	public boolean SubDeptCodeIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode);
	public boolean ClassCodeIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode,String tstrClassCode);
	public boolean SubClassIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode,String tstrClassCode,String tstrSubClassCode);
	public boolean DateFormatIsValid(String tstrDate);
	public boolean CheckForMustEntries(String tstrDate,String tstrDateTo,String tstrDocCode,String tstrComp,String tstrDivision,String tstrGL,String tstrSL,String tstrDept,String tstrSubDept,String tstrClass,String tstrSubClass,String tstrRef,String tstrRemarks,String tstrAmount);
	public boolean DocCodeIsValid(String tstrDocCode);
	public boolean AmountIsValid(String tstrAmount);
}

