/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import com.thinkgem.jeesite.modules.salary.entity.BaseSalary;
import com.thinkgem.jeesite.modules.salary.entity.BaseSalaryItem;
import com.thinkgem.jeesite.modules.salary.entity.MonthlySalaryItem;
import com.thinkgem.jeesite.modules.salary.service.BaseSalaryService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 月度薪资Controller
 * @author dason
 * @version 2018-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/salary/monthlySalary")
public class MonthlySalaryController extends BaseController {

	@Autowired
	private MonthlySalaryService monthlySalaryService;

	@Autowired
	private BaseSalaryService baseSalaryService;

	@Autowired
	private CashService cashService;
	
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
		if (monthlySalary.getPaid() == null) monthlySalary.setPaid(MonthlySalary.UNPAID);
		if (monthlySalary.getYear() == null) monthlySalary.setYear(LocalDate.now().getYear());
		if (monthlySalary.getMonth() == null) monthlySalary.setMonth(LocalDate.now().getMonthValue());
		model.addAttribute("monthlySalary", monthlySalary);
		model.addAttribute("payments", cashService.findList(new Cash()));
		return "modules/salary/monthlySalaryForm";
	}

	@RequiresPermissions("salary:monthlySalary:edit")
	@RequestMapping(value = "pay")
	public String pay(MonthlySalary monthlySalary, RedirectAttributes redirectAttributes) {
		monthlySalaryService.pay(monthlySalary);
		addMessage(redirectAttributes, "支付月度薪资成功");
		return "redirect:"+Global.getAdminPath()+"/salary/monthlySalary/?repage";
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