import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Kitchen extends JFrame 
{
  // Text area for displaying contents
  private JTextArea jta = new JTextArea();
  

  
  public static void main(String[] args) 
  {
    new Kitchen();
  }
  
  public Kitchen() 
  {
    // Place text area on the frame
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
    
    setTitle("Server");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    

   try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8888);
      jta.append("----------------\n");
      jta.append("KITCHEN\n");
      jta.append("----------------\n");
      
      
      // Listen for a connection request
      Socket connectToClient = serverSocket.accept();
      
      // Create data input and output streams
      DataInputStream isFromClient = new DataInputStream(
        connectToClient.getInputStream());
      DataOutputStream osToClient = new DataOutputStream(
        connectToClient.getOutputStream());
      
      String item="";
      int tableNo=0;
      
      while (true) 
      {
        

        // Receive items from client
        tableNo=isFromClient.readInt();
        item=isFromClient.readUTF();
        
        jta.append("\nTable no "+tableNo+" requested: ");
        jta.append(" [" + new Date() + "]\n");
        jta.append("************************************************\n");
		jta.append(item);
		jta.append("************************************************\n");
		
		//toServer.writeUTF();
	    

      }
    }
    
    catch(IOException ex) 
    {
      System.err.println(ex);
    }
    
    
    
    
  }
}
