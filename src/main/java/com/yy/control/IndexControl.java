package com.yy.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yy.common.domain.ResponseResult;
import com.yy.web.utils.JsonViewFactory;

@Controller
@RequestMapping(value="index")
public class IndexControl {
	@RequestMapping(value="loginSite",method = RequestMethod.POST)
	public ModelAndView loginSite(HttpServletRequest request){
		String currentWebsite=request.getParameter("currentWebsite");
		String websiteAccount=request.getParameter("websiteAccount");
		String pwd=request.getParameter("pwd");
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", null));
	}
}
