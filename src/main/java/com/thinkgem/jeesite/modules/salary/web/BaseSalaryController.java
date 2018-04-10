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
import com.thinkgem.jeesite.modules.salary.entity.BaseSalary;
import com.thinkgem.jeesite.modules.salary.service.BaseSalaryService;

/**
 * 标准薪资Controller
 * @author dason
 * @version 2018-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/baseSalary")
public class BaseSalaryController extends BaseController {

	@Autowired
	private BaseSalaryService baseSalaryService;
	
	@ModelAttribute
	public BaseSalary get(@RequestParam(required=false) String id) {
		BaseSalary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseSalaryService.get(id);
		}
		if (entity == null){
			entity = new BaseSalary();
		}
		return entity;
	}
	
	@RequiresPermissions("salary:baseSalary:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseSalary baseSalary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseSalary> page = baseSalaryService.findPage(new Page<BaseSalary>(request, response), baseSalary); 
		model.addAttribute("page", page);
		return "modules/salary/baseSalaryList";
	}

	@RequiresPermissions("salary:baseSalary:view")
	@RequestMapping(value = "form")
	public String form(BaseSalary baseSalary, Model model) {
		model.addAttribute("baseSalary", baseSalary);
		return "modules/salary/baseSalaryForm";
	}

	@RequiresPermissions("salary:baseSalary:edit")
	@RequestMapping(value = "save")
	public String save(BaseSalary baseSalary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseSalary)){
			return form(baseSalary, model);
		}
		baseSalaryService.save(baseSalary);
		addMessage(redirectAttributes, "保存标准薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/baseSalary/?repage";
	}
	
	@RequiresPermissions("salary:baseSalary:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseSalary baseSalary, RedirectAttributes redirectAttributes) {
		baseSalaryService.delete(baseSalary);
		addMessage(redirectAttributes, "删除标准薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/baseSalary/?repage";
	}

}