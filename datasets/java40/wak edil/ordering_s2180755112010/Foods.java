import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


public class Foods extends JFrame implements ActionListener
{
	private JButton jbtChoc1=new JButton("Nasi Lemak");
	private JButton jbtChoc2=new JButton("Nasi Beriyani");
	private JButton jbtChoc3=new JButton("Nasi Minyak ");
	private JButton jbtChoc4=new JButton("Nasi Goreng Kampung");
	private JButton jbtChoc5=new JButton("Mee Goreng");
	private JButton jbtChoc6=new JButton("Bihun Goreng");
	private JButton jbtBack=new JButton("Back");
	private JButton jbtExit=new JButton("Exit");
	private JLabel FoodLabelChoice=new JLabel("Choose your Foods : ");
	private ImageIcon Food1Image=new ImageIcon("makanan/nasi lemak.jpeg");
	private ImageIcon Food2Image=new ImageIcon("makanan/nasi beriyani.jpeg");
	private ImageIcon Food3Image=new ImageIcon("makanan/nasi minyak.jpeg");
	private ImageIcon Food4Image=new ImageIcon("makanan/nasi goreng kampung.jpeg");
	private ImageIcon Food5Image=new ImageIcon("makanan/mee goreng.jpeg");
	private ImageIcon Food6Image=new ImageIcon("makanan/bihun goreng.jpeg");


	public Foods()
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
				NasiLemakInfo frame = new NasiLemakInfo();
				frame.setTitle("Nasi Lemak Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc2)
		{
				NasiBeriyaniInfo frame = new NasiBeriyaniInfo();
				frame.setTitle("Nasi Beriyani Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc3)
		{
				NasiMinyakInfo frame = new NasiMinyakInfo();
				frame.setTitle("Nasi Minyak Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc4)
		{
				NasiGorengKampungInfo frame = new NasiGorengKampungInfo();
				frame.setTitle("Nasi Minyak Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc5)
		{
				MeeGorengInfo frame = new MeeGorengInfo();
				frame.setTitle("Mee Goreng Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
		if(e.getSource() ==jbtChoc6)
		{
				BihunGorengInfo frame = new BihunGorengInfo();
				frame.setTitle("Bihun Goreng Info");
				frame.setSize(950,400);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
		}
	}
	
	
	public static void main(String[] args)
	{
		Foods frame = new Foods();
		frame.setTitle("Foods");
		frame.setSize(950,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}