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

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("user loginForm come!");
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);		
		
		if(user == null) {
			System.out.println("존재치 않는 ID 입니다.");
			return "redirect:/users/loginForm";
		}
		
		if(!user.getPassword().equals(password)) {
			System.out.println("패스워드가 동일하지 않습니다.");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("user login session set come!");
		session.setAttribute("user", user); // 세션에 성공한 정보 저장
		
		return "redirect:/";
	}	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user create come! "+user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		System.out.println("user list come!");
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		System.out.println("id/form come! "+userRepository.findById(id).get());
		model.addAttribute("user", userRepository.findById(id).get()); // ID에 따른 정보 조회후, model에 담음
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User newUser) {
		User user = userRepository.findById(id).get();
		user.update(newUser); // vo에 새로받은 user vo를 set한다.
		userRepository.save(user);
		return "redirect:/users";
	}
}
