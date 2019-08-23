import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PudingInfo extends JFrame implements ActionListener
{

	private JTextArea jtaInfo=new JTextArea("Ais batu yang di kisar \nAir gula hitam & merah \nSatu sudu Cendul & Jelly \nSatu sudu campuran Kacang \n  ");
	private JLabel lblInfo=new JLabel("Ingredient: ");
	private ImageIcon ABC=new ImageIcon("desert/puding.jpeg");
	private JButton jbtBack =new JButton("Back");

    public PudingInfo() 
    {
    	Container cont=getContentPane();
		cont.setLayout(new BorderLayout(5,5));
		cont.setBackground(Color.white);
		
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(3,1,5,5));
		p1.setBackground(Color.white);
		p1.add(lblInfo);
		p1.add(jtaInfo);
		jtaInfo.setEditable(false);
		JScrollPane scpInfo=new JScrollPane(jtaInfo);
		p1.add(scpInfo);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		p2.add(new JLabel(ABC));
		
		JPanel p3=new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
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
			Desert frame = new Desert();
			frame.setTitle("Desert");
			frame.setSize(550,400);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.dispose();
		}
	}
    
    	public static void main(String[] args)
	{
		PudingInfo frame = new PudingInfo();
		frame.setTitle("Puding Info");
		frame.setSize(450,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
    
}