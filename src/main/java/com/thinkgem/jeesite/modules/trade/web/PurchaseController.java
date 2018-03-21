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
import com.thinkgem.jeesite.modules.trade.entity.Purchase;
import com.thinkgem.jeesite.modules.trade.service.PurchaseService;

/**
 * 购买Controller
 * @author maokeluo
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/purchase")
public class PurchaseController extends BaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@ModelAttribute
	public Purchase get(@RequestParam(required=false) String id) {
		Purchase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaseService.get(id);
		}
		if (entity == null){
			entity = new Purchase();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:purchase:view")
	@RequestMapping(value = {"list", ""})
	public String list(Purchase purchase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Purchase> page = purchaseService.findPage(new Page<Purchase>(request, response), purchase); 
		model.addAttribute("page", page);
		return "modules/trade/purchaseList";
	}

	@RequiresPermissions("trade:purchase:view")
	@RequestMapping(value = "form")
	public String form(Purchase purchase, Model model) {
		model.addAttribute("purchase", purchase);
		return "modules/trade/purchaseForm";
	}

	@RequiresPermissions("trade:purchase:edit")
	@RequestMapping(value = "save")
	public String save(Purchase purchase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, purchase)){
			return form(purchase, model);
		}
		purchaseService.save(purchase);
		addMessage(redirectAttributes, "保存购买成功");
		return "redirect:"+Global.getAdminPath()+"/trade/purchase/?repage";
	}
	
	@RequiresPermissions("trade:purchase:edit")
	@RequestMapping(value = "delete")
	public String delete(Purchase purchase, RedirectAttributes redirectAttributes) {
		purchaseService.delete(purchase);
		addMessage(redirectAttributes, "删除购买成功");
		return "redirect:"+Global.getAdminPath()+"/trade/purchase/?repage";
	}

}