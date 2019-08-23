/**
 * @(#)about.java
 *
 *
 * @author 
 * @version 1.00 2010/3/24
 */


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.*;

public class about extends JFrame implements ActionListener
{
	private JTextArea jtaIntro2=new JTextArea("\n\t\tWak Lue Bistro Restaurent"+"\n\t\t(A SUBSIDARY OF WAK & CO. SDN BHD)\n");
											 
											
												
	private JTextArea jtaIntro=new JTextArea("\t\t\tWAK & CO SDN BHD,\n"+ 
		                                     "\t\t2000 Restaurent. HALAL MALAYSIA. All right reserved.\n"+
		                                     "\t\tThe restaurant has some signature dishes such as the\n"+
		                                     "\t\tNasi Lemak Jawa, Mee Goreng Utara,the first in City\n"+
		                                     "\t\tSquare Centre opened in June 2005 and the second in \n"+
		                                     "\t\tTaman Tun Dr. Ismail (a residential suburb) in Nov 2006.\n"+
											 "\t\tHowever, vegetarians can also find refuge here.\n"+
											 "\t\tAll of our meats are halal slaughtered and absolutely\n"+
											 "\t\tNO PORK is served.\n");
	
	String ttback = "<html>" + "<img src=\"file:back.jpg\">" + " Back Main Menu " + "</html>";
	String ttexit = "<html>" + "<img src=\"file:exit.jpg\">" + " Exit Program " + "</html>";
	
	private JButton jbBack=new JButton("Back");
	private JButton jbExit=new JButton("Exit");
	private JMenuItem jmiExit,jmiAbout;
	
	public about()
	{
		Container container=getContentPane();
		container.setLayout(new BorderLayout(2,1));
		container.setBackground(Color.white);
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(2,1));
	
		jtaIntro2.setEditable(false);
		jtaIntro2.setFont(new Font("Serif",Font.BOLD,17));
	
		jtaIntro.setEditable(false);
		jtaIntro.setFont(new Font("Serif",Font.BOLD,16));
		
		p1.add(jtaIntro2);
		p1.add(jtaIntro);

		
		container.add(p1,BorderLayout.CENTER);
		
				
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout (1,4));
		p2.add(new JLabel ());
		p2.add(jbBack);
		p2.add(jbExit);
		p2.add(new JLabel ());
		p2.setBackground(Color.white);
		
		jbBack.setToolTipText(ttback);
		jbExit.setToolTipText(ttexit);
		container.add(p2,BorderLayout.SOUTH);
				
		JMenuBar jmb=new JMenuBar();
		
		JMenu fileMenu= new JMenu("File");
		fileMenu.setMnemonic('F');
		jmb.add(fileMenu);
		fileMenu.add(jmiExit=new JMenuItem("Exit"));
					
		JMenu helpMenu= new JMenu("Help");
		helpMenu.setMnemonic('H');
		jmb.add(helpMenu);
		helpMenu.add(jmiAbout=new JMenuItem("About Us"));
		
		container.add(jmb,BorderLayout.NORTH);
		
		jbBack.addActionListener(this);
		jbExit.addActionListener(this);
		jmiExit.addActionListener(this);
	    jmiAbout.addActionListener(this);
	
	}
	
	
		public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == jbBack)
	 	{
		mainmenu frame = new mainmenu();
		frame.setTitle("MAIN ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		this.dispose();
	 	}
	 	
	 	if (e.getSource() == jmiExit)
		 	{
		 	
					int exit=JOptionPane.showConfirmDialog(null,"Confirm Exit?","EXIT",JOptionPane.YES_NO_OPTION);
		 	
		 		if(exit==0)
		 		{
		 			System.exit(0);
		 		}
		 		else
		 		{
		 		}
						
		 	}
		 	if (e.getSource() == jmiAbout)
		 	{
		 	
				JOptionPane.showMessageDialog(null,"Wak Lue Bistro"+
					"\n\nAddress: First OutLet,"+"\n Lt L3-18A(P),Level 3,"+"\n Mines shopping Fair,"+
					"\n Jalan Dulang, Mines Resort City"+"\n 43300 Seri Kembangan, Selangor."+"\n 03-89386655"+
					"\n\nAddress: Secand OutLet,"+"\n Lt L3-20B(P),Level 3,"+"\n AmCorp Mall,"+
					"\n Jalan Gasing, Menara AmCorp"+"\n 42200 Petaling Jaya, Selangor."+"\n 03-89385566"+
					"","About Us",JOptionPane.INFORMATION_MESSAGE);
						
		 	}
		 	
		 if (e.getSource() == jbExit){
	 		int exit=JOptionPane.showConfirmDialog(null,"Confirm Exit?","EXIT",JOptionPane.YES_NO_OPTION);
		 	
		 		if(exit==0)
		 		{
		 			System.exit(0);
		 		}
		 		else
		 		{
		 		}
	 	}
		 
	}
	public static void main(String[]args)
	{
		about frame=new about();
		frame.setTitle("About");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}