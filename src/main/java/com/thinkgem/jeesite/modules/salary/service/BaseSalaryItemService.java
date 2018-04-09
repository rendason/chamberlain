/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.salary.entity.BaseSalaryItem;
import com.thinkgem.jeesite.modules.salary.dao.BaseSalaryItemDao;

/**
 * 标准薪资Service
 * @author dason
 * @version 2018-04-09
 */
@Service
@Transactional(readOnly = true)
public class BaseSalaryItemService extends CrudService<BaseSalaryItemDao, BaseSalaryItem> {

	public BaseSalaryItem get(String id) {
		return super.get(id);
	}
	
	public List<BaseSalaryItem> findList(BaseSalaryItem baseSalaryItem) {
		return super.findList(baseSalaryItem);
	}
	
	public Page<BaseSalaryItem> findPage(Page<BaseSalaryItem> page, BaseSalaryItem baseSalaryItem) {
		return super.findPage(page, baseSalaryItem);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseSalaryItem baseSalaryItem) {
		super.save(baseSalaryItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseSalaryItem baseSalaryItem) {
		super.delete(baseSalaryItem);
	}
	
}