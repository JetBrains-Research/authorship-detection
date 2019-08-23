import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.EventListener.*;
import javax.swing.*;
import java.awt.print.*;
import java.text.*;

public class book_master extends JInternalFrame implements book_master_Interface, book_master_tab_Interface, book_master_info_Interface{
 	
 	 private int paintx, painty;
 	 public JButton button;
 	 
    public book_master() {
        super("Book Master", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              
        int inset = 250;
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);
        //book_master.setDefaultCloseOperation (HIDE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        paintx = (screenSize.width);
		painty = (screenSize.height);
        Container c = getContentPane();
        setSize(800, 500);
 		setLocation(0, 0);
 		setResizable(false);
 		show();
 		setBounds( (paintx - 800)/2 , ((painty-100) - 500)/2,
						 800,500);
                
		c.add(toolBar, BorderLayout.NORTH);
		c.add(gen, BorderLayout.CENTER);
		delete.setEnabled(false);
		update.setEnabled(false);
		
		
			}
			
		protected void addButtons(JToolBar toolBar) {
        //first button
        newForm.setToolTipText("New");
        newForm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	book_no_text.setText("BK-");
            	book_title_text.setText("");
            	isbn_text.setText("");
            	class_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	book_no_text.setEditable(true);
            	book_title_text.setEditable(true);
 				isbn_text.setEditable(true);
 				class_text.setEditable(true);
 				publisher_text.setEditable(true);
 				year_text.setEditable(true);
 				pages_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
 				
 				delete.setEnabled(false);
 				update.setEnabled(false);
            	            }
        });

        toolBar.add(newForm);
        
        //second button
        save.setToolTipText("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String a = book_no_text.getText();
            	String b =  book_title_text.getText();
            	String c =  isbn_text.getText();
            	String d =  class_text.getText();
            	String h =  publisher_text.getText();
            	String g =  year_text.getText();
            	String i = pages_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	bookSQL booksql = new bookSQL(a, b, c, d, h, g, i, j, k, l);
            	booksql.add();
            }
        });
        toolBar.add(save);
        
        //second button
        update.setToolTipText("Update");
        update.addActionListener(new ActionListener() 
			 				{
            					public void actionPerformed(ActionEvent e) 
            					{
            						JOptionPane.showMessageDialog(null, "pressed");
					 				String a = book_no_text.getText();
					            	String b =  book_title_text.getText();
					            	String c =  isbn_text.getText();
					            	String d =  class_text.getText();
					            	String h =  publisher_text.getText();
					            	String g =  year_text.getText();
					            	String i = pages_text.getText();
					            	String j = authorsArea.getText();
					            	String k = subjectArea.getText();
					            	String l = location_text.getText();
					            	//Object m = campus_combo.getSelectedItem();
					            	bookSQL booksql = new bookSQL(a, b, c, d, h, g, i, j, k, l);
					            	booksql.update();
					            	delete.setEnabled(false);
 									update.setEnabled(false);
            					}
            				});
        toolBar.add(update);
        
         //forth button
        delete.setToolTipText("Delete");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = book_no_text.getText();
            	String b =  book_title_text.getText();
            	String c =  isbn_text.getText();
            	String d =  class_text.getText();
            	String h =  publisher_text.getText();
            	String g =  year_text.getText();
            	String i = pages_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	bookSQL booksql = new bookSQL(a, b, c, d, h, g, i, j, k, l);
            	booksql.delete();
            	delete.setEnabled(false);
 				update.setEnabled(false);
            }
        });
        toolBar.add(delete);
    }
 		}