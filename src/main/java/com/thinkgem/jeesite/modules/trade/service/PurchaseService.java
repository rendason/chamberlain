/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.List;

import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;
import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import com.thinkgem.jeesite.modules.assets.service.FixedAssetsService;
import com.thinkgem.jeesite.modules.assets.service.InventoryService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.trade.entity.Purchase;
import com.thinkgem.jeesite.modules.trade.dao.PurchaseDao;

/**
 * 采购Service
 * @author dason
 * @version 2018-04-09
 */
@Service
@Transactional(readOnly = true)
public class PurchaseService extends CrudService<PurchaseDao, Purchase> {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private FixedAssetsService fixedAssetsService;

	@Autowired
	private CashService cashService;

	public Purchase get(String id) {
		return super.get(id);
	}
	
	public List<Purchase> findList(Purchase purchase) {
		return super.findList(purchase);
	}
	
	public Page<Purchase> findPage(Page<Purchase> page, Purchase purchase) {
		return super.findPage(page, purchase);
	}
	
	@Transactional(readOnly = false)
	public void save(Purchase purchase) {
		if (purchase.getType() == 1) {  //1 means inventory
			Inventory inventory = new Inventory();
			inventory.setName(purchase.getName());
			inventory.setUnit(purchase.getUnit());
			inventory.setCostPrice(purchase.getPrice());
			Inventory old = inventoryService.findOne(inventory);
			if (old != null && old.getQuantity() != null) {
				old.setQuantity(old.getQuantity() + purchase.getQuantity());
				inventoryService.save(old);
			} else {
				inventory.setQuantity(purchase.getQuantity());
				inventory.setSellingPrice(0.0);
				inventory.setSales(0);
				inventoryService.save(inventory);
			}
		} else {
			FixedAssets fixedAssets = new FixedAssets();
			fixedAssets.setName(purchase.getName());
			fixedAssets.setUnit(purchase.getUnit());
			fixedAssets.setPrice(purchase.getPrice());
			FixedAssets old = fixedAssetsService.findOne(fixedAssets);
			if (old != null && old.getQuantity() != null) {
				old.setQuantity(old.getQuantity() + purchase.getQuantity());
				fixedAssetsService.save(old);
			} else {
				fixedAssets.setQuantity(purchase.getQuantity());
				fixedAssetsService.save(fixedAssets);
			}
		}
		Cash cash = cashService.get(purchase.getPayment());
		cash.setAmount(cash.getAmount() - purchase.getQuantity() * purchase.getPrice());
		cashService.save(cash);
		super.save(purchase);
	}
	
	@Transactional(readOnly = false)
	public void delete(Purchase purchase) {
		super.delete(purchase);
	}
	
}