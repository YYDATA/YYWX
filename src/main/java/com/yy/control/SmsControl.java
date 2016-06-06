package com.yy.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yy.common.domain.ResponseResult;
import com.yy.domain.entity.SmsDetail;
import com.yy.service.SmsService;
import com.yy.web.utils.JsonViewFactory;

@Controller
@RequestMapping(value="sms")
public class SmsControl {
	@Autowired
	SmsService smsService;
	/**
	 * @Title: sendVerificationCode 
	 * @Description: 发送短信验证码
	 * @author caiZhen
	 * @date 2016年6月6日 上午10:29:58
	 * @param @param request
	 * @param @param smsDetail
	 * @param @return    设定文件 
	 * @return ModelAndView    返回类型 
	 */
	@RequestMapping(value="sendVerificationCode",method=RequestMethod.POST)
	public ModelAndView sendVerificationCode(HttpServletRequest request,SmsDetail smsDetail){
		Assert.notNull(request.getParameter("cellPhone"), "手机号不能为空");
//		smsDetail = new SmsDetail();
		smsDetail.setPhone(request.getParameter("cellPhone"));
		smsService.sendSms(request, smsDetail);
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "发送成功！", null));
	}
}
