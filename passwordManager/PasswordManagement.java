package passwordManager;

import static java.lang.System.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PasswordManagement {
	int delete_choice;		//	一項目削除か全部削除か選ぶ
	int choice;				//	データを削除するか否か
	int count;				//	データベースの件数が何件あるかを格納する変数

	static boolean flag = false;

	PasswordList pl = new PasswordList();

	public static void Register(String password, String id, String homePage, LocalDate date) {
		dataBase(2, password, id, homePage, date);
	}

	public static int dataBase(int dbOperation, String password, String id, String homepage_name, LocalDate date) {
		int data_Count = 0;
		String str, str2, str3;

		try {
			Class.forName("org.h2.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("ドライバのロードに失敗しました。");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "kd", "");

			if(dbOperation == 1) {				//	参照
				pstmt = con.prepareStatement("SELECT * FROM PASSWORD_LIST");

				rs = pstmt.executeQuery();

				if(flag == false) {
					while(rs.next()) {
						str = "Password " + rs.getString("PASSWORD");
						str2 = "  ID or MailAddress " + rs.getString("ID")	+ "   ホームページ名 ";
						str3 = rs.getString("HOMEPAGE_NAME") + "  最終更新日 " + rs.getString("UPDATE") + "\n\n";
						data_Count++;

						PasswordList.ta.append(str);
						PasswordList.ta.append(str2);
						PasswordList.ta.append(str3);
						out.println(str + str2 + str3);
						flag = true;
					}
				}

				if(data_Count == 0) {		//	ダイヤログでデータがない旨を表示する処理を後で作る
					out.println("表示できるデータがありません。\n");
				}

				pstmt.close();
				rs.close();
			}
			else if(dbOperation == 2) {		//	更新
				 sql = "INSERT INTO PASSWORD_LIST VALUES('"+password+"', '"+id+"', '"+homepage_name+"', '"+date+"')";

				pstmt = con.prepareStatement(sql);

				int rowscount = pstmt.executeUpdate();

				out.println("パスワード：" + password + "  ID：" + id + "  ホームページ名：" + homepage_name + " 最終更新日：" + date);
				out.println("以上" + rowscount + "件のデータを追加しました。");

				pstmt.close();
			}
			else if(dbOperation == 3) {		//	削除
				out.println(password);
				if(password == null) {			//	全件削除
					sql = "TRUNCATE table PASSWORD_LIST";
					pstmt = con.prepareStatement(sql);
					out.println("null");
				}
				else {							//	1件削除
					sql = "DELETE FROM PASSWORD_LIST WHERE PASSWORD = '"+password+"' AND UPDATE = '"+date+"'";
					pstmt = con.prepareStatement(sql);
					out.println(sql);
				}

				int delete_count = pstmt.executeUpdate();

				out.println(delete_count + "件のデータを削除しました。");

				pstmt.close();

				return delete_count;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return data_Count;
	}
}