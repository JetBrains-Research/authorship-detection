import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.EventListener.*;
import javax.swing.*;
import java.awt.print.*;

public class video extends JInternalFrame implements video_Interface, video_master_info_Interface, video_tab_Interface{
 	
 	 private int paintx, painty;
 	 	public JButton newButton = new JButton(new ImageIcon("new.gif"));
	public JButton saveButton = new JButton(new ImageIcon("save.gif"));
	public JButton deleteButton = new JButton(new ImageIcon("delete.gif"));
	public JButton updateButton = new JButton(new ImageIcon("update.gif"));
	JToolBar toolBarVideo = new JToolBar();

    public video() {
        super("Journal", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              
        int inset = 250;
        addButtons(toolBarVideo);
        
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
        
		c.add(toolBarVideo, BorderLayout.NORTH);
		c.add(gen, BorderLayout.CENTER);
		
		
			}
			
		protected void addButtons(JToolBar toolBarVideo) {

        //first button
        
        newButton.setToolTipText("New");
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Video_no_text.setText("VD-");
            	Video_title_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	Video_no_text.setEditable(true);
            	Video_title_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
                      }
        });

        toolBarVideo.add(newButton);
        
        //second button
       
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            String a = Video_no_text.getText();
            	String b =  Video_title_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	videoSQL vidsql = new videoSQL(a, b, j, k, l);
            	vidsql.add();            	
            }
        });
        toolBarVideo.add(saveButton);
        
		updateButton.setToolTipText("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {	
            String a = Video_no_text.getText();
            	String b =  Video_title_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	videoSQL vidsql = new videoSQL(a, b, j, k, l);
            	vidsql.update();
            }
        });
        toolBarVideo.add(updateButton);      
       
        deleteButton.setToolTipText("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "once");	
            String a = Video_no_text.getText();
            	String b =  Video_title_text.getText();
            	String j = authorsArea.getText();
            	String k = subjectArea.getText();
            	String l = location_text.getText();
            	//Object m = campus_combo.getSelectedItem();
            	videoSQL vidsql = new videoSQL(a, b, j, k, l);
            	vidsql.delete();
            }
        });
        toolBarVideo.add(deleteButton);
    }
 		}