/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.member.entity.Member;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售Entity
 * @author dason
 * @version 2018-04-10
 */
public class Sale extends DataEntity<Sale> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 销售员
	private Member member;		// 会员
	private Cash receipt;		// 收款方式
	private Integer quantity;		// 数量
	private Integer discount;		// 折扣(%)
	private Double exempt;		// 减免
	private Double expected;		// 应收
	private Double actual;		// 实收
	private List<SaleItem> saleItemList = Lists.newArrayList();		// 子表列表
	
	public Sale() {
		super();
	}

	public Sale(String id){
		super(id);
	}

	@NotNull(message="销售员不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@NotNull(message="付款方式不能为空")
	public Cash getReceipt() {
		return receipt;
	}

	public void setReceipt(Cash receipt) {
		this.receipt = receipt;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Double getExempt() {
		return exempt;
	}

	public void setExempt(Double exempt) {
		this.exempt = exempt;
	}

	public Double getExpected() {
		return expected;
	}

	public void setExpected(Double expected) {
		this.expected = expected;
	}

	public Double getActual() {
		return actual;
	}

	public void setActual(Double actual) {
		this.actual = actual;
	}

	public List<SaleItem> getSaleItemList() {
		return saleItemList;
	}

	public void setSaleItemList(List<SaleItem> saleItemList) {
		this.saleItemList = saleItemList;
	}
}