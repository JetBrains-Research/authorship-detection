import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


public class Drinks extends JFrame implements ActionListener
{
	private JButton jbtChoc1=new JButton("Sirap");
	private JButton jbtChoc2=new JButton("Bandung");
	private JButton jbtChoc3=new JButton("Teh Tarik ");
	private JButton jbtChoc4=new JButton("Milo");
	private JButton jbtChoc5=new JButton("Nescafe");
	private JButton jbtChoc6=new JButton("Horlick");
	private JButton jbtBack=new JButton("Back");
	private JButton jbtExit=new JButton("Exit");
	private JLabel FoodLabelChoice=new JLabel("Choose your Foods : ");
	private ImageIcon Food1Image=new ImageIcon("minuman/sirap.jpeg");
	private ImageIcon Food2Image=new ImageIcon("minuman/bandung.jpeg");
	private ImageIcon Food3Image=new ImageIcon("minuman/tehtarik.jpeg");
	private ImageIcon Food4Image=new ImageIcon("minuman/milo.jpeg");
	private ImageIcon Food5Image=new ImageIcon("minuman/nescafe.jpeg");
	private ImageIcon Food6Image=new ImageIcon("minuman/horlick.jpeg");


	public Drinks()
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
				SirapInfo frame = new SirapInfo();
				frame.setTitle("Sirap Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc2)
		{
				BandungInfo frame = new BandungInfo();
				frame.setTitle("Bandung Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc3)
		{
				TehTarikInfo frame = new TehTarikInfo();
				frame.setTitle("Teh Tarik Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc4)
		{
				MiloInfo frame = new MiloInfo();
				frame.setTitle("Milo Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc5)
		{
				NescafeInfo frame = new NescafeInfo();
				frame.setTitle("Nescafe Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc6)
		{
				HorlickInfo frame = new HorlickInfo();
				frame.setTitle("Horlick Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
	}
	
	
	public static void main(String[] args)
	{
		Drinks frame = new Drinks();
		frame.setTitle("Drinks");
		frame.setSize(950,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}