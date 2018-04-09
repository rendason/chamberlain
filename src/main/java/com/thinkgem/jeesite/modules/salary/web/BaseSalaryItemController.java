/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.web;

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
import com.thinkgem.jeesite.modules.salary.entity.BaseSalaryItem;
import com.thinkgem.jeesite.modules.salary.service.BaseSalaryItemService;

/**
 * 标准薪资Controller
 * @author dason
 * @version 2018-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/baseSalaryItem")
public class BaseSalaryItemController extends BaseController {

	@Autowired
	private BaseSalaryItemService baseSalaryItemService;
	
	@ModelAttribute
	public BaseSalaryItem get(@RequestParam(required=false) String id) {
		BaseSalaryItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseSalaryItemService.get(id);
		}
		if (entity == null){
			entity = new BaseSalaryItem();
		}
		return entity;
	}
	
	@RequiresPermissions("salary:baseSalaryItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseSalaryItem baseSalaryItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseSalaryItem> page = baseSalaryItemService.findPage(new Page<BaseSalaryItem>(request, response), baseSalaryItem); 
		model.addAttribute("page", page);
		return "modules/salary/baseSalaryItemList";
	}

	@RequiresPermissions("salary:baseSalaryItem:view")
	@RequestMapping(value = "form")
	public String form(BaseSalaryItem baseSalaryItem, Model model) {
		model.addAttribute("baseSalaryItem", baseSalaryItem);
		return "modules/salary/baseSalaryItemForm";
	}

	@RequiresPermissions("salary:baseSalaryItem:edit")
	@RequestMapping(value = "save")
	public String save(BaseSalaryItem baseSalaryItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseSalaryItem)){
			return form(baseSalaryItem, model);
		}
		baseSalaryItemService.save(baseSalaryItem);
		addMessage(redirectAttributes, "保存标准薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/baseSalaryItem/?repage";
	}
	
	@RequiresPermissions("salary:baseSalaryItem:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseSalaryItem baseSalaryItem, RedirectAttributes redirectAttributes) {
		baseSalaryItemService.delete(baseSalaryItem);
		addMessage(redirectAttributes, "删除标准薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/baseSalaryItem/?repage";
	}

}