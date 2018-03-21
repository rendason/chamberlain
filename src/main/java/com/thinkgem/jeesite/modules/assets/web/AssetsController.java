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
import com.thinkgem.jeesite.modules.assets.entity.Assets;
import com.thinkgem.jeesite.modules.assets.service.AssetsService;

/**
 * assetsController
 * @author maokeluo
 * @version 2018-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/assets/assets")
public class AssetsController extends BaseController {

	@Autowired
	private AssetsService assetsService;
	
	@ModelAttribute
	public Assets get(@RequestParam(required=false) String id) {
		Assets entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = assetsService.get(id);
		}
		if (entity == null){
			entity = new Assets();
		}
		return entity;
	}
	
	@RequiresPermissions("assets:assets:view")
	@RequestMapping(value = {"list", ""})
	public String list(Assets assets, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Assets> page = assetsService.findPage(new Page<Assets>(request, response), assets); 
		model.addAttribute("page", page);
		return "modules/assets/assetsList";
	}

	@RequiresPermissions("assets:assets:view")
	@RequestMapping(value = "form")
	public String form(Assets assets, Model model) {
		model.addAttribute("assets", assets);
		return "modules/assets/assetsForm";
	}

	@RequiresPermissions("assets:assets:edit")
	@RequestMapping(value = "save")
	public String save(Assets assets, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, assets)){
			return form(assets, model);
		}
		assetsService.save(assets);
		addMessage(redirectAttributes, "保存资产成功");
		return "redirect:"+Global.getAdminPath()+"/assets/assets/?repage";
	}
	
	@RequiresPermissions("assets:assets:edit")
	@RequestMapping(value = "delete")
	public String delete(Assets assets, RedirectAttributes redirectAttributes) {
		assetsService.delete(assets);
		addMessage(redirectAttributes, "删除资产成功");
		return "redirect:"+Global.getAdminPath()+"/assets/assets/?repage";
	}

}