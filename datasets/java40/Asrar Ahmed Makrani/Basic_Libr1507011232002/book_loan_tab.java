import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class book_loan_tab extends JPanel implements book_loan_tab_Interface
{
	
	    
	public Connection connection;
	public int i;
    public book_loan_tab() {   
     		
    
    for (i = 0; i<5; i++)
    {
    acc_no_text[i] = new JTextField(10);
    acc_no_text[i].setEditable(false);
   }
   	
    for (int i = 0; i<5; i++)
    {
    book_title_text1[i] = new JTextField(30);
    book_title_text1[i].setEditable(false);
   	}
    
 
   	
    for (int i = 0; i<5; i++)
    {
    rent_date_text[i] = new JTextField(10);
    rent_date_text[i].setEditable(false);
   	}
    
    for (int i = 0; i<5; i++)
    {
    due_date_text[i] = new JTextField(10);
    due_date_text[i].setEditable(false);
   	}
    
    upper.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	
	gbc.gridy = 0;
    gbc.gridx = 0;
    upper.add(loan_items, gbc);
    
    
    
    gbc.gridy = 1;
     upper.add(acc_no, gbc);
    
    for (int i = 0; i<5; i++)
    {
    gbc.gridy = 2+i;
    upper.add(acc_no_text[i], gbc);
	}
    
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    upper.add(book_title, gbc);
    
	for (int i = 0; i<5; i++)
    {
    gbc.gridy = 2+i;
    upper.add(book_title_text1[i], gbc);
	}
    
    gbc.gridx = 2;
    gbc.gridy = 1;
    upper.add(rent_date, gbc);
    
    for (int i = 0; i<5; i++)
    {
    gbc.gridy = 2+i;
    upper.add(rent_date_text[i], gbc);
	}
        
    gbc.gridx = 3;
    gbc.gridy = 1;
    upper.add(due_date, gbc);
    for (int i = 0; i<5; i++)
    {
    gbc.gridy = 2+i;
    upper.add(due_date_text[i], gbc);
	}
	
	
	
	
	gbc.gridx = 1;
    gbc.gridy = 9;
    upper.add(txtBookSearch, gbc);
    
      
    gbc.gridx = 3;
    gbc.gridy = 9;
    upper.add(dump, gbc);
    dump.setVisible(false);
    
    add(upper);
    
    
    }
    
    }
