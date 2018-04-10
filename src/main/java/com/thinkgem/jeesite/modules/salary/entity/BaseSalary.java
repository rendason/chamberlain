/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 标准薪资Entity
 * @author dason
 * @version 2018-04-10
 */
public class BaseSalary extends DataEntity<BaseSalary> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private Double total;		//薪资
	private List<BaseSalaryItem> baseSalaryItemList = Lists.newArrayList();		// 子表列表
	
	public BaseSalary() {
		super();
	}

	public BaseSalary(String id){
		super(id);
	}

	@NotNull(message="用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<BaseSalaryItem> getBaseSalaryItemList() {
		return baseSalaryItemList;
	}

	public void setBaseSalaryItemList(List<BaseSalaryItem> baseSalaryItemList) {
		this.baseSalaryItemList = baseSalaryItemList;
	}
}