import javax.swing.*;
import java.awt.*;

public interface book_master_info_Interface
{
	public JPanel text = new JPanel(); 
    public String campus_list[] = {"Damansara", "KL"};
    public String type_list[] = {"Reference", "Lendable"};
    public String category_list[] = {"OOP", "DCCN", "IM", "BIT"};
    public JTextField book_no_text = new JTextField(20);
    public JTextField book_title_text = new JTextField(20);
	public JTextField isbn_text = new JTextField(20);
	public JTextField class_text = new JTextField(20);
	public JTextField publisher_text = new JTextField(20);
	public JTextField place_text = new JTextField(20);
	public JTextField year_text = new JTextField(20);
	public JTextField pages_text = new JTextField(20);
	public JComboBox campus_combo = new JComboBox(campus_list);
	public JComboBox type_combo = new JComboBox(type_list);
	public JComboBox category_combo = new JComboBox(category_list);
	public JLabel book_master = new JLabel("Book Master");
	public JLabel campus = new JLabel("Campus");
	public JLabel book_no = new JLabel("Book No");
	public JLabel book_title = new JLabel("Book Title");
	public JLabel book_type = new JLabel("Book Type");
	public JLabel book_cat = new JLabel("Book Category");
	public JLabel isbn = new JLabel("ISBN");
	public JLabel class_no = new JLabel("Classification No");
	public JLabel publisher = new JLabel("Publisher");
	public JLabel place = new JLabel("Place Published");
	public JLabel yer = new JLabel("Year");
	public JLabel pages = new JLabel("Pages");
	
}