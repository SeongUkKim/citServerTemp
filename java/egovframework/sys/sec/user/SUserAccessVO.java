package egovframework.sys.sec.user;

public class SUserAccessVO {

	private String user_id;
	private String user_crpt;
	private String user_pw;
	private String user_nm;
	private String user_hp;
	private String user_cp;
	private String user_cp_no;
	
	private String authority;
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_crpt() {
		return user_crpt;
	}

	public void setUser_crpt(String user_crpt) {
		this.user_crpt = user_crpt;
	}

	
	public String getUser_pw() {
		return user_pw;
	}
	
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	
	public String getUser_nm() {
		return user_nm;
	}
	
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getUser_hp() {
		return user_hp;
	}

	public void setUser_hp(String user_hp) {
		this.user_hp = user_hp;
	}

	public String getUser_cp() {
		return user_cp;
	}

	public void setUser_cp(String user_cp) {
		this.user_cp = user_cp;
	}

	public String getUser_cp_no() {
		return user_cp_no;
	}

	public void setUser_cp_no(String user_cp_no) {
		this.user_cp_no = user_cp_no;
	}
}
