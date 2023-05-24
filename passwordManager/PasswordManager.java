package passwordManager;

import static java.lang.System.*;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class PasswordManager {

	boolean a = true, b = true;
	int countA = 0, countB = 0;

	public void setJudgeA(boolean a) {
		this.a = a;
	}

	public boolean getJudgeA() {
		return a;
	}

	public void setJudgeB(boolean b) {
		this.b = b;
	}

	public boolean getJudgeB() {
		return b;
	}

	public int getCountA() {
		return countA;
	}

	public int getCountB() {
		return countB;
	}

	public void setCountA(int countA) {
		this.countA = countA;
	}

	public void setCountB(int countB) {
		this.countB = countB;
	}

	public static void main(String[] args) throws Exception{
		PasswordCreate pc = new PasswordCreate("ABCDEFGH");
		PasswordManager pm2 = new PasswordManager();
		PasswordList pl = new PasswordList();
		PasswordDelete pd = new PasswordDelete();

		JFrame frame = new JFrame("Password Manager");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 700);

		Panel panel1 = new Panel();
		Panel panel2 = new Panel();
		Panel panel3 = new Panel();
		Panel panel4 = new Panel();
		Panel panel5 = new Panel();
		Panel panel6 = new Panel();
		Panel panel7 = new Panel();

		frame.setLayout(new GridLayout(7, 1));
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel5.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel6.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

		JLabel label = new JLabel("Password Manager");
		label.setFont(new Font("Arial", Font.PLAIN,16));

		Label label2 = new Label("ランダムパスワード");
		JCheckBox cb1 = new JCheckBox();
		JTextField tf1 = new JTextField(30);
		tf1.setEnabled(false);

		Label label3 = new Label("シーザー暗号　　　");
		JCheckBox cb2 = new JCheckBox();
		JTextField tf2 = new JTextField(30);
		tf2.setEnabled(false);

		cb1.addMouseListener(new MouseAdapter() {
			int clicked_CountA = pm2.getCountA();
			boolean clicked_judgeA = pm2.getJudgeA();

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(clicked_judgeA);
				System.out.println(pm2.getJudgeB());
				System.out.println("A" + clicked_CountA);
				clicked_CountA++;

				if((clicked_CountA % 2) == 1) {
					cb2.setEnabled(false);
					tf2.setEnabled(false);
					tf1.setEnabled(true);
					clicked_judgeA = true;
					pm2.setJudgeB(false);
					tf1.setText("25文字以上32文字以下の文字数で入力してください。");
				}
				else if((clicked_CountA % 2) == 0) {
					cb2.setEnabled(true);
					tf1.setEnabled(false);
					clicked_judgeA = false;
					tf1.setText(null);
				}
				pm2.setCountA(clicked_CountA);
			}
		});

		cb2.addMouseListener(new MouseAdapter(){
			int clicked_CountB = 0;
			boolean clicked_judgeB = pm2.getJudgeB();

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(pm2.getJudgeA());	//	検証用
				System.out.println(clicked_judgeB);
				System.out.println("B" + clicked_CountB);

				clicked_CountB++;

				if((clicked_CountB % 2) == 1 ) {
					cb1.setEnabled(false);
					tf1.setEnabled(false);
					tf2.setEnabled(true);
					clicked_judgeB = true;
					pm2.setJudgeA(false);
					tf2.setText("16文字以上32文字以下のパスワードを入力してください。");
				}
				else if((clicked_CountB % 2) == 0) {
					cb1.setEnabled(true);
					tf2.setEnabled(false);
					clicked_judgeB = false;
					tf2.setText(null);
				}
				pm2.setCountB(clicked_CountB);
			}
		});

		Label label4 = new Label("パスワード");
		JTextField jpf = new JTextField(30);

		Label label5 = new Label("id　　　　");
		JTextField tf3 = new JTextField(30);

		Label label6 = new Label("サイト名　");
		JTextField tf4 = new JTextField(30);

		JButton button1 = new JButton("登録");
		JButton button2 = new JButton("クリア");
		JButton button3 = new JButton("パスワードを表示");
		JButton button4 = new JButton("決定");
		JButton button5 = new JButton("データ削除");
		JButton button6 = new JButton("アプリ終了");

		button1.addActionListener(new ActionListener() {		//	登録

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("tf3: " + tf3.getText());
				System.out.println("tf4: " + tf4.getText());
				PasswordManagement.Register(jpf.getText(), tf3.getText(), tf4.getText(), LocalDate.now());
				JOptionPane.showMessageDialog(frame, "データを登録しました。", "PasswordManager", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		button2.addActionListener(new ActionListener() {	//	クリア

			@Override
			public void actionPerformed(ActionEvent e) {
				jpf.setText(null);
				tf3.setText(null);
				tf4.setText(null);
			}
		});

		button3.addActionListener(new ActionListener() {	//	データ表示

			@Override
			public void actionPerformed(ActionEvent e) {
				pl.passwordList();
			}
		});

		button4.addActionListener(new ActionListener() {	//	決定

			@Override
			public void actionPerformed(ActionEvent e) {
				String str;
				String randomPassword;					//	randomCreateクラスからランダム暗号文字列を取得
				String caesar_Password;					//	randomCreateクラスからシーザー暗号文字列を取得
				int word_Count = 0;	//	文字数を取得

				if(cb1.isEnabled()) {
					str = tf1.getText();					//	TF1に入力された文字列を取得

					try {
						word_Count = Integer.parseInt(str);
					} catch (NumberFormatException e1) {
						System.out.println(word_Count + "数値に変換できません。");
					}

					if(word_Count >= 25 && word_Count <= 32) {
						try {
							randomPassword = pc.randomCreate(word_Count);

							JOptionPane.showMessageDialog(frame, "ランダムパスワード生成完了しました。", "PasswordManager",
									JOptionPane.INFORMATION_MESSAGE);
							jpf.setText(randomPassword);
						} catch(Exception ie) {
							JOptionPane.showMessageDialog(frame, "エラー！パスワード生成失敗しました。",
									"PasswordManager", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "25文字以上32文字以下で入力してください。",
								"PasswordManager", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if(cb2.isEnabled()) {
					str = tf2.getText();					//	TF2に入力された文字列を取得

					for(word_Count=0; word_Count<str.length(); word_Count++) {}

					System.out.println(word_Count);

					if(word_Count >= 16 && word_Count <= 32) {
						try {
							caesar_Password = pc.CaesarCipher(str);

							JOptionPane.showMessageDialog(frame, "シーザーパスワード生成完了しました。", "PasswordManager",
									JOptionPane.INFORMATION_MESSAGE);
							jpf.setText(caesar_Password);
						} catch(Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(frame, "エラー！パスワード生成失敗しました。", "PasswordManager",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "16文字以上32文字以下で入力してください。", "PasswordManager",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		button5.addActionListener(new ActionListener() {		//	データ削除

			@Override
			public void actionPerformed(ActionEvent e) {
				pd.passwordDelete();
			}
		});

		button6.addActionListener(new ActionListener() {		//	アプリ終了

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		panel1.add(label);
		frame.add(panel1);
		panel2.add(label2);
		panel2.add(cb1);
		panel2.add(tf1);
		frame.add(panel2);
		panel3.add(label3);
		panel3.add(cb2);
		panel3.add(tf2);
		frame.add(panel3);
		panel4.add(label4);
		panel4.add(jpf);
		frame.add(panel4);
		panel5.add(label5);
		panel5.add(tf3);
		frame.add(panel5);
		panel6.add(label6);
		panel6.add(tf4);
		frame.add(panel6);
		panel7.add(button4);
		panel7.add(button1);
		panel7.add(button2);
		panel7.add(button3);
		panel7.add(button5);
		panel7.add(button6);
		frame.add(panel7);

		frame.setVisible(true);
		out.println("フレームを表示");
	}
}
