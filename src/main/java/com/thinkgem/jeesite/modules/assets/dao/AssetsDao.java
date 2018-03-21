/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assets.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.assets.entity.Assets;

/**
 * assetsDAO接口
 * @author maokeluo
 * @version 2018-03-21
 */
@MyBatisDao
public interface AssetsDao extends CrudDao<Assets> {
	
}