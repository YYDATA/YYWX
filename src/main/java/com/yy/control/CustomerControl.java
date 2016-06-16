package com.yy.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yy.common.domain.ResponseResult;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.LoanOrder;
import com.yy.service.CustomerService;
import com.yy.service.LoanOrderService;
import com.yy.service.SmsService;
import com.yy.web.utils.HttpXmlClient;
import com.yy.web.utils.JsonViewFactory;
import com.yy.web.utils.StringUtil;
import com.zxlh.comm.async.service.AsyncService;

/**
 * @ClassName: CustomerControl
 * @Description: 客户信息控制器
 * @author caizhen
 * @date 2016年5月22日 下午5:19:36
 */
@Controller
@RequestMapping(value="customer")
public class CustomerControl {
	@Autowired
	CustomerService customerService;
	@Autowired
	LoanOrderService loanOrderService;
	@Autowired
	SmsService smsService;
	@Resource
	private AsyncService asyncService;

	/**
	 * @Title: saveCustomerLoan
	 * @Description: 保存贷款记录
	 * @author caizhen
	 * @param @param request
	 * @param @param customerLoan
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/saveCustomerLoan", method = RequestMethod.POST)
	public ModelAndView saveCustomerLoan(HttpServletRequest request, LoanOrder loanOrder){
		Assert.notNull(loanOrder.getCellPhone(), "手机号不能为空");
		Assert.notNull(request.getParameter("code"), "验证码不能为空");
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", loanOrderService.saveCustomerLoan(request, loanOrder)));
	}
	/**
	 * @Title: saveOrUpdateCustomer
	 * @Description: 保存客户信息
	 * @author caizhen
	 * @param @param request
	 * @param @param customer
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/saveOrUpdateCustomer", method = RequestMethod.POST)
	public ModelAndView doSupplementCustomer(HttpServletRequest request, Customer customer){
		Assert.notNull(customer.getName(), "借款人新姓名不能为空");
		Assert.notNull(request.getParameter("idCard"), "借款人身份证号不能为空");
		
		customerService.doSupplementCustomer(request,customer);
		
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", null));
	}
	/**
	 * @Title: saveCustomerPersonal
	 * @Description: 保存客户个人信息
	 * @author caizhen
	 * @param @param request
	 * @param @param customer
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/saveOrUpdateCustomerPersonal", method = RequestMethod.POST)
	public ModelAndView saveOrUpCustomerPersonal(HttpServletRequest request){
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", customerService.doSupplementCustomer(request)));
	}
}
