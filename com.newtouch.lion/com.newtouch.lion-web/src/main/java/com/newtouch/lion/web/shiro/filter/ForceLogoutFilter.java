/*
 * Copyright (c)  2015, Newtouch
 * All rights reserved. 
 *
 * $id: ForceLogoutFilter.java 9552 2015年2月27日 下午2:54:10 WangLijun$
 */
package com.newtouch.lion.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.newtouch.lion.model.system.User;
import com.newtouch.lion.service.session.SessionService;
import com.newtouch.lion.web.shiro.cache.SessionCacheManager;
import com.newtouch.lion.web.shiro.constant.Constants;
import com.newtouch.lion.web.shiro.session.LoginSecurityUtil;

/**
 * <p>
 * Title: Shiro强制用户退出过滤器
 * </p>
 * <p>
 * Description: Shiro强制用户退出过滤器
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: Newtouch
 * </p>
 * 
 * @author WangLijun
 * @version 1.0
 */
public class ForceLogoutFilter extends AccessControlFilter {
	
	/**后用户将强制退出的URL*/
	private String forceLogoutUrl;
	/** Shiro Session缓存管理*/
	private SessionCacheManager sessionCacheManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax
	 * .servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Session session = getSubject(request, response).getSession(false);
		if (session == null) {
			return true;
		}
		return (session.getAttribute(SessionService.SESSION_FORCE_LOGOUT_KEY) == null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.
	 * servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
		User user = LoginSecurityUtil.getUser();
		//清除缓存
		sessionCacheManager.removeSessionController(Constants.CACHE_SESSION_NAME,user.getUsername());
		//强制退出	
		getSubject(request, response).logout();
		
		
		
		StringBuilder sb=new StringBuilder();
		sb.append(this.forceLogoutUrl);
		sb.append(this.forceLogoutUrl.contains("?")?"&":"?");
		sb.append(Constants.FORCE_LOGOUT);
		sb.append("=");
		sb.append(Constants.LOGIN_FORCE_ERROR);		
		WebUtils.issueRedirect(request, response, sb.toString());
		return false;
	}

	/**
	 * @return 后用户将强制退出的URL
	 */
	public String getForceLogoutUrl() {
		return forceLogoutUrl;
	}

	/**
	 * @param forceLogoutUrl 后用户将强制退出的URL
	 */
	public void setForceLogoutUrl(String forceLogoutUrl) {
		this.forceLogoutUrl = forceLogoutUrl;
	}

	/**
	 * @return the sessionCacheManager Shiro Session缓存管理
	 */
	public SessionCacheManager getSessionCacheManager() {
		return sessionCacheManager;
	}

	/**
	 * @param sessionCacheManager Shiro Session缓存管理
	 */
	public void setSessionCacheManager(SessionCacheManager sessionCacheManager) {
		this.sessionCacheManager = sessionCacheManager;
	}
	
	
}
