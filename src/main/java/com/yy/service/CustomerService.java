package com.yy.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.common.exception.CustomException;
import com.yy.dao.AccountDao;
import com.yy.dao.CardDao;
import com.yy.dao.CustomerDao;
import com.yy.domain.entity.Account;
import com.yy.domain.entity.Card;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerCertificate;
import com.yy.domain.entity.CustomerPersonal;
import com.yy.web.utils.HttpXmlClient;
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
	CardDao cardDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	CustomerCertificateService customerCertificateService;
	@Autowired
	CustomerWorkexperienceService customerWorkexperienceService;
	@Autowired
	CustomerEducationService customerEducationService;
	@Autowired
	CustomerPersonalService customerPersonalService;
	/**
	 *
	 * @Title: saveOrUpCustomer
	 * @Description: 更新或修改客户信息
	 * @author caizhen
	 * @param @param customer    设定文件
	 * @return void    返回类型
	 */
	public void saveOrUpCustomer(HttpServletRequest request,Customer customer){
//		Customer c=(Customer)request.getSession().getAttribute("customer");
//		if(c!=null)
//			customer.setCustomerID(c.getCustomerID());
		if(customer!=null&&customer.getCustomerID()!=null){
			customer.setLastLoginTime(new Date());
			customerDao.updateByPrimaryKeySelective(customer);
		}else{
			customer.setCreateTime(new Date());
			customer.setCustomerStatus("PENDINZX");//等待失信检查
			customerDao.insertSelective(customer);
		}
		customer=customerDao.selectByPrimaryKey(customer.getCustomerID());
		StringUtil.setSession(request, customer, "customer");
	}
	/**
	* @Title: doSupplementCustomer 
	* @Description: 实名认证
	* @param @param request
	* @param @param customer    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void doSupplementCustomer(HttpServletRequest request,Customer customer){
		Customer c=(Customer)request.getSession().getAttribute("customer");
		if(c!=null)
			customer.setCustomerID(c.getCustomerID());
		
		saveOrUpCustomer(request,customer);
		
		saveOrUpCustomerCertificate(request,customer);
		
		this.saveCard(request, customer);
	}
	/**
	 * @Title: doSupplementCustomerPersonal 
	 * @Description: 信息完善
	 * @author caiZhen
	 * @date 2016年6月7日 下午2:24:45
	 * @param @param request
	 * @param @param customer    设定文件 
	 * @return void    返回类型 
	 */
	public void doSupplementCustomerPersonal(HttpServletRequest request,CustomerPersonal customerPersonal){
		Customer customer=(Customer)request.getSession().getAttribute("customer");
		customerPersonal.setCustomerID(customer.getCustomerID());
		customerPersonalService.saveOrUpCustomerPersonal(request,customerPersonal);
		customerEducationService.saveOrUpCustomerEducation(request, customer);
		customerWorkexperienceService.saveWorkexperience(request, customer);
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
		Map<String, String> params = new HashMap<String, String>();  
		params.put("name", customer.getName()); 
		params.put("idNo", request.getParameter("idCard"));
		params.put("resonCd", "01"); 
		params.put("mobileNo", customer.getCellPhone());
		      
		return HttpXmlClient.post("http://139.196.136.32/captureOL/company_executeAuth.action", params);  
	}
	/**
	 * @Title: saveCard 
	 * @Description: 保存账户信息
	 * @author caiZhen
	 * @date 2016年6月7日 下午4:27:16
	 * @param @param request
	 * @param @param customer    设定文件 
	 * @return void    返回类型 
	 */
	public void saveCard(HttpServletRequest request,Customer customer){
		Account account = new Account();
		account.setCustomerID(customer.getCustomerID());
		accountDao.insertSelective(account);
		Card card = new Card();
		card.setAccountID(account.getAccountID());
		card.setCardCode(request.getParameter("cardCode"));
		cardDao.insertSelective(card);
	}
//	public String collect_info2(HttpServletRequest request,Customer customer){
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("name", customer.getName());
//		param.put("idNo", request.getParameter("idCard"));
//		param.put("resonCd", "01");
//		param.put("mobileNo", customer.getCellPhone());
//		
//		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
//		requestHeads.add(new RequestHead("Content-Type", "application/json"));
//		try {
//			String json =HttpConnect.getJson("http://127.0.0.1:8080/captureOL/company_executeAuth.action?resonCd=01&name="+customer.getName()
//					+"&idNo="+request.getParameter("idCard")+"&mobileNo="+customer.getCellPhone(),
//					param, requestHeads,"post");
//			System.out.print("collect_info-----------------------------------"+json);
//			if (!"".equals(json)) {
//				JSONObject jsonObject = JSONObject.fromObject(json);
//				if("true".equals(jsonObject.getString("success"))){
//					
//				}else{
//					
//				}
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//		return null;
//	}
	public String submitCapture(HttpServletRequest request){
		List<Customer> customerList = customerDao.getCustomer(new Customer(request.getParameter("account")));
		if(customerList!=null&&customerList.size()>0){
			Customer Customer = customerList.get(0);
			Map<String, String> params = new HashMap<String, String>();  
//		params.put("name",  request.getParameter("name")); 
//		params.put("idCard", request.getParameter("idCard"));
//		params.put("account",  request.getParameter("account")); 
//		params.put("password",  request.getParameter("password"));
			params.put("name",  "蔡杭军"); 
			params.put("idCard", "339011197809199014");
			params.put("account",  "18806756337"); 
			params.put("password",  "999888");
			return HttpXmlClient.post("http://127.0.0.1:8080/captureOL/company_submitCapture.action", params);  
		}else{
			throw new CustomException("无该用户");
		}
			
		}
}
