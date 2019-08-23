import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;

public class ViewAllBooks extends JInternalFrame implements ItemListener {

	private JPanel pBook = new JPanel ();

	private JLabel lbCategory;
	private JComboBox cboCategory;
	private String category[] = {"Computers", "Science", "History", "General"};	//ComboBox Items.

	private JScrollPane scroller;
	private JTable table;	//Table for Displaying Records.
	private Statement st;	//Statement for Getting the Required Table.

	private int rec = 0;
	private int total = 0;

	private String rowRec[][];	//String Type Array use to Load Records into File.

	//Constructor of Class.

	public ViewAllBooks (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("View All Books", false, true, false, true);
		setSize (510, 300);

		//Setting Panel's Layout.

		pBook.setLayout (null);

		//Setting the Form's Labels.

		lbCategory = new JLabel ("Select Book Category to Show Book Records:");
		lbCategory.setForeground (Color.black);
		lbCategory.setBounds (20, 18, 275, 25);

		//Setting the Form's ComboBox.

		cboCategory = new JComboBox (category);
		cboCategory.addItemListener (this);
		cboCategory.setBounds (300, 18, 170, 25);

		try {
			st = con.createStatement ();	//Creating Statement Object.
		}
		catch (SQLException sqlex) {			//If Problem then Show the User a Message.
 			JOptionPane.showMessageDialog (null, "A Problem Occurs While Loading Form.");
 			dispose ();				//Closing the Form.
	 	}

		getRecords ("" + cboCategory.getSelectedItem ());	//Getting Records.

		table = makeTable ();			//Creating Table.
		scroller = new JScrollPane (table);	//Adding Table to ScrollPane.
		scroller.setBounds (20, 50, 460, 200);	//Aligning ScrollPane.

		//Adding All the Controls in Panel.

		pBook.add (lbCategory);
		pBook.add (cboCategory);
		pBook.add (scroller);

		//Adding Panel to the Form.

		getContentPane().add (pBook);

		setVisible (true);

	}

	//Function Perform By the ComboBox of Form.

	public void itemStateChanged (ItemEvent e) {

		rec = 0;
		total = 0;
		getRecords ("" + cboCategory.getSelectedItem ());	//Getting Records.
		scroller.getViewport().remove (table);
		scroller.getViewport().add (makeTable ());
		this.repaint();

	}

	//Function use to Getting Records.

	private void getRecords (String cat) {

		String records[][] = new String [500][5];		//String Type Array use to Load Records From Table.

		try {	//SELECT Query to Retrieve Records From Table.
		 	String q = "SELECT * FROM Books WHERE Category = '" + cat + "' ORDER By BookId";

			ResultSet rs = st.executeQuery (q);		//Running Query.

			while (rs.next ()) {
				records[rec][0] = "" + rs.getLong ("BookId");
				records[rec][1] = rs.getString ("BookName");
				records[rec][2] = rs.getString ("BookAuthor");
				records[rec][3] = "" + rs.getLong ("BookPrice");
				records[rec][4] = rs.getString ("Category");
				rec++;
			}

			total = rec;
			rowRec = new String [total][5];

			if (total == 0) {
			}
			else {
				for (int i = 0; i < total; i++) {
					rowRec[i][0] = 	records[i][0];
					rowRec[i][1] = 	records[i][1];
					rowRec[i][2] = 	records[i][2];
					rowRec[i][3] = 	records[i][3];
					rowRec[i][4] = 	records[i][4];
				}
			}
		}
		catch (Exception sqlEx) { }

	}

	//Function to Create the Table and Add Data to Show.

	private JTable makeTable () {

		//String Array For Table Columns.
		String colsName[] = {"Book ID", "Book Name", "Book Author", "Book Price", "Category"};

		table = new JTable (rowRec, colsName) {
			public boolean isCellEditable (int iRows, int iCols) {
				return false;	//Disable All Columns of Table.
			}
		};

		table.setRowHeight (20);	//Set Rows Height.

		return table;

	}

}