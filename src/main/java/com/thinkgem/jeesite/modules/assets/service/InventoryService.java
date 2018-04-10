/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.dao.InventoryDao;

/**
 * 库存Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class InventoryService extends CrudService<InventoryDao, Inventory> {

	public Inventory get(String id) {
		return super.get(id);
	}
	
	public List<Inventory> findList(Inventory inventory) {
		return super.findList(inventory);
	}
	
	public Page<Inventory> findPage(Page<Inventory> page, Inventory inventory) {
		return super.findPage(page, inventory);
	}
	
	@Transactional(readOnly = false)
	public void save(Inventory inventory) {
		super.save(inventory);
	}
	
	@Transactional(readOnly = false)
	public void delete(Inventory inventory) {
		super.delete(inventory);
	}
	
}