import java.sql.*;
import javax.swing.*;
import java.util.Date;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;
import java.text.*;
import java.util.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import java.net.*;
class employee_report extends JFrame  {
	public Container content;
	public JPanel reportingPanel;
	public JTabbedPane listsTabs;
	public JTextArea listPane;
	public JPanel reportPanel;
	public JPanel statusPanel;
	public JComboBox graphTypesCombo;
	public Color skyblue=new Color(150,190,255);
	public 	final ImageIcon imageIcon = new ImageIcon("Icon/header/cool.png");
	private static Connection dbcon = null;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
    Statement stmt = null;
	private JButton print,cancel;
	private JPanel panel;	
	public employee_report()
	{ 
		
		super("Employee Reports");
		
		content=getContentPane();
		content.setBackground(skyblue);
		listsTabs=new JTabbedPane();
		print=new JButton("PRINT ",new ImageIcon("Icon/i16x16/prints.png"));
		cancel=new JButton("CANCEL",new ImageIcon("Icon/i16x16/exit.png"));
		panel=new JPanel();
		panel.add(print);
		panel.add(cancel);
		
		reportingPanel=new JPanel();
		reportingPanel.setLayout(new BorderLayout());
		reportingPanel.setBorder(BorderFactory.createEtchedBorder());
		reportingPanel.add(new JLabel("Employee Report"),BorderLayout.NORTH);
		reportPanel=new JPanel();
		reportPanel.setLayout(new GridLayout(1,1));
		reportPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.blue));
		reportPanel.setBackground(Color.white);
		
		reportingPanel.add(new JScrollPane(reportPanel),BorderLayout.CENTER);
		reportingPanel.add(panel,BorderLayout.SOUTH);
		listsTabs.add(reportingPanel);
	setLocation((screen.width-1270)/2,((screen.height-740)/2));
		
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
        super.paint(g);
      }
    };
    
    
		listPane.setEditable(false);
		listPane.setFont(new Font("Serif", Font.BOLD, 12));
		listPane.setForeground(Color.black);
	
		listPane.setLineWrap(true);
		listPane.setWrapStyleWord(true);
		reportPanel.add(listPane);
		 
         	try

                {
                Statement s = Connect.getConnection().createStatement();
                }

     
      catch ( Exception excp )

         {
            excp.printStackTrace();
         }    
         printList();
         


      	content.add(listsTabs,BorderLayout.CENTER);
      	setResizable(false);
      	setSize(1250,720);
      	setVisible(true);
      	cancel.addActionListener(new java.awt.event.ActionListener() {
	       public void actionPerformed(java.awt.event.ActionEvent e) {
				//dispose();
				print(createBuffer());
				
			}
		});

	
	}

	private void printList() 
	{
    	try {
                
            ResultSet rst=Connect.getConnection().createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE).executeQuery("select empNo,Sname,Fname,Gender,Designation,Telephone,E_Mail from Emp");
                
			
				listPane.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				listPane.append("Emp_No"+"\t\t"+"Sname"+"\t\t"+"Fname"+"\t\t"+"Gender\t\t"+"Designation"+"\t\t"+"Telephone\t\t"+"E-Mail\n");
				while (rst.next())
                {
                	listPane.append("       ");
                	listPane.append(rst.getString(1).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(2).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(3).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(4).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(5).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(6).trim());
                	listPane.append("\t\t");
                	listPane.append(rst.getString(7).trim());
				    listPane.append("\n\n");
				 }
				

                 if (rst != null)
                  rst.close();
                 
               } catch (SQLException sqle) {
                     JOptionPane.showMessageDialog(null, " No Records found"
                                       + sqle.getMessage());
                    return;
               }
       }
		
	


	
	public static void main(String[] args)
	{
		new employee_report();
		
	}
		public String createBuffer()
	{
		String buffer;
		buffer = listPane.getText();
		return buffer;
	}
	private void print(String s)
	{
		StringReader sr = new StringReader(s);
		LineNumberReader lnr = new LineNumberReader(sr);
		Font typeface = new Font("Monospaced", Font.PLAIN, 12);
		Properties p = new Properties();
		PrintJob pjob = getToolkit().getPrintJob(this, "Print report", p);

		if (pjob != null) {
			Graphics pg = pjob.getGraphics();
			if (pg != null) {
				FontMetrics fm = pg.getFontMetrics(typeface);
				int margin = 20;
				int pageHeight = pjob.getPageDimension().height - margin;
    			int fontHeight = fm.getHeight();
    			int fontDescent = fm.getDescent();
    			int curHeight = margin;

				String nextLine;
				pg.setFont (listPane.getFont());

				try
				{
					do
					{
						nextLine = lnr.readLine();
						if (nextLine == null) {
							if ((curHeight + fontHeight) > pageHeight)
							{ 
								pg.dispose();
								pg = pjob.getGraphics();
								curHeight = margin;
							}

							curHeight += fontHeight;

							if (pg != null)
							{
								pg.setFont (typeface);
								pg.drawString (nextLine, margin, curHeight - fontDescent);
							}
						}
					}
					while (nextLine != null);

				}
				catch (EOFException eof)
				{
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
			pg.dispose();
		}
		if (pjob != null)
			pjob.end();
	}
	
}
