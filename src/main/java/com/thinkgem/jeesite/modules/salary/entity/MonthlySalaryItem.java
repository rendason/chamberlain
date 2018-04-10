/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salary.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度薪资Entity
 * @author dason
 * @version 2018-04-10
 */
public class MonthlySalaryItem extends DataEntity<MonthlySalaryItem> {
	
	private static final long serialVersionUID = 1L;
	private MonthlySalary salary;		// salary_id 父类
	private String name;		// 名称
	private Double amount;		// 金额
	private Integer coefficient;		// 类型
	
	public MonthlySalaryItem() {
		super();
	}

	public MonthlySalaryItem(String id){
		super(id);
	}

	public MonthlySalaryItem(MonthlySalary salary){
		this.salary = salary;
	}

	@Length(min=1, max=64, message="salary_id长度必须介于 1 和 64 之间")
	public MonthlySalary getSalary() {
		return salary;
	}

	public void setSalary(MonthlySalary salary) {
		this.salary = salary;
	}
	
	@Length(min=1, max=50, message="名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="金额不能为空")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@NotNull(message="类型不能为空")
	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}
	
}