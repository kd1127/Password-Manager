package passwordManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PasswordCreate{
	private List<Character> alphabetB = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
				  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
	private List<Character> alphabetS = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

	private List<Character> numbers = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	private List<Character> mark = new ArrayList<Character>(Arrays.asList('.', '-', '_'));

	private String passwordCreate;		//	生成パスワード格納
	private String Caesar;				//	シーザー暗号
	char[] str = new char[221];
	char strC[];						//	シーザー暗号化前の平文を入れる
	char str_Body[];					//	 "            後の暗号文を入れる

	int r, bs, kinds;				//	乱数取得変数
	int num_Positioning = 0, apb_Positioning = 0, apbs_Positioning = 0;		//	位置決め変数
	int mark_positioning;													//	同↑

	Random random = new Random();

	//	インスタンス生成時にデフォルトパスワードを設定する
	public PasswordCreate(String passwordCreate) {
		this.passwordCreate = passwordCreate;
	}


	//	ランダムパスワード方式　英字・数字・記号の中からランダムでパスワードを生成
	public String randomCreate(int wCount) throws IOException{
		for(int i=0; i<wCount; i++) {
			kinds = random.nextInt(3);

			if(kinds == 0) {					//	英字
				r = random.nextInt(26);
				bs = random.nextInt(2);
				if(bs == 0) {					//	大文字
					str[i] = alphabetB.get(r);
				}
				else if(bs == 1) {				//	小文字
					str[i] = alphabetS.get(r);
				}
			}
			else if(kinds == 1) {				//	数字
				r = random.nextInt(10);
				str[i] = (char)(r + '0');
			}
			else {								//	記号
				r = random.nextInt(3);
				str[i] = mark.get(r);
			}
		}

		passwordCreate = new String(str);
		return passwordCreate;
	}

	//	シーザー暗号方式
	public String CaesarCipher(String rCaesar) throws Exception{

		strC = new char[rCaesar.length()];
		str_Body = new char[rCaesar.length()];

		for(int i=0; i<rCaesar.length(); i++) {
			strC[i] = rCaesar.charAt(i);

			if(strC[i] >= '0' && strC[i] <= '9') {
				for(num_Positioning=0; numbers.get(num_Positioning) != strC[i]; num_Positioning++) {
					if(num_Positioning == 10) {
						break;
					}
				}
			}
			if(strC[i] >= 'A' && strC[i] <= 'Z') {
				for(apb_Positioning=0; alphabetB.get(apb_Positioning) != strC[i]; apb_Positioning++) {
					if(apb_Positioning == 26) {
						break;
					}
				}
			}
			if(strC[i] >= 'a' && strC[i] <= 'z') {
				for(apbs_Positioning = 0; strC[i] != alphabetS.get(apbs_Positioning); apbs_Positioning++) {
					if(apbs_Positioning == 26) {
						break;
					}
				}
			}
			if(strC[i] == '.' || strC[i] == '-' || strC[i] == '_') {
				for(mark_positioning=0; mark.get(mark_positioning) != strC[i]; mark_positioning++) {
					if(mark_positioning == 3) {
						break;
					}
				}
			}

			if(strC[i] == alphabetS.get(apbs_Positioning)) {
				apb_Positioning = 5;
				num_Positioning = 5;
			}

			if(strC[i] == alphabetB.get(apb_Positioning)) {
				apbs_Positioning = 5;
				num_Positioning = 5;
			}

			if(strC[i] == '.' || strC[i] == '-' || strC[i] == '_') {
				apbs_Positioning = 5;
				apb_Positioning = 5;
				num_Positioning = 5;
			}

			if(strC[i] >= 'A' && strC[i] <= 'Z') {
				if(strC[i] == 'A') {
					str_Body[i] = alphabetB.get(25-2);
				}
				else if(strC[i] == 'B') {
					str_Body[i] = alphabetB.get(25-1);
				}
				else if(strC[i] == 'C') {
					str_Body[i] = alphabetB.get(25);
				}
				else {
					apb_Positioning = apb_Positioning - 3;
					str_Body[i] = alphabetB.get(apb_Positioning);
				}
			}

			if(strC[i] >= 'a' && strC[i] <= 'z') {
				if(strC[i] == 'a') {
					str_Body[i] = alphabetS.get(25-2);
				}
				else if(strC[i] == 'b') {
					str_Body[i] = alphabetS.get(25-1);
				}
				else if(strC[i] == 'c') {
					str_Body[i] = alphabetS.get(25);
				}
				else {
					apbs_Positioning = apbs_Positioning - 3;
					str_Body[i] = alphabetS.get(apbs_Positioning);
				}
			}

			if(strC[i] >= '0' && strC[i] <= '9') {
				if(strC[i] == '0') {
					str_Body[i] = numbers.get(7);
				}
				else if(strC[i] == '1') {
					str_Body[i] = numbers.get(8);
				}
				else if(strC[i] == '2') {
					str_Body[i] = numbers.get(9);
				}
				else {
					num_Positioning = num_Positioning - 3;
					str_Body[i] = numbers.get(num_Positioning);
				}
			}

			if(strC[i] == '.' || strC[i] == '-' || strC[i] == '_') {
				str_Body[i] = mark.get(mark_positioning);
			}
		}

		Caesar = new String(str_Body);

		return Caesar;
	}
}