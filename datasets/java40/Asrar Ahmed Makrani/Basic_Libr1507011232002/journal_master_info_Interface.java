import javax.swing.*;
import java.awt.*;

public interface journal_master_info_Interface
{
	public JPanel text = new JPanel(); 
    public String type_list[] = {"Reference", "Lendable"};
    public String category_list[] = {"INTERNET", "PROGRAMMING", "GAMES", "3D ANIMATIONS", "ENTERTAINMENT"};
    public JTextField journal_no_text = new JTextField(20);
    public JTextField journal_title_text = new JTextField(20);
	public JTextField class_text = new JTextField(20);
    public JTextField issue_number_text = new JTextField(20);
	public JTextField publisher_text = new JTextField(20);
	public JTextField place_text = new JTextField(20);
	public JTextField year_text = new JTextField(20);
	public JTextField pages_text = new JTextField(20);
	public JComboBox type_combo = new JComboBox(type_list);
	public JComboBox category_combo = new JComboBox(category_list);
	public JLabel journal_master = new JLabel("journal Master");
	public JLabel journal_no = new JLabel("journal No");
	public JLabel journal_title = new JLabel("journal Title");
	public JLabel journal_type = new JLabel("journal Type");
	public JLabel journal_cat = new JLabel("journal Category");
	public JLabel issue_number = new JLabel("Journal Issue Number");
	public JLabel class_no = new JLabel("Classification No");
	public JLabel publisher = new JLabel("Publisher");
	public JLabel place = new JLabel("Place Published");
	public JLabel yer = new JLabel("Year");
	public JLabel pages = new JLabel("Pages");
}