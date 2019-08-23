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

public class General_frame extends JInternalFrame implements General_frame_Interface, remark_Interface, General_Info_Interface, passwordInterface{
 	
 	 public java.util.Date dt;
 	 private int paintx, painty;
 	 public String dd;	
 	 
    public General_frame() {
        super("Patron Setup", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              
        
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        
        Calendar cal = Calendar.getInstance();
        
        cal.add(Calendar.DATE, 364);
        dt = cal.getTime();
        
        System.out.println(dt);
        
        reg_by_text.setEnabled(false);
        reg_date_text.setEnabled(false);
        expiry_date_text.setEnabled(false);
        expiry_date_text.setText(String.valueOf(formatter.format(dt)));
        reg_by_text.setText(usersName.getText());
		dd = formatter.format(currentDate);
		reg_date_text.setText(dd);
        int inset = 250;
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);
        
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
		System.gc();
//		toolBar.DISPOSE_ON_CLOSE();
		
			}
			
		protected void addButtons(JToolBar toolBar) {
        JButton button = null;

        //first button
        button = new JButton(new ImageIcon("new.gif"));
        button.setToolTipText("New");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
							patron_text.setText("P-");
			            	name_text.setText("");
			 				passport_text.setText("");
			 				expiry_date_text.setText(String.valueOf(formatter.format(dt)));
			 				reg_by_text.setText(usersName.getText());
			 				reg_date_text.setText(dd);
			 				textArea.setText("");
			 				tel_text.setText("");
			 				fax_text.setText("");
			 				email_text.setText("");
			 				
			 				patron_text.setEditable(true);
			            	name_text.setEditable(true);
			 				passport_text.setEditable(true);
			 				expiry_date_text.setEditable(true);
			 				reg_by_text.setEditable(true);
			 				reg_date_text.setEditable(true);
			 				textArea.setEditable(true);
			 				tel_text.setEditable(true);
			 				fax_text.setEditable(true);
			 				email_text.setEditable(true);
            }
        });

        toolBar.add(button);
        //second button
        button = new JButton(new ImageIcon("save.gif"));
        button.setToolTipText("Save");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = patron_text.getText();
            	String b = name_text.getText();
            	String c = passport_text.getText();
            	String d = expiry_date_text.getText();
            	String g = reg_by_text.getText();
            	String  l = reg_date_text.getText();
            	String h = textArea.getText();
            	String i = tel_text.getText();
            	String j = fax_text.getText();
            	String k = email_text.getText();
            	patronSQL patsql = new patronSQL(a, b, c, d, g, l, h, i, j, k);
            	patsql.add();
            	patron_text.setText("P-");
            }
        });
        toolBar.add(button);
		
		//forth button
        button = new JButton(new ImageIcon("clear.gif"));
        button.setToolTipText("Update");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = patron_text.getText();
            	String b = name_text.getText();
            	String c = passport_text.getText();
            	String d = expiry_date_text.getText();
            	String g = reg_by_text.getText();
            	String  l = reg_date_text.getText();
            	String h = textArea.getText();
            	String i = tel_text.getText();
            	String j = fax_text.getText();
            	String k = email_text.getText();
            	patronSQL patsql = new patronSQL(a, b, c, d, g, l, h, i, j, k);
            	patsql.update();
            	patron_text.setText("P-");
            	
            }
        });
        toolBar.add(button);
		
         //forth button
        button = new JButton(new ImageIcon("delete.gif"));
        button.setToolTipText("Delete");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = patron_text.getText();
            	String b = name_text.getText();
            	String c = passport_text.getText();
            	String d = expiry_date_text.getText();
            	String g = reg_by_text.getText();
            	String  l = reg_date_text.getText();
            	String h = textArea.getText();
            	String i = tel_text.getText();
            	String j = fax_text.getText();
            	String k = email_text.getText();
            	patronSQL patsql = new patronSQL(a, b, c, d, g, l, h, i, j, k);
            	patsql.delete();
            	patron_text.setText("P-");
            }
        });
        toolBar.add(button);
    }
    
 		}