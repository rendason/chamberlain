/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;
import com.thinkgem.jeesite.modules.assets.dao.FixedAssetsDao;

/**
 * 固定资产Service
 * @author dason
 * @version 2018-04-09
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetsService extends CrudService<FixedAssetsDao, FixedAssets> {

	public FixedAssets get(String id) {
		return super.get(id);
	}

	public FixedAssets findOne(FixedAssets fixedAssets) {
		return dao.findOne(fixedAssets);
	}
	
	public List<FixedAssets> findList(FixedAssets fixedAssets) {
		return super.findList(fixedAssets);
	}
	
	public Page<FixedAssets> findPage(Page<FixedAssets> page, FixedAssets fixedAssets) {
		return super.findPage(page, fixedAssets);
	}
	
	@Transactional(readOnly = false)
	public void save(FixedAssets fixedAssets) {
		super.save(fixedAssets);
	}
	
	@Transactional(readOnly = false)
	public void delete(FixedAssets fixedAssets) {
		super.delete(fixedAssets);
	}
	
}