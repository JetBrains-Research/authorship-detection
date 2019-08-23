/* Graphics Studio v1.0 By Max Lynch 
 * 
 * A graphics application with similar features
 * as a professional vector graphics application.
 */



package com.gs.main;


//Main class in the GraphicsStudio application, 
//creates an instance of the Controlling GraphicsFrame class
  

import java.io.*;

import javax.swing.plaf.*;
import javax.swing.*;

import com.gs.ui.*;


public class GraphicsStudio {

	//Starting point for whole application
	public static void main(String args[]) throws ClassNotFoundException, 
												  InstantiationException, 
												  IllegalAccessException,
												  UnsupportedLookAndFeelException {
		System.out.println("Starting Graphics Studio v0.1...");
			//Create the opening splash window, window is shown within SplashWindow class
			//set Look and Feel, if look and feel is motif, you really dont want
			//That dumb ass shit, so we make it the normal java l+f
			if(UIManager.getSystemLookAndFeelClassName().equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel"))
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			else
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			SplashWindow splash = new SplashWindow();
		//try{
			GraphicsFrame studio = GraphicsFrame.instance();
			//When instance is made and App is loaded, close splash Window
			splash.close();
		/*} catch(Exception e) {
			System.out.println ("Shitttttttttttttttttttt");
			try {
				
				
				FileWriter fw = new FileWriter("Shii.txt");
				PrintWriter pw = new PrintWriter(fw);
			
				e.printStackTrace(pw);
			} catch(IOException ie) { System.out.println("Shitttt"); }
		} */


	}
}