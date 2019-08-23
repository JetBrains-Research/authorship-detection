import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class mainmenu extends JFrame implements ActionListener {

	private JButton jbabout =new JButton("ABOUT");
	private JButton jbexit =new JButton("EXIT");
	
	String ttmenu = "<html>" + "<img src=\"file:menu.gif\">" + " Go to Menu Item Catalog " + "</html>";
	String ttorder = "<html>" + "<img src=\"file:order.jpg\">" + " Go to Menu Order " + "</html>";
	String ttabout = "<html>" + "<img src=\"file:about.jpg\">" + " About Us " + "</html>";
	String ttexit = "<html>" + "<img src=\"file:exit.jpg\">" + " Exit program " + "</html>";
	
	private JButton jbmenu =new JButton(" Menu Item ");//menupic
	private JButton jborder =new JButton("Order Menu");
	
	public mainmenu(){
			
		Container cont=getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Color.PINK);
		
		ImageIcon restpic = new ImageIcon("rest2.gif");
	   	JLabel jlbl = new JLabel("Wak Lue Bistro",restpic,SwingConstants.CENTER);
    	jlbl.setForeground(Color.BLACK);
    	jlbl.setFont(new Font("Times New Roman",5,24));
    	jlbl.setHorizontalTextPosition(SwingConstants.CENTER);
    	jlbl.setVerticalTextPosition(SwingConstants.NORTH);
    	jlbl.setIconTextGap(5);
		
		jbabout.setToolTipText(ttabout);
		jbmenu.setToolTipText(ttmenu); 
		jborder.setToolTipText(ttorder);
		jbexit.setToolTipText(ttexit);
			
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.setBackground(Color.PINK);
		p1.add(jlbl, BorderLayout.NORTH);
		
		cont.add(p1,BorderLayout.NORTH);			
		
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout());
		p2.setBackground(Color.PINK);	
		p2.add(jbabout);
		p2.add(jbmenu);
		p2.add(jborder);
		p2.add(jbexit);	
		p2.setBorder(new TitledBorder("SelecTion Button"));
		Border lineborder = new LineBorder(Color.GREEN,2);
		cont.add(p2,BorderLayout.SOUTH);	
		
		jbmenu.addActionListener(this);
		jborder.addActionListener(this);
		jbabout.addActionListener(this);
		jbexit.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e){
	 	
	 	if (e.getSource() == jbmenu){
		Open frame = new Open();
		frame.setTitle("Menu View");
		frame.setSize(600,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		this.dispose();
	 	}
	 	
	 	if (e.getSource() == jborder){
		ordermenu frame = new ordermenu();
		frame.pack();
		frame.setTitle("Ordering System");
		frame.setSize(1100, 650);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		this.dispose();
	 	}
	 	
	 	if (e.getSource() == jbabout){
		about frame=new about();
		frame.setTitle("About");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);		this.dispose();
	 	}
		
		if (e.getSource() == jbexit){
	 			int confirm=JOptionPane.showConfirmDialog(null,"Are you sure to exit?","Exit ",JOptionPane.YES_NO_OPTION);
		 	
		 		if(confirm==0)
		 			System.exit(0);
	 	}
	}
	
   public static void main(String [] args)
   	{
   		mainmenu frame = new mainmenu();
		frame.setTitle("MAIN ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,500);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
   
   }
    
    
}