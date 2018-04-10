/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.modules.assets.dao.CashDao;
import com.thinkgem.jeesite.modules.assets.dao.FixedAssetsDao;
import com.thinkgem.jeesite.modules.assets.dao.InventoryDao;
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;
import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.trade.entity.Purchase;
import com.thinkgem.jeesite.modules.trade.dao.PurchaseDao;
import com.thinkgem.jeesite.modules.trade.entity.PurchaseItem;
import com.thinkgem.jeesite.modules.trade.dao.PurchaseItemDao;

/**
 * 采购Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class PurchaseService extends CrudService<PurchaseDao, Purchase> {

	@Autowired
	private PurchaseItemDao purchaseItemDao;

	@Autowired
	private CashDao cashDao;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private FixedAssetsDao fixedAssetsDao;
	
	public Purchase get(String id) {
		Purchase purchase = super.get(id);
		purchase.setPurchaseItemList(purchaseItemDao.findList(new PurchaseItem(purchase)));
		return purchase;
	}
	
	public List<Purchase> findList(Purchase purchase) {
		return super.findList(purchase);
	}
	
	public Page<Purchase> findPage(Page<Purchase> page, Purchase purchase) {
		return super.findPage(page, purchase);
	}
	
	@Transactional(readOnly = false)
	public void save(Purchase purchase) {
		super.save(purchase);
		for (PurchaseItem purchaseItem : purchase.getPurchaseItemList()){
			if (purchaseItem.getId() == null){
				continue;
			}
			if (PurchaseItem.DEL_FLAG_NORMAL.equals(purchaseItem.getDelFlag())){
				if (StringUtils.isBlank(purchaseItem.getId())){
					purchaseItem.setPurchase(purchase);
					purchaseItem.preInsert();
					purchaseItemDao.insert(purchaseItem);
					Cash cash = cashDao.get(purchase.getPayment());
					cash.setAmount(cash.getAmount() - purchaseItem.getPrice() * purchaseItem.getQuantity());
					cash.preUpdate();
					cashDao.update(cash);
					if (purchase.getType() == Purchase.INVENTORY_TYPE) {
						Inventory inventory = new Inventory();
						inventory.setName(purchaseItem.getName());
						inventory.setUnit(purchaseItem.getUnit());
						inventory.setCostPrice(purchaseItem.getPrice());
						Inventory old = inventoryDao.findOne(inventory);
						if (old != null) {
							old.setQuantity(old.getQuantity() + purchaseItem.getQuantity());
							List<String> remarks = new ArrayList<>();
							if (StringUtils.isNotEmpty(old.getRemarks())) remarks.add(old.getRemarks());
							if (StringUtils.isNotEmpty(purchaseItem.getRemarks())) remarks.add(purchaseItem.getRemarks());
							if (!remarks.isEmpty()) old.setRemarks(String.join(";", remarks));
							old.preUpdate();
							inventoryDao.update(old);
						} else {
							inventory.setQuantity(purchaseItem.getQuantity());
							inventory.setSales(Inventory.NOT_SELL);
							inventory.setSellingPrice(0.0);
							inventory.setRemarks(purchaseItem.getRemarks());
							inventory.preInsert();
							inventoryDao.insert(inventory);
						}
					} else {
						FixedAssets fixedAssets = new FixedAssets();
						fixedAssets.setName(purchaseItem.getName());
						fixedAssets.setUnit(purchaseItem.getUnit());
						fixedAssets.setPrice(purchaseItem.getPrice());
						FixedAssets old = fixedAssetsDao.findOne(fixedAssets);
						if (old != null) {
							old.setQuantity(old.getQuantity() + purchaseItem.getQuantity());
							List<String> remarks = new ArrayList<>();
							if (StringUtils.isNotEmpty(old.getRemarks())) remarks.add(old.getRemarks());
							if (StringUtils.isNotEmpty(purchaseItem.getRemarks())) remarks.add(purchaseItem.getRemarks());
							if (!remarks.isEmpty()) old.setRemarks(String.join(";", remarks));
							old.preUpdate();
							fixedAssetsDao.update(old);
						} else {
							fixedAssets.setQuantity(purchaseItem.getQuantity());
							fixedAssets.setRemarks(purchaseItem.getRemarks());
							fixedAssets.preInsert();
							fixedAssetsDao.insert(fixedAssets);
						}
					}
				}else{
					purchaseItem.preUpdate();
					purchaseItemDao.update(purchaseItem);
				}
			}else{
				purchaseItemDao.delete(purchaseItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Purchase purchase) {
		super.delete(purchase);
		purchaseItemDao.delete(new PurchaseItem(purchase));
	}
	
}