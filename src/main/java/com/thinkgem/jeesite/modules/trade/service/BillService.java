/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.trade.entity.Bill;
import com.thinkgem.jeesite.modules.trade.dao.BillDao;

/**
 * 账单Service
 * @author dason
 * @version 2018-04-11
 */
@Service
@Transactional(readOnly = true)
public class BillService extends CrudService<BillDao, Bill> {

	public Bill get(String id) {
		return super.get(id);
	}
	
	public List<Bill> findList(Bill bill) {
		return super.findList(bill);
	}
	
	public Page<Bill> findPage(Page<Bill> page, Bill bill) {
		return super.findPage(page, bill);
	}
	
	@Transactional(readOnly = false)
	public void save(Bill bill) {
		super.save(bill);
	}
	
	@Transactional(readOnly = false)
	public void delete(Bill bill) {
		super.delete(bill);
	}
	
}