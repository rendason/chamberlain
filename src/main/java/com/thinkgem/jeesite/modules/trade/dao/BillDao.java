/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.trade.entity.Bill;

/**
 * 账单DAO接口
 * @author dason
 * @version 2018-04-11
 */
@MyBatisDao
public interface BillDao extends CrudDao<Bill> {
	
}