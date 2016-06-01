package com.yy.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: UrlControl
 * @Description: 地址管理器
 * @author caizhen
 * @date 2016年5月22日 下午5:33:45
 */
@Controller
public class UrlControl {
	/**
	 * @ClassName: index
	 * @Description: 重定位到首页
	 * @author caizhen
	 * @date 2016年5月30日 下午3:19:36
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) throws Exception{
		return "/index";
	}
}
