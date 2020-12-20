package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serciceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {

	/*
	 * 统一跳转处理
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	public String regist(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, String[]> parameterMap = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, parameterMap);
		
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		
//		String parameter = request.getParameter("name");
//		System.out.println("parameter:" + parameter);
//		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
//		for (Entry<String, String[]> entry : entrySet) {
//			System.out.print(entry.getKey() + ": ");
//			String[] value = entry.getValue();
//			for (int i = 0; i < value.length; i++) {
//				System.out.println(value[i]);
//			}
//		}
		UserService userService = new UserServiceImp();
		try {
			userService.userRegist(user);
			// success
			// 发邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "注册成功,请激活!");
		} catch (Exception e) {
			// 注册失败
			request.setAttribute("msg", "注册失败");
		}
		return "/jsp/info.jsp";
		
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String code = request.getParameter("code");
		UserService userService = new UserServiceImp();
		boolean flag =userService.userActive(code);
		if (flag) {
			//激活成功--登录页面
			request.setAttribute("msg", "用户激活成功!");
			return "/jsp/login.jsp";
		}else {
			// 登录失败--提示界面
			request.setAttribute("msg", "用户激活失败");
			return "/jsp/info.jsp";
		}
		
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		// 页面跳转
		return "/jsp/login.jsp";
		
	}
	
	public String userLogin(HttpServletRequest request, HttpServletResponse response) {
		//获取用户登录信息
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用登录功能
		UserService userService = new UserServiceImp();
		User user2 = null;
		try {
			user2 = userService.userLogin(user);
			// 用户信息放在session
			request.getSession().setAttribute("loginUser", user2);
			return "/jsp/index.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			return "/jsp/login.jsp";
		}
	
	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.getSession().invalidate();
		response.sendRedirect("/store_v6/index.jsp");
		return null;
		
	}
	
}
