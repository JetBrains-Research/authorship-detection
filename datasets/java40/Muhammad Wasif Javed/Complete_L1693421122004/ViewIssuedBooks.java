import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import javax.swing.table.DefaultTableModel;

public class ViewIssuedBooks extends JInternalFrame {

	private JPanel pBook = new JPanel ();

	private JScrollPane scroller;
	private JTable table;	//Table for Displaying Records.
	private Statement st;	//Statement for Getting the Required Table.

	private int rec = 0;
	private int total = 0;

	private String rowRec[][];	//String Type Array use to Load Records into File.

	//Constructor of Class.

	public ViewIssuedBooks (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("View All Issued Books", false, true, false, true);
		setSize (510, 285);

		//Setting Panel's Layout.

		pBook.setLayout (null);

		String records[][] = new String [500][5];		//String Type Array use to Load Records From Table.

		try {	//Opening the Required Table.
			//Query use to Retrieve the Records.
			String q = "SELECT IssuedBooks.BookId, IssuedBooks.BookName, IssuedBooks.BookAuthor, " + 
				"IssuedBooks.Category, Members.MemberName FROM IssuedBooks " + 
				"INNER JOIN Members ON IssuedBooks.MemberId = Members.MemberId ORDER BY BookId";

			st = con.createStatement ();			//Creating Statement Object.
			ResultSet rs = st.executeQuery (q);		//Running Query.

			while (rs.next ()) {
				records[rec][0] = "" + rs.getLong ("BookId");
				records[rec][1] = rs.getString ("BookName");
				records[rec][2] = rs.getString ("BookAuthor");
				records[rec][3] = rs.getString ("Category");
				records[rec][4] = rs.getString ("MemberName");
				rec++;
			}

			total = rec;

			if (total == 0) {
				JOptionPane.showMessageDialog (null, "Table is Empty");
				return;
			}
			else {
				rowRec = new String [total][5];
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

		table = makeTable ();			//Creating Table.
		scroller = new JScrollPane (table);	//Adding Table to ScrollPane.
		scroller.setBounds (20, 20, 460, 200);	//Aligning ScrollPane.

		//Adding All the Controls in Panel.

		pBook.add (scroller);

		//Adding Panel to the Form.

		getContentPane().add (pBook);

		setVisible (true);

	}

	//Function to Create the Table and Add Data to Show.

	private JTable makeTable () {

		//String Array For Table Columns.
		String colsName[] = {"Book ID", "Book Name", "Book Author", "Category", "Issue To"};

		table = new JTable (rowRec, colsName) {
			public boolean isCellEditable (int iRows, int iCols) {
				return false;	//Disable All Columns of Table.
			}
		};

		table.setRowHeight (20);	//Set Rows Height.

		return table;

	}

}