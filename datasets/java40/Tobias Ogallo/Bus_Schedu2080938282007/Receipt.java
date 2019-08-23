import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.text.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.text.*;
import java.util.Date;


class Receipt extends JFrame {
	public Container content;
	public JPanel reportingPanel;
	public JTabbedPane listsTabs;
	public JPanel chartPanel;
	public JButton hide;
	public JTextArea listPane;
	public JPanel reportPanel;
	public JButton drawGraphButton;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	public int ID;
	public Color skyblue=new Color(150,190,255);
	public 	final ImageIcon imageIcon = new ImageIcon("Icon/header/cool.png");
	private static Connection dbcon = null;
	private JButton print,cancel;
	private JPanel panel;	
    Statement stmt = null;
	 public Receipt()
	 {
	 	
	 	super("Receipt");
		
		content=getContentPane();
		content.setBackground(skyblue);
		
		print=new JButton("PRINT ",new ImageIcon("Icon/i16x16/prints.png"));
		cancel=new JButton("CANCEL",new ImageIcon("Icon/i16x16/exit.png"));
		panel=new JPanel();
		panel.add(print);
		panel.add(cancel);
		reportingPanel=new JPanel();
		reportingPanel.setLayout(new BorderLayout());
		reportingPanel.setBorder(BorderFactory.createEtchedBorder());
		reportingPanel.add(new JLabel("Receipt for Payment"),BorderLayout.NORTH);
		reportingPanel.add(panel,BorderLayout.SOUTH);
		reportPanel=new JPanel();
		reportPanel.setLayout(new GridLayout(1,1));
		reportPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.blue));
		reportPanel.setBackground(Color.white);
		
		reportingPanel.add(new JScrollPane(reportPanel),BorderLayout.CENTER);
	    
		
	 
    	produceCertificate();
		listPane.setEditable(false);
		listPane.setFont(new Font("Serif", Font.PLAIN, 12));
		listPane.setForeground(Color.black);
	
		listPane.setLineWrap(true);
		listPane.setWrapStyleWord(true);
		reportPanel.add(listPane);
		setLocation((screen.width-1270)/2,((screen.height-740)/2));
		setResizable(false);
         	try

         {
	
                Statement s = Connect.getConnection().createStatement();
         }
      catch ( Exception excp )

      {
            excp.printStackTrace();	
	}    
        
		JPanel dpanel=new JPanel();
		dpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		dpanel.setLayout(new GridLayout(1,1));
		DateFormat defaultDate=DateFormat.getDateInstance(DateFormat.FULL);
      	content.add(reportingPanel,BorderLayout.CENTER);

      	setLocation(5,0);
      	setSize(780,700);
      	setVisible(true);
		
	 }
	 public void produceCertificate()
	 {
	 	listPane = new JTextArea() {
      Image image = imageIcon.getImage();
      {
        setOpaque(false);
      } 

      public void paint(Graphics g) {
        g.drawImage(image, 340, 30, this);
        g.setColor(Color.blue);
        
        g.drawString("Phone: +254 720576879: Cellphone: 0720576879",385,70);
        g.drawString("Fax: +254 720576879 ",385,90);
        g.drawString("Address: Box 6046-20100, Nakuru, Kenya ",385,110);
        g.drawString("Email:rvs@gmail.com",385,140);
        g.drawString("Website:www.rvs.co.ke",385,170);
        g.setColor(Color.black);
        g.drawString("PAYMENT RECEIPT",365,200);
        g.drawString("Payment Number      "+new Payment().text1.getText(),80,230);
        g.drawString("Name of Passenger   "+new Payment().combo2.getSelectedItem(),80,260);
        
        g.drawString("Amount Paid Kshs    "+new Payment().combo8.getSelectedItem(),80,290);
        g.drawString("Pay on              "+new Payment().combo4.getSelectedItem(),80,320);
        g.drawString("Date Paid           "+new Payment().p_date.getText(),620,350);
        g.drawString("Received By         "+new Payment().combo3.getSelectedItem(),80,380);
       
        g.setColor(Color.red);
        g.drawString("Welcome back....and..... Safe Journey",200,410);
       
      
        super.paint(g);
      }
    };
	 }
	
	
	 public static void main(String [] args)
	 {
	 	new Receipt();
	 	
	 }
	
		 
	 
}