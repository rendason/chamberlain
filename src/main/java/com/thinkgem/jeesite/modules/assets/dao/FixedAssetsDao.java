/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;

/**
 * 固定资产DAO接口
 * @author dason
 * @version 2018-04-09
 */
@MyBatisDao
public interface FixedAssetsDao extends CrudDao<FixedAssets> {

    FixedAssets findOne(FixedAssets fixedAssets);
	
}