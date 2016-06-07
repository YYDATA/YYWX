package com.yy.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerWorkexperienceDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerWorkexperience;

/**
 * @ClassName: CustomerWorkexperienceService 
 * @Description: 客户工作经验服务类 
 * @author caiZhen
 * @date 2016年6月7日 上午11:02:39
 */
@Service
public class CustomerWorkexperienceService {
	@Autowired
	CustomerWorkexperienceDao customerWorkexperienceDao;
	public void saveOrUpWorkexperience(HttpServletRequest request,Customer customer){
		CustomerWorkexperience customerWorkexperience=new CustomerWorkexperience();
		customerWorkexperience.setCustomerID(customer.getCustomerID());
		customerWorkexperience.setCompanyType(request.getParameter("companyType"));
		customerWorkexperience.setRole("role");
		if(customerWorkexperience!=null&&customerWorkexperience.getWorkExperienceID()!=null){
			customerWorkexperienceDao.insertSelective(customerWorkexperience);
		}else{
			customerWorkexperienceDao.updateByPrimaryKeySelective(customerWorkexperience);
		}
		
	}
}
