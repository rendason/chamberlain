/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 账单Entity
 * @author dason
 * @version 2018-04-11
 */
public class Bill extends DataEntity<Bill> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String payment;		// 付款人
	private Double amount;		// 金额
	private String payee;		// 收款人
	private Integer type;		// 类型
	
	public Bill() {
		super();
	}

	public Bill(String id){
		super(id);
	}

	@Length(min=1, max=50, message="名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=50, message="付款人长度必须介于 1 和 50 之间")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@NotNull(message="金额不能为空")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=50, message="收款人长度必须介于 1 和 50 之间")
	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	@NotNull(message="类型不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static final int EXPENSE_TYPE = -1;
	public static final int INCOME_TYPE = 1;

}