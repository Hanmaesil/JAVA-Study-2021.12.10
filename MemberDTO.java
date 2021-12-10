
public class MemberDTO {
	//데이터베이스를 표현하기 위한 사용자 임의의 VO클래스 이다.
	//VO클래스는 -> Value Object(사용자 임의의 클래스)
	
	
	String id;
	String pw;
	String nick;
	
	
	public MemberDTO(String id, String pw, String nick) {
		super();
		this.id = id;
		this.pw = pw;
		this.nick = nick;
	}
	
	
	
	
	
	
}
