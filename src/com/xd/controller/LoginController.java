package com.xd.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xd.model.Department;
import com.xd.model.User;
import com.xd.service.UserService;

@Controller
@RequestMapping("view/login.it")
public class LoginController {
	private UserService userService;
	public LoginController (){
		userService=new UserService();
	}

	@RequestMapping(params="action=doLogin",method=RequestMethod.POST)
	public @ResponseBody String doLogin(User user,RedirectAttributes attr) {
		UserService service=new UserService();
		String result="";
		System.out.println("loginName:>>>>>>>>>>>>>>>>>>>>>>>:"+user.getLoginName());
		System.out.println("password>>>>>>>>>>>>>>>>>>>>>>>>>:"+user.getPassword());
		/*int i=service.getUserCount(user);
		if(i==1){
			result="success";
		}else {
			result="error";
		}*/
		return result;		
	}
	@RequestMapping(params="it=register",method=RequestMethod.GET)
	public String getRegisterPage(Model model){
		model.addAttribute("depart",userService.getDepart());
		return "login/register";
	}
   @RequestMapping(params="it=doRegister",method=RequestMethod.POST)
	@ResponseBody
  public String doRegister(User user,HttpServletResponse response){
		System.out.println("name:>>>>>>>>>>>>>>>"+user.getName());
		String result=userService.saveUser(user);
		return result;
	}
	
	@RequestMapping(params="it=registerInit",method=RequestMethod.GET)
	public @ResponseBody List<Department> getDepart(){
		return userService.getDepart();
	}

	

}