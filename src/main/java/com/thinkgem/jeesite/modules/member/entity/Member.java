/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.member.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员Entity
 * @author maokeluo
 * @version 2018-03-22
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String mobile;		// 手机
	private Integer level;		// 等级
	private Double balance;		// 余额
	
	public Member() {
		super();
	}

	public Member(String id){
		super(id);
	}

	@Length(min=1, max=50, message="姓名长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=20, message="手机长度必须介于 1 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@NotNull(message="等级不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@NotNull(message="余额不能为空")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}