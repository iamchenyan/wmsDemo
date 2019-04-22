package com.chen.security.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
*  页面重定向
* @author 作者 chenyan
* @version 创建时间：2019年4月22日 下午4:11:26 
*/
@RequestMapping("/")
@Controller
public class PageForwardController {

	/**
	 *  内部重定向到登录页面
	 * @return login.jsp
	 */
	@RequestMapping("login")
	public String loginPageForward() {
		// 判断当前用户是否已经登录
		Subject currentSubject = SecurityUtils.getSubject() ;
		if(!currentSubject.isAuthenticated()) {
			return "login" ;
		} else {
			return "mainPage" ;
		}
	}
	
	/**
	 *  内部重定向到主页面
	 * @return
	 */
	@RequestMapping("mainPage")
	public String showLoginView() {
		Subject currentSubject = SecurityUtils.getSubject() ;
		if(!currentSubject.isAuthenticated()) {
			return "login" ;
		} else {
			return "mainPage" ;
		}
	}
	
	
}
