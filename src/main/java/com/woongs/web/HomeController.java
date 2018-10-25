package com.woongs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.woongs.domain.QuestionRepository;

/**
 * Description : 홈 관련 컨트롤러 클레스
 * @author Woongs
 * @since 18.10.24
 */
@Controller
public class HomeController {

	@Autowired
	private QuestionRepository questionRepository;
	
	/**
	 * Description : url에 따라 templates의 index 페이지 이동 메소드
	 * @return index 이동할 페이지
	 */
	@GetMapping("/")
	public String home(Model model) {
		System.out.println("home come!");
		
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
}
