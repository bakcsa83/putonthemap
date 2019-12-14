package net.potm.web.jsf.util;

import javax.servlet.http.HttpServletRequest;

public class HTTPUtil {

	public static String  getClientIP(HttpServletRequest request) {
		//To get the request from a bean:
		//HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		var ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
		    ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
	
	public static String getBaseUrl(HttpServletRequest request) {
		var url = request.getRequestURL().toString();
		return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
	}
}
