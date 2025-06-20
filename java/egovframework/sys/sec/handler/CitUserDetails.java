package egovframework.sys.sec.handler;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CitUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 3978365858447784518L;

	private String user_id;
	private String user_crpt;
	private String user_nm;
	private String user_hp;
	private String user_cp;
	private String user_cp_no;
	private String authority;
	private String admin_id;
	private Collection<? extends GrantedAuthority> authorities;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
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

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	/**
	 * @return 사용자 권한(DB)
	 */
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * @return 계정의 권한 목록
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return password
	 * 사용하지 않는다.
	 */
	@Override
	public String getPassword() {
		return null;
	}
	
	/**
	 * @return 사용자 ID
	 * 사용하지 않도록 한다.
	 */
	@Override
	public String getUsername() {
		return this.user_id;
	}
	
	/**
	 * @return 계정이 만료 되었는지를 확인
	 * true: 만료되지 않음
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * @return 계정이 잠겨있는지를 확인
	 * true: 잠겨있지 않음
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * @return 계정의 password가 만료 되었는지 확인
	 * true: 만료되지 않음
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * @return 사용 가능한 계정인지 확인
	 * true: 사용가능
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
