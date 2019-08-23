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

public class Show_schedules extends JFrame {
	Dimension screen 		= Toolkit.getDefaultToolkit().getScreenSize();
    JFrame JFParentFrame;
	private static JTable jTable;
	private JScrollPane jScrollPane;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JButton Print;
	private JButton jButton2;
	//private JButton jButton3;
	private JButton jButton4;
	
	private JButton AddNew,Update,Remove,Search,Clear,Exit;
	private static int rowCnt = 0;
	private static int selectedRow;
    private static JTextArea txtInfo=new JTextArea( 15, 40 );
	private Connection dbconn;
	private static String info;
	
	public Show_schedules () {
		super("RiftValley Shuttle Schedules");
		jTable = new JTable(new AbstractTable());
		
		javax.swing.table.TableColumn column = null;
		for(int i = 0; i < 6; i++) {
			column = jTable.getColumnModel().getColumn(i);
		}
		jScrollPane = new JScrollPane(jTable);
		
		jPanel1 = new JPanel(new BorderLayout());
		jPanel1.add(jScrollPane, BorderLayout.CENTER);
		
		Print=new JButton("Print Record");
	
		jButton4 = new JButton("Close");
		
		jPanel2 = new javax.swing.JPanel(new java.awt.FlowLayout());
		
		
		
	//	jPanel2.add(jButton2);
		
		jPanel2.add(Print);
		jPanel2.add(jButton4); 
		setLocation((screen.width - 500)/2,((screen.height-350)/2));     
			try

         {
	
                Statement s = Connect.getConnection().createStatement();
      }

     
      catch ( Exception excp )

      {
            excp.printStackTrace();
            info=info+excp.toString();
      
		
	}    
		
		try
				{
					Statement statement =Connect.getConnection().createStatement();
					{
							String temp = ("SELECT * FROM Schedules Order by Date_Scheduled");
					   int  Numrow = 0;
					   ResultSet result = statement.executeQuery(temp);
					   while (result.next()) {
                         jTable.setValueAt(result.getString(1),Numrow,0);
                         jTable.setValueAt(result.getString(2),Numrow,1);
                         jTable.setValueAt(result.getString(3),Numrow,2);
                         jTable.setValueAt(result.getString(4),Numrow,3);
                         jTable.setValueAt(result.getString(5),Numrow,4);
                         jTable.setValueAt(result.getString(6),Numrow,5);
                         jTable.setValueAt(result.getString(7),Numrow,6);
                         jTable.setValueAt(result.getDate(8),Numrow,7);
                         
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
		jPanel1.setPreferredSize(new java.awt.Dimension(750, 300));
        jPanel1.setBackground(new java.awt.Color(200, 200, 200));
        jPanel1.setBounds(2,200,770,2);
		setSize(750,400);
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
		private String[] columnNames = { "BusNo", "RegNo", "RouteNo", "RouteName" ,
		"DriverNumber","DriverName","TripNo","Date_Scheduled"};
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
		new LoginScreen().setVisible(true);
	}
}