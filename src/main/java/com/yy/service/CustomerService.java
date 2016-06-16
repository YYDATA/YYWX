package com.yy.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yy.common.exception.CustomException;
import com.yy.dao.AccountDao;
import com.yy.dao.CardDao;
import com.yy.dao.CustomerDao;
import com.yy.domain.entity.Account;
import com.yy.domain.entity.Card;
import com.yy.domain.entity.Customer;
import com.yy.web.utils.HttpXmlClient;
import com.yy.web.utils.StringUtil;

import net.sf.json.JSONObject;
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
	
	@Value("#{settings['is_get_juxinli_data']}")
	private String is_get_juxinli_data="";
	/**
	 *
	 * @Title: saveOrUpCustomer
	 * @Description: 更新或修改客户信息
	 * @author caizhen
	 * @param @param customer    设定文件
	 * @return void    返回类型
	 */
	public void saveOrUpCustomer(HttpServletRequest request,Customer customer){
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
	 */
	public void doSupplementCustomer(HttpServletRequest request,Customer customer){
		Customer c=(Customer)request.getSession().getAttribute("customer");
		if(c!=null)
			customer.setCustomerID(c.getCustomerID());
		
		saveOrUpCustomer(request,customer); //更新姓名
		
		customerCertificateService.saveCustomerCertificate(request,customer);//更新身份证
		customerEducationService.saveOrUpCustomerEducation(request, customer);//更新学历
		customerPersonalService.saveCustomerPersonal(request, customer);//更新婚姻情况
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
	public JSONObject doSupplementCustomer(HttpServletRequest request){
		Customer c=(Customer)request.getSession().getAttribute("customer");
		if(c==null){
			throw new CustomException("会话消失");
		}
//		customerPersonal.setCustomerID(customer.getCustomerID()); 
//		customerPersonalService.saveOrUpCustomerPersonal(request,customerPersonal);
//		customerEducationService.saveOrUpCustomerEducation(request, customer);
		customerWorkexperienceService.saveWorkexperience(request, c);//更新工作经历
		
		
		Customer customer = new Customer();
		customer.setCustomerID(c.getCustomerID());
		customer.setEmail(request.getParameter("email"));
		saveOrUpCustomer(request,customer); //更新邮箱
		
		customerCertificateService.saveCustomerCertificate(request,customer);//更新QQ
		JSONObject jObject = new JSONObject();
		jObject.put("account", c.getCellPhone());
		jObject.put("is_get_juxinli_data", is_get_juxinli_data);
		return jObject;
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
//	private void saveOrUpCustomerCertificate(HttpServletRequest request,Customer customer){
//		CustomerCertificate customerCertificate=null;
//		if(StringUtils.isNoneBlank(request.getParameter("idCard"))){
//			customerCertificate=new CustomerCertificate(customer.getCustomerID(),"ID",request.getParameter("idCard"));
//			customerCertificateService.saveOrUpCustomerCertificate(customerCertificate);
//		}
//		if(StringUtils.isNoneBlank(request.getParameter("qq"))){
//			customerCertificate=new CustomerCertificate(customer.getCustomerID(),"QQ",request.getParameter("qq"));
//			customerCertificateService.saveOrUpCustomerCertificate(customerCertificate);
//		}
//	}

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
		this.saveOrUpAccount(account);
		
		Card card = new Card();
		card.setAccountID(account.getAccountID());
		card.setCardCode(request.getParameter("cardCode"));
//		cardDao.insertSelective(card);
		this.saveOrUpCard(card);
	}
	public void saveOrUpAccount(Account account){
		List<Account> accountList = accountDao.selectByCustomerID(account.getCustomerID());
		if(accountList!=null&&accountList.size()>0){
			Account record = accountList.get(0);
			account.setAccountID(record.getAccountID());
			accountDao.updateByPrimaryKeySelective(account);
		}else{
			accountDao.insertSelective(account);
		}
	}
	public void saveOrUpCard(Card card){
		List<Card> cardList = cardDao.selectByAccountID(card.getAccountID());
		if(cardList!=null&&cardList.size()>0){
			Card record = cardList.get(0);
			card.setCardID(record.getCardID());
			cardDao.updateByPrimaryKeySelective(card);
		}else{
			cardDao.insertSelective(card);
		}
	}

	public Map doExecuteJxl(HttpServletRequest request){
		Map<String, String> params = new HashMap<String, String>();  
				
		return params;
		
		
			
	}
	/**
	 * @Title: saveCard 
	 * @Description: 验证手机验证码
	 * @author caiZhen
	 * @date 2016年6月13日 下午4:27:16
	 * @param @param request
	 * @param @param customer    设定文件 
	 * @return void    返回类型 
	 */
	public void doValidateCode(HttpServletRequest request){
			
	}
}
