package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serciceImp.UserServiceImp;
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
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException
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
			request.setAttribute("msg", "注册成功,请激活!");
		} catch (Exception e) {
			// 注册失败

			request.setAttribute("msg", "注册失败");
		}
		return "/jsp/info.jsp";
		
	}

}
