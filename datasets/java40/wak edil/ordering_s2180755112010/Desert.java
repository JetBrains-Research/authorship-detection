import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


public class Desert extends JFrame implements ActionListener
{
	private JButton jbtChoc1=new JButton("Ais Batu Campur");
	private JButton jbtChoc2=new JButton("Aiskrim");
	private JButton jbtChoc3=new JButton("Banana Split");
	private JButton jbtChoc4=new JButton("Kek Coklat");
	private JButton jbtChoc5=new JButton("Puding");
	private JButton jbtChoc6=new JButton("Rojak Buah");
	private JButton jbtBack=new JButton("Back");
	private JButton jbtExit=new JButton("Exit");
	private JLabel FoodLabelChoice=new JLabel("Choose your Foods : ");
	private ImageIcon Food1Image=new ImageIcon("desert/ABC.jpeg");
	private ImageIcon Food2Image=new ImageIcon("desert/aiskrim.jpeg");
	private ImageIcon Food3Image=new ImageIcon("desert/banana split.jpeg");
	private ImageIcon Food4Image=new ImageIcon("desert/kek coklat.jpeg");
	private ImageIcon Food5Image=new ImageIcon("desert/puding.jpeg");
	private ImageIcon Food6Image=new ImageIcon("desert/rojak buah.jpeg");


	public Desert()
	{
		Container cont=getContentPane();
		cont.setLayout(new BorderLayout(5,5));;
		cont.setBackground(Color.white);
		
		cont.add(FoodLabelChoice,BorderLayout.NORTH);
		
		
		JPanel pA=new JPanel(new GridLayout(2,3,5,5));
		   jbtChoc1.setIcon(Food1Image);
		   pA.add(jbtChoc1);
		   jbtChoc1.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc1.setHorizontalTextPosition(AbstractButton.CENTER);

		   jbtChoc2.setIcon(Food2Image);
		   pA.add(jbtChoc2);
		   jbtChoc2.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc2.setHorizontalTextPosition(AbstractButton.CENTER);
			
		   jbtChoc3.setIcon(Food3Image);
		   pA.add(jbtChoc3);
		   jbtChoc3.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc3.setHorizontalTextPosition(AbstractButton.CENTER);
			
		   jbtChoc4.setIcon(Food4Image);
		   pA.add(jbtChoc4);
		   jbtChoc4.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc4.setHorizontalTextPosition(AbstractButton.CENTER);
			
		   jbtChoc5.setIcon(Food5Image);
		   pA.add(jbtChoc5);
		   jbtChoc5.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc5.setHorizontalTextPosition(AbstractButton.CENTER);
			
		   jbtChoc6.setIcon(Food6Image);
		   pA.add(jbtChoc6);
		   jbtChoc6.setVerticalTextPosition(AbstractButton.BOTTOM);
			jbtChoc6.setHorizontalTextPosition(AbstractButton.CENTER);
         
         cont.add(pA,BorderLayout.CENTER);
         
        JPanel pB=new JPanel(new FlowLayout(FlowLayout.CENTER));
        pB.setBackground(Color.white);
		pB.add(jbtBack);
		pB.add(jbtExit);
		cont.add(pB,BorderLayout.SOUTH);
	
		jbtChoc1.addActionListener(this);
		jbtChoc2.addActionListener(this);
		jbtChoc3.addActionListener(this);
		jbtChoc4.addActionListener(this);
		jbtChoc5.addActionListener(this);
		jbtChoc6.addActionListener(this);
		jbtBack.addActionListener(this);
		jbtExit.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() ==jbtBack)
		{
	Open frame = new Open();
		frame.setTitle("Menu View");
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		this.dispose();
		}
		if(e.getSource() ==jbtExit)
		{
			int confirm=JOptionPane.showConfirmDialog(null,"Are you sure to exit?","Exit ",JOptionPane.YES_NO_OPTION);
		 	
		 		if(confirm==0)
		 			System.exit(0);
		}
		if(e.getSource() ==jbtChoc1)
		{
				AisBatuCampurInfo frame = new AisBatuCampurInfo();
				frame.setTitle("Ais Batu Campur Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc2)
		{
				AiskrimInfo frame = new AiskrimInfo();
				frame.setTitle("Aiskrim Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc3)
		{
				BananaSplitInfo frame = new BananaSplitInfo();
				frame.setTitle("Banana Split Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc4)
		{
				KekCoklatInfo frame = new KekCoklatInfo();
				frame.setTitle("Kek Coklat Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc5)
		{
				PudingInfo frame = new PudingInfo();
				frame.setTitle("Puding Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc6)
		{
				RojakBuahInfo frame = new RojakBuahInfo();
				frame.setTitle("Rojak Buah Info");
				frame.setSize(450,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
	}
	
	
	public static void main(String[] args)
	{
		Desert frame = new Desert();
		frame.setTitle("Desert");
		frame.setSize(550,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}