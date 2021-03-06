package com.woongs.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woongs.domain.Answer;
import com.woongs.domain.AnswerRepository;
import com.woongs.domain.Question;
import com.woongs.domain.QuestionRepository;
import com.woongs.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			System.out.println("로그인이 되어있지 않음.");
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		
		Answer answer = new Answer(loginUser, contents, question);
		answerRepository.save(answer);
		
		return String.format("redirect:/questions/%d", questionId);
	}
}
