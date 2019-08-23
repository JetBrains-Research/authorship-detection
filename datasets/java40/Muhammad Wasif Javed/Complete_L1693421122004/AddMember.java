import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class AddMember extends JInternalFrame implements ActionListener, FocusListener {

	private JPanel pMember = new JPanel ();
	private JLabel lbMemberId, lbMemberName, lbMemberAddress, lbEntryDate;
	private JTextField txtMemberId, txtMemberName, txtMemberAddress;
	private JComboBox cboMonth, cboDate, cboYear;
	private JButton btnOk, btnCancel;

	private Statement st;			//Statement for Getting the Required Table.
	private long id = 0;			//To Hold the BookId.

	//Constructor of Class.

	public AddMember (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Add New Member", false, true, false, true);
		setSize (355, 222);

		//Setting the Form's Labels.

		lbMemberId = new JLabel ("Member Id:");
		lbMemberId.setForeground (Color.black);
		lbMemberId.setBounds (15, 15, 100, 20);
		lbMemberName = new JLabel ("Member Name:");
		lbMemberName.setForeground (Color.black);
		lbMemberName.setBounds (15, 45, 100, 20);
		lbMemberAddress = new JLabel ("Member Address:");
		lbMemberAddress.setForeground (Color.black);
		lbMemberAddress.setBounds (15, 75, 110, 20);
		lbEntryDate = new JLabel ("Entry Date:");
		lbEntryDate.setForeground (Color.black);
		lbEntryDate.setBounds (15, 105, 100, 20);

		//Setting the Form's TextFields.

		txtMemberId = new JTextField ();
		txtMemberId.setHorizontalAlignment (JTextField.RIGHT);
		txtMemberId.addFocusListener (this);
		txtMemberId.setBounds (125, 15, 205, 25);
		txtMemberName = new JTextField ();
		txtMemberName.setBounds (125, 45, 205, 25);
		txtMemberAddress = new JTextField ();
		txtMemberAddress.setBounds (125, 75, 205, 25);

		//Creating EntryDate Option & Setting the Form's ComboBox..

		String Months[] = {"Janruary", "February", "March", "April", "May", "June", "July", 
					"August", "September", "October", "November", "December"};
		cboMonth = new JComboBox (Months);
		cboDate = new JComboBox ();
		for (int i = 1; i <= 31; i++) {
			String days = "" + i;
			cboDate.addItem (days);
		}
		cboYear = new JComboBox ();
		for (int i = 2000; i <= 2010; i++) {
			String years = "" + i;
			cboYear.addItem (years);
		}
		cboMonth.setBounds (125, 105, 92, 25);
		cboDate.setBounds (222, 105, 45, 25);
		cboYear.setBounds (272, 105, 58, 25);

		//Setting the Form's Buttons.

		btnOk = new JButton ("OK");
		btnOk.setBounds (30, 145, 125, 25);
		btnOk.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (190, 145, 125, 25);
		btnCancel.addActionListener (this);

		//Registering the KeyListener to Restrict user to type only Numeric in Numeric Boxes.

		txtMemberId.addKeyListener (new KeyAdapter () {
			public void keyTyped (KeyEvent ke) {
				char c = ke.getKeyChar ();
				if (! ((Character.isDigit (c)) || (c == KeyEvent.VK_BACK_SPACE))) {
					getToolkit().beep ();
					ke.consume ();
				}
			}
		}
		);

		//Adding All the Controls in Panel.

		pMember.setLayout (null);
		pMember.add (lbMemberId);
		pMember.add (lbMemberName);
		pMember.add (lbMemberAddress);
		pMember.add (lbEntryDate);
		pMember.add (txtMemberId);
		pMember.add (txtMemberName);
		pMember.add (txtMemberAddress);
		pMember.add (cboMonth);
		pMember.add (cboDate);
		pMember.add (cboYear);
		pMember.add (btnOk);
		pMember.add (btnCancel);

		//Adding Panel to Form.

		getContentPane().add (pMember, BorderLayout.CENTER);

		try {
			st = con.createStatement ();	//Creating Statement Object.
		}
		catch (SQLException sqlex) {			//If Problem then Show the User a Message.
 			JOptionPane.showMessageDialog (null, "A Problem Occurs While Loading the Form.");
 			dispose ();				//Closing the Form.
	 	}

		setVisible (true);

	}

	//Action Performed By the Form's Button.

	public void actionPerformed (ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == btnOk) {		//If OK Button Pressed.

			//Validating to Check All Required Information Provided or Not.

			if (txtMemberId.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Member's Id not Provided.");
				txtMemberId.requestFocus ();
			}
			else if (txtMemberName.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Member's Name not Provided.");
				txtMemberName.requestFocus ();
			}
			else if (txtMemberAddress.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Member's Address not Provided.");
				txtMemberAddress.requestFocus ();
			}
			else {	//If All Information Provided then.
				String entryDate = cboMonth.getSelectedItem () + ", " + cboDate.getSelectedItem () + ", " + 
					cboYear.getSelectedItem ();

				try {	//INSERT Query to Add Member Record in Table.
					String q = "INSERT INTO Members (MemberId, MemberName, MemberAddress, EntryDate)" + 
						" VALUES (" + id + ", '" + txtMemberName.getText() + "', '" + 
						txtMemberAddress.getText() + "', '" + entryDate + "')";

					int result = st.executeUpdate (q);	//Running Query.
					if (result == 1) {			//If Query Successful.
						JOptionPane.showMessageDialog (this, "Record has been Saved.");
						txtClear ();			//Clearing the TextFields.
					}
					else {					//If Query Failed.
						JOptionPane.showMessageDialog (this, "Problem while Saving the Record.");
					}
				}
				catch (SQLException sqlex) { }
			}

		}		

		if (obj == btnCancel) {		//If Cancel Button Pressed Unload the From.

			setVisible (false);
			dispose();

		}

	}

	//OverRidding the FocusListener Class Function.

	public void focusGained (FocusEvent fe) { }

	public void focusLost (FocusEvent fe) {

		if (txtMemberId.getText().equals ("")) {	//If TextField is Empty.
		}
		else {
			id = Integer.parseInt (txtMemberId.getText ());	//Converting String to Numeric.
			long memberNo;					//Use for Comparing the Member's Id.
			boolean found = false;				//To Confirm the Member's Id Existance.

			try {	//SELECT Query to Retrieved the Record.
				String q = "SELECT * FROM Members WHERE MemberId = " + id + "";
				ResultSet rs = st.executeQuery (q);	//Executing the Query.
				rs.next ();				//Moving towards the Record.
				memberNo = rs.getLong ("MemberId");	//Storing the Record.
				if (memberNo == id) {			//If Record Found then Display Message.
					found = true;
					txtClear ();			//Clearing the TextFields.
					JOptionPane.showMessageDialog (this, id + " is already assigned.");
				}
				else {
					found = false;
				}
			}
			catch (SQLException sqlex) { }
		}

	}

	//Function Use to Clear All the TextFields of Form.

	private void txtClear () {

		txtMemberId.setText ("");
		txtMemberName.setText ("");
		txtMemberAddress.setText ("");
		txtMemberId.requestFocus ();

	}

}	