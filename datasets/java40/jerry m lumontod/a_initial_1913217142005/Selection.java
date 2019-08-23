
package com.jml.eisapp.acctg.utils.src.gui;

import com.jml.eisapp.acctg.base.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class Selection implements ListSelectionListener {

	JLabel debugger;

	ListSelectionModel model;
	TableModel tm;

	public Selection(JLabel target, ListSelectionModel lsm,TableModel ttm) {		
		debugger = target;
		model = lsm;
		tm=ttm;
		
	}
	public void valueChanged(ListSelectionEvent lse) {

		if (!lse.getValueIsAdjusting()) {

			// skip all the intermediate events . . .

			StringBuffer buf = new StringBuffer();
			int[] selection = getSelectedIndices(model.getMinSelectionIndex(),
			                                     model.getMaxSelectionIndex());
			if (selection.length == 0) {

				buf.append("none");

			} else {

				for (int i = 0; i < selection.length -1; i++) {
					buf.append(selection[i]);
					buf.append(", ");
				}

				buf.append(selection[selection.length - 1]);

			}

			debugger.setText(buf.toString());

			//just insert the value here, it will override everything... =)
			debugger.setText(tm.getValueAt(model.getMinSelectionIndex(),1).toString());
			//end, just insert the value here, it will override everything... =)

		}

	}

	// This method returns an array of selected indices. It's guaranteed to
	// return a nonnull value.

	protected int[] getSelectedIndices(int start, int stop) {

		if ((start == -1) || (stop == -1)) {
			// no selection, so return an empty array
			return new int[0];
		}

		int guesses[] = new int[stop - start + 1];
		int index = 0;
		// manually walk through these . . .

		for (int i = start; i <= stop; i++) {

			if (model.isSelectedIndex(i)) {
				guesses[index++] = i;
			}

		}

		// ok, pare down the guess array to the real thing
		int realthing[] = new int[index];
		System.arraycopy(guesses, 0, realthing, 0, index);
		return realthing;

	}
	
}