/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.entity;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.member.entity.Member;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售Entity
 * @author dason
 * @version 2018-04-09
 */
public class Sale extends DataEntity<Sale> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 销售员
	private Member member;		// 会员
	private String name;		// 名称
	private Integer quantity;		// 数量
	private String unit;		// 单位
	private Double price;		// 价格
	private Double disaccount;		// 折扣
	private Double exempt;		// 减免
	private Cash receipt;		//收款方式
	private String remark;		// 备注
	
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
	
	public Double getDisaccount() {
		return disaccount;
	}

	public void setDisaccount(Double disaccount) {
		this.disaccount = disaccount;
	}
	
	public Double getExempt() {
		return exempt;
	}

	public void setExempt(Double exempt) {
		this.exempt = exempt;
	}

	public Cash getReceipt() {
		return receipt;
	}

	public void setReceipt(Cash receipt) {
		this.receipt = receipt;
	}

	@Length(min=0, max=50, message="备注长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}