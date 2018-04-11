/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.trade.entity.Bill;
import com.thinkgem.jeesite.modules.trade.service.BillService;

/**
 * 账单Controller
 * @author dason
 * @version 2018-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/bill")
public class BillController extends BaseController {

	@Autowired
	private BillService billService;
	
	@ModelAttribute
	public Bill get(@RequestParam(required=false) String id) {
		Bill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = billService.get(id);
		}
		if (entity == null){
			entity = new Bill();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:bill:view")
	@RequestMapping(value = {"list", ""})
	public String list(Bill bill, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Bill> page = billService.findPage(new Page<Bill>(request, response), bill); 
		model.addAttribute("page", page);
		return "modules/trade/billList";
	}

	@RequiresPermissions("trade:bill:view")
	@RequestMapping(value = "form")
	public String form(Bill bill, Model model) {
		model.addAttribute("bill", bill);
		return "modules/trade/billForm";
	}

	@RequiresPermissions("trade:bill:edit")
	@RequestMapping(value = "save")
	public String save(Bill bill, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bill)){
			return form(bill, model);
		}
		billService.save(bill);
		addMessage(redirectAttributes, "保存账单成功");
		return "redirect:"+Global.getAdminPath()+"/trade/bill/?repage";
	}
	
	@RequiresPermissions("trade:bill:edit")
	@RequestMapping(value = "delete")
	public String delete(Bill bill, RedirectAttributes redirectAttributes) {
		billService.delete(bill);
		addMessage(redirectAttributes, "删除账单成功");
		return "redirect:"+Global.getAdminPath()+"/trade/bill/?repage";
	}

}