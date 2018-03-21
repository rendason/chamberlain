/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.salary.entity.MonthlySalary;

/**
 * 月度薪资DAO接口
 * @author maokeluo
 * @version 2018-03-22
 */
@MyBatisDao
public interface MonthlySalaryDao extends CrudDao<MonthlySalary> {
	
}