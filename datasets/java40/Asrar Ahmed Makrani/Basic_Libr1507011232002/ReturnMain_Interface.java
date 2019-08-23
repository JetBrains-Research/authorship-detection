import javax.swing.*;
import java.awt.*;

public interface ReturnMain_Interface
{
    public JPanel rtn_ent = new JPanel(); 
    public JPanel pat_info = new JPanel();
    public JPanel bk_info = new JPanel();
    public JPanel pan = new JPanel();
    public JPanel pan2 = new JPanel();
    public JPanel main_panel = new JPanel();
    public JLabel patron_info = new JLabel("Patron Info");
    public JLabel patron_no = new JLabel("Patron No");
    public JLabel name = new JLabel("Name");
    public JLabel passport = new JLabel("IC/Passport");
    public JLabel group_type = new JLabel("Group Type");
    public JLabel expiry_date = new JLabel("Expiry Date");
    public JLabel status = new JLabel("Status");
    public JLabel loan_duration = new JLabel("Loan Duration");
    public JLabel outstanding_fine = new JLabel("Outstanding Fine");
    public JLabel book_no = new JLabel("Item No");
    public JLabel book_title = new JLabel("Title");
    public JLabel book_type = new JLabel("Type");
    public JLabel book_category = new JLabel("Category");
    public JLabel author = new JLabel("Author");
    public JLabel publisher = new JLabel("Publisher");
    public JLabel subject = new JLabel("Subject");
    public JLabel location = new JLabel("Location");
    public JLabel book_info = new JLabel("Item Info");
    public JTextField patron_no_text = new JTextField(20);
    public JTextField name_text = new JTextField(20);
    public JTextField passport_text = new JTextField(20);
    public JTextField group_type_text = new JTextField(20);
    public JTextField expiry_date_text = new JTextField(20);
    public JTextField status_text = new JTextField(20);
    public JTextField loan_duration_text = new JTextField(20);
    public JTextField outstanding_fine_text = new JTextField(20);
    public JTextField book_no_text = new JTextField(20);
    public JTextField book_title_text = new JTextField(20);
    public JTextField book_type_text = new JTextField(20);
    public JTextField book_category_text = new JTextField(20);
    public JTextField author_text = new JTextField(20);
    public JTextField publisher_text = new JTextField(20);
    public JTextField subject_text = new JTextField(20);
    public JTextField location_text = new JTextField(20);
    
    public JLabel book_rtn_entry = new JLabel("Item Return Entry");
    	public JLabel book_ac_no = new JLabel("Item Acc No.");
    	public JLabel rent_date = new JLabel("Rent Date");
    	public JLabel rent_due_date = new JLabel("Rent Due Date");
    	public JLabel tag = new JLabel("Tag");
    	public JLabel day_overdue = new JLabel("Days Overdue");
    	public JLabel fine_collection = new JLabel("Fine Collection");
    	public JLabel amount_fine = new JLabel("Amount Fine");
    	public JLabel amount_collected = new JLabel("Amount Collected");
    	public JTextField book_ac_no_text = new JTextField(10);
    	public JTextField rent_date_text = new JTextField(10);

    	public JTextField rent_due_date_text = new JTextField(10);
    	public JTextField tag_text = new JTextField(10);
    	public JTextField day_overdue_text = new JTextField(10);
    	public JTextField amount_fine_text = new JTextField(10);
    	public JTextField amount_collected_text = new JTextField(10);
    	
    	public JTextField yeah = new JTextField("a");
    	
    	public ImageIcon icon = new ImageIcon("");
	
}