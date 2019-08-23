
package com.jml.eisapp.acctg.base;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class SelDataListCellRenderer extends JLabel implements ListCellRenderer {
	
	public static final int OFFSET = 16;
	protected Color mclrTextSelectionColor = Color.white;
	protected Color mclrTextNonSelectionColor = Color.black;
	protected Color mclrTextNonselectableColor = Color.gray;
	protected Color mclrBackSelectionColor = new Color(0, 0, 128);
	protected Color mclrBackNonSelectionColor = Color.white;
	protected Color mclrBorderSelectionColor = Color.yellow;

	protected Color  mclrTextColor;
	protected Color  mclrBackColor;

	protected boolean mblnHasFocus;
	protected Border[] mborBorders;

	public SelDataListCellRenderer() {
		super();
		mclrTextColor = mclrTextNonSelectionColor;
		mclrBackColor = mclrBackNonSelectionColor;
		mborBorders = new Border[20];
		for (int k=0; k<mborBorders.length; k++)
			mborBorders[k] = new EmptyBorder(0, OFFSET*k, 0, 0);
		setOpaque(false);
	}

	public Component getListCellRendererComponent(JList list,Object obj, int row, boolean sel, boolean hasFocus) {
		if (obj == null)
			return this;
		setText(obj.toString());
		return this;
	}
    
	public void paint(Graphics g) {
		Icon icon = getIcon();
		Border b = getBorder();

		g.setColor(mclrBackNonSelectionColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(mclrBackColor);
		int offset = 0;
		
		if(icon != null && getText() != null) {
			Insets ins = getInsets();
			offset = ins.left + icon.getIconWidth() + getIconTextGap();
		}
		g.fillRect(offset, 0, getWidth() - 1 - offset,
			getHeight() - 1);
		
		if (mblnHasFocus) {
			g.setColor(mclrBorderSelectionColor);
			g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
		}

		setForeground(mclrTextColor);
		setBackground(mclrBackColor);
		super.paint(g);
    }
}