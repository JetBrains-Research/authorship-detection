import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.EventListener.*;
import javax.swing.*;
import java.awt.print.*;

public class journal extends JInternalFrame implements journal_Interface, journal_master_info_Interface, journal_tab_Interface{
 	
 	 private int paintx, painty;

    public journal() {
        super("Journal", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              
        int inset = 250;
        addButtons(toolBar);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Container c = getContentPane();
        setSize(800, 500);
 		setLocation(0, 0);
 		setResizable(false);
 		show();
 		paintx = (screenSize.width);
		painty = (screenSize.height);
		setBounds( (paintx - 800)/2 , ((painty-100) - 500)/2,
						 800,500);
        
		c.add(toolBar, BorderLayout.NORTH);
		c.add(gen, BorderLayout.CENTER);
		
		
			}
			
		protected void addButtons(JToolBar toolBar) {

        //first button
        
        newBut.setToolTipText("New");
        newBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	journal_no_text.setText("JL-");
            	journal_title_text.setText("");
            	class_text.setText("");
            	issue_number_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	journal_no_text.setEditable(true);
            	journal_title_text.setEditable(true);
 				class_text.setEditable(true);
 				issue_number_text.setEditable(true);
 				publisher_text.setEditable(true);
 				year_text.setEditable(true);
 				pages_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
           }
        });

        toolBar.add(newBut);
        
        //second button
       
        save.setToolTipText("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = journal_no_text.getText();
            	String b =  journal_title_text.getText();
            	String c =  issue_number_text.getText();
            	String d =  class_text.getText();
            	String h =  publisher_text.getText();
            	String g =  year_text.getText();
            	String i = pages_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	journalSQL jorsql = new journalSQL(a, b, c, d, h, g, i, j, k, l);
            	jorsql.add();
            	
            }
        });
        toolBar.add(save);
        
       
        delete.setToolTipText("Delete");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            String a = journal_no_text.getText();
            	String b =  journal_title_text.getText();
            	String c =  issue_number_text.getText();
            	String d =  class_text.getText();
            	String h =  publisher_text.getText();
            	String g =  year_text.getText();
            	String i = pages_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	journalSQL jorsql = new journalSQL(a, b, c, d, h, g, i, j, k, l);
            	jorsql.delete();	
            }
        });
        toolBar.add(delete);
    }
 		}