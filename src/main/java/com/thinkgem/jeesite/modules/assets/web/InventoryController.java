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
import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.service.InventoryService;

/**
 * 库存Controller
 * @author dason
 * @version 2018-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/assets/inventory")
public class InventoryController extends BaseController {

	@Autowired
	private InventoryService inventoryService;
	
	@ModelAttribute
	public Inventory get(@RequestParam(required=false) String id) {
		Inventory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = inventoryService.get(id);
		}
		if (entity == null){
			entity = new Inventory();
		}
		return entity;
	}
	
	@RequiresPermissions("assets:inventory:view")
	@RequestMapping(value = {"list", ""})
	public String list(Inventory inventory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Inventory> page = inventoryService.findPage(new Page<Inventory>(request, response), inventory); 
		model.addAttribute("page", page);
		return "modules/assets/inventoryList";
	}

	@RequiresPermissions("assets:inventory:view")
	@RequestMapping(value = "form")
	public String form(Inventory inventory, Model model) {
		model.addAttribute("inventory", inventory);
		return "modules/assets/inventoryForm";
	}

	@RequiresPermissions("assets:inventory:edit")
	@RequestMapping(value = "save")
	public String save(Inventory inventory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, inventory)){
			return form(inventory, model);
		}
		inventoryService.save(inventory);
		addMessage(redirectAttributes, "保存库存成功");
		return "redirect:"+Global.getAdminPath()+"/assets/inventory/?repage";
	}
	
	@RequiresPermissions("assets:inventory:edit")
	@RequestMapping(value = "delete")
	public String delete(Inventory inventory, RedirectAttributes redirectAttributes) {
		inventoryService.delete(inventory);
		addMessage(redirectAttributes, "删除库存成功");
		return "redirect:"+Global.getAdminPath()+"/assets/inventory/?repage";
	}

}