import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDAO {
	// DAO : DataBase Access Object
	// 데이터베이스에 접근하기 위한 객체를 만들 수 있는 클래스.
	Scanner sc = new Scanner(System.in);
	private Connection conn; // 필드에 넣어둔 참조형 변수는 기본값으로 null이 들어간다.
	private PreparedStatement psmt;// fianlly 에서 사용하기 위해 이쪽에 생성한다.
	private ResultSet rs;
	private PreparedStatement afterId; // 변경할 아이디를 받는 객체 생성

	// 드라이버 로딩과 커넥션 객체를 가져오는 메소드

	private void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";
			conn = DriverManager.getConnection(db_url, db_id, db_pw); // 연결통로를 conn이라는 변수에 담는다.

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// database와 연결을 끊어주는 메소드

	private void close() {

		try {
			if (afterId != null) {
				afterId.close();
			}
			if (rs != null) { // select문을 사용하면 rs가 추가되고 제일 마지막에 실행되기 때문에 제일 먼저 닫아주어야 한다.
				rs.close();
			}

			if (psmt != null) { // 실행이 안되면 null값이 들어가기때문에 null값이 아닐때만 닫는걸 조건문으로 준다. 실행이 되면 null값이 아니기 때문이다.
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

	// 로그인 기능
	public String login(String id, String pw) {

		getConnection(); // try 안에 있어도 된다.
		String nick = null;
		try {
			// JDBC 사용
			// 0. JDBC를 사용하는 프로젝트에 Driver 파일 넣기.
			// ->>프로젝트 우클릭 - build path - configure - library - classpath - add extra jar -
			// 경로 찾고 apply
			// 1. 드라이버 로딩(Oracle Driver) ->> 드라이버 동적로딩
			// 내가 사용하는 DBMS의 드라이버를 로딩해야 한다.
			// try - catch : 예외사항(경로안에 파일이 없거나 경로가 틀렸을때 등등)을 처리해준다.
			// 내가 사용하는 드라이버의 경로를 괄호 안에 적는다.

			// 2. connection 연결
			// 연결시 필요한것 : 데이터베이스가 위치한 주소(url), 접근할 수 있는 아이디(id), 접근할 수 있는 비밀번호(pw)

			// 3. SQL문 작성 및 실행
			String sql = "select * from bigmember where id = ? and pw = ?"; // 무조건!!! ?로한다. //sqlexception이 필요하다!

			psmt = conn.prepareStatement(sql); // PreparedStatement : sql문의 부족한 부분(위에있는 ? 를
												// 지칭)을 커넥션(conn)을 통해 채워주는 역활
			psmt.setString(1, id); // 매개변수 - 위치값, 받는값 ->>(?,?,?) 중에서 첫번째 칸에 id를 넣는다.
			psmt.setString(2, pw); // 매개변수 - 위치값, 받는값

			rs = psmt.executeQuery(); // select 는 resultSet을 반환한다. (sql result에 있는 표현대로 있는 값을 resultset이라고 한다.)
										// // 테이블에 데이터가 변하지 않을 때.
			// resultset은 데이터가 있다면 컬럼명 표에서 한칸 내려간다.
			if (rs.next()) { // rs.next -> 컬럼명에서 한칸 내려갈수 있으면 내려가게 하고 true를 반환 내려갈수 없다면 안내려가게 하고 false 반환하는
								// 메소드기능
				nick = rs.getString("NICK"); // 컬럼명
			}
		} catch (SQLException e) { // 데이터베이스에 관련된 모든 예외사항은 SQLException로 해결이 가능하다.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 4. 자바와 데이터베이스간의 연결을 끊어준다.
		finally { // 무조건 마지막에 finally로 넘어오게 된다.
			close();
		}
		return nick;

	}

	// 회원가입
	public int join(String id, String pw, String nick) {

		getConnection();
		int cnt = 0;
		try {

			// 3. SQL문 작성 및 실행
			String sql = "insert into bigmember values(?,?,?)"; // 무조건!!! ?로한다.

			psmt = conn.prepareStatement(sql); // PreparedStatement : sql문의 부족한 부분(위에있는 ? 를
												// 지칭)을 커넥션(conn)을 통해 채워주는 역활
			psmt.setString(1, id); // 매개변수 - 위치값, 받는값 ->>(?,?,?) 중에서 첫번째 칸에 id를 넣는다.
			psmt.setString(2, pw); // 매개변수 - 위치값, 받는값
			psmt.setString(3, nick); // 매개변수 - 위치값, 받는값

			cnt = psmt.executeUpdate(); // 테이블안에 값을 넣어주기 때문에 업데이트 이다. // 테이블의 내용이 변경될 때
			// psmt.executeUpdate(); -> 리턴타입이 int이다. -->>>insert가 된 횟수만큰 반환해준다.

		} catch (SQLException e) { // 데이터베이스에 관련된 모든 예외사항은 SQLException로 해결이 가능하다.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 4. 자바와 데이터베이스간의 연결을 끊어준다.
		finally { // 무조건 마지막에 finally로 넘어오게 된다.
			close();
		}
		return cnt;
	}

	public ArrayList<MemberDTO> selectAll() { // 회원정보 보기

		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>(); // 정보를 한군데에 모으기 위해 만든다.
		getConnection();

		String sql = "select * from bigmember";

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery(); // 회원목록 보기

			while (rs.next()) {
				String id = rs.getString("id"); // 컬럼의 이름.
				String pw = rs.getString("pw");
				String nick = rs.getString("nick");
				MemberDTO m = new MemberDTO(id, pw, nick); // dto에 정보를 집어 넣는다.
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

	// 회원정보 수정
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
//				String afterid = "update bigmember set id = ? where id = ?"; // sql에서 변경문
//
//				afterId = conn.prepareStatement(afterid); // 바뀌는 값 확인
//				afterId.setString(1, id); // ?에 입력
//				afterId.setString(2, pw); // ?에 입력
//
//				cnt = afterId.executeUpdate(); // 변경되면 1증가한다.
//				
//				
//		} catch (SQLException e) { // 데이터베이스에 관련된 모든 예외사항은 SQLException로 해결이 가능하다.
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 4. 자바와 데이터베이스간의 연결을 끊어준다.
//		finally { // 무조건 마지막에 finally로 넘어오게 된다.
//			close();
//		}
//	}

	public int delete(String id, String pw) { // 회원탈퇴

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

	public int adminUpdate(String change_id, String change_nick) { // 관리자모드 - 회원정보 수정

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
