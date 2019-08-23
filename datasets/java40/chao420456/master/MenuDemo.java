package cn.itcast_07;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * һ���˵�
 */
public class MenuDemo {
	public static void main(String[] args) {
		// �����������
		final Frame f = new Frame("һ���˵�");
		// ���ô�������
		f.setBounds(500, 250, 400, 300);

		// �����˵���
		MenuBar mb = new MenuBar();
		// �����˵�
		Menu m = new Menu("�ļ�");
		// �����˵���
		MenuItem mi = new MenuItem("�˳�ϵͳ");

		// �˵��������
		m.add(mi);
		mb.add(m);

		// ��ӵ�����
		f.setMenuBar(mb);

		// ע���¼�
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		f.setVisible(true);
	}
}
