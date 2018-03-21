/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 基础薪资Entity
 * @author maokeluo
 * @version 2018-03-22
 */
public class BasicSalary extends DataEntity<BasicSalary> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private String itemName;		// 项目
	private Double amount;		// 金额
	
	public BasicSalary() {
		super();
	}

	public BasicSalary(String id){
		super(id);
	}

	@NotNull(message="用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=50, message="项目长度必须介于 1 和 50 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@NotNull(message="金额不能为空")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}