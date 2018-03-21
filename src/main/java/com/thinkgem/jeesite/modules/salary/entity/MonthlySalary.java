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
 * 月度薪资Entity
 * @author maokeluo
 * @version 2018-03-22
 */
public class MonthlySalary extends DataEntity<MonthlySalary> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private Integer year;		// 年
	private Integer month;		// 月
	private Integer paid;		// 是否支付
	private List<MonthlySalaryItem> monthlySalaryItemList = Lists.newArrayList();		// 子表列表
	
	public MonthlySalary() {
		super();
	}

	public MonthlySalary(String id){
		super(id);
	}

	@NotNull(message="用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull(message="年不能为空")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	@NotNull(message="月不能为空")
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@NotNull(message="是否支付不能为空")
	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}
	
	public List<MonthlySalaryItem> getMonthlySalaryItemList() {
		return monthlySalaryItemList;
	}

	public void setMonthlySalaryItemList(List<MonthlySalaryItem> monthlySalaryItemList) {
		this.monthlySalaryItemList = monthlySalaryItemList;
	}
}