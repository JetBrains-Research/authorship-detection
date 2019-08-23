import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public interface reportInterface{
	
	public JLabel item_id_label = new JLabel("Item ID");
	public JLabel item_title_label = new JLabel("Item Title");
	public JLabel item_type_label = new JLabel("Item Type");
	
	public JLabel location_label = new JLabel("Location");
	public JLabel author_label = new JLabel("Author");
	
	public JLabel year_label = new JLabel("Year");
	public JLabel subject_label = new JLabel("Subject Area");
	
	public JLabel avail_label = new JLabel("Availability");
	public JLabel permission_label = new JLabel("Permissions");
	
	public JLabel print_label = new JLabel("Print Layout");
	
	
	public JTextField item_id_text = new JTextField(5);
	public JTextField item_title_text = new JTextField(20);
	public JTextField item_type_text = new JTextField(10);
	
	public JTextField location_text = new JTextField(20);
	public JTextField author_text = new JTextField(10);
	
	public JTextField year_text = new JTextField(5);
	public JTextField subject_text = new JTextField(20);
	
	public JTextField avail_text = new JTextField(5);
	public JTextField permission_text = new JTextField(10);
	
	public JTextArea textArea = new JTextArea(20,10);
	
	
}