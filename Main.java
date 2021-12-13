import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
				System.out.print("아이디 입력: "); // 사용자의 입력값
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				String nick = dao.login(id, pw); // 입력값을 매개변수로 넣어서 출력.

				if (nick != null) {
					System.out.println("로그인 성공");
					System.out.println(nick + "님 환영합니다!");
					
					if(id.equals("admin")) {  //관리자 기능
						System.out.print("1. 회원정보 수정 2. 회원삭제 >>>>");
						choice = sc.nextInt();
						
						if(choice == 1) {
							System.out.println("===== 관리자 회원정보수정 =====");
							System.out.print("아이디 입력 : ");
							String change_id = sc.next();
							System.out.print("변경할 닉네임 입력 : ");
							String change_nick = sc.next();
							
							int cnt = dao.adminUpdate(change_id, change_nick);
							if(cnt > 0) {
								System.out.println("회원정보 수정 완료");
							}else {
								System.out.println("회원정보 수정 실패");
							}
							
							
							
						}else if(choice == 2) {
							
						}
						
						
					}
					
					
				} else {
					System.out.println("로그인 실패");
				}

			} else if (choice == 2) {
				System.out.println("===== 회원가입 =====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				System.out.print("닉네임 입력 : ");
				String nick = sc.next();

				int cnt = dao.join(id, pw, nick);

				if (cnt > 0) {
					System.out.println("회원가입 성공");
				} else {
					System.out.println("회원가입 성공");
				}

			} else if (choice == 3) { // 회원 목록 보기

				System.out.println("=====회원목록보기=====");
				ArrayList<MemberDTO> list = dao.selectAll();
				
//				System.out.println(list.toString());
//				System.out.println();
				
				for(int i = 0; i< list.size(); i++) {
					System.out.print(list.get(i).getId() + "-");
					System.out.print(list.get(i).getPw()+ "-");
					System.out.print(list.get(i).getNick());
					System.out.println();
				}
				
//				for(int i = 0; i < list.size(); i++) {
//					System.out.println(list.get(i));
//				}

			} else if (choice == 4) { // 회원 정보 수정 //다시 만들기 ㅠㅠㅠㅠㅠ
				System.out.println("===== 회원정보수정 =====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);

				if (nick != null) {
					System.out.println("로그인 성공");
					System.out.println(nick + "님 환영합니다!");
					System.out.print("변경할 아이디를 입력해주세요 : ");
					String inputid = sc.next();
//					int cnt =  dao.update(inputid, id);

//					if(cnt > 0) {
//						System.out.println("회원정보 수정 완료");
//					}else {
//						System.out.println("회원정보 수정 실패");
//					}
				} else {
					System.out.println("로그인 실패");
				}

			} else if (choice == 5) { //회원탈퇴
				System.out.println("===== 회원탈퇴 =====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				
				int cnt = dao.delete(id, pw);
				
				if(cnt > 0) {
					System.out.println("회원탈퇴 완료");
				}else {
					System.out.println("잘못된 정보 입니다.");
				}
				
				

			} else if (choice == 6) {
				System.out.println("프로그램 종료...");
				break;
			} else {
				System.out.println("정확한 숫자를 다시 입력해주세요");
			}

		}
	}

}
