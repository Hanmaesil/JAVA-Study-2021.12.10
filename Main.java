import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				System.out.print("���̵� �Է�: "); //������� �Է°�
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				
				dao.login(id, pw);  //�Է°��� �Ű������� �־ ���.
				
			} else if (choice == 2) {
				System.out.println("===== ȸ������ =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("�г��� �Է� : ");
				String nick = sc.next();
				
				dao.join(id, pw, nick);

			} else if (choice == 3) { // ȸ�� ��� ����

			} else if (choice == 4) { // ȸ�� ���� ����
				System.out.println("===== ȸ���������� =====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				dao.update(id, pw);

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
