
package com.jml.eisapp.acctg.utils.src.gui;

import java.awt.event.*;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.Rectangle;
import javax.swing.table.DefaultTableModel;


public class ARS extends MouseAdapter {
	JTable cjt;
	DefaultTableModel cmodel;
	public ARS(JTable tjt,DefaultTableModel tmodel) {
		cjt=tjt;
		cmodel=tmodel;
	}
    public void mouseClicked(MouseEvent evt) {
        JTable table = ((JTableHeader)evt.getSource()).getTable();
        TableColumnModel colModel = cjt.getColumnModel();
		
        // The index of the column whose header was clicked
        int vColIndex = colModel.getColumnIndexAtX(evt.getX());
        int mColIndex = table.convertColumnIndexToModel(vColIndex);
		//System.out.print("\nvColIndex is " + vColIndex + "\nmColIndex is " + mColIndex);
        // Return if not clicked on any column header
        //if (vColIndex == -1 || vColIndex == 0) {
        if (vColIndex == -1) {
            return;
        }

        // Determine if mouse was clicked between column heads
        Rectangle headerRect = table.getTableHeader().getHeaderRect(vColIndex);
        if (!headerRect.contains(evt.getX(), evt.getY())) {
			return;
        }
        
        ARowSort ARowSortInst = new ARowSort();
        ARowSortInst.sortAllRowsBy(cmodel,mColIndex,true);
        
    }
}