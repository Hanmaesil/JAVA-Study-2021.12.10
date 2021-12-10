import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// 기능 : 1. 로그인 2. 회원가입 3. 회원목록보기 4. 회원정보수정 5. 회원탈퇴 6.종료

		Scanner sc = new Scanner(System.in);
		System.out.println("===== 회원관리 시스템 =====");
		MemberDAO dao = new MemberDAO();
		
		while (true) {

			System.out.print("1.로그인 2.회원가입 3.회원목록보기 4.회원정보수정 5.회원탈퇴 6.종료");
			int choice = sc.nextInt();

			if (choice == 1) {
				System.out.println("===== 로그인 ====="); 
				System.out.print("아이디 입력: "); //사용자의 입력값
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				
				dao.login(id, pw);  //입력값을 매개변수로 넣어서 출력.
				
			} else if (choice == 2) {
				System.out.println("===== 회원가입 =====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				System.out.print("닉네임 입력 : ");
				String nick = sc.next();
				
				dao.join(id, pw, nick);

			} else if (choice == 3) { // 회원 목록 보기

			} else if (choice == 4) { // 회원 정보 수정
				System.out.println("===== 회원정보수정 =====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				dao.update(id, pw);

			} else if (choice == 5) {

			} else if (choice == 6) {
				System.out.println("프로그램 종료...");
				break;
			} else {
				System.out.println("정확한 숫자를 다시 입력해주세요");
			}

		}
	}

}
