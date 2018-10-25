package com.woongs.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woongs.domain.User;
import com.woongs.domain.UserRepository;

/**
 * Description : 유저 관련 처리/페이지이동 컨트롤러
 * @author Woongs
 * @since 18.10.24
 */
@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository; // User VO관련 처리를 위한 레파지토리 선언

	/**
	 * Description : 로그인 페이지로 이동하는 메소드
	 * @return /user/login 로그인 페이지
	 */
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("user loginForm come!");
		return "/user/login";
	}
	
	/**
	 * 유저 ID, Password 일치여부에 따라 세션을 생성 (로그인 처리)하는 메소드
	 * @param userId 유저ID
	 * @param password 유저Password
	 * @param session 세션
	 * @return url / (홈 페이지 이동)
	 */
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId); // DB에서 유저ID가 존재하는지 조회		
		
		// DB에 유저ID가 존재치 않는다면, 로그인폼 페이지로 이동
		if(user == null) { 
			System.out.println("존재치 않는 ID 입니다.");
			return "redirect:/users/loginForm";
		}

		// DB에 저장된 유저Password가 일치하지 않는다면, 로그인폼 페이지로 이동
		if(!user.matchPassword(password)) {
			System.out.println("패스워드가 동일하지 않습니다.");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("user login session set come!");
		
		// DB에 유저ID와 유저Password가 일치한다면, 유저ID에 따라 조회된 User VO를 세션에 저장
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		// 세션유지시간은 30분 (단위가 초)
		session.setMaxInactiveInterval(30*60);
		
		return "redirect:/";
	}	
	
	/**
	 * 저장된 세션을 삭제하는 메소드 (로그아웃)
	 * @param session 세션
	 * @return url / (홈 페이지 이동)
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		
		return "redirect:/";
	}
	
	/**
	 * 회원가입을 위한 페이지 이동 메소드
	 * @return /user/form url (회원가입 페이지 이동)
	 */
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	/**
	 * 회원가입에서 입력한 User VO를 저장하는 메소드 (회원가입 DB 저장)
	 * @param user 폼에서 입력한 User VO
	 * @return /users 메소드 (회원가입된 유저정보 조회하는 메소드)
	 */
	@PostMapping("")
	public String create(User user) {
		System.out.println("user create come! "+user);
		// 입력한 User VO를 DB에 저장
		userRepository.save(user);
		return "redirect:/users";
	}
	
	/**
	 * 회원가입된 유저정보를 조회하는 메소드
	 * @param model 페이지에 넘기기 위한 model
	 * @return /user/list url (회원가입된 유저정보 확인 리스트 페이지)
	 */
	@GetMapping("")
	public String list(Model model) {
		System.out.println("user list come!");
		// DB에 저장된 User정보를 모두 조회하여 model에 담음
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	/**
	 * 로그인 유무, 작성id 일치에 따라 수정페이지로 이동하는 메소드 (개인정보 수정 가능유무 판단후 페이지 이동) 
	 * @param id 수정할id
	 * @param model 결과값을 담을 model
	 * @param session 세션
	 * @return
	 */
	// {id} 개인정보수정 버튼을 눌렀을때 
	@GetMapping("{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
	
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
				
		System.out.println("id/form come! "+userRepository.findById(id).get());
		model.addAttribute("user", userRepository.findById(id).get()); // ID에 따른 정보 조회후, model에 담음
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser); // vo에 새로받은 user vo를 set한다.
		userRepository.save(user);
		return "redirect:/users";
	}
}
