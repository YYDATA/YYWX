package com.yy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.common.utils.Common;
import com.yy.dao.LoanOrderDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.LoanOrder;

@Service
public class LoanOrderService {
	@Autowired
	LoanOrderDao loanOrderDao;
	@Autowired
	private CustomerService customerService;
	public void saveOrUpLoadOrder(HttpServletRequest request,LoanOrder loanOrder){

		//增加贷款记录前，查看有无客户信息(有：获取客户id，无：增加)
		Customer customer = new Customer(loanOrder.getCellphone());
		List<Customer> listCustomer = customerService.getCustomer(customer);
		//设置customerid
		if(listCustomer.size()>0){
			loanOrder.setCustomerid(listCustomer.get(0).getCustomerid());
			customer=listCustomer.get(0);
		}else{
			customerService.saveOrUpCustomer(request,customer);
			loanOrder.setCustomerid(customer.getCustomerid());
		}
		if(customer!=null)
			request.getSession().setAttribute("customer", customer);
		loanOrder.setOrderdate(new Date());
		loanOrder.setLoanordercode(getLoanOrderCode());
		loanOrderDao.insert(loanOrder);
	}
	/**
	 * @return BA+年月日+6位流水号
	 */
	private String getLoanOrderCode(){
		return new StringBuilder("BA"+Common.getFormatDate(new Date(), "yyyyMMdd")+Common.randomCode(6)).toString();
	}
}
