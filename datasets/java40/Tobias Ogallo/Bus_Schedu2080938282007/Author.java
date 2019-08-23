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
public class Author extends JFrame{
	private JLabel me;
	private JButton view,exit;
	private JTextArea about;
	private JPanel pan,pan2;
	private JScrollPane jScrollPane;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
public Author(){
	super("The Man Behind it All");
me=new JLabel(new ImageIcon("aut.jpg"));
view=new JButton ("More Info");
exit=new JButton("Exit");

	about=new JTextArea ("Name:   Tobias Oluoch Ogallo \n" +
	                     "Cell:   0720233259\n"+
	                     "E-mail:  tobi2006@gmail.com\n"+
	                     "Address:6046,Kisumu" +
	                     "Message\n\n"+
                         "Thanks to all those who are supporting me\n"+
                         "To developed this system\n"+
                         "Your contribution are higly appreciated\n"+
                         "Special thanks to\n"+
                         "1) Erastus Gichuhi\n"+
                         "2) Mike Mboya\n"+
                         "3) Kamu King'ora\n"+
                         "4) Kefa Mwangi\n"+
                         "who were really there to give advice\n"+
                         "and to do peer-to-peer testing for errors\n"+
                         "BRAVOOOOOOOOOO TO ALL");
about.setForeground(Color.blue);
pan2=new JPanel();
pan2.add(view);
pan2.add(exit);
pan=new JPanel(new GridLayout(2,2));
pan.setPreferredSize(new Dimension(450,300));

pan.add(me);pan .add( new JScrollPane(about));

pan.add(pan2);


getContentPane().add(pan);

		pack();
		setVisible(true);
		setLocation((screen.width - 500)/2,((screen.height-350)/2));
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(true);
				dispose();
			}
		});
}

}