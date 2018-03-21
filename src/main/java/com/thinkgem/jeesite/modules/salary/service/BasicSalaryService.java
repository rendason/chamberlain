/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.salary.entity.BasicSalary;
import com.thinkgem.jeesite.modules.salary.dao.BasicSalaryDao;

/**
 * 基础薪资Service
 * @author maokeluo
 * @version 2018-03-22
 */
@Service
@Transactional(readOnly = true)
public class BasicSalaryService extends CrudService<BasicSalaryDao, BasicSalary> {

	public BasicSalary get(String id) {
		return super.get(id);
	}
	
	public List<BasicSalary> findList(BasicSalary basicSalary) {
		return super.findList(basicSalary);
	}
	
	public Page<BasicSalary> findPage(Page<BasicSalary> page, BasicSalary basicSalary) {
		return super.findPage(page, basicSalary);
	}
	
	@Transactional(readOnly = false)
	public void save(BasicSalary basicSalary) {
		super.save(basicSalary);
	}
	
	@Transactional(readOnly = false)
	public void delete(BasicSalary basicSalary) {
		super.delete(basicSalary);
	}
	
}