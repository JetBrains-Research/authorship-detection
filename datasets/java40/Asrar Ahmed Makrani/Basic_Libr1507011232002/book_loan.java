import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.EventListener.*;
import javax.swing.*;
import java.awt.print.*;

public class book_loan extends JInternalFrame implements book_loan_Interface, book_loan_tab_Interface, book_loan_master_Interface {
 	
 	 private int paintx, painty;
	 public JButton button = null;	
	
    public book_loan() {
        super("Item Loan", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              
        int inset = 250;
 		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);
        paintx = (screenSize.width);
		painty = (screenSize.height);
        
        int xy = this.getX();
        
        Container c = getContentPane();
        setSize(800, 500);
 		setLocation(0, 0);
 		setResizable(false);
 		show();
 		setBounds( (paintx - 800)/2 , ((painty-100) - 500)/2,
						 800,500);
        
		c.add(toolBar, BorderLayout.NORTH);
		c.add(gen, BorderLayout.CENTER);
		patron_no_text.setText("");
            	patron_name_text.setText("");
            	patron_group_type_text.setText("");
            	patron_status_text.setText("");
            	patron_loan_limit_text.setText("");
            	patron_expiry_date_text.setText("");
            	
            	for (int z = 0; z<5; z++)
			    {
			    acc_no_text[z].setText("");
			    book_title_text1[z].setText("");
			    rent_date_text[z].setText("");
			    due_date_text[z].setText("");
				}
				patron_no_text.setEditable(true);
            	dump.setText("");
		
			}
			
		protected void addButtons(JToolBar toolBar) {
        
        //first button
        button = new JButton(new ImageIcon("new.gif"));
        button.setToolTipText("New");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	patron_no_text.setText("");
            	patron_name_text.setText("");
            	patron_group_type_text.setText("");
            	patron_status_text.setText("");
            	patron_loan_limit_text.setText("");
            	patron_expiry_date_text.setText("");
            	
            	for (int z = 0; z<5; z++)
			    {
			    acc_no_text[z].setText("");
			    book_title_text1[z].setText("");
			    rent_date_text[z].setText("");
			    due_date_text[z].setText("");
				}
            	
            	patron_no_text.setEditable(true);
            	dump.setText("");
            	
            }
        });

        toolBar.add(button);
        
        //second button
        button = new JButton(new ImageIcon("save.gif"));
        button.setToolTipText("Save");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
    	{
    	if (!txtBookSearch.getText().equals(""))
    		{
    			testing.setText("");
    			bookSQL bsql = new bookSQL();
    			bsql.loan();
    		}
    	}
        });
        toolBar.add(button);
        
         //forth button
        button = new JButton(new ImageIcon("clear.gif"));
        button.setToolTipText("Clear");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i<5; i++)
			    {
			    acc_no_text[i].setText("");
			    book_title_text1[i].setText("");
			    rent_date_text[i].setText("");
			    due_date_text[i].setText("");
				}
			    patron_no_text.setText("");
			    patron_name_text.setText("");
            	patron_group_type_text.setText("");
            	patron_status_text.setText("");
            	patron_loan_limit_text.setText("");
            	patron_expiry_date_text.setText("");
				 }
        });
        toolBar.add(button);
    }
 		}