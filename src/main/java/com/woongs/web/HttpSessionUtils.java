package com.woongs.web;

import javax.servlet.http.HttpSession;

import com.woongs.domain.User;

/**
 * Description : 리펙토링을 위한 세션 관련 유틸 클레스
 * @author Woongs
 * @since 18.10.24
 */
public class HttpSessionUtils {
	
	public static final String USER_SESSION_KEY = "sessionedUser"; // 세션을 키값으로써 관리하여 이 값만 수정되면 해당 변수를 사용하는 코드에 키값이 자동 적용됨 
	
	/**
	 * Description : 세션 존재여부를 확인하는 함수 (즉, 로그인 여부 확인)
	 * @param session 세션값
	 * @return 세션존재 여부 (true or false)
	 * @since 18.10.24
	 */
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		
		if(sessionedUser == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Description : 세션이 존재하면, 해당 세션을 리턴하는 함수
	 * @param session
	 * @return 저장되어 있는 세션
	 */
	public static User getUserFromSession(HttpSession session) {
		if(!isLoginUser(session)) {
			return null;
		}

		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}