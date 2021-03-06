package com.ailu.tokenmedia.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ailu.tokenmedia.manage.sign.dto.ManagerDTO;

public class SessionInterceptor implements HandlerInterceptor {

	private final static Logger log = LoggerFactory.getLogger(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ManagerDTO manager = (ManagerDTO) request.getSession().getAttribute("manager");
		if (manager == null) {
			log.debug("未登录或登陆超时");
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
				return false;
			}
			response.sendRedirect(request.getContextPath() + "/manage");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
