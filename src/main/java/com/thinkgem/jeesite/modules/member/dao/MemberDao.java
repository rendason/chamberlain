/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.member.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.member.entity.Member;

/**
 * 会员DAO接口
 * @author dason
 * @version 2018-04-10
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {
	
}