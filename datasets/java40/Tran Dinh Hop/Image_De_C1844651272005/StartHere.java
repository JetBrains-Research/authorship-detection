/**
 	*************************************************************************************
	* File: StartHere.java                Date: 12/05/2004		      Version: 1.00 		*
	*-----------------------------------------------------------------------------------*
	* This program is released under the GNU General Public License 2.00. 	              
	* Details of GNU GPL at http://www.opensource.org/licenses/gpl-license.php  		    
	* You must agree to this license before using, copying or modifying this code.								    
	*
 	* Redistribution and use in source and binary forms, with or without
 	* modification, are permitted provided that the following conditions
 	* are met:
 	*
 	* 1. Redistributions of source code must retain the above copyright
 	*    notice, this list of conditions and the following disclaimer.
 	* 2. The source can be used and modified by individual/organizations, 
	*	  ONLY IF the source will not be used 
 	*    for commercial purposes or incorporated into commercial applications.
	*
 	* --------------------------------- WARRANTY --------------------------------- 
	* THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED 
	* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
	* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
	* IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
	* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
	* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
	* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
	* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
	* OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
	* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	*
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;


class StartHere extends Frame{
	private Image image;

	public StartHere(){
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.getImage("Icons\\Welcome.jpg");
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);
		try{
			mediaTracker.waitForID(0);
		}
		catch (InterruptedException ie){
				System.exit(1);
		}
/*		
		
		addWindowListener(new WindowAdapter(){
      		public void windowClosing(WindowEvent e){
        			System.exit(0);
      		}
		});
		setSize(image.getWidth(null), image.getHeight(null));
		setTitle("Welcome to visitors");
		show();
*/
		this.pack();
		this.setTitle("Welcome to visitors");
		this.setSize(image.getWidth(null), image.getHeight(null));
		this.setLocation(150,100);
		this.setVisible(true);
		this.setResizable(false);
		for (int i=0; i<214748364; i++){}
		login();

	}

	public void paint(Graphics graphics){
		graphics.drawImage(image, 0, 0, null);
	}

	public void login(){
			JPasswordField tf = new JPasswordField(10);
	      tf.setEchoChar('*');
			Object[] msg = {"Please enter your PIN: ", tf};
			String input="";
			int result;
			do{ 
	            tf.selectAll();
	            tf.requestFocus();
					result = JOptionPane.showConfirmDialog(this,	msg, "PIN information", 	
									JOptionPane.OK_CANCEL_OPTION,	JOptionPane.PLAIN_MESSAGE);
					if(result == JOptionPane.OK_OPTION) {
						input = tf.getText();
						if(!input.equals("java")) {
								String message1 = "\nYour number entered is incorrect\n"+
														"Please try again!";
				         	JOptionPane.showMessageDialog(this, message1, "PIN information",
				                        JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						System.exit(1);
					}
			}while (!input.equals("java"));	
			this.dispose();	
			new CodecDeskTop();
		}

	public static void main(String[] args){
		new StartHere();
	}
}
