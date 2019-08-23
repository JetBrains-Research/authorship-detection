import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class DeleteMember extends JInternalFrame implements ActionListener, FocusListener {

	private JPanel pMember = new JPanel ();
	private JLabel lbMemberId, lbMemberName, lbMemberAddress, lbDate;
	private JTextField txtMemberId, txtMemberName, txtMemberAddress, txtDate;
	private JButton btnDel, btnCancel;

	private Statement st;		//Statement for Getting the Required Table.
	private ResultSet rs;		//For Getting the Records From Table.
	private long id = 0;		//To Hold the MemberId.

	//Constructor of Class.

	public DeleteMember (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Delete Member", false, true, false, true);
		setSize (350, 222);

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
		lbDate = new JLabel ("Member Since:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 105, 100, 20);

		//Setting the Form's TextFields.

		txtMemberId = new JTextField ();
		txtMemberId .setHorizontalAlignment (JTextField.RIGHT);
		txtMemberId.addFocusListener (this);
		txtMemberId .setBounds (125, 15, 200, 25);
		txtMemberName = new JTextField ();
		txtMemberName.setEnabled (false);
		txtMemberName.setBounds (125, 45, 200, 25);
		txtMemberAddress = new JTextField ();
		txtMemberAddress.setEnabled (false);
		txtMemberAddress.setBounds (125, 75, 200, 25);
		txtDate = new JTextField ();
		txtDate.setEnabled (false);
		txtDate.setBounds (125, 105, 200, 25);

		//Setting the Form's Buttons.

		btnDel = new JButton ("Delete Member");
		btnDel.setBounds (30, 145, 125, 25);
		btnDel.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (185, 145, 125, 25);
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
		pMember.add (lbDate);
		pMember.add (txtMemberId);
		pMember.add (txtMemberName);
		pMember.add (txtMemberAddress);
		pMember.add (txtDate);
		pMember.add (btnDel);
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

		if (obj == btnDel) {		//If Delete Button Pressed.

			if (txtMemberId.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Member's Id not Provided.");
				txtMemberId.requestFocus ();
			}
			else {
				//Show a Confirmation Dialog to Delete the Record.
			    	int reply = JOptionPane.showConfirmDialog (this, 
				"Are you really want to Delete\nthe " + txtMemberName.getText () + " Record?",
				"LibrarySystem - Delete Member", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

				//Check the User Selection.
				if (reply == JOptionPane.YES_OPTION) {			//If User's Choice Yes then.

					try {	//DELETE Query to Delete the Record from Table.
						String q = "DELETE FROM Members WHERE MemberId = " + id + "";
						txtClear ();				//Clearing the TextFields.
						JOptionPane.showMessageDialog (this, "Record has been Deleted.");
						ResultSet rs = st.executeQuery (q);	//Executing the Query.
					}
					catch (SQLException sqlex) { }

				}
				//If User's Choice No then Do Nothing Return to Program.
				else if (reply == JOptionPane.NO_OPTION) { }
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
				if (memberNo == id) {			//If Record Found then Display Records.
					found = true;
					txtMemberId.setText ("" + id);
					txtMemberName.setText ("" + rs.getString ("MemberName"));
					txtMemberAddress.setText ("" + rs.getString ("MemberAddress"));
					txtDate.setText ("" + rs.getString ("EntryDate"));
				}
				else {
					found = false;
				}
			}
			catch (SQLException sqlex) {
				if (found == false) {
					txtClear ();		//Clearing the TextFields.
					JOptionPane.showMessageDialog (this, "Record not Found.");
				}
			}

		}

	}

	//Function Use to Clear All the TextFields of Form.

	private void txtClear () {

		txtMemberId.setText ("");
		txtMemberName.setText ("");
		txtMemberAddress.setText ("");
		txtDate.setText ("");
		txtMemberId.requestFocus ();

	}

}	