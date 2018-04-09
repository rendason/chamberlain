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
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;
import com.thinkgem.jeesite.modules.assets.service.FixedAssetsService;

/**
 * 固定资产Controller
 * @author dason
 * @version 2018-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/assets/fixedAssets")
public class FixedAssetsController extends BaseController {

	@Autowired
	private FixedAssetsService fixedAssetsService;
	
	@ModelAttribute
	public FixedAssets get(@RequestParam(required=false) String id) {
		FixedAssets entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fixedAssetsService.get(id);
		}
		if (entity == null){
			entity = new FixedAssets();
		}
		return entity;
	}
	
	@RequiresPermissions("assets:fixedAssets:view")
	@RequestMapping(value = {"list", ""})
	public String list(FixedAssets fixedAssets, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FixedAssets> page = fixedAssetsService.findPage(new Page<FixedAssets>(request, response), fixedAssets); 
		model.addAttribute("page", page);
		return "modules/assets/fixedAssetsList";
	}

	@RequiresPermissions("assets:fixedAssets:view")
	@RequestMapping(value = "form")
	public String form(FixedAssets fixedAssets, Model model) {
		model.addAttribute("fixedAssets", fixedAssets);
		return "modules/assets/fixedAssetsForm";
	}

	@RequiresPermissions("assets:fixedAssets:edit")
	@RequestMapping(value = "save")
	public String save(FixedAssets fixedAssets, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fixedAssets)){
			return form(fixedAssets, model);
		}
		fixedAssetsService.save(fixedAssets);
		addMessage(redirectAttributes, "保存固定资产成功");
		return "redirect:"+Global.getAdminPath()+"/assets/fixedAssets/?repage";
	}
	
	@RequiresPermissions("assets:fixedAssets:edit")
	@RequestMapping(value = "delete")
	public String delete(FixedAssets fixedAssets, RedirectAttributes redirectAttributes) {
		fixedAssetsService.delete(fixedAssets);
		addMessage(redirectAttributes, "删除固定资产成功");
		return "redirect:"+Global.getAdminPath()+"/assets/fixedAssets/?repage";
	}

}