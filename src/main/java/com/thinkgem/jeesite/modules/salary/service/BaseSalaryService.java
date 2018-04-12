/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.service;

import java.util.Collections;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.salary.entity.BaseSalary;
import com.thinkgem.jeesite.modules.salary.dao.BaseSalaryDao;
import com.thinkgem.jeesite.modules.salary.entity.BaseSalaryItem;
import com.thinkgem.jeesite.modules.salary.dao.BaseSalaryItemDao;

/**
 * 标准薪资Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class BaseSalaryService extends CrudService<BaseSalaryDao, BaseSalary> {

	@Autowired
	private BaseSalaryItemDao baseSalaryItemDao;
	
	public BaseSalary get(String id) {
		BaseSalary baseSalary = super.get(id);
		baseSalary.setBaseSalaryItemList(baseSalaryItemDao.findList(new BaseSalaryItem(baseSalary)));
		return baseSalary;
	}

	public BaseSalary findOne(BaseSalary baseSalary) {
		if (baseSalary.getUser() != null && (baseSalary = dao.findOne(baseSalary)) != null) {
			baseSalary.setBaseSalaryItemList(baseSalaryItemDao.findList(new BaseSalaryItem(baseSalary)));
			return baseSalary;
		}
		baseSalary = new BaseSalary();
		baseSalary.setBaseSalaryItemList(Collections.emptyList());
		return baseSalary;
	}
	
	public List<BaseSalary> findList(BaseSalary baseSalary) {
		return super.findList(baseSalary);
	}
	
	public Page<BaseSalary> findPage(Page<BaseSalary> page, BaseSalary baseSalary) {
		dataScopeFilter(baseSalary, "baseSalary", "", "id=a.user_id");
		return super.findPage(page, baseSalary);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseSalary baseSalary) {
		super.save(baseSalary);
		for (BaseSalaryItem baseSalaryItem : baseSalary.getBaseSalaryItemList()){
			if (baseSalaryItem.getId() == null){
				continue;
			}
			if (BaseSalaryItem.DEL_FLAG_NORMAL.equals(baseSalaryItem.getDelFlag())){
				if (StringUtils.isBlank(baseSalaryItem.getId())){
					baseSalaryItem.setSalary(baseSalary);
					baseSalaryItem.preInsert();
					baseSalaryItemDao.insert(baseSalaryItem);
				}else{
					baseSalaryItem.preUpdate();
					baseSalaryItemDao.update(baseSalaryItem);
				}
			}else{
				baseSalaryItemDao.delete(baseSalaryItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseSalary baseSalary) {
		super.delete(baseSalary);
		baseSalaryItemDao.delete(new BaseSalaryItem(baseSalary));
	}
	
}