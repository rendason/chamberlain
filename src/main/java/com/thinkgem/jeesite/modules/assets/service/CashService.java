/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.dao.CashDao;

/**
 * 现金Service
 * @author dason
 * @version 2018-04-09
 */
@Service
@Transactional(readOnly = true)
public class CashService extends CrudService<CashDao, Cash> {

	public Cash get(String id) {
		return super.get(id);
	}
	
	public List<Cash> findList(Cash cash) {
		return super.findList(cash);
	}
	
	public Page<Cash> findPage(Page<Cash> page, Cash cash) {
		return super.findPage(page, cash);
	}
	
	@Transactional(readOnly = false)
	public void save(Cash cash) {
		super.save(cash);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cash cash) {
		super.delete(cash);
	}
	
}