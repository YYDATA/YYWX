package com.yy.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yy.common.domain.ResponseResult;
import com.yy.domain.entity.Customer;
import com.yy.domain.entity.CustomerPersonal;
import com.yy.domain.entity.LoanOrder;
import com.yy.service.CustomerPersonalService;
import com.yy.service.CustomerService;
import com.yy.service.LoanOrderService;
import com.yy.web.utils.JsonViewFactory;

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
	CustomerPersonalService customerPersonalService;

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
		loanOrderService.saveOrUpLoadOrder(request, loanOrder);
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", null));
	}
	/**
	 * @Title: saveCustomer
	 * @Description: 保存客户信息
	 * @author caizhen
	 * @param @param request
	 * @param @param customer
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/saveOrUpdateCustomer", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateCustomer(HttpServletRequest request, Customer customer){
		customerService.saveOrUpCustomer(request,customer);
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
	public ModelAndView saveOrUpCustomerPersonal(HttpServletRequest request, CustomerPersonal customerPersonal){
		customerPersonalService.saveOrUpCustomerPersonal(request,customerPersonal);
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "操作成功！", null));
	}
}