/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.trade.entity.Sale;
import com.thinkgem.jeesite.modules.trade.dao.SaleDao;

/**
 * 销售Service
 * @author dason
 * @version 2018-04-09
 */
@Service
@Transactional(readOnly = true)
public class SaleService extends CrudService<SaleDao, Sale> {

	public Sale get(String id) {
		return super.get(id);
	}
	
	public List<Sale> findList(Sale sale) {
		return super.findList(sale);
	}
	
	public Page<Sale> findPage(Page<Sale> page, Sale sale) {
		return super.findPage(page, sale);
	}
	
	@Transactional(readOnly = false)
	public void save(Sale sale) {
		if (sale.getDisaccount() == null) sale.setDisaccount(1.0);
		if (sale.getExempt() == null) sale.setExempt(0.0);
		super.save(sale);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sale sale) {
		super.delete(sale);
	}
	
}