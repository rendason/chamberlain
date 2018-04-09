/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度薪资Entity
 * @author dason
 * @version 2018-04-09
 */
public class MonthlySalary extends DataEntity<MonthlySalary> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private Integer year;		// 年
	private Integer month;		// 月
	private String paid;		// 支付
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
	
	@Length(min=1, max=1, message="支付长度必须介于 1 和 1 之间")
	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}
	
	public List<MonthlySalaryItem> getMonthlySalaryItemList() {
		return monthlySalaryItemList;
	}

	public void setMonthlySalaryItemList(List<MonthlySalaryItem> monthlySalaryItemList) {
		this.monthlySalaryItemList = monthlySalaryItemList;
	}
}