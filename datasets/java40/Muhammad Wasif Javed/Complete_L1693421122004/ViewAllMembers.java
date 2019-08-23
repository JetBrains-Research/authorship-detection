import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import javax.swing.table.DefaultTableModel;

public class ViewAllMembers extends JInternalFrame {

	private JPanel pMember = new JPanel ();

	private JScrollPane scroller;
	private JTable table;	//Table for Displaying Records.
	private Statement st;	//Statement for Getting the Required Table.

	private int rec = 0;
	private int total = 0;

	private String rowRec[][];	//String Type Array use to Load Records into File.

	//Constructor of Class.

	public ViewAllMembers (Connection con) {

		//super (Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("View All Members", false, true, false, true);
		setSize (510, 280);

		//Setting Panel's Layout.

		pMember.setLayout (null);

		String records[][] = new String [500][4];		//String Type Array use to Load Records From Table.

		try {	//Opening the Required Table.
			String q = "SELECT * FROM Members ORDER BY MemberId";	//Query use to Retrieve the Records.

			st = con.createStatement ();			//Creating Statement Object.
			ResultSet rs = st.executeQuery (q);		//Running Query.

			while (rs.next ()) {
				records[rec][0] = "" + rs.getLong ("MemberId");
				records[rec][1] = rs.getString ("MemberName");
				records[rec][2] = rs.getString ("MemberAddress");
				records[rec][3] = rs.getString ("EntryDate");
				rec++;
			}

			total = rec;

			if (total == 0) {
				JOptionPane.showMessageDialog (null, "Table is Empty");
				return;
			}
			else {
				rowRec = new String [total][4];
				for (int i = 0; i < total; i++) {
					rowRec[i][0] = 	records[i][0];
					rowRec[i][1] = 	records[i][1];
					rowRec[i][2] = 	records[i][2];
					rowRec[i][3] = 	records[i][3];
				}
			}
		}
		catch (Exception sqlEx) { }

		table = makeTable ();			//Creating Table.
		scroller = new JScrollPane (table);	//Adding Table to ScrollPane.
		scroller.setBounds (20, 20, 460, 200);	//Aligning ScrollPane.

		//Adding All the Controls in Panel.

		pMember.add (scroller);

		//Adding Panel to the Form.

		getContentPane().add (pMember);

		setVisible (true);

	}

	//Function to Create the Table and Add Data to Show.

	private JTable makeTable () {

		//String Array For Table Columns.
		String colsName[] = {"Member ID", "Member Name", "Member Address", "Member Since"};

		table = new JTable (rowRec, colsName) {
			public boolean isCellEditable (int iRows, int iCols) {
				return false;	//Disable All Columns of Table.
			}
		};

		table.setRowHeight (20);	//Set Rows Height.

		return table;

	}

}