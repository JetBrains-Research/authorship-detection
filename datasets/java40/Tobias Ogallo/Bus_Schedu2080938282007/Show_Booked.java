import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.applet.Applet;
import javax.swing.*;
import java.lang.StringBuffer;
import java.io.IOException;
import java.io.*;
import java.sql.*;  

public class Show_Booked extends JFrame {
	Dimension screen 		= Toolkit.getDefaultToolkit().getScreenSize();
    JFrame JFParentFrame;
	private static JTable jTable;
	private JScrollPane jScrollPane;
	private JPanel jPanel1;
	private JPanel jPanel2;
	//private JButton Print;
	private JButton jButton2;
	private JButton jButton4;
	//private JButton AddNew,Update,Exit;
	private static int rowCnt = 0;
	private static int selectedRow;
    private static JTextArea txtInfo=new JTextArea( 15, 40 );
	private Connection dbconn;
	private static String info;
	
	public Show_Booked () {
		super("RiftValley Shuttle Schedules");
		jTable = new JTable(new AbstractTable());
		
		javax.swing.table.TableColumn column = null;
		for(int i = 0; i < 5; i++) {
			column = jTable.getColumnModel().getColumn(i);
		}
		jScrollPane = new JScrollPane(jTable);
		jPanel1 = new JPanel(new BorderLayout());
		jPanel1.add(jScrollPane, BorderLayout.CENTER);
		jButton4 = new JButton("Close");
		jPanel2 = new javax.swing.JPanel(new java.awt.FlowLayout());
		jPanel2.add(jButton4); 
	     setLocation((screen.width - 500)/2,((screen.height-350)/2));
			try

         {
	
                Statement s = Connect.getConnection().createStatement();
         }

      
      catch ( Exception excp )

      {
            excp.printStackTrace();
            
      
		
	}    
		
		try
				{
					Statement statement =Connect.getConnection().createStatement();
					{
							String temp = ("SELECT Pass_No,PassName,SeatNo,Bus_RegNo,Date_of_Travel FROM BOOKING Order by SeatNo");
					   int  Numrow = 0;
					   ResultSet result = statement.executeQuery(temp);
					   while (result.next()) {
                         jTable.setValueAt(result.getString(1),Numrow,0);
                         jTable.setValueAt(result.getString(2),Numrow,1);
                         jTable.setValueAt(result.getString(3),Numrow,2);
                         jTable.setValueAt(result.getString(4),Numrow,3);
                         jTable.setValueAt(result.getString(5),Numrow,4);
                         Numrow++;
                       
					   }	
					   	
					
					
				}
				
			}
			catch ( SQLException sqlex ) {
                         txtInfo.append( sqlex.toString() );
             }	

	
		

		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
				
			}
		}); 
         
         
		jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);
		//jPanel1.setPreferredSize(new java.awt.Dimension(750, 300));
        jPanel1.setBackground(new java.awt.Color(200, 200, 200));
        jPanel1.setBounds(2,200,770,2);
		setSize(500,250);
		add(jPanel1);
	}

	

	
	public static int getSelectedRow() {
		jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		javax.swing.ListSelectionModel rowSel = jTable.getSelectionModel();
		rowSel.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;

				javax.swing.ListSelectionModel sel = (ListSelectionModel)e.getSource();
				if (!sel.isSelectionEmpty()) {
					selectedRow = sel.getMinSelectionIndex();
				}
			}
		});

		return selectedRow;
	}

	class AbstractTable extends javax.swing.table.AbstractTableModel {
		private String[] columnNames = { "Pass_No","Passenger_Nane","Seat Number", "Bus_RegNo", "Date_of_Travel"};
		private Object[][] data = new Object[50][50];

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

	public static void main(String args[]) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new Show_Booked().setVisible(true);
	}
}