import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class SearchBook extends JInternalFrame implements ActionListener {

	private JPanel pBook = new JPanel ();
	private JLabel lbBookId, lbBookName, lbBookAuthor, lbBookPrice, lbBookCategory;
	private JTextField txtBookId, txtBookName, txtBookAuthor, txtBookPrice, txtBookCategory, txtRec;
	private JButton btnFind, btnCancel;

	private Statement st;			//Statement for Getting the Required Table.
	private long id = 0;			//To Hold the BookId.

	//Constructor of Class.

	public SearchBook (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Search Books", false, true, false, true);
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
		lbBookPrice = new JLabel ("Book Price:");
		lbBookPrice.setForeground (Color.black);
		lbBookPrice.setBounds (15, 105, 100, 20);
		lbBookCategory = new JLabel ("Book Category:");
		lbBookCategory.setForeground (Color.black);
		lbBookCategory.setBounds (15, 135, 100, 20);

		//Setting the Form's TextFields.

		txtBookId = new JTextField ();
		txtBookId.setHorizontalAlignment (JTextField.RIGHT);
		txtBookId.setBounds (120, 15, 175, 25);
		txtBookName = new JTextField ();
		txtBookName.setEnabled (false);
		txtBookName.setBounds (120, 45, 175, 25);
		txtBookAuthor = new JTextField ();
		txtBookAuthor.setEnabled (false);
		txtBookAuthor.setBounds (120, 75, 175, 25);
		txtBookPrice = new JTextField ();
		txtBookPrice.setEnabled (false);
		txtBookPrice.setHorizontalAlignment (JTextField.RIGHT);
		txtBookPrice.setBounds (120, 105, 175, 25);
		txtBookCategory = new JTextField ();
		txtBookCategory.setEnabled (false);
		txtBookCategory.setBounds (120, 135, 175, 25);

		//Setting the Form's Buttons.

		btnFind = new JButton ("Find Book");
		btnFind.setBounds (25, 175, 125, 25);
		btnFind.addActionListener (this);
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
		pBook.add (lbBookPrice);
		pBook.add (lbBookCategory);
		pBook.add (txtBookId);
		pBook.add (txtBookName);
		pBook.add (txtBookAuthor);
		pBook.add (txtBookPrice);
		pBook.add (txtBookCategory);
		pBook.add (btnFind);
		pBook.add (btnCancel);

		//Adding Panel to Form.

		getContentPane().add (pBook, BorderLayout.CENTER);

		try {
			st = con.createStatement ();	//Creating Statement Object.
		}
		catch (SQLException sqlex) {			//If Problem then Show the User a Message.
 			JOptionPane.showMessageDialog (null, "A Problem Occurs While Loading Form.");
 			dispose ();				//Closing the Form.
	 	}

		setVisible (true);

	}

	//Action Performed By the Form's Button.

	public void actionPerformed (ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == btnFind) {		//If Find Button Pressed.

			if (txtBookId.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Book's Id not Provided.");
				txtBookId.requestFocus ();
			}
			else {
				id = Integer.parseInt (txtBookId.getText ());	//Converting String to Numeric.
				long bookNo;					//Use for Comparing the Book's Id.
				boolean found = false;				//To Confirm the Book's Id Existance.

				try {	//SELECT Query to Retrieved the Record.
					String q = "SELECT * FROM Books WHERE BookId = " + id + "";
					ResultSet rs = st.executeQuery (q);	//Executing the Query.
					rs.next ();				//Moving towards the Record.
					bookNo = rs.getLong ("BookId");		//Storing the Record.
					if (bookNo == id) {			//If Record Found then Display Records.
						found = true;
						txtBookId.requestFocus ();
						txtBookId.setText ("" + id);
						txtBookName.setText ("" + rs.getString ("BookName"));
						txtBookAuthor.setText ("" + rs.getString ("BookAuthor"));
						txtBookPrice.setText ("" + rs.getLong ("BookPrice"));
						txtBookCategory.setText ("" + rs.getString ("Category"));
					}
					else {
						found = false;
					}
				}
				catch(SQLException sqlex) {
					if (found == false) {
						txtClear ();		//Clearing the TextFields.
						JOptionPane.showMessageDialog (this, "Record not Found.");
					}
				}
			}

		}		

		if (obj == btnCancel) {		//If Cancel Button Pressed Unload the From.

			setVisible (false);
			dispose();

		}

	}

	//Function Use to Clear All the TextFields of Form.

	private void txtClear () {

		txtBookId.setText ("");
		txtBookName.setText ("");
		txtBookAuthor.setText ("");
		txtBookPrice.setText ("");
		txtBookCategory.setText ("");
		txtBookId.requestFocus ();

	}

}	