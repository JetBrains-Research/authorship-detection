
package com.jml.eisapp.acctg.base;

//import com.jml.eisapp.acctg.ledger.src.gui.guibase.*;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;


public class AcctTableData extends AbstractTableModel {

	static final public ColumnSpecs mobjColumns[] = {
		new ColumnSpecs("Code", 100, JLabel.LEFT),
		new ColumnSpecs("Description", 160, JLabel.LEFT)
	};

	protected SimpleDateFormat mdFrm;
	protected Vector moVector;
	protected java.util.Date mdDate;
	protected int mintColumnsCount = mobjColumns.length;
	
	protected int mintSortCol = 0;
	protected boolean mblnSortAsc = true;
	
	protected int mintResult = 0;
	protected String mstrSQL;
	
	public AcctTableData() {
		mdFrm = new SimpleDateFormat("MM/dd/yyyy");
		moVector = new Vector();
	}
	public AcctTableData(String tstrSQL) {
		if (tstrSQL.length()>0) {
			mstrSQL = tstrSQL;
		}
		mdFrm = new SimpleDateFormat("MM/dd/yyyy");
		moVector = new Vector();
	}
		
	public int getRowCount() {
		//System.out.println("\n getRowCount fired " + (moVector==null ? 0 : moVector.size()));
		return moVector==null ? 0 : moVector.size();
	}
	
	public int getColumnCount() {
		//System.out.println("\n getColumnCount fired");
		return mintColumnsCount;
	}

	public String getColumnName(int tintColumn) {
		String str = mobjColumns[tintColumn].mstrTitle;
		if (tintColumn==mintSortCol)
			str += mblnSortAsc ? " (asc)" : " (desc)";
		//System.out.println("\n getColumnName fired");
		return str;
	}

	public boolean isCellEditable(int tintRow, int tintCol) {
		return false;
	}

	public Object getValueAt(int tintRow, int tintCol) {
		if (tintRow < 0 || tintRow>=getRowCount())
			return "";

		AcctData row = (AcctData)moVector.elementAt(tintRow);

		switch (tintCol) {
			case 0: return row.mstrCode;
			case 1: return row.mstrDesc;
		}
		//System.out.println("\n getValueAt fired");
		return "";
	}

	public String getTitle() {
		if (mdDate==null)
			return "Stock Quotes";

		return "Stock Quotes at "+mdFrm.format(mdDate);
	}

	public int retrieveData(String tstrSQL) {

		if (tstrSQL.length()<1) {
			return -1;
		}
		
		mstrSQL = tstrSQL;

		//Thread runner = new Thread() {

		//	public void run() {

				try {

					Class.forName("com.mysql.jdbc.Driver").newInstance();			
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/eis?user=root&password=");
					//System.out.println("Connection open");
					Statement st;
					ResultSet rs;
					st=conn.createStatement();
					rs=st.executeQuery(mstrSQL);

					moVector.removeAllElements();

					while (rs.next()) {


						String mstrSLCode= rs.getString(1);
						String mstrSLDesc= rs.getString(2);

						moVector.addElement(new AcctData(mstrSLCode, mstrSLDesc));
					}

					rs.close();
					st.close();
					conn.close();
					mintResult=1;

				} catch (Exception e) {

					e.printStackTrace();
					System.err.println("Load data error: "+e.toString());
					mintResult = -1;

				}

				Collections.sort(moVector, 
					new AcctSorter(mintSortCol, mblnSortAsc));

			//}
		//};

		//runner.start();

		return mintResult;

	}


	public class ColumnListener extends MouseAdapter {
	
		protected JTable mtblTable;
	
		public ColumnListener(JTable ttblTable) {
			mtblTable = ttblTable;
		}
	
		public void mouseClicked(MouseEvent e) {
	
			TableColumnModel colModel = mtblTable.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
	
			if (modelIndex < 0)
				return;
			if (mintSortCol==modelIndex)
				mblnSortAsc = !mblnSortAsc;
			else
				mintSortCol = modelIndex;
	
			for (int i=0; i < mintColumnsCount; i++) { //NEW
	
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(getColumnName(column.getModelIndex()));
			}
	
			mtblTable.getTableHeader().repaint();
			Collections.sort(moVector, new AcctSorter(modelIndex, mblnSortAsc));
			mtblTable.tableChanged(new TableModelEvent(AcctTableData.this));
			mtblTable.repaint();
	
		}
	
	}
	
	public class ColumnMovementListener implements TableColumnModelListener {

		public void columnAdded(TableColumnModelEvent e) {
			mintColumnsCount++;
		}

		public void columnRemoved(TableColumnModelEvent e) {
			mintColumnsCount--;
			if (mintSortCol >= e.getFromIndex())
				mintSortCol = 0;
		}

		public void columnMarginChanged(ChangeEvent e) {}
		public void columnMoved(TableColumnModelEvent e) {}
		public void columnSelectionChanged(ListSelectionEvent e) {}

	}
}
