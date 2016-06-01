package com.yy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerCertificateDao;
import com.yy.domain.entity.CustomerCertificate;

@Service
public class CustomerCertificateService {
	@Autowired
	CustomerCertificateDao customerCertificateDao;
	
	public void saveOrUpCustomerCertificate(CustomerCertificate customerCertificate){
		customerCertificateDao.insertSelective(customerCertificate);
	}
}
