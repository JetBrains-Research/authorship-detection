package com.jml.eisapp.acctg.ledger.src.gui.guibase;

import com.jml.eisapp.acctg.ledger.src.process.processbase.*;
import java.util.*;
import java.text.*;

class GenJournalTableSorter implements Comparator {
	
	protected int     mintSortCol;
	protected boolean mblnSortAsc;
	DateIntervalProcessor moDateIntervalProcessor = new DateIntervalProcessor();
	
	public GenJournalTableSorter(int tintSortCol, boolean tblnSortAsc) {
		mintSortCol = tintSortCol;
		mblnSortAsc = tblnSortAsc;
	}
	
	public int compare(Object o1, Object o2) {
		
		if(!(o1 instanceof GenJournalData) || !(o2 instanceof GenJournalData))
			return 0;

		GenJournalData s1 = (GenJournalData)o1;
		GenJournalData s2 = (GenJournalData)o2;

		int mintResult = 0;
		double d1, d2;
		long mlngTimeInMillis1,mlngTimeInMillis2;
		Date mdDate1=new Date(),mdDate2=new Date();
		SimpleDateFormat msdf = new SimpleDateFormat("mm/dd/yyyy");
		GregorianCalendar mgc1 = new GregorianCalendar(),mgc2 = new GregorianCalendar();
		int mintYear1,mintMonth1,mintDay1,mintYear2,mintMonth2,mintDay2;
				
		/**
		case 0: return row.mstrCompanyCode;
		case 1: return row.mstrDivisionCode;
		case 2: return row.mstrGLCode;
		case 3: return row.mstrSLCode;
		case 4: return row.mstrDeptCode;
		case 5: return row.mstrSubDeptCode;
		case 6: return row.mstrClassCode;
		case 7: return row.mstrSubClassCode;
		case 8: return row.mdblAmount;
		case 9: return row.mstrUserDesc;
		case 10: return row.mdblDRCR;
		case 11: return row.mdEntryDate;
		case 12: return row.mdPostDate;
		case 13: return row.mdblFiscalCode;
		case 14: return row.mstrReference;
		case 15: return row.mstrRemarks;
		case 16: return row.mdDueDate;
		case 17: return row.mdPayDate;
		case 18: return row.mdblRefDocnum;
		case 19: return row.mdblRefDoclineum;
		case 20: return row.mstrCompanyDesc; 
		case 21: return row.mstrDivisionDesc;
		case 22: return row.mstrGLDesc;
		case 23: return row.mstrSLDesc;
		case 24: return row.mstrDeptDesc;
		case 25: return row.mstrSubDeptDesc;
		case 26: return row.mstrClassDesc;
		case 27: return row.mstrSubClassDesc;
		case 28: return row.mdblUserCode;
		case 29: return row.mdblDocnum;
		case 30: return row.mdblDoclinenum;
		case 31: return row.mdblPostType;
		case 32: return row.mdblEntrySource;
		case 33: return row.mdblMatchedVia;
		case 34: return row.mdblBatch;
		case 35: return row.mdblEntryCode;
		*/

								
		switch (mintSortCol) {
			
			case 0:
				mintResult = s1.mstrCompanyCode.compareTo(s2.mstrCompanyCode);
				break;
			case 1:
				mintResult = s1.mstrDivisionCode.compareTo(s2.mstrDivisionCode);
				break;
			case 2:
				mintResult = s1.mstrGLCode.compareTo(s2.mstrGLCode);
				break;
			case 3:
				mintResult = s1.mstrSLCode.compareTo(s2.mstrSLCode);
				break;
			case 4:
				mintResult = s1.mstrDeptCode.compareTo(s2.mstrDeptCode);
				break;
			case 5:
				mintResult = s1.mstrSubDeptCode.compareTo(s2.mstrSubDeptCode);
				break;
			case 6:
				mintResult = s1.mstrClassCode.compareTo(s2.mstrClassCode);
				break;
			case 7:
				mintResult = s1.mstrSubClassCode.compareTo(s2.mstrSubClassCode);
				break;
			case 8:
				mintResult = s1.mstrAmount.compareTo(s2.mstrAmount);
				break;
			case 9:
				mintResult = s1.mstrUserDesc.compareTo(s2.mstrUserDesc);
				break;
			case 10:
				mintResult = s1.mstrDRCR.compareTo(s2.mstrDRCR);
				break;			
			case 11:
				mintResult = Date1GreaterThanDate2(s1.mstrEntryDate,s2.mstrEntryDate);
				break;
			case 12:
				mintResult = Date1GreaterThanDate2(s1.mstrPostDate,s2.mstrPostDate);
				break;
			case 13:
				mintResult = s1.mstrFiscalCode.compareTo(s2.mstrFiscalCode);
				break;						
			case 14:
				mintResult = s1.mstrReference.compareTo(s2.mstrReference);
				break;			
			case 15:
				mintResult = s1.mstrRemarks.compareTo(s2.mstrRemarks);			
				break;
			case 16:
				mintResult = Date1GreaterThanDate2(s1.mstrDueDate,s2.mstrDueDate);
				break;
			case 17:
				mintResult = Date1GreaterThanDate2(s1.mstrPayDate,s2.mstrPayDate);
				break;
			case 18:
				mintResult = s1.mstrRefDocnum.compareTo(s2.mstrRefDocnum);
				break;
			case 19:
				mintResult = s1.mstrRefDoclineum.compareTo(s2.mstrRefDoclineum);
				break;
			case 20:
				mintResult = s1.mstrCompanyDesc.compareTo(s2.mstrCompanyDesc);
				break;
			case 21:
				mintResult = s1.mstrDivisionDesc.compareTo(s2.mstrDivisionDesc);
				break;
			case 22:
				mintResult = s1.mstrGLDesc.compareTo(s2.mstrGLDesc);
				break;
			case 23:
				mintResult = s1.mstrSLDesc.compareTo(s2.mstrSLDesc);
				break;
			case 24:
				mintResult = s1.mstrDeptDesc.compareTo(s2.mstrDeptDesc);
				break;
			case 25:
				mintResult = s1.mstrSubDeptDesc.compareTo(s2.mstrSubDeptDesc);
				break;
			case 26:
				mintResult = s1.mstrClassDesc.compareTo(s2.mstrClassDesc);
				break;
			case 27:
				mintResult = s1.mstrSubClassDesc.compareTo(s2.mstrSubClassDesc);
				break;
			case 28:
				mintResult = s1.mstrUserCode.compareTo(s2.mstrUserCode);
				break;
			case 29:
				mintResult = s1.mstrDocnum.compareTo(s2.mstrDocnum);
				break;
			case 30:
				mintResult = s1.mstrRefDoclineum.compareTo(s2.mstrRefDoclineum);
				break;
			case 31:
				mintResult = s1.mstrPostType.compareTo(s2.mstrPostType);
				break;
			case 32:
				mintResult = s1.mstrEntrySource.compareTo(s2.mstrEntrySource);
				break;
			case 33:
				mintResult = s1.mstrMatchedVia.compareTo(s2.mstrMatchedVia);
				break;
			case 34:
				mintResult = s1.mstrBatch.compareTo(s2.mstrBatch);
				break;
			case 35:
				mintResult = s1.mstrEntryCode.compareTo(s2.mstrEntryCode);
				break;
		}

		
		if (!mblnSortAsc)
			mintResult = -mintResult;
		return mintResult;
	}
	
	public boolean equals(Object obj) {
		
		if (obj instanceof GenJournalTableSorter) {
			GenJournalTableSorter compObj = (GenJournalTableSorter)obj;
			return (compObj.mintSortCol==mintSortCol) && (compObj.mblnSortAsc==mblnSortAsc);
		}
		return false;
	}
	
	public int Date1GreaterThanDate2(String tstrDate1,String tstrDate2) {
		
		int mintYear1,mintMonth1,mintDay1,mintYear2,mintMonth2,mintDay2;
		int mintResult=0;
		
		mintYear1 = moDateIntervalProcessor.GetYearFromString(tstrDate1);
		mintMonth1=moDateIntervalProcessor.GetMonthFromString(tstrDate1);
		mintDay1=moDateIntervalProcessor.GetDayFromString(tstrDate1);

		mintYear2 = moDateIntervalProcessor.GetYearFromString(tstrDate2);
		mintMonth2=moDateIntervalProcessor.GetMonthFromString(tstrDate2);
		mintDay2=moDateIntervalProcessor.GetDayFromString(tstrDate2);

		if (mintYear1>mintYear2) { 
			mintResult=1;
		}else if (mintYear1>=mintYear2 && mintMonth1>mintMonth2) {
			 mintResult=1;
		}else if (mintYear1>=mintYear2 && mintMonth1 >=mintMonth2 && mintDay1 > mintDay2) { 
			mintResult=1; 
		}else {
			mintResult=-1;
		}
		
		return mintResult;
		
	}
}