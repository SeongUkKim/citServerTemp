package egovframework.sys.sec.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import egovframework.sys.cmm.util.EncryptionComponent;
import egovframework.sys.cmm.util.UtilityComponent;
import egovframework.sys.sec.user.SUserAccessService;
import egovframework.sys.sec.user.SUserAccessVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class CitAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(CitAuthenticationProvider.class);
	
	@Autowired
	private UtilityComponent utilComp;

	@Autowired
	private SUserAccessService SERVICE;
	
	@Autowired
	private EncryptionComponent encComp;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		
		String id = authToken.getName();
		String password = authToken.getCredentials().toString();
		
		try {
			id = this.encComp.sha256(authToken.getName().toString());
			password = this.encComp.sha256(authToken.getCredentials().toString());

		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			throw new BadCredentialsException("사용자 인증에 문제가 발생 했습니다.");
		}
		SUserAccessVO userVO = null;
		
		try {
			
			userVO = this.SERVICE.getUserInfo(id);
		} catch (Exception e) {
			logger.error(this.utilComp.stackTrace(e));
			throw new BadCredentialsException("사용자 인증에 문제가 발생 했습니다.");
		}
		
		if(userVO != null) {
			
			if(userVO.getUser_pw().equals(password)) {
				
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				
				String auth = userVO.getAuthority();
				
				CitUserDetails details = new CitUserDetails();
				
				/**
				 * 권한 확인
				 * A: 인증된 사용자
				 * B: 일반
				 * R: 루트
				 */
				if(auth.equals("B")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_BASIC"));
					details.setUser_id(userVO.getUser_id());
				} else if(auth.equals("R")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					details.setAdmin_id(userVO.getUser_id());
				} else if(auth.equals("A")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_AUTHIRIZED"));
					details.setUser_id(userVO.getUser_id());
				}
				details.setUser_nm(userVO.getUser_nm());
				details.setUser_hp(userVO.getUser_hp());
				details.setUser_cp(userVO.getUser_cp());
				details.setUser_cp_no(userVO.getUser_cp_no());
				details.setAuthority(auth);
				details.setAuthorities(authorities);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password, authorities);
				
				authenticationToken.setDetails(details);
				return authenticationToken;
				
			} else {
				throw new BadCredentialsException("등록되지않은 아이디 혹은 이메일이거나 비밀번호가 일치하지않습니다.");
			}
		} else {
			throw new UsernameNotFoundException("등록되지않은 아이디 혹은 이메일이거나 비밀번호가 일치하지않습니다.");
		}
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
