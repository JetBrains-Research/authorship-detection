import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ReturnBook extends JInternalFrame implements ActionListener, FocusListener {

	private JPanel pBook = new JPanel ();
	private JLabel lbBookId, lbBookName, lbBookAuthor, lbIssuedTo, lbBookCategory;
	private JTextField txtBookId, txtBookName, txtBookAuthor, txtIssuedTo, txtBookCategory;
	private JButton btnReturn, btnCancel;

	private Statement st;		//Statement for Getting the Required Table.
	private ResultSet rs;		//For Getting the Records From Table.
	private long id = 0;		//To Hold the BookId.

	//Constructor of Class.

	public ReturnBook (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Return Book", false, true, false, true);
		setSize (325, 250);

		//Setting the Form's Labels.

		lbBookId = new JLabel ("Book Id:");
		lbBookId.setForeground (Color.black);
		lbBookId.setBounds (15, 15, 100, 20);
		lbBookName = new JLabel ("Book Name:");
		lbBookName.setForeground (Color.black);
		lbBookName.setBounds (15, 45, 100, 20);
		lbBookAuthor = new JLabel ("Book Author:");
		lbBookAuthor.setForeground (Color.black);
		lbBookAuthor.setBounds (15, 75, 100, 20);
		lbBookCategory = new JLabel ("Book Category:");
		lbBookCategory.setForeground (Color.black);
		lbBookCategory.setBounds (15, 105, 100, 20);
		lbIssuedTo = new JLabel ("Book Issued to:");
		lbIssuedTo.setForeground (Color.black);
		lbIssuedTo.setBounds (15, 135, 115, 20);

		//Setting the Form's TextFields.

		txtBookId = new JTextField ();
		txtBookId.setHorizontalAlignment (JTextField.RIGHT);
		txtBookId.addFocusListener (this);
		txtBookId.setBounds (120, 15, 175, 25);
		txtBookName = new JTextField ();
		txtBookName.setEnabled (false);
		txtBookName.setBounds (120, 45, 175, 25);
		txtBookAuthor = new JTextField ();
		txtBookAuthor.setEnabled (false);
		txtBookAuthor.setBounds (120, 75, 175, 25);
		txtBookCategory = new JTextField ();
		txtBookCategory.setEnabled (false);
		txtBookCategory.setBounds (120, 105, 175, 25);
		txtIssuedTo = new JTextField ();
		txtIssuedTo.setEnabled (false);
		txtIssuedTo.setBounds (120, 135, 175, 25);

		//Setting the Form's Buttons.

		btnReturn = new JButton ("Return Book");
		btnReturn.setBounds (25, 175, 125, 25);
		btnReturn.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (165, 175, 125, 25);
		btnCancel.addActionListener (this);

		//Registering the KeyListener to Restrict user to type only Numeric in Numeric Boxes.

		txtBookId.addKeyListener (new KeyAdapter () {
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

		pBook.setLayout (null);
		pBook.add (lbBookId);
		pBook.add (lbBookName);
		pBook.add (lbBookAuthor);
		pBook.add (lbIssuedTo);
		pBook.add (lbBookCategory);
		pBook.add (txtBookId);
		pBook.add (txtBookName);
		pBook.add (txtBookAuthor);
		pBook.add (txtIssuedTo);
		pBook.add (txtBookCategory);
		pBook.add (btnReturn);
		pBook.add (btnCancel);

		//Adding Panel to Form.

		getContentPane().add (pBook, BorderLayout.CENTER);

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

		if (obj == btnReturn) {		//If Return Button Pressed.

			if (txtBookId.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Book's Id not Provided.");
				txtBookId.requestFocus ();
			}
			else {
				try {	//DELETE Query to Delete the Record from Table.
					String q = "DELETE FROM IssuedBooks WHERE BookId = " + id + "";
					txtClear ();				//Clearing the TextFields.
					ResultSet rs = st.executeQuery (q);	//Executing the Query.
				}
				catch (SQLException sqlex) {
					JOptionPane.showMessageDialog (this, "Book has been Returned.");
				}
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

		if (txtBookId.getText().equals ("")) {	//If TextField is Empty.
		}
		else {
			id = Integer.parseInt (txtBookId.getText ());	//Converting String to Numeric.
			long bookNo;					//Use for Comparing the Book's Id.
			boolean found = false;				//To Confirm the Book's Id Existance.

			try {	//SELECT Query to Retrieved the Record.
				String q = "SELECT IssuedBooks.BookId, IssuedBooks.BookName, IssuedBooks.BookAuthor, " + 
					"IssuedBooks.Category, Members.MemberName FROM IssuedBooks " + 
					"INNER JOIN Members ON IssuedBooks.MemberId = Members.MemberId " + 
					"WHERE BookId = " + id + "";
				ResultSet rs = st.executeQuery (q);	//Executing the Query.
				rs.next ();				//Moving towards the Record.
				bookNo = rs.getLong ("BookId");		//Storing the Record.
				if (bookNo == id) {			//If Record Found then Display Records.
					found = true;
					txtBookId.setText ("" + id);
					txtBookName.setText ("" + rs.getString ("BookName"));
					txtBookAuthor.setText ("" + rs.getString ("BookAuthor"));
					txtBookCategory.setText ("" + rs.getString ("Category"));
					txtIssuedTo.setText ("" + rs.getString ("MemberName"));
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

		txtBookId.setText ("");
		txtBookName.setText ("");
		txtBookAuthor.setText ("");
		txtBookCategory.setText ("");
		txtIssuedTo.setText ("");
		txtBookId.requestFocus ();

	}

}	