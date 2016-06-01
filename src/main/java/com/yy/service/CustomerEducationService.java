package com.yy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerEducationDao;
import com.yy.domain.entity.CustomerEducation;

/**
 * @ClassName: CustomerEducationService
 * @Description: 客户教育服务类
 * @author caizhen
 * @date 2016年5月23日 下午9:01:38
 */
@Service
public class CustomerEducationService {
	@Autowired
	private CustomerEducationDao customerEducationDao;

	public void saveOrUpCustomerEducation(CustomerEducation customerEducation){
		if(customerEducation!=null&&customerEducation.getCustomereducationid()!=null){
			customerEducationDao.updateByPrimaryKeySelective(customerEducation);
		}else{
			customerEducationDao.insertSelective(customerEducation);
		}
	}
	public CustomerEducation getCustomerEducation(CustomerEducation customerEducation){
		List<CustomerEducation> list = customerEducationDao.getCustomerEducationByParam(customerEducation);
		if(list.size()>0){
			CustomerEducation c=list.get(0);
			customerEducation.setCustomereducationid(c.getCustomereducationid());
		}
		return customerEducation;
	}

}
