import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.text.*;
import java.sql.*;

public class book_loan_master extends JPanel implements book_loan_tab_Interface, book_loan_master_Interface{
public Connection connection;


public JTextField bookid = new JTextField(5);
public int i = 0;
public int maincnt;

    public book_loan_master() { 

    
    	String url = "jdbc:odbc:lib";
 		
 		
 		try {
 			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 			connection = DriverManager.getConnection(url);
 		}
 		catch(ClassNotFoundException cnfex)  {
 			System.err.println("Failed to load driver");
 			cnfex.printStackTrace();
 			System.exit(1);
 		}
 		catch(SQLException sqlex){
 			System.err.println("unable to connect");
 			sqlex.printStackTrace();
 		}
 		

    
    patron_name_text.setEditable(false);
    
    patron_group_type_text.setEditable(false);
    
    patron_status_text.setEditable(false);
    
    patron_loan_limit_text.setEditable(false);
    //validation for loan
    patron_loan_limit_text.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();      
      		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
        	getToolkit().beep();
        	e.consume();
      		}
    		}
  		});
   
    patron_expiry_date_text.setEditable(false);
    
    testing.setText("a");
    
    //testing.setVisible(true);
    
    tab.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
        gbc.gridy = 0;
        gbc.gridx = 0;
        tab.add(patron_no, gbc);
        //dump.setVisible(true);
        testing.setVisible(false);
  		patron_no_text.addFocusListener(new MyActionListener()
  		{
			public void focusLost(FocusEvent e)
			{
				if (!testing.getText().equals(""))
				{
				testing.setText("");
				try
				{
					DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT);
					String bname[] = new String[5]; // ={""};
					java.sql.Date rdate[] = new java.sql.Date[5];
					java.sql.Date ddate[] = new java.sql.Date[5];
					
					Statement statement = connection.createStatement();
					String query1 = "SELECT * FROM patronmaster " + 
 								"WHERE id = '" + 
 								(String)patron_no_text.getText() +"'";
					
					ResultSet rs1 = statement.executeQuery(query1);
					try
					{
						rs1.next();
						String query3;
						
						patron_name_text.setText(rs1.getString("name"));
						patron_status_text.setText(rs1.getString("status"));
						patron_loan_limit_text.setText("5");
						patron_group_type_text.setText(rs1.getString("group_type"));
						patron_expiry_date_text.setText(rs1.getString("expiry_date"));
						
						String query2 = "SELECT * From loan WHERE member_id = '" + patron_no_text.getText() + "'";
							
						ResultSet rs2 = statement.executeQuery(query2);
						ResultSet rs3;
						int cnt = 0;
							
						while (rs2.next())
						{
							cnt++;
						}
						int cnt1 = cnt;
						maincnt = cnt;
						dump.setText(String.valueOf(cnt));
						String query22 = "SELECT * From loan WHERE member_id = '" + patron_no_text.getText() + "'";
						ResultSet rs22 = statement.executeQuery(query22);
						try
						{
							while (rs22.next()  && cnt1> 0 && i < cnt) 
							{
								bname[i] = rs22.getString("item_id");
								rdate[i] = rs22.getDate("rent_date");
								ddate[i] = rs22.getDate("due_date");
									
								i = i+1;
								cnt1 = cnt1 - 1;
									
							}
						
							int j = 0;
							while (cnt>0)
							{
								query3 = "SELECT * FROM Item WHERE item_id = '" + bname[j] + "'";
								try
								{
								rs3 = statement.executeQuery(query3);
								
								rs3.next();
								cnt = cnt-1;
								
								book_title_text1[j].setText(rs3.getString("title"));
								acc_no_text[j].setText(bname[j]);
								rent_date_text[j].setText(df2.format(rdate[j]));
								due_date_text[j].setText(df2.format(ddate[j]));
	
								j = j+1;
								patron_no_text.setEditable(false);
								
								testing.setText("a");
							}
							catch(SQLException sqlex)
							{
								sqlex.printStackTrace();
								testing.setText("a");
							}
						}
						
						
						cnt = 0;
						i = 0;
						statement.close();
						

						testing.setText("a");
				}
				catch(SQLException sqlex)
						{
							sqlex.printStackTrace();
							testing.setText("a");
						}
					}
					
					
					catch (SQLException sqlex)
					{
						sqlex.printStackTrace();
						testing.setText("a");
					}
				}
				catch (SQLException sqlex)
					{
					sqlex.printStackTrace();
					testing.setText("a");
					}	
			
			}
					}
					});
					
					

					
        gbc.gridy = 1;
        tab.add(patron_name, gbc);
        gbc.gridy = 2;
        tab.add(patron_group_type, gbc);
        gbc.gridx = 2;
        tab.add(patron_status, gbc);
        gbc.gridx = 4;
        tab.add(patron_loan_limit, gbc);
        gbc.gridx = 6;
        tab.add(patron_expiry_date, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        tab.add(patron_no_text, gbc);
        gbc.gridy = 1;
        tab.add(patron_name_text, gbc);
        gbc.gridy = 2;
        tab.add(patron_group_type_text, gbc);
        gbc.gridx = 3;
        tab.add(patron_status_text, gbc);
        gbc.gridx = 5;
        tab.add(patron_loan_limit_text, gbc);
        gbc.gridx = 7;
        tab.add(patron_expiry_date_text, gbc);
        
        setBackground(Color.blue);
        add(testing);
        add(tab); 
       
    }
    
    }
