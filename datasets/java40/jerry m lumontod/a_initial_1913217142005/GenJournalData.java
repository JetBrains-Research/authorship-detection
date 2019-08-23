

package com.jml.eisapp.acctg.ledger.src.gui.guibase;

import com.jml.eisapp.acctg.base.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
public class GenJournalData {

	public static ImageIcon ICON_UP = new ImageIcon("ArrUp.gif");
	public static ImageIcon ICON_DOWN = new ImageIcon("ArrDown.gif");
	public static ImageIcon ICON_BLANK = new ImageIcon("blank.gif");

	SimpleDateFormat frm = new SimpleDateFormat("MM/dd/yyyy");

		

	public String mstrBatch;
	public String mstrEntryCode;
	public String mstrCompanyCode;
	public String mstrCompanyDesc;	
	public String mstrDivisionCode;
	public String mstrDivisionDesc;	
	public String mstrGLCode;
	public String mstrGLDesc;	
	public String mstrSLCode;
	public String mstrSLDesc;	
	public String mstrDeptCode;
	public String mstrDeptDesc;	
	public String mstrSubDeptCode;
	public String mstrSubDeptDesc;	
	public String mstrClassCode;
	public String mstrClassDesc;
	public String mstrSubClassCode;
	public String mstrSubClassDesc;	
	public String mstrAmount;
	public String mstrUserCode;
	public String mstrUserDesc;
	public String mstrDRCR;
	public String mstrEntryDate;
	public String mstrPostDate;
	public String mstrFiscalCode;
	public String mstrReference;
	public String mstrRemarks;
	public String mstrDocCode;
	public String mstrDocnum;
	public String mstrDoclinenum;
	public String mstrDueDate;
	public String mstrPayDate;
	public String mstrPostType;
	public String mstrEntrySource;
	public String mstrMatchedVia;
	public String mstrRefDocnum;
	public String mstrRefDoclineum;

	

	public GenJournalData(String tstrBatch,
		String tstrEntryCode,
		String tstrCompanyCode,
		String tstrCompanyDesc,
		String tstrDivisionCode,
		String tstrDivisionDesc,
		String tstrGLCode,
		String tstrGLDesc,
		String tstrSLCode,
		String tstrSLDesc,
		String tstrDeptCode,
		String tstrDeptDesc,
		String tstrSubDeptCode,
		String tstrSubDeptDesc,
		String tstrClassCode,
		String tstrClassDesc,
		String tstrSubClassCode,
		String tstrSubClassDesc,
		String tstrAmount,
		String tstrUserCode,
		String tstrUserDesc,
		String tstrDRCR,
		String tstrEntryDate,
		String tstrPostDate,
		String tstrFiscalCode,
		String tstrReference,
		String tstrRemarks,
		String tstrDocCode,
		String tstrDocnum,
		String tstrDoclinenum,
		String tstrDueDate,
		String tstrPayDate,
		String tstrPostType,
		String tstrEntrySource,
		String tstrMatchedVia,
		String tstrRefDocnum,
		String tstrRefDoclineum) {
		
		mstrBatch=tstrBatch;
		mstrEntryCode=tstrEntryCode;
		mstrCompanyCode=tstrCompanyCode;
		mstrCompanyDesc=tstrCompanyDesc;
		mstrDivisionCode=tstrDivisionCode;
		mstrDivisionDesc=tstrDivisionDesc;
		mstrGLCode=tstrGLCode;
		mstrGLDesc=tstrGLDesc;
		mstrSLCode=tstrSLCode;
		mstrSLDesc=tstrSLDesc;
		mstrDeptCode=tstrDeptCode;
		mstrDeptDesc=tstrDeptDesc;
		mstrSubDeptCode=tstrSubDeptCode;
		mstrSubDeptDesc=tstrSubDeptDesc;
		mstrClassCode=tstrClassCode;
		mstrClassDesc=tstrClassDesc;
		mstrSubClassCode=tstrSubClassCode;
		mstrSubClassDesc=tstrSubClassDesc;
		mstrAmount=tstrAmount; //need to convert this to object;
		mstrUserCode=tstrUserCode;
		mstrUserDesc=tstrUserDesc;
		mstrDRCR=tstrDRCR;
		mstrEntryDate = tstrEntryDate; 
		mstrPostDate = tstrPostDate;
		mstrFiscalCode=tstrFiscalCode;
		mstrReference=tstrReference;
		mstrRemarks=tstrRemarks;
		mstrDocCode=tstrDocCode;
		mstrDocnum=tstrDocnum;
		mstrDoclinenum=tstrDoclinenum;
		mstrDueDate = tstrDueDate;
		mstrPayDate = tstrPayDate;
		mstrPostType=tstrPostType;
		mstrEntrySource=tstrEntrySource;
		mstrMatchedVia=tstrMatchedVia;
		mstrRefDocnum=tstrRefDocnum;
		mstrRefDoclineum=tstrRefDoclineum;

	}

	public static ImageIcon getIcon(double tdblChange) {
		return (tdblChange>0 ? ICON_UP : (tdblChange<0 ? ICON_DOWN : ICON_BLANK));
	}

}

