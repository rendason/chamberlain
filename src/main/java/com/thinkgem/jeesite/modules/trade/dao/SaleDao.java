/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.trade.entity.Sale;

/**
 * 销售DAO接口
 * @author maokeluo
 * @version 2018-03-22
 */
@MyBatisDao
public interface SaleDao extends CrudDao<Sale> {
	
}