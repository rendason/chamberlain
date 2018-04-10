/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售Entity
 * @author dason
 * @version 2018-04-10
 */
public class SaleItem extends DataEntity<SaleItem> {
	
	private static final long serialVersionUID = 1L;
	private Sale sale;		// sale_id 父类
	private String name;		// 名称
	private Integer quantity;		// 数量
	private String unit;		// 单位
	private Double price;		// 价格
	
	public SaleItem() {
		super();
	}

	public SaleItem(String id){
		super(id);
	}

	public SaleItem(Sale sale){
		this.sale = sale;
	}

	@Length(min=1, max=64, message="sale_id长度必须介于 1 和 64 之间")
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	@Length(min=1, max=50, message="名称长度必须介于 1 和 50 之间")
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
	
	@Length(min=1, max=20, message="单位长度必须介于 1 和 20 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="价格不能为空")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}