/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.assets.entity.Inventory;

/**
 * 库存DAO接口
 * @author dason
 * @version 2018-04-10
 */
@MyBatisDao
public interface InventoryDao extends CrudDao<Inventory> {

    Inventory findOne(Inventory inventory);
}