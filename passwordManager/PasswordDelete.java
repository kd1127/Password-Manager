package passwordManager;

import static java.lang.System.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PasswordDelete {
	JFrame frame = new JFrame();
	PasswordManagement pm = new PasswordManagement();

	int count = 0;

	public PasswordDelete() {
		frame.setTitle("データ削除");
		frame.setSize(600, 300);
	}

	public void passwordDelete() throws DateTimeParseException{
		Panel panel = new Panel();
		Panel panel2 = new Panel();
		Panel panel3 = new Panel();

		frame.setLayout(new GridLayout(3, 1));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel label = new JLabel("削除するパスワードを入力してください");
		JTextField jtf = new JTextField(40);
		
		JLabel label2 = new JLabel("次に、最終更新日を入力してください。");
		JTextField jtf2 = new JTextField(40);

		JButton button = new JButton("入力パスワード削除");
		JButton button2 = new JButton("全件削除");
		
		button.addActionListener(new ActionListener() {		//	入力パスワード削除

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = jtf2.getText();
				out.println("最終更新日：" + jtf2.getText());
				LocalDate ld = LocalDate.parse(jtf2.getText());
				
				count = PasswordManagement.dataBase(3, jtf.getText(), null, null, ld);

				if(count == 0) {
					JOptionPane.showMessageDialog(frame, "データを削除できませんでした。", "PasswordManager", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "データを削除しました。", "PasswordManager", JOptionPane.INFORMATION_MESSAGE);
				}
				out.println(jtf.getText());
			}
		});

		button2.addActionListener(new ActionListener() {	//	全件削除
			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordManagement.dataBase(3, null, null, null, null);

				JOptionPane.showMessageDialog(frame, "データを全件削除しました。", "PasswordManager", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		panel.add(label);
		panel2.add(jtf);
		panel2.add(label2);
		panel2.add(jtf2);
		panel3.add(button);
		panel3.add(button2);
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel3);

		frame.setVisible(true);
	}
}