import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

//The Following Class Use to Display Help of Program. You Can Show Your Program's Help too. Just Provide the Help Form
//Title & HTML FileName With Full Path to the Constructor of this Class. If HTML File is in Same Folder then Just Provide
//the Name of the HTML File with its Extension.

public class LibraryHelp extends JInternalFrame {

	//Constructor of Class.

	public LibraryHelp (String title, String filename) {

		//super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super (title, true, true, true, true);
		setSize (500, 350);

		HtmlPane html = new HtmlPane (filename);	//Providing the HTML File.
		setContentPane (html);				//Setting the ContentPane of Form to Show HTML File.

		setVisible (true);

	}

}

//Following Class Show HTML Help inside an EditorPane.

class HtmlPane extends JScrollPane implements HyperlinkListener {

	JEditorPane html;

	public HtmlPane (String filename) {

		try {
			File f = new File (filename);		//Getting the HTML File.
			String s = f.getAbsolutePath();		//Getting the AbsolutePath of File.
			s = "file:" + s;
			URL url = new URL (s);			//Setting URL.
			html = new JEditorPane (s);		//EditorPane to Display File Contents.
			html.setEditable (false);		//Set EditorPane Disabled So User only View the File.
			html.addHyperlinkListener (this);	//Adding the HyperLink Listener.
			JViewport vp = getViewport();		//Creating ViewPort to View the File.
			vp.add (html);				//Adding EditorPane to ViewPort.
		}
		catch (MalformedURLException e) { }
		catch (IOException e) {	}

	}

	//Function Perform By the HyperLinks of HTML Help File.

	public void hyperlinkUpdate (HyperlinkEvent e) {
	
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			linkActivated (e.getURL());
		}
	}

	//Function for Loading other HTML Pages in Your HTML Help.

	protected void linkActivated (URL u) {

		Cursor c = html.getCursor();
		Cursor waitCursor = Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR);
		html.setCursor (waitCursor);
		SwingUtilities.invokeLater (new PageLoader (u, c));

	}

	//Following Class Load Other Pages Included in Our HTML Help File.

	class PageLoader implements Runnable {

		//Constructor of Class.

		PageLoader (URL u, Cursor c) {

			url = u;
			cursor = c;

		}

		public void run () {
	
			if (url == null) {
				html.setCursor (cursor);
				Container parent = html.getParent();
				parent.repaint();
			}
			else {
				Document doc = html.getDocument();
				try {
					html.setPage (url);
				}
				catch (IOException ioe) {
					html.setDocument (doc);
					getToolkit().beep();
				}
				finally {
					url = null;
					SwingUtilities.invokeLater (this);
				}
			}

		}

		URL url;
		Cursor cursor;

	}

}