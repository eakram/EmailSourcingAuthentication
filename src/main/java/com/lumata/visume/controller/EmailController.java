package com.lumata.visume.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lumata.visume.info.User;
import com.lumata.visume.service.EmailService;



@Controller
public class EmailController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	EmailService emailService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/source", method = RequestMethod.GET)
	public ModelAndView sourcePage()
	{
		ModelAndView mav = new ModelAndView("source");
		User user;
		try {
			user = emailService.sourceEmails(null);
			mav.addObject("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return mav;
		
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView errorPage(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
	{
		ModelAndView mav = new ModelAndView("error");
		Object error = httpRequest.getAttribute("error");
		mav.addObject("error", error);
		return mav;
		
	}
	
	@RequestMapping(value = "/source-folder", method = RequestMethod.POST)
	public ModelAndView sourceEmailFolder(@ModelAttribute("user") User user)
	{
		ModelAndView mav = new ModelAndView("result");		
		try {
			user.setSourcedFileCounter(11);
			//user = emailService.sourceEmails(null);
			mav.addObject("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return mav;
		
	}
}
