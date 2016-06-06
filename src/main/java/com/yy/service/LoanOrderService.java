package com.yy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.common.exception.CustomException;
import com.yy.dao.LoanOrderDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.LoanOrder;
import com.yy.web.utils.StringUtil;

@Service
public class LoanOrderService {
	@Autowired
	LoanOrderDao loanOrderDao;
	@Autowired
	private CustomerService customerService;
	public void saveOrUpLoadOrder(HttpServletRequest request,LoanOrder loanOrder){
//		if(customer!=null)
//			request.getSession().setAttribute("customer", customer);
		if(StringUtils.isBlank(loanOrder.getLoanOrderCode())){
			loanOrder.setOrderDate(new Date());
			loanOrder.setLoanOrderCode(getLoanOrderCode());
			loanOrderDao.insert(loanOrder);
		}else{
			loanOrderDao.updateByPrimaryKeySelective(loanOrder);
		}
	}
	/**
	 * @Title: saveCustomerLoan 
	 * @Description: 保存信息 
	 * @author caiZhen
	 * @date 2016年6月6日 上午11:09:46
	 * @param @param request
	 * @param @param loanOrder    设定文件 
	 * @return void    返回类型 
	 */
	public void saveCustomerLoan(HttpServletRequest request,LoanOrder loanOrder){
		String verificationCode = (String)StringUtil.getSession(request, "verificationCode");
		String phone = (String)StringUtil.getSession(request, "phone");
		if(StringUtils.isBlank(verificationCode)||StringUtils.isBlank(phone)){
			throw new CustomException("验证码已过期,请重置");
		}
		if(!phone.equals(loanOrder.getCellPhone())){
			throw new CustomException("您输入手机号与收验证码手机号不一致");
		}
		String code=request.getParameter("code");
		if(!verificationCode.equals(code)){
			throw new CustomException("请核对验证码");
		}
		//增加贷款记录前，查看有无客户信息(有：获取客户id，无：增加)
		Customer customer = new Customer(loanOrder.getCellPhone());
		List<Customer> listCustomer = customerService.getCustomer(customer);
		//设置customerid
		if(listCustomer.size()>0){
			loanOrder.setCustomerID(listCustomer.get(0).getCustomerID());
			customer=listCustomer.get(0);
		}else{
			customerService.saveOrUpCustomer(request,customer);
			loanOrder.setCustomerID(customer.getCustomerID());
		}
		saveOrUpLoadOrder(request,loanOrder);
	}
	/**
	 * 
	 * @return BA+年月日+6位流水号
	 */
	private String getLoanOrderCode(){
		return new StringBuilder("BA"+StringUtil.getFormatDate(new Date(), "yyyyMMdd")+StringUtil.randomCode(6)).toString();
	}
}
