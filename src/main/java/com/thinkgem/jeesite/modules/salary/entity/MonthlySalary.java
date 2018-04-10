/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度薪资Entity
 * @author dason
 * @version 2018-04-10
 */
public class MonthlySalary extends DataEntity<MonthlySalary> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private Integer year;		// 年
	private Integer month;		// 月
	private Double standard;	//标准
	private Double planned;		//应发
	private Double deducted;	//扣减
	private Double actual;		//实发
	private Cash payment;		// 支付方式
	private Integer paid;		// 支付
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

	public Double getStandard() {
		return standard;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public Double getPlanned() {
		return planned;
	}

	public void setPlanned(Double planned) {
		this.planned = planned;
	}

	public Double getDeducted() {
		return deducted;
	}

	public void setDeducted(Double deducted) {
		this.deducted = deducted;
	}

	public Double getActual() {
		return actual;
	}

	public void setActual(Double actual) {
		this.actual = actual;
	}

	@NotNull(message="支付方式不能为空")
	public Cash getPayment() {
		return payment;
	}

	public void setPayment(Cash payment) {
		this.payment = payment;
	}

	@NotNull(message="支付不能为空")
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

	public static final int PAID = 1;
	public static final int UNPAID = 2;
}