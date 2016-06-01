package com.yy.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerPersonalDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerPersonal;

/**
 * @ClassName: CustomerPersonalService
 * @Description: 客户个人信息
 * @author caizhen
 * @date 2016年5月23日 下午8:35:03
 */
@Service
public class CustomerPersonalService {
	@Autowired
	CustomerPersonalDao customerPersonalDao;
	public void saveOrUpCustomerPersonal(HttpServletRequest request,CustomerPersonal customerPersonal){
		Customer c=(Customer)request.getSession().getAttribute("customer");
		if(c!=null)
			customerPersonal.setCustomerID(c.getCustomerID());
		customerPersonalDao.insertSelective(customerPersonal);

	}
}
