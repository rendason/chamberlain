/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.trade.entity.Bill;
import com.thinkgem.jeesite.modules.trade.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.dao.CashDao;

/**
 * 现金Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class CashService extends CrudService<CashDao, Cash> {

	@Autowired
	private BillService billService;

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
		if (cash.getIsNewRecord()){
			Bill bill = new Bill();
			bill.setName(cash.getName() + "开户");
			bill.setPayment(UserUtils.getUser().getName());
			bill.setAmount(cash.getAmount());
			bill.setPayee(cash.getName());
			bill.setType(Bill.INCOME_TYPE);
			billService.save(bill);
			cash.preInsert();
			dao.insert(cash);
		}else{
			Cash old = get(cash);
			if (old != null) {
				double amount = cash.getAmount() - old.getAmount();
				if (amount > 0) {
					Bill bill = new Bill();
					bill.setName(cash.getName() + "充值");
					bill.setPayment(UserUtils.getUser().getName());
					bill.setAmount(amount);
					bill.setPayee(cash.getName());
					bill.setType(Bill.INCOME_TYPE);
					billService.save(bill);
				} else if (amount < 0) {
					Bill bill = new Bill();
					bill.setName(cash.getName() + "提现");
					bill.setPayment(cash.getName());
					bill.setAmount(-amount);
					bill.setPayee(UserUtils.getUser().getName());
					bill.setType(Bill.INCOME_TYPE);
					billService.save(bill);
				}
			}
			cash.preUpdate();
			dao.update(cash);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Cash cash) {
		cash = get(cash);
		if (cash != null) {
			Bill bill = new Bill();
			bill.setName(cash.getName() + "销户");
			bill.setPayment(cash.getName());
			bill.setAmount(cash.getAmount());
			bill.setPayee(UserUtils.getUser().getName());
			bill.setType(Bill.EXPENSE_TYPE);
			billService.save(bill);
		}
		super.delete(cash);
	}

	public void expense(String name, Cash cash, String payee, Double amount) {
		if (amount != null && (cash = get(cash)) != null) {
			cash.setAmount(cash.getAmount() - amount);
			cash.preUpdate();
			dao.update(cash);
			Bill bill = new Bill();
			bill.setName(name);
			bill.setPayment(cash.getName());
			bill.setAmount(amount);
			bill.setPayee(payee);
			bill.setType(Bill.EXPENSE_TYPE);
			billService.save(bill);
		}
	}

	public void income(String name, Cash cash, String payment, Double amount) {
		if (amount != null && (cash = get(cash)) != null) {
			cash.setAmount(cash.getAmount() + amount);
			cash.preUpdate();
			dao.update(cash);
			Bill bill = new Bill();
			bill.setName(name);
			bill.setPayment(payment);
			bill.setAmount(amount);
			bill.setPayee(cash.getName());
			bill.setType(Bill.INCOME_TYPE);
			billService.save(bill);
		}
	}
	
}