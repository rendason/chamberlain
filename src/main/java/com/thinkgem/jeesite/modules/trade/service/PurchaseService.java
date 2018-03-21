/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.trade.entity.Purchase;
import com.thinkgem.jeesite.modules.trade.dao.PurchaseDao;

/**
 * 购买Service
 * @author maokeluo
 * @version 2018-03-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaseService extends CrudService<PurchaseDao, Purchase> {

	public Purchase get(String id) {
		return super.get(id);
	}
	
	public List<Purchase> findList(Purchase purchase) {
		return super.findList(purchase);
	}
	
	public Page<Purchase> findPage(Page<Purchase> page, Purchase purchase) {
		return super.findPage(page, purchase);
	}
	
	@Transactional(readOnly = false)
	public void save(Purchase purchase) {
		if (purchase.getConsumer() == null) {
			purchase.setConsumer(purchase.getCurrentUser());
		}
		super.save(purchase);
	}
	
	@Transactional(readOnly = false)
	public void delete(Purchase purchase) {
		super.delete(purchase);
	}
	
}