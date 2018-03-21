/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assets.entity.Assets;
import com.thinkgem.jeesite.modules.assets.dao.AssetsDao;

/**
 * assetsService
 * @author maokeluo
 * @version 2018-03-21
 */
@Service
@Transactional(readOnly = true)
public class AssetsService extends CrudService<AssetsDao, Assets> {

	public Assets get(String id) {
		return super.get(id);
	}
	
	public List<Assets> findList(Assets assets) {
		return super.findList(assets);
	}
	
	public Page<Assets> findPage(Page<Assets> page, Assets assets) {
		return super.findPage(page, assets);
	}
	
	@Transactional(readOnly = false)
	public void save(Assets assets) {
		super.save(assets);
	}
	
	@Transactional(readOnly = false)
	public void delete(Assets assets) {
		super.delete(assets);
	}
	
}