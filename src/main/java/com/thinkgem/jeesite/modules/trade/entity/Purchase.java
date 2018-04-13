/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 采购Entity
 * @author dason
 * @version 2018-04-10
 */
public class Purchase extends DataEntity<Purchase> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 采购员
	private Integer type;		// 类型
	private String seller;		// 供货商
	private Integer kind;		// 种类
	private Double total;		// 金额
	private Cash payment;		// 支付方式
	private List<PurchaseItem> purchaseItemList = Lists.newArrayList();		// 子表列表
	
	public Purchase() {
		super();
	}

	public Purchase(String id){
		super(id);
	}

	@NotNull(message="采购员不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull(message="类型不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=1, max=50, message="供货商长度必须介于 1 和 50 之间")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@NotNull(message="支付方式不能为空")
	public Cash getPayment() {
		return payment;
	}

	public void setPayment(Cash payment) {
		this.payment = payment;
	}

	public List<PurchaseItem> getPurchaseItemList() {
		return purchaseItemList;
	}

	public void setPurchaseItemList(List<PurchaseItem> purchaseItemList) {
		this.purchaseItemList = purchaseItemList;
	}

	public static final int INVENTORY_TYPE = 1;
	public static final int FIXED_ASSETS_TYPE = 2;
}