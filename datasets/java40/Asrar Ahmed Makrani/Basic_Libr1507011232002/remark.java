import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class remark extends JPanel implements remark_Interface{

    public remark() {
    	
      patron.setFont (new Font ("Arial", Font.BOLD, 20));


    	main_panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		main_panel.add(patron, gbc);
		gbc.gridy = 1;
		main_panel.add(patron_no, gbc);
		gbc.gridy = 2;
		main_panel.add(name, gbc);
		gbc.gridy = 3;
		main_panel.add(passport, gbc);
		gbc.gridy = 4;
		main_panel.add(group, gbc);
		gbc.gridy = 5;
		main_panel.add(status, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		main_panel.add(patron_text, gbc);
		gbc.gridy = 2;
		main_panel.add(name_text, gbc);
		gbc.gridy = 3;
		main_panel.add(passport_text, gbc);
		gbc.gridy = 4;
		main_panel.add(group_combo, gbc);
		gbc.gridy = 5;
		main_panel.add(status_combo, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		main_panel.add(salute, gbc);
		gbc.gridy = 3;
		main_panel.add(expiry_date, gbc);
		gbc.gridy = 4;
		main_panel.add(reg_by, gbc);
		gbc.gridy = 5;
		main_panel.add(reg_date, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		main_panel.add(salute_combo, gbc);
		gbc.gridy = 3;
		main_panel.add(expiry_date_text, gbc);
		gbc.gridy = 4;
		main_panel.add(reg_by_text, gbc);
		gbc.gridy = 5;
		main_panel.add(reg_date_text, gbc);
		setBackground(Color.blue);
		add(main_panel);   	
    }
    
    }
