

package com.jml.eisapp.acctg.base;


import java.util.*;


public class AcctSorter implements Comparator {

	protected int     	mintSortCol;
	protected boolean 	mblnSortAsc;
	protected int 		mintResult = 0;

	public AcctSorter(int tintSortCol, boolean tblnSortAsc) {
		mintSortCol = tintSortCol;
		mblnSortAsc = tblnSortAsc;
	}

	public int compare(Object o1, Object o2) {

		if(!(o1 instanceof AcctData) || !(o2 instanceof AcctData))
			return 0;

		AcctData s1 = (AcctData)o1;
		AcctData s2 = (AcctData)o2;
		
		switch (mintSortCol) {
			
			case 0:    // Code
				mintResult = s1.mstrCode.compareTo(s2.mstrCode);
				break;
			case 1:    // Desc
				mintResult = s1.mstrDesc.compareTo(s2.mstrDesc);
				break;
		}		

		if (!mblnSortAsc)
			mintResult = -mintResult;


		return mintResult;
		
	}

	public boolean equals(Object obj) {
		
		if (obj instanceof AcctSorter) {
			
			AcctSorter compObj = (AcctSorter)obj;
			return (compObj.mintSortCol==mintSortCol) && (compObj.mblnSortAsc==mblnSortAsc);
		}
		
		return false;
	}
}

