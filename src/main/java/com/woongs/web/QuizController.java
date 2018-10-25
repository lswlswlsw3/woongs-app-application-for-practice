package com.woongs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woongs.domain.Quiz;
import com.woongs.domain.QuizRepository;

/**
 * 퀴즈관련 처리와 분기 컨트롤러
 * @author Woongs
 * @since 18.10.24
 */


@Controller
@RequestMapping("/quiz") // 이하의 url mapping에는 /quiz가 앞에 붙음
public class QuizController {
	
	@Autowired
	QuizRepository quizRepository;
	
	/**
	 * 퀴즈등록 페이지 이동 메소드
	 * @return /quiz/form url 퀴즈등록 페이지 이동
	 * @since 18.10.24
	 */
	@GetMapping("/form")
	public String form() {
		return "/quiz/form";
	}

	
	/**
	 * 퀴즈등록 페이지에서 입력한 quiz vo 저장하는 메소드
	 * @param 
	 * @return 
	 * @since 18.10.24
	 */
	@PostMapping()
	public String createQuiz(Quiz quiz) {
		System.out.println("quiz create come! "+quiz);
		// 입력한 User VO를 DB에 저장
		quizRepository.save(quiz);
		return "redirect:/quiz";
	}
	
	@GetMapping()
	public String listQuiz(Model model) {
		System.out.println("quiz list come!");
		model.addAttribute("quiz", quizRepository.findAll());
		return "/quiz/list";
	}
}
