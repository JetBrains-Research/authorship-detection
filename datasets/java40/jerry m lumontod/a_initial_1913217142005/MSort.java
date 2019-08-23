

package com.jml.eisapp.acctg.utils.src.gui;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;


public final class MSort implements Comparator, Serializable {

	public MSort (int new_index, Object new_data) {
		index = new_index;
		data = new_data;
	}


	public int 		index;
	public Object 	data;
	private int		m_multiplier = 1;

	public void setSortAsc (boolean ascending) {
		if (ascending)
			m_multiplier = 1;
		else
			m_multiplier = -1;
	}

	public int compare(Object o1, Object o2) {

		Object cmp1 = null;
		if (o1 instanceof MSort)
			cmp1 = ((MSort)o1).data;
		if (cmp1 instanceof NamePair)
			cmp1 = ((NamePair)cmp1).getName();

		Object cmp2 = o2;
		if (o2 instanceof MSort)
			cmp2 = ((MSort)o2).data;
		if (cmp2 instanceof NamePair)
			cmp2 = ((NamePair)cmp2).getName();

		if (cmp1 == null)
			cmp1 = new String("");
		if (cmp2 == null)
			cmp2 = new String("");

		if (cmp1 instanceof Timestamp) {
			Timestamp t = (Timestamp)cmp1;
			return t.compareTo(cmp2) * m_multiplier;
		}

		else if (cmp1 instanceof BigDecimal) {
			BigDecimal d = (BigDecimal)cmp1;
			return d.compareTo(cmp2) * m_multiplier;
		}

		else if (cmp1 instanceof Integer) {
			Integer d = (Integer)cmp1;
			return d.compareTo(cmp2) * m_multiplier;
		}

		else if (cmp1 instanceof String) {
			String s = (String)cmp1;
			return s.compareTo(cmp2.toString()) * m_multiplier;
		}

		String s = cmp1.toString();
		return s.compareTo(cmp2.toString()) * m_multiplier;
	}

	public boolean equals (Object obj) {
		if (obj instanceof MSort) {
			MSort ms = (MSort)obj;
			if (data == ms.data)
				return true;
		}
		return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("MSort[");
		sb.append("Index=").append(index).append(",Data=").append(data);
		sb.append("]");
		return sb.toString();
	}

}
