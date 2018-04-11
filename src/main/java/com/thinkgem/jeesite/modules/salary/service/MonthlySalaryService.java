/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.service;

import java.util.List;

import com.thinkgem.jeesite.modules.assets.dao.CashDao;
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.salary.entity.MonthlySalary;
import com.thinkgem.jeesite.modules.salary.dao.MonthlySalaryDao;
import com.thinkgem.jeesite.modules.salary.entity.MonthlySalaryItem;
import com.thinkgem.jeesite.modules.salary.dao.MonthlySalaryItemDao;

/**
 * 月度薪资Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class MonthlySalaryService extends CrudService<MonthlySalaryDao, MonthlySalary> {

	@Autowired
	private MonthlySalaryItemDao monthlySalaryItemDao;

	@Autowired
	private CashService cashService;
	
	public MonthlySalary get(String id) {
		MonthlySalary monthlySalary = super.get(id);
		monthlySalary.setMonthlySalaryItemList(monthlySalaryItemDao.findList(new MonthlySalaryItem(monthlySalary)));
		return monthlySalary;
	}
	
	public List<MonthlySalary> findList(MonthlySalary monthlySalary) {
		return super.findList(monthlySalary);
	}
	
	public Page<MonthlySalary> findPage(Page<MonthlySalary> page, MonthlySalary monthlySalary) {
		return super.findPage(page, monthlySalary);
	}
	
	@Transactional(readOnly = false)
	public void save(MonthlySalary monthlySalary) {
		super.save(monthlySalary);
		for (MonthlySalaryItem monthlySalaryItem : monthlySalary.getMonthlySalaryItemList()){
			if (monthlySalaryItem.getId() == null){
				continue;
			}
			if (MonthlySalaryItem.DEL_FLAG_NORMAL.equals(monthlySalaryItem.getDelFlag())){
				if (StringUtils.isBlank(monthlySalaryItem.getId())){
					monthlySalaryItem.setSalary(monthlySalary);
					monthlySalaryItem.preInsert();
					monthlySalaryItemDao.insert(monthlySalaryItem);
				}else{
					monthlySalaryItem.preUpdate();
					monthlySalaryItemDao.update(monthlySalaryItem);
				}
			}else{
				monthlySalaryItemDao.delete(monthlySalaryItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthlySalary monthlySalary) {
		super.delete(monthlySalary);
		monthlySalaryItemDao.delete(new MonthlySalaryItem(monthlySalary));
	}

	@Transactional(readOnly = false)
	public void pay(MonthlySalary monthlySalary) {
		MonthlySalary result = get(monthlySalary);
		if (result.getPaid() == MonthlySalary.UNPAID) {
			String billName = String.format("支付%s%d年%d月薪资", result.getUser().getName(), result.getYear(), result.getMonth());
			cashService.expense(billName, result.getPayment(), monthlySalary.getUser().getName(), result.getActual());
			result.setPaid(MonthlySalary.PAID);
			save(result);
		}
	}
	
}