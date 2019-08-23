/**
 * @(#)Report.java
 *
 *
 * @author 
 * @version 1.00 2010/4/16
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Report extends JFrame implements ActionListener{
	
	//String numtable = (String)jcbnumtable.getSelectedItem();
	//String numperson =(String)jcbnumpersonpertable.getSelectedItem();
	//String datetime = dateFormat.format(date);
	//String all= jtasummary.getText();
	//String sub=jtfSub.getText();
	//String tax=jtfTax.getText();
	//String total=jtfTotal.getText();
	
	private JTextArea jtaheader=new JTextArea("\n-------------------------------------------------------------"+
											  "\n\tWak Lue Bistro"+
											  "\n\t Lt L3-18A(P),Level 3,"+
											  "\n\tMines shopping Fair,"+
											  "\n\tJalan Dulang,"+
											  "\n\tMines Resort City"+
											  "\n\t43300 Seri Kembangan,"+
											  "\n\tSelangor."+
											  "\n\n\t Tel : 03-89386655"+
											  "\n-------------------------------------------------------------");
	private JTextArea jtaAll=new JTextArea(50,50);
	private JButton btnPrint = new JButton("Print");
	
    public Report() {
    }
    
    public Report(String numtable,String numperson,String datetime,String all,String sub,String tax,String total)
    {
  		Container myCont = getContentPane();
		myCont.setLayout(new BorderLayout());
		
		// PANEL 1 - UP
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(jtaheader,BorderLayout.CENTER);
		jtaheader.setEditable(false);
		myCont.add(p1,BorderLayout.NORTH);
		
    	JPanel p2 = new JPanel();
    	p2.setLayout(new GridLayout(3,1,10,10));
    	//p2.add(new JLabel (""));
    	p2.add(new JLabel("Number Table : "+numtable));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	p2.add(new JLabel ("Number Person Per Table : "+numperson));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	p2.add(new JLabel ("Date : "+datetime));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	
    	JPanel p20 = new JPanel();
    	p20.setLayout(new GridLayout(1,1,10,10));
    	p20.add(jtaAll =new JTextArea("Your Order : "+"\n"+all));
    	jtaAll.setEditable(false);
    	JScrollPane spreport=new JScrollPane(jtaAll);
		p20.add(spreport);
    	
    	JPanel p200 = new JPanel();
    	p200.setLayout(new GridLayout(3,1,10,10));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	p200.add(new JLabel ("Sub Total : "+sub));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	p200.add(new JLabel ("Tax 5%: "+tax));
    	//p2.add(new JLabel (""));
    	//p2.add(new JLabel (""));
    	p200.add(new JLabel ("Total : "+total));
    	//p2.add(new JLabel (""));
		
		JPanel p2000 = new JPanel();
		p2000.setLayout(new GridLayout(3,1,10,10));
		p2000.add(p2);
		p2000.add(p20);
		p2000.add(p200);
    	myCont.add(p2000,BorderLayout.CENTER);  
    		
    	JPanel p3 = new JPanel();
    	p3.setLayout(new FlowLayout());
		p3.setBackground(Color.YELLOW);
		p3.add(btnPrint);
		myCont.add(p3,BorderLayout.SOUTH);
    	
    	btnPrint.addActionListener(this);
    }

public void actionPerformed(ActionEvent e)
	{
    	if (e.getSource() == btnPrint)
    	{
			this.dispose();
			System.exit(0);
	 	}
	}
     public static void main(String[]args)
	{
        Report  myR=new Report();
		myR.setTitle("Receipt Detail");
		myR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myR.setSize(600,600);
		myR.setVisible(true);
		myR.setResizable(true);
		myR.setLocationRelativeTo(null);
	}
    
}