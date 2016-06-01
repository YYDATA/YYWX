package com.yy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerCertificate;

/**
 * @ClassName: CustomerService
 * @Description: 客户管理服务类
 * @author caizhen
 * @date 2016年5月23日 下午8:35:03
 */
@Service
public class CustomerService {
	@Autowired
	CustomerDao customerDao;
	@Autowired
	CustomerCertificateService customerCertificateService;
	/**
	 *
	 * @Title: saveOrUpCustomer
	 * @Description: 更新或修改客户信息
	 * @author caizhen
	 * @param @param customer    设定文件
	 * @return void    返回类型
	 */
	public void saveOrUpCustomer(HttpServletRequest request,Customer customer){
		Customer c=(Customer)request.getSession().getAttribute("customer");
		if(c!=null)
			customer.setCustomerID(c.getCustomerID());
		if(customer!=null&&customer.getCustomerID()!=null){
			customer.setLastLoginTime(new Date());
			customerDao.updateByPrimaryKeySelective(customer);
		}else{
			customer.setCreateTime(new Date());
			customerDao.insertSelective(customer);
		}
		saveOrUpCustomerCertificate(request,customer);
	}
	/**
	 *
	 * @Title: getCustomer
	 * @Description: 根据客户信息获取客户列表
	 * @author caizhen
	 * @param @param customer
	 * @return List<Customer>    返回类型
	 */
	public List<Customer> getCustomer(Customer customer){
		return customerDao.getCustomer(customer);
	}
	private void saveOrUpCustomerCertificate(HttpServletRequest request,Customer customer){
		CustomerCertificate customerCertificate=null;
		if(StringUtils.isNoneBlank(request.getParameter("idCard"))){
			customerCertificate=new CustomerCertificate(customer.getCustomerID(),"ID",request.getParameter("idCard"));
			customerCertificateService.saveOrUpCustomerCertificate(customerCertificate);
		}
		if(StringUtils.isNoneBlank(request.getParameter("qq"))){
			customerCertificate=new CustomerCertificate(customer.getCustomerID(),"QQ",request.getParameter("qq"));
			customerCertificateService.saveOrUpCustomerCertificate(customerCertificate);
		}
	}
}
