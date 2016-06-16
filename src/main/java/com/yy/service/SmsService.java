package com.yy.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.yy.dao.SmsDetailDao;
import com.yy.domain.entity.SmsDetail;
import com.yy.web.utils.StringUtil;

/**
 * @ClassName: SmsService 
 * @Description: 短信接口
 * @author caiZhen
 * @date 2016年6月5日 下午2:45:11
 */
@Service
public class SmsService {
	@Autowired
	SmsDetailDao smsDetailDao;
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	public void sendSms(HttpServletRequest request,SmsDetail smsDetail){
		if(smsDetail==null){
			smsDetail.setPhone(request.getParameter("cellPhone"));
		}
		String code=StringUtil.randomCode(6);
		smsDetail.setContent(" 您的验证码是"+code);
		
		
		smsDetail.setMessage("OK");
		StringUtil.setSession(request, code, "verificationCode");
		StringUtil.setSession(request, smsDetail.getPhone(), "phone");
		
		smsDetail.setReturnstatus(1);//发送成功
		smsDetail.setType(1);
		smsDetail.setAction("send");
		smsDetailDao.insertSelective(smsDetail);
	}
}
