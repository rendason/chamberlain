/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.web;

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
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.service.CashService;

/**
 * 现金Controller
 * @author dason
 * @version 2018-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/assets/cash")
public class CashController extends BaseController {

	@Autowired
	private CashService cashService;
	
	@ModelAttribute
	public Cash get(@RequestParam(required=false) String id) {
		Cash entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cashService.get(id);
		}
		if (entity == null){
			entity = new Cash();
		}
		return entity;
	}
	
	@RequiresPermissions("assets:cash:view")
	@RequestMapping(value = {"list", ""})
	public String list(Cash cash, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cash> page = cashService.findPage(new Page<Cash>(request, response), cash); 
		model.addAttribute("page", page);
		return "modules/assets/cashList";
	}

	@RequiresPermissions("assets:cash:view")
	@RequestMapping(value = "form")
	public String form(Cash cash, Model model) {
		model.addAttribute("cash", cash);
		return "modules/assets/cashForm";
	}

	@RequiresPermissions("assets:cash:edit")
	@RequestMapping(value = "save")
	public String save(Cash cash, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cash)){
			return form(cash, model);
		}
		cashService.save(cash);
		addMessage(redirectAttributes, "保存现金成功");
		return "redirect:"+Global.getAdminPath()+"/assets/cash/?repage";
	}
	
	@RequiresPermissions("assets:cash:edit")
	@RequestMapping(value = "delete")
	public String delete(Cash cash, RedirectAttributes redirectAttributes) {
		cashService.delete(cash);
		addMessage(redirectAttributes, "删除现金成功");
		return "redirect:"+Global.getAdminPath()+"/assets/cash/?repage";
	}

}