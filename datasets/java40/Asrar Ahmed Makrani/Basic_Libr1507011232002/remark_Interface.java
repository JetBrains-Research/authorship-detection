import javax.swing.*;
import java.awt.*;

public interface remark_Interface
{
    public JTextField patron_text = new JTextField(20); 
    public JTextField name_text = new JTextField(20);
    public JTextField passport_text = new JTextField(20);
    public JTextField expiry_date_text = new JTextField(20);
    public JTextField reg_by_text = new JTextField(20);
    public JTextField reg_date_text = new JTextField(20);
    public JLabel patron = new JLabel("Patron Master");
    public JLabel patron_no = new JLabel("Patron No");
    public JLabel name = new JLabel("Name");
    public JLabel salute = new JLabel("Salute");
    public JLabel passport = new JLabel("IC/Passport");
    public JLabel group = new JLabel("Group Type");
    public JLabel status = new JLabel("Status");
    public JLabel expiry_date = new JLabel("Expiry Date");
    public JLabel reg_by = new JLabel("Registered By");
    public JLabel reg_date = new JLabel("Registration Date");
    public String group_str[] = {"   Student   ","   Lecturer   "};
    public String status_str[] = {" Active ", "Disabled"};
    public String salute_str[] = {"   Mr   ", "   Mrs   "};
    public JComboBox group_combo = new JComboBox(group_str);
    public JComboBox status_combo = new JComboBox(status_str);
    public JComboBox salute_combo = new JComboBox(salute_str);
    public JPanel main_panel = new JPanel();
}