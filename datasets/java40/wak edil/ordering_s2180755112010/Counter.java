import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Counter extends JFrame 
{
  // Text area for displaying contents
  private JTextArea jta = new JTextArea();
  DataOutputStream toServer;
  DataInputStream fromServer;
  
  public static void main(String[] args) 
  {
    new Counter();
  }
  
  public Counter() 
  {
	// Place text area on the frame
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
    
    setTitle("Counter");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
	
	try 
	{
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(7777);
      jta.append("-------------------------\n");
      jta.append("Cashier DeliFood\n");
      jta.append("-------------------------\n");
      
      // Listen for a connection request
      Socket connectToClient = serverSocket.accept();
      
      // Create data input and output streams
      DataInputStream isFromClient = new DataInputStream(
        connectToClient.getInputStream());
      DataOutputStream osToClient = new DataOutputStream(
        connectToClient.getOutputStream());
	    

	String receipt = "a";
	int tableNo = 0;

	while(true)
	{
		tableNo = isFromClient.readInt();
		receipt = isFromClient.readUTF();
		
		jta.append("Table no "+tableNo+" :\n");
		jta.append("**************************************************************\n");
		jta.append(receipt);
		jta.append("\n**************************************************************\n");
	
	}
	
	
	}
	catch (IOException ex) 
	    {
	      System.err.println(ex);
	    }
		
    
  }
}
