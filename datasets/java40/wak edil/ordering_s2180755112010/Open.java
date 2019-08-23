import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Open extends JFrame implements ActionListener {

	
	private JButton jbtFoods =new JButton("Foods");
	private JButton jbtDrinks =new JButton("Drinks");
	private JButton jbtDesert =new JButton("Desert");
	private JButton jbtExit =new JButton("Exit");
	
	public Open(){
			
		Container cont=getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Color.white);
		
		ImageIcon restpic = new ImageIcon("rest2.gif");
	   	JLabel jlbl = new JLabel("Menu View",restpic,SwingConstants.CENTER);
    	jlbl.setForeground(Color.red);
    	jlbl.setHorizontalTextPosition(SwingConstants.CENTER);
    	jlbl.setVerticalTextPosition(SwingConstants.NORTH);
    	jlbl.setIconTextGap(5);
			
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.setBackground(Color.white);
		p1.add(jlbl, BorderLayout.NORTH);
		
		cont.add(p1,BorderLayout.NORTH);			
		
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout());
		p2.add(jbtFoods);
		p2.add(jbtDrinks);
		p2.add(jbtDesert);
		p2.add(jbtExit);
		p2.setBorder(new TitledBorder("SelecTion Button"));
		Border lineborder = new LineBorder(Color.GREEN,2);
		cont.add(p2,BorderLayout.SOUTH);	
		
		jbtFoods.addActionListener(this);
		jbtDrinks.addActionListener(this);
		jbtDesert.addActionListener(this);
		jbtExit.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e){
	 	
	 	if (e.getSource() == jbtFoods){
		Foods frame = new Foods();
		frame.setTitle("Foods");
		frame.setSize(950,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		this.dispose();
	 	}
	 	
	 	if (e.getSource() == jbtDrinks){
		Drinks frame = new Drinks();
		frame.setTitle("Drinks");
		frame.setSize(950,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.dispose();
	 	}
	 	
	 	if (e.getSource() == jbtDesert){
		Desert frame = new Desert();
		frame.setTitle("Desert");
		frame.setSize(950,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.dispose();
	 	}
		
		if (e.getSource() == jbtExit){
	 			int confirm=JOptionPane.showConfirmDialog(null,"Are you sure to exit?","Exit ",JOptionPane.YES_NO_OPTION);
		 	
		 		if(confirm==0)
		 			System.exit(0);
	 	}
	}

	
	
	public static void main(String[] args)
	{
		Open frame = new Open();
		frame.setTitle("Menu View");
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
	}
}