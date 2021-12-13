import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDAO {
	// DAO : DataBase Access Object
	// �����ͺ��̽��� �����ϱ� ���� ��ü�� ���� �� �ִ� Ŭ����.
	Scanner sc = new Scanner(System.in);
	private Connection conn; // �ʵ忡 �־�� ������ ������ �⺻������ null�� ����.
	private PreparedStatement psmt;// fianlly ���� ����ϱ� ���� ���ʿ� �����Ѵ�.
	private ResultSet rs;
	private PreparedStatement afterId; // ������ ���̵� �޴� ��ü ����

	// ����̹� �ε��� Ŀ�ؼ� ��ü�� �������� �޼ҵ�

	private void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";
			conn = DriverManager.getConnection(db_url, db_id, db_pw); // ������θ� conn�̶�� ������ ��´�.

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// database�� ������ �����ִ� �޼ҵ�

	private void close() {

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

	// �α��� ���
	public String login(String id, String pw) {

		getConnection(); // try �ȿ� �־ �ȴ�.
		String nick = null;
		try {
			// JDBC ���
			// 0. JDBC�� ����ϴ� ������Ʈ�� Driver ���� �ֱ�.
			// ->>������Ʈ ��Ŭ�� - build path - configure - library - classpath - add extra jar -
			// ��� ã�� apply
			// 1. ����̹� �ε�(Oracle Driver) ->> ����̹� �����ε�
			// ���� ����ϴ� DBMS�� ����̹��� �ε��ؾ� �Ѵ�.
			// try - catch : ���ܻ���(��ξȿ� ������ ���ų� ��ΰ� Ʋ������ ���)�� ó�����ش�.
			// ���� ����ϴ� ����̹��� ��θ� ��ȣ �ȿ� ���´�.

			// 2. connection ����
			// ����� �ʿ��Ѱ� : �����ͺ��̽��� ��ġ�� �ּ�(url), ������ �� �ִ� ���̵�(id), ������ �� �ִ� ��й�ȣ(pw)

			// 3. SQL�� �ۼ� �� ����
			String sql = "select * from bigmember where id = ? and pw = ?"; // ������!!! ?���Ѵ�. //sqlexception�� �ʿ��ϴ�!

			psmt = conn.prepareStatement(sql); // PreparedStatement : sql���� ������ �κ�(�����ִ� ? ��
												// ��Ī)�� Ŀ�ؼ�(conn)�� ���� ä���ִ� ��Ȱ
			psmt.setString(1, id); // �Ű����� - ��ġ��, �޴°� ->>(?,?,?) �߿��� ù��° ĭ�� id�� �ִ´�.
			psmt.setString(2, pw); // �Ű����� - ��ġ��, �޴°�

			rs = psmt.executeQuery(); // select �� resultSet�� ��ȯ�Ѵ�. (sql result�� �ִ� ǥ����� �ִ� ���� resultset�̶�� �Ѵ�.)
										// // ���̺� �����Ͱ� ������ ���� ��.
			// resultset�� �����Ͱ� �ִٸ� �÷��� ǥ���� ��ĭ ��������.
			if (rs.next()) { // rs.next -> �÷����� ��ĭ �������� ������ �������� �ϰ� true�� ��ȯ �������� ���ٸ� �ȳ������� �ϰ� false ��ȯ�ϴ�
								// �޼ҵ���
				nick = rs.getString("NICK"); // �÷���
			}
		} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
		finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
			close();
		}
		return nick;

	}

	// ȸ������
	public int join(String id, String pw, String nick) {

		getConnection();
		int cnt = 0;
		try {

			// 3. SQL�� �ۼ� �� ����
			String sql = "insert into bigmember values(?,?,?)"; // ������!!! ?���Ѵ�.

			psmt = conn.prepareStatement(sql); // PreparedStatement : sql���� ������ �κ�(�����ִ� ? ��
												// ��Ī)�� Ŀ�ؼ�(conn)�� ���� ä���ִ� ��Ȱ
			psmt.setString(1, id); // �Ű����� - ��ġ��, �޴°� ->>(?,?,?) �߿��� ù��° ĭ�� id�� �ִ´�.
			psmt.setString(2, pw); // �Ű����� - ��ġ��, �޴°�
			psmt.setString(3, nick); // �Ű����� - ��ġ��, �޴°�

			cnt = psmt.executeUpdate(); // ���̺�ȿ� ���� �־��ֱ� ������ ������Ʈ �̴�. // ���̺��� ������ ����� ��
			// psmt.executeUpdate(); -> ����Ÿ���� int�̴�. -->>>insert�� �� Ƚ����ū ��ȯ���ش�.

		} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
		finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
			close();
		}
		return cnt;
	}

	public ArrayList<MemberDTO> selectAll() { // ȸ������ ����

		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>(); // ������ �ѱ����� ������ ���� �����.
		getConnection();

		String sql = "select * from bigmember";

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery(); // ȸ����� ����

			while (rs.next()) {
				String id = rs.getString("id"); // �÷��� �̸�.
				String pw = rs.getString("pw");
				String nick = rs.getString("nick");
				MemberDTO m = new MemberDTO(id, pw, nick); // dto�� ������ ���� �ִ´�.
				list.add(m);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}

	// ȸ������ ����
//	public int update(String id, String pw) {
//
//		getConnection();
//		int cnt = 0;
//
//		try {
//
//			String sql = "select * from bigmember where id = ? and pw = ?";
//
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			psmt.setString(2, pw);
//
//			rs = psmt.executeQuery();
//			
//			if (rs.next()) {
//				String afterid = "update bigmember set id = ? where id = ?"; // sql���� ���湮
//
//				afterId = conn.prepareStatement(afterid); // �ٲ�� �� Ȯ��
//				afterId.setString(1, id); // ?�� �Է�
//				afterId.setString(2, pw); // ?�� �Է�
//
//				cnt = afterId.executeUpdate(); // ����Ǹ� 1�����Ѵ�.
//				
//				
//		} catch (SQLException e) { // �����ͺ��̽��� ���õ� ��� ���ܻ����� SQLException�� �ذ��� �����ϴ�.
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 4. �ڹٿ� �����ͺ��̽����� ������ �����ش�.
//		finally { // ������ �������� finally�� �Ѿ���� �ȴ�.
//			close();
//		}
//	}

	public int delete(String id, String pw) { // ȸ��Ż��

		getConnection();
		int cnt = 0;

		String sql = "delete from bigmember where id = ? and pw = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;

	}

	public int adminUpdate(String change_id, String change_nick) { // �����ڸ�� - ȸ������ ����

		getConnection();
		int cnt = 0;

		String sql = "update bigmember set nick = ? where id = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, change_nick);
			psmt.setString(2, change_id);
			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			close();
		}

		return cnt;
	}

}
