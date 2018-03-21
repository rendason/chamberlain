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
import com.thinkgem.jeesite.modules.salary.entity.BasicSalary;
import com.thinkgem.jeesite.modules.salary.service.BasicSalaryService;

/**
 * 基础薪资Controller
 * @author maokeluo
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/basicSalary")
public class BasicSalaryController extends BaseController {

	@Autowired
	private BasicSalaryService basicSalaryService;
	
	@ModelAttribute
	public BasicSalary get(@RequestParam(required=false) String id) {
		BasicSalary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basicSalaryService.get(id);
		}
		if (entity == null){
			entity = new BasicSalary();
		}
		return entity;
	}
	
	@RequiresPermissions("salary:basicSalary:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasicSalary basicSalary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasicSalary> page = basicSalaryService.findPage(new Page<BasicSalary>(request, response), basicSalary); 
		model.addAttribute("page", page);
		return "modules/salary/basicSalaryList";
	}

	@RequiresPermissions("salary:basicSalary:view")
	@RequestMapping(value = "form")
	public String form(BasicSalary basicSalary, Model model) {
		model.addAttribute("basicSalary", basicSalary);
		return "modules/salary/basicSalaryForm";
	}

	@RequiresPermissions("salary:basicSalary:edit")
	@RequestMapping(value = "save")
	public String save(BasicSalary basicSalary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basicSalary)){
			return form(basicSalary, model);
		}
		basicSalaryService.save(basicSalary);
		addMessage(redirectAttributes, "保存基础薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/basicSalary/?repage";
	}
	
	@RequiresPermissions("salary:basicSalary:edit")
	@RequestMapping(value = "delete")
	public String delete(BasicSalary basicSalary, RedirectAttributes redirectAttributes) {
		basicSalaryService.delete(basicSalary);
		addMessage(redirectAttributes, "删除基础薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/basicSalary/?repage";
	}

}