

package com.jml.eisapp.acctg.utils.src.gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.Collections;

public class ARowSort {
	
	public ARowSort() {
		super();
	}

	/*
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);
    private int cint = 2;
    private Object[] obj = new Object[cint];
    private String cstr;
    
    cstr="row 0 column 0";
    obj[0]=cstr;
    cstr="row 1 column 0";
    obj[1]=cstr;
    model.addRow(obj);
    obj[0]="row 2 column 0";
    obj[1]="row 3 column 0";
    model.addRow(obj);
    
    
    // Add data here...
    
    // Disable autoCreateColumnsFromModel otherwise all the column customizations
    // and adjustments will be lost when the model data is sorted
    table.setAutoCreateColumnsFromModel(false);
    
    
    // Sort all the rows in descending order based on the
    // values in the second column of the model
    sortAllRowsBy(model, 1, false); */
    
    // Regardless of sort order (ascending or descending), null values always appear last.
    // colIndex specifies a column in model.
    
    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        //System.out.print("\nSorting fired");
        model.fireTableStructureChanged();
    }
    
    // This comparator is used to sort vectors of data
    public class ColumnSorter implements Comparator {
        int colIndex;
        boolean ascending;
        ColumnSorter(int colIndex, boolean ascending) {
            this.colIndex = colIndex;
            this.ascending = ascending;
        }
        
        
        public int compare(Object a, Object b) {
        	
            Vector v1 = (Vector)a;
            Vector v2 = (Vector)b;
            Object o1 = v1.get(colIndex);
            Object o2 = v2.get(colIndex);
    
            // Treat empty strains like nulls
            if (o1 instanceof String && ((String)o1).length() == 0) {
                o1 = null;
            }
            if (o2 instanceof String && ((String)o2).length() == 0) {
                o2 = null;
            }

            // Sort nulls so they appear last, regardless
            // of sort order
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            } else if (o1 instanceof Comparable) {
                if (ascending) {
                    return ((Comparable)o1).compareTo(o2);
                } else {
                    return ((Comparable)o2).compareTo(o1);
                }
            } else {
                if (ascending) {
                    return o1.toString().compareTo(o2.toString());
                } else {
                    return o2.toString().compareTo(o1.toString());
                }
            }
        }
    }
	
}