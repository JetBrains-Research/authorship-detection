import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class Logon extends JFrame implements ActionListener {

	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();	//Getting the User's Screen Dimensions.

	private JPanel pLog = new JPanel();
	private JLabel lbUser, lbPass;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JButton btnOk, btnCancel;

	private Connection con;		//For Creating the Connection Between Program & Database.
	public String user;		//For Getting the Current User's Name

	public Logon () {

		//Setting Program's Title.

		super ("Muhammad Wasif's LibrarySystem.");

		//Setting the Main Window of Program.

		setIconImage (getToolkit().getImage ("Images/Home.gif"));	//Setting the Program's Icon.
		setSize (275, 150);						//Setting Main Window Size.
		setResizable (false);						//Make it UnResizeable.

		//Closing Code of Main Window.

		addWindowListener (new WindowAdapter () {		//Attaching the WindowListener to Program.
			public void windowClosing (WindowEvent we) {	//Overriding the windowClosing Function.
				setVisible (false);			//Hide the Form.
				dispose();            			//Free the System Resources.
				System.exit (0);        		//Close the Application.
			}
		}
		);

		//Setting the Logon Form Position on User's Screen.

		setLocation (d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);

		//Setting the Layout of Panel.

		pLog.setLayout (null);

		//Setting the Form's Labels.

		lbUser = new JLabel ("Username:");
		lbUser.setForeground (Color.black);
		lbUser.setBounds (20, 15, 75, 25);
	        lbPass = new JLabel ("Password:");
		lbPass.setForeground (Color.black);
        	lbPass.setBounds (20, 50, 75, 25);

		//Setting the Form's TextField & PasswordField.

		txtUser = new JTextField ();
		txtUser.setBounds (100, 15, 150, 25);
		txtPass = new JPasswordField ();
		txtPass.setBounds (100, 50, 150, 25);

		//Setting the Form's Buttons.

		btnOk = new JButton ("OK");
		btnOk.setBounds (20, 90, 100, 25);
		btnOk.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (150, 90, 100, 25);
		btnCancel.addActionListener (this);

		//Adding All the Controls in Panel.

		pLog.add (lbUser);
		pLog.add (lbPass);
		pLog.add (txtUser);
		pLog.add (txtPass);
		pLog.add (btnOk);
		pLog.add (btnCancel);

		//Adding Panel to the Form.

		getContentPane().add (pLog);

		//Opening the Database.

		try {
			Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
			String loc = "jdbc:odbc:Library";
			con = DriverManager.getConnection (loc);
		}
		catch (ClassNotFoundException cnf)  {
			JOptionPane.showMessageDialog (null, "Driver not Loaded...");
			System.exit (0);
		}
		catch (SQLException sqlex) {
 			JOptionPane.showMessageDialog (null, "Unable to Connect to Database...");
 			System.exit (0);
	 	}

		//Showing The Logon Form.

		setVisible (true);

	}

	public void actionPerformed (ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == btnOk) {		//If OK Button Pressed.

			String password = new String (txtPass.getPassword());

			if (txtUser.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Provide Username to Logon.");
				txtUser.requestFocus();
			}
			else if (password.equals ("")) {
				txtPass.requestFocus();
				JOptionPane.showMessageDialog (null,"Provide Password to Logon.");
			}
			else {
				String pass;			//To Hold the Password.
				boolean verify = false;		//To Confirm Logon.

	 			try {	//SELECT Query to Retrieved the Record.
 					String query = "SELECT * FROM Users WHERE Username = '" + txtUser.getText() + "'";

 					Statement st = con.createStatement ();		//Creating Statement Object.
		 			ResultSet rs = st.executeQuery (query);		//Executing the Query.
					rs.next();					//Moving Towards the Record.
 					user = rs.getString ("Username");		//Storing UserName.
 					pass = rs.getString ("Password");		//Storing Password.

 					if (txtUser.getText().equals (user) && password.equals (pass)) {//If Found then.
						verify = true;
						new LibrarySystem (user, con);	//Show Main Form.
						setVisible (false);		//Hide the Form.
						dispose();            		//Free the System Resources.
					}
					else {
						verify = false;
						JOptionPane.showMessageDialog (this, "Incorrect Information Provided.");
						txtUser.setText ("");
						txtPass.setText ("");
						txtUser.requestFocus ();
					}
				}
				catch (SQLException sqlex) {
					if (verify == false) {
						JOptionPane.showMessageDialog (this, "Incorrect Information Provided.");
						txtUser.setText ("");
						txtPass.setText ("");
						txtUser.requestFocus ();
					}
				}
			}

		}
		else if (obj == btnCancel) {		//If Cancel Button Pressed Unload the From.

			setVisible (false);
			dispose();
			System.exit (0);

		}

	}

}	