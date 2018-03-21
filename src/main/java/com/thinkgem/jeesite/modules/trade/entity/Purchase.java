/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 购买Entity
 * @author maokeluo
 * @version 2018-03-22
 */
public class Purchase extends DataEntity<Purchase> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 品名
	private Integer quantity;		// 数量
	private Double price;		// 单价
	private String unit;		// 单位
	private String saler;		// 卖方
	private User consumer;		// 买方
	private String remark;		// 备注
	
	public Purchase() {
		super();
	}

	public Purchase(String id){
		super(id);
	}

	@Length(min=1, max=50, message="品名长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="数量不能为空")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@NotNull(message="单价不能为空")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=1, max=20, message="单位长度必须介于 1 和 20 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=50, message="卖方长度必须介于 0 和 50 之间")
	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}
	
	public User getConsumer() {
		return consumer;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}
	
	@Length(min=0, max=50, message="备注长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}