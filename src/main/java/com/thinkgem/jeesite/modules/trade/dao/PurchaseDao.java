/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.trade.entity.Purchase;

/**
 * 采购DAO接口
 * @author dason
 * @version 2018-04-10
 */
@MyBatisDao
public interface PurchaseDao extends CrudDao<Purchase> {
	
}