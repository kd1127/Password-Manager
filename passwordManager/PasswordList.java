package passwordManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class PasswordList {
	JFrame frame = new JFrame("PasswordList");
	static TextArea ta = new TextArea(90, 20);

	public PasswordList() {
		frame.setSize(1200, 700);
	}

	public void passwordList() {
		int data_Count;				//	データがない場合、ダイヤログでデータない表示される処理を作る

		frame.setVisible(true);

		frame.setLayout(new BorderLayout());
		JLabel label = new JLabel("PasswordManager");
		label.setFont(new Font("Arial", Font.PLAIN,16));

		JScrollPane jsp = new JScrollPane(ta);

		frame.add(label, BorderLayout.NORTH);
		frame.add(ta, BorderLayout.CENTER);

		PasswordManagement.dataBase(1, null, null, null, null);
	}
}