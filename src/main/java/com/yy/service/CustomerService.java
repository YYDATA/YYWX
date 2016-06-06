package com.yy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.dao.CustomerDao;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerCertificate;
import com.yy.web.utils.RequestHead;
import com.yy.web.utils.HttpConnect;
import com.yy.web.utils.StringUtil;
/**
 * @ClassName: CustomerService
 * @Description: 客户管理服务类
 * @author caizhen
 * @date 2016年5月23日 下午8:35:03
 */
@Service
public class CustomerService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
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
			customer.setCustomerStatus("PENDINGSX");//等待失信检查
			customerDao.insertSelective(customer);
		}
		customer=customerDao.selectByPrimaryKey(customer.getCustomerID());
		StringUtil.setSession(request, customer, "customer");
	}
	/**
	* @Title: doSupplementCustomer 
	* @Description: 补充信息
	* @param @param request
	* @param @param customer    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void doSupplementCustomer(HttpServletRequest request,Customer customer){
		saveOrUpCustomer(request,customer);
		saveOrUpCustomerCertificate(request,customer);
		//执行信息收集
		customer=(Customer)StringUtil.getSession(request, "customer");
		collect_info(request,customer);
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
	public String collect_info(HttpServletRequest request,Customer customer){
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", customer.getName());
		param.put("idNo", request.getParameter("idCard"));
		param.put("resonCd", "01");
		param.put("mobileNo", customer.getCellPhone());
		
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		try {
			String json =HttpConnect.getJson("http://127.0.0.1:8080/captureOL/company_executeAuth.action?resonCd=01&name="+customer.getName()
					+"&idNo="+request.getParameter("idCard")+"&mobileNo="+customer.getCellPhone(),
					param, requestHeads,"post");
			System.out.print("collect_info-----------------------------------"+json);
			if (!"".equals(json)) {
				JSONObject jsonObject = JSONObject.fromObject(json);
				if("true".equals(jsonObject.getString("success"))){
					
				}else{
					
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
