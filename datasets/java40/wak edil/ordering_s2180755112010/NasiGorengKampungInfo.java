import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class NasiGorengKampungInfo extends JFrame implements ActionListener
{

	private JTextArea jtaInfo=new JTextArea("Nasi Goreng Kampung yang dimasak \nTelur mata kerbau \nAcar Timun atau buah-buahan \nSayur-sayuran \nRamuan daging & ayam ");
	private JLabel lblInfo=new JLabel("Ingredient: ");
	private ImageIcon ABC=new ImageIcon("makanan/nasi goreng kampung.jpeg");
	private JButton jbtBack =new JButton("Back");

    public NasiGorengKampungInfo() 
    {
    	Container cont=getContentPane();
		cont.setLayout(new BorderLayout(5,5));
		cont.setBackground(Color.white);
		
		jbtBack.setToolTipText("Go back to Foods menu");
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(3,1,5,5));
		p1.setBackground(Color.white);
		p1.add(lblInfo);
		p1.add(jtaInfo);
		jtaInfo.setEditable(false);
		//JScrollPane scpInfo=new JScrollPane(jtaInfo);
		p1.add(jtaInfo);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		p2.setBackground(Color.white);
		p2.add(new JLabel(ABC));
		
		JPanel p3=new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		p3.setBackground(Color.white);
		p3.add(jbtBack);
		
		cont.add(p1,BorderLayout.CENTER);
		cont.add(p2,BorderLayout.NORTH);
		cont.add(p3,BorderLayout.SOUTH);
		
		jbtBack.addActionListener(this);
		
    }
    
    public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() ==jbtBack)
		{
			Foods frame = new Foods();
			frame.setTitle("Foods");
			frame.setSize(550,500);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.dispose();
		}
	}
    
    	public static void main(String[] args)
	{
		NasiGorengKampungInfo frame = new NasiGorengKampungInfo();
		frame.setTitle("Nasi Goreng Kampung Info");
		frame.setSize(400,550);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
    
}