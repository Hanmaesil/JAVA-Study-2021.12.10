import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {

	public static void main(String[] args) {
		// ��� : 1. �α��� 2. ȸ������ 3. ȸ����Ϻ��� 4. ȸ���������� 5. ȸ��Ż�� 6.����

		Scanner sc = new Scanner(System.in);
		System.out.println("===== ȸ������ �ý��� =====");

		while (true) {

			System.out.print("1.�α��� 2.ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.����");
			int choice = sc.nextInt();

			if (choice == 1) {
				System.out.println("===== �α��� =====");
				System.out.print("���̵� �Է�: ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				Connection conn = null;
				PreparedStatement psmt = null;// fianlly ���� ����ϱ� ���� ���ʿ� �����Ѵ�.
				ResultSet rs = null;

				try {
					// JDBC ���
					// 0. JDBC�� ����ϴ� ������Ʈ�� Driver ���� �ֱ�.
					// ->>������Ʈ ��Ŭ�� - build path - configure - library - classpath - add extra jar -
					// ��� ã�� apply
					// 1. ����̹� �ε�(Oracle Driver) ->> ����̹� �����ε�
					// ���� ����ϴ� DBMS�� ����̹��� �ε��ؾ� �Ѵ�.
					// try - catch : ���ܻ���(��ξȿ� ������ ���ų� ��ΰ� Ʋ������ ���)�� ó�����ش�.
					Class.forName("oracle.jdbc.driver.OracleDriver"); // ���� ����ϴ� ����̹��� ��θ� ��ȣ �ȿ� ���´�.

					// 2. connection ����
					// ����� �ʿ��Ѱ� : �����ͺ��̽��� ��ġ�� �ּ�(url), ������ �� �ִ� ���̵�(id), ������ �� �ִ� ��й�ȣ(pw)
					String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
					String db_id = "hr";
					String db_pw = "hr";
					conn = DriverManager.getConnection(db_url, db_id, db_pw); // ������θ� conn�̶�� ������ ��´�.

					// 3. SQL�� �ۼ� �� ����
					String sql = "select * from bigmember where id = ? and pw = ?"; // ������!!! ?���Ѵ�.

					psmt = conn.prepareStatement(sql); // PreparedStatement : sql���� ������ �κ�(�����ִ� ? ��
														// ��Ī)�� Ŀ�ؼ�(conn)�� ���� ä���ִ� ��Ȱ
					psmt.setString(1, id); // �Ű����� - ��ġ��, �޴°� ->>(?,?,?) �߿��� ù��° ĭ�� id�� �ִ´�.
					psmt.setString(2, pw); // �Ű����� - ��ġ��, �޴°�

					rs = psmt.executeQuery(); // select �� resultSet�� ��ȯ�Ѵ�. (sql result�� �ִ� ǥ����� �ִ� ���� resultset�̶�� �Ѵ�.)
												// // ���̺� �����Ͱ� ������ ���� ��.
					// resultset�� �����Ͱ� �ִٸ� �÷��� ǥ���� ��ĭ ��������.
					if (rs.next()) { // rs.next -> �÷����� ��ĭ �������� ������ �������� �ϰ� true�� ��ȯ �������� ���ٸ� �ȳ������� �ϰ� false ��ȯ�ϴ�
										// �޼ҵ���
						System.out.println("�α��� ����");
//						System.out.println(rs.getString(1)); //ù��° �÷��� �����Ͷ�!
//						System.out.println(rs.getString("id")); //ID �÷��� �����Ͷ�!
//						System.out.println(rs.getString("PW")); //PW �÷��� �����Ͷ�!
						System.out.println(rs.getString("NICK") + "�� ȯ���մϴ�!"); // NICK �÷��� �����Ͷ�!
					} else {
						System.out.println("�α��� ����");
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
				finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
					try {
						if (rs != null) { // select���� ����ϸ� rs�� �߰��ǰ� ���� �������� ����Ǳ� ������ ���� ���� �ݾ��־�� �Ѵ�.
							rs.close();
						}

						if (psmt != null) { // ������ �ȵǸ� null���� ���⶧���� null���� �ƴҶ��� �ݴ°� ���ǹ����� �ش�. ������ �Ǹ� null���� �ƴϱ� �����̴�.
							psmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else if (choice == 2) {
				System.out.println("===== ȸ������ =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("�г��� �Է� : ");
				String nick = sc.next();

				Connection conn = null;
				PreparedStatement psmt = null;// fianlly ���� ����ϱ� ���� ���ʿ� �����Ѵ�.

				try {
					// JDBC ���
					// 0. JDBC�� ����ϴ� ������Ʈ�� Driver ���� �ֱ�.
					// ->>������Ʈ ��Ŭ�� - build path - configure - library - classpath - add extra jar -
					// ��� ã�� apply
					// 1. ����̹� �ε�(Oracle Driver) ->> ����̹� �����ε�
					// ���� ����ϴ� DBMS�� ����̹��� �ε��ؾ� �Ѵ�.
					// try - catch : ���ܻ���(��ξȿ� ������ ���ų� ��ΰ� Ʋ������ ���)�� ó�����ش�.
					Class.forName("oracle.jdbc.driver.OracleDriver"); // ���� ����ϴ� ����̹��� ��θ� ��ȣ �ȿ� ���´�.

					// 2. connection ����
					// ����� �ʿ��Ѱ� : �����ͺ��̽��� ��ġ�� �ּ�(url), ������ �� �ִ� ���̵�(id), ������ �� �ִ� ��й�ȣ(pw)
					String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
					String db_id = "hr";
					String db_pw = "hr";
					conn = DriverManager.getConnection(db_url, db_id, db_pw); // ������θ� conn�̶�� ������ ��´�.

					// 3. SQL�� �ۼ� �� ����
					String sql = "insert into bigmember values(?,?,?)"; // ������!!! ?���Ѵ�.

					psmt = conn.prepareStatement(sql); // PreparedStatement : sql���� ������ �κ�(�����ִ� ? ��
														// ��Ī)�� Ŀ�ؼ�(conn)�� ���� ä���ִ� ��Ȱ
					psmt.setString(1, id); // �Ű����� - ��ġ��, �޴°� ->>(?,?,?) �߿��� ù��° ĭ�� id�� �ִ´�.
					psmt.setString(2, pw); // �Ű����� - ��ġ��, �޴°�
					psmt.setString(3, nick); // �Ű����� - ��ġ��, �޴°�

					int cnt = psmt.executeUpdate(); // ���̺�ȿ� ���� �־��ֱ� ������ ������Ʈ �̴�. // ���̺��� ������ ����� ��
					// psmt.executeUpdate(); -> ����Ÿ���� int�̴�. -->>>insert�� �� Ƚ����ū ��ȯ���ش�.
					if (cnt > 0) { // ȸ�������� �Ǹ� insert�� ���������� ��ٴ� ���̰� ����Ÿ�� ��Ʈ�� 1�����Ѵ�.
						System.out.println("ȸ������ ����");
					} else {
						System.out.println("ȸ������ ����");
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
				finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
					try {
						if (psmt != null) { // ������ �ȵǸ� null���� ���⶧���� null���� �ƴҶ��� �ݴ°� ���ǹ����� �ش�. ������ �Ǹ� null���� �ƴϱ� �����̴�.
							psmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else if (choice == 3) { // ȸ�� ��� ����

			} else if (choice == 4) { // ȸ�� ���� ����
				System.out.println("===== ȸ���������� =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				Connection conn = null;
				PreparedStatement psmt = null;// fianlly ���� ����ϱ� ���� ���ʿ� �����Ѵ�.
				PreparedStatement afterId = null; //������ ���̵� �޴� ��ü ����
				ResultSet rs = null;

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
					String db_id = "hr";
					String db_pw = "hr";
					conn = DriverManager.getConnection(db_url, db_id, db_pw);

					String sql = "select * from bigmember where id = ? and pw = ?";
					
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, id);
					psmt.setString(2, pw);

					rs = psmt.executeQuery();
					if (rs.next()) {
						System.out.println("�α��� ����");
						System.out.println(rs.getString("NICK") + "�� ȯ���մϴ�!");
						System.out.print("������ ���̵� �Է����ּ��� : ");
						String inputid = sc.next();

						String afterid = "update bigmember set id = ? where id = ?"; //sql���� ���湮

						afterId = conn.prepareStatement(afterid); //�ٲ�� �� Ȯ��
						afterId.setString(1, inputid); //?�� �Է�
						afterId.setString(2, id); //?�� �Է�

						int cnt = afterId.executeUpdate(); //����Ǹ� 1�����Ѵ�.
						
						if (cnt > 0) {
							System.out.println("����Ǿ����ϴ�.");
						} else {
							System.out.println("���� ����");
						}
					} else {
						System.out.println("�α��� ����");
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
				finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
					try {
						if (afterId != null) {
							afterId.close();
						}

						if (rs != null) { // select���� ����ϸ� rs�� �߰��ǰ� ���� �������� ����Ǳ� ������ ���� ���� �ݾ��־�� �Ѵ�.
							rs.close();
						}

						if (psmt != null) { // ������ �ȵǸ� null���� ���⶧���� null���� �ƴҶ��� �ݴ°� ���ǹ����� �ش�. ������ �Ǹ� null���� �ƴϱ� �����̴�.
							psmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else if (choice == 5) {

			} else if (choice == 6) {
				System.out.println("���α׷� ����...");
				break;
			} else {
				System.out.println("��Ȯ�� ���ڸ� �ٽ� �Է����ּ���");
			}

		}
	}

}
