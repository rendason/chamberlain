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
import com.thinkgem.jeesite.modules.salary.entity.MonthlySalary;
import com.thinkgem.jeesite.modules.salary.service.MonthlySalaryService;

/**
 * 月度薪资Controller
 * @author dason
 * @version 2018-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/monthlySalary")
public class MonthlySalaryController extends BaseController {

	@Autowired
	private MonthlySalaryService monthlySalaryService;
	
	@ModelAttribute
	public MonthlySalary get(@RequestParam(required=false) String id) {
		MonthlySalary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monthlySalaryService.get(id);
		}
		if (entity == null){
			entity = new MonthlySalary();
		}
		return entity;
	}
	
	@RequiresPermissions("salary:monthlySalary:view")
	@RequestMapping(value = {"list", ""})
	public String list(MonthlySalary monthlySalary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthlySalary> page = monthlySalaryService.findPage(new Page<MonthlySalary>(request, response), monthlySalary); 
		model.addAttribute("page", page);
		return "modules/salary/monthlySalaryList";
	}

	@RequiresPermissions("salary:monthlySalary:view")
	@RequestMapping(value = "form")
	public String form(MonthlySalary monthlySalary, Model model) {
		model.addAttribute("monthlySalary", monthlySalary);
		return "modules/salary/monthlySalaryForm";
	}

	@RequiresPermissions("salary:monthlySalary:edit")
	@RequestMapping(value = "save")
	public String save(MonthlySalary monthlySalary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthlySalary)){
			return form(monthlySalary, model);
		}
		monthlySalaryService.save(monthlySalary);
		addMessage(redirectAttributes, "保存月度薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/monthlySalary/?repage";
	}
	
	@RequiresPermissions("salary:monthlySalary:edit")
	@RequestMapping(value = "delete")
	public String delete(MonthlySalary monthlySalary, RedirectAttributes redirectAttributes) {
		monthlySalaryService.delete(monthlySalary);
		addMessage(redirectAttributes, "删除月度薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/monthlySalary/?repage";
	}

}