package com.chen.security.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chen.common.service.Interface.SystemLogService;
import com.chen.common.util.Response;
import com.chen.common.util.ResponseUtil;
import com.chen.exception.SystemLogServiceException;
import com.chen.security.util.CheckCodeGenerator;


/** 
* 用户登录系统 相关
* @author 作者 chenyan: 
* @version 创建时间：2019年3月6日 下午4:55:14 
*/
@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static Logger log = Logger.getLogger("application") ;
	
	@Autowired
	private ResponseUtil responseUtil ;
	@Autowired
    private CheckCodeGenerator checkCodeGenerator;
	@Autowired
	private SystemLogService systemLogService ;
	
	private static final String USER_ID = "id" ;
	private static final String USER_NAME = "userName" ;
	private static final Object USER_PASSWORD = "password";
	
	/**
	 *  登录管理
	 * @param user 用户信息
	 * @return  Map (包括登录结果)
	 */
	// 抑制单类型警告
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "login" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestBody Map<String, Object> user){
		// 获取 Response
		Response response = responseUtil.newResponseInstance() ;
		String result = Response.RESPONSE_RESULT_ERROR ;
		String errorMsg = "" ;
		
		// 获取当前的用户的 subject(shiro)
		Subject currentUser = SecurityUtils.getSubject() ;
		// 判断是否已登录过
		if(currentUser != null && !currentUser.isAuthenticated()) {
			String id = (String)user.get(USER_ID) ;
			String pwd = (String)user.get(USER_PASSWORD) ;
			
			UsernamePasswordToken token  = new UsernamePasswordToken(id ,pwd) ;
			try {
				// 调用 realms/UserAuthorizingRealm 中的 doGetAuthenticationInfo 方法
				currentUser.login(token) ;
				
				Session session = currentUser.getSession() ;
				session.setAttribute("isAuthenticate", "true") ;
				Integer userId_int = (Integer)session.getAttribute("userId") ;
				String userName = (String)session.getAttribute("userName") ;
				String accessIp = session.getHost() ;
				systemLogService.insertAccessRecord(userId_int, userName, accessIp, SystemLogService.ACCESS_TYPE_LOGIN);
			
				result = Response.RESPONSE_RESULT_SUCCESS ;
			}  catch (UnknownAccountException e) {
                errorMsg = "unknownAccount";
            } catch (IncorrectCredentialsException e) {
                errorMsg = "incorrectCredentials";
            } catch (AuthenticationException e) {
                errorMsg = "authenticationError";
            } catch (SystemLogServiceException e) {
                errorMsg = "ServerError";
            }
		} else {
			errorMsg = "already login";
		}
		// 设置 Response
        response.setResponseResult(result);
        response.setResponseMsg(errorMsg);
        return response.generateResponse();
	}
	
	/**
	 *  获取图形验证码 将返回一个包含4位字符（字母或数字）的图形验证码，并且将图形验证码的值设置到用户的 session中
	 * @param time 时间戳
	 * @param response 返回的 HttpServletResponse 响应
	 * @param request
	 */
	@RequestMapping(value = "checkCode/{time}" ,method = RequestMethod.GET)
	public void getCheckCode(@PathVariable("time") String time ,HttpServletResponse response ,HttpServletRequest request) {
		
		BufferedImage checkCodeImage = null ;
		String checkCodeString = null ;
		
		// 获取图形验证码，依赖于 util/CheckCodeGenerator
		Map<String ,Object> checkCode = checkCodeGenerator.generlateCheckCode() ;
		
		if(checkCode != null) {
			checkCodeString = (String) checkCode.get("checkCodeString");
            checkCodeImage = (BufferedImage) checkCode.get("checkCodeImage");
		}
		
		if(checkCodeString != null && checkCodeImage != null) {
			// 获取 response.getOutputStream()
			try(ServletOutputStream outputStream = response.getOutputStream()){
				// 设置 Session 
				HttpSession session = request.getSession() ;
				session.setAttribute("checkCode", checkCodeString) ;
				
				// 将验证码输出
				ImageIO.write(checkCodeImage, "png", outputStream) ;
				
				response.setHeader("Pragma", "mo-cache") ;
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
                response.setContentType("image/png");
				
			} catch (IOException e) {
				log.error("fail to get the ServletOutputStream");
				e.printStackTrace();
			}
		}
		
	}
	
}
