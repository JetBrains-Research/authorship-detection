import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Splash extends JWindow {

	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();	//Getting the User's Screen Dimensions.

	public Splash () {

		JLabel lbImage    = new JLabel (new ImageIcon ("Splash.jpg"));	//Splash Screen Image.
		Color cl = new Color (0, 0, 0);					//Setting Splash Window Border Color.
		lbImage.setBorder (new LineBorder (cl, 1));			//Setting Splash Window Border.

		getContentPane().add (lbImage, BorderLayout.CENTER);		//Adding the Image Label to Window.
		pack();								//Packing the Splash Window.

		setSize (getSize().width, getSize().height);			//Setting the Size.

		//Setting the Splash Window Position on User's Screen.
		setLocation (d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);

		show();					//Displaying the Splash Screen.

		for (int i = 0; i <= 1000; i++) { }	//Loop for Delay.

		new Logon ();				//Showing the Logon Window of Program.

		toFront();		//Making the Splash Window to Front of Main Window.
		setVisible (false);	//Setting the Splash Window InVisible.
		dispose ();		//Unloading the Window & Release the Memory Resources.

	}

	//Main Function of Program to Execute the Program.

	public static void main (String args[]) {
		
		new Splash ();

	}

}