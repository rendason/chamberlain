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
import com.thinkgem.jeesite.modules.trade.entity.Sale;
import com.thinkgem.jeesite.modules.trade.service.SaleService;

/**
 * 销售Controller
 * @author maokeluo
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/sale")
public class SaleController extends BaseController {

	@Autowired
	private SaleService saleService;
	
	@ModelAttribute
	public Sale get(@RequestParam(required=false) String id) {
		Sale entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saleService.get(id);
		}
		if (entity == null){
			entity = new Sale();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:sale:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sale sale, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sale> page = saleService.findPage(new Page<Sale>(request, response), sale); 
		model.addAttribute("page", page);
		return "modules/trade/saleList";
	}

	@RequiresPermissions("trade:sale:view")
	@RequestMapping(value = "form")
	public String form(Sale sale, Model model) {
		model.addAttribute("sale", sale);
		return "modules/trade/saleForm";
	}

	@RequiresPermissions("trade:sale:edit")
	@RequestMapping(value = "save")
	public String save(Sale sale, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sale)){
			return form(sale, model);
		}
		saleService.save(sale);
		addMessage(redirectAttributes, "保存销售成功");
		return "redirect:"+Global.getAdminPath()+"/trade/sale/?repage";
	}
	
	@RequiresPermissions("trade:sale:edit")
	@RequestMapping(value = "delete")
	public String delete(Sale sale, RedirectAttributes redirectAttributes) {
		saleService.delete(sale);
		addMessage(redirectAttributes, "删除销售成功");
		return "redirect:"+Global.getAdminPath()+"/trade/sale/?repage";
	}

}