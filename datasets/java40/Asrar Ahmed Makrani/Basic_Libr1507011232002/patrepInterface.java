import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public interface patrepInterface{
	
	public JLabel item_id_label = new JLabel("Patron ID");
	public JLabel item_title_label = new JLabel("Patron Name");
	public JLabel item_type_label = new JLabel("IC/Passport");
	
	public JLabel location_label = new JLabel("Registered");
	public JLabel author_label = new JLabel("Expiry Date");
	
	public JLabel year_label = new JLabel("Status");
	public JLabel subject_label = new JLabel("Address");
	
	public JLabel avail_label = new JLabel("Telephone");
	public JLabel permission_label = new JLabel("Salute");
	
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