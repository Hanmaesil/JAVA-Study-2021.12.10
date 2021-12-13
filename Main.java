import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// ��� : 1. �α��� 2. ȸ������ 3. ȸ����Ϻ��� 4. ȸ���������� 5. ȸ��Ż�� 6.����

		Scanner sc = new Scanner(System.in);
		System.out.println("===== ȸ������ �ý��� =====");
		MemberDAO dao = new MemberDAO();

		while (true) {

			System.out.print("1.�α��� 2.ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.����");
			int choice = sc.nextInt();

			if (choice == 1) {
				System.out.println("===== �α��� =====");
				System.out.print("���̵� �Է�: "); // ������� �Է°�
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				String nick = dao.login(id, pw); // �Է°��� �Ű������� �־ ���.

				if (nick != null) {
					System.out.println("�α��� ����");
					System.out.println(nick + "�� ȯ���մϴ�!");
					
					if(id.equals("admin")) {  //������ ���
						System.out.print("1. ȸ������ ���� 2. ȸ������ >>>>");
						choice = sc.nextInt();
						
						if(choice == 1) {
							System.out.println("===== ������ ȸ���������� =====");
							System.out.print("���̵� �Է� : ");
							String change_id = sc.next();
							System.out.print("������ �г��� �Է� : ");
							String change_nick = sc.next();
							
							int cnt = dao.adminUpdate(change_id, change_nick);
							if(cnt > 0) {
								System.out.println("ȸ������ ���� �Ϸ�");
							}else {
								System.out.println("ȸ������ ���� ����");
							}
							
							
							
						}else if(choice == 2) {
							
						}
						
						
					}
					
					
				} else {
					System.out.println("�α��� ����");
				}

			} else if (choice == 2) {
				System.out.println("===== ȸ������ =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("�г��� �Է� : ");
				String nick = sc.next();

				int cnt = dao.join(id, pw, nick);

				if (cnt > 0) {
					System.out.println("ȸ������ ����");
				} else {
					System.out.println("ȸ������ ����");
				}

			} else if (choice == 3) { // ȸ�� ��� ����

				System.out.println("=====ȸ����Ϻ���=====");
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

			} else if (choice == 4) { // ȸ�� ���� ���� //�ٽ� ����� �ФФФФ�
				System.out.println("===== ȸ���������� =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);

				if (nick != null) {
					System.out.println("�α��� ����");
					System.out.println(nick + "�� ȯ���մϴ�!");
					System.out.print("������ ���̵� �Է����ּ��� : ");
					String inputid = sc.next();
//					int cnt =  dao.update(inputid, id);

//					if(cnt > 0) {
//						System.out.println("ȸ������ ���� �Ϸ�");
//					}else {
//						System.out.println("ȸ������ ���� ����");
//					}
				} else {
					System.out.println("�α��� ����");
				}

			} else if (choice == 5) { //ȸ��Ż��
				System.out.println("===== ȸ��Ż�� =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				
				int cnt = dao.delete(id, pw);
				
				if(cnt > 0) {
					System.out.println("ȸ��Ż�� �Ϸ�");
				}else {
					System.out.println("�߸��� ���� �Դϴ�.");
				}
				
				

			} else if (choice == 6) {
				System.out.println("���α׷� ����...");
				break;
			} else {
				System.out.println("��Ȯ�� ���ڸ� �ٽ� �Է����ּ���");
			}

		}
	}

}
