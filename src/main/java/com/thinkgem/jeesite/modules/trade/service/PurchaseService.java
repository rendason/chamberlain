/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.thinkgem.jeesite.modules.assets.dao.CashDao;
import com.thinkgem.jeesite.modules.assets.dao.FixedAssetsDao;
import com.thinkgem.jeesite.modules.assets.dao.InventoryDao;
import com.thinkgem.jeesite.modules.assets.entity.Cash;
import com.thinkgem.jeesite.modules.assets.entity.FixedAssets;
import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import com.thinkgem.jeesite.modules.assets.service.FixedAssetsService;
import com.thinkgem.jeesite.modules.assets.service.InventoryService;
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
	private CashService cashService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private FixedAssetsService fixedAssetsService;
	
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
		double cost = 0;
		for (PurchaseItem purchaseItem : purchase.getPurchaseItemList()){
			if (purchaseItem.getId() == null){
				continue;
			}
			if (PurchaseItem.DEL_FLAG_NORMAL.equals(purchaseItem.getDelFlag())){
				if (StringUtils.isBlank(purchaseItem.getId())){
					purchaseItem.setPurchase(purchase);
					purchaseItem.preInsert();
					purchaseItemDao.insert(purchaseItem);
					cost += purchaseItem.getPrice() * purchaseItem.getQuantity();
					if (purchase.getType() == Purchase.INVENTORY_TYPE) {
						Inventory inventory = new Inventory();
						inventory.setName(purchaseItem.getName());
						inventory.setUnit(purchaseItem.getUnit());
						inventory.setCostPrice(purchaseItem.getPrice());
						Inventory old = inventoryService.findOne(inventory);
						if (old != null) {
							old.setQuantity(old.getQuantity() + purchaseItem.getQuantity());
							old.setRemarks(combineRemarks(old.getRemarks(), purchaseItem.getRemarks()));
							inventoryService.save(old);
						} else {
							inventory.setQuantity(purchaseItem.getQuantity());
							inventory.setSales(Inventory.NOT_SELL);
							inventory.setSellingPrice(0.0);
							inventory.setRemarks(purchaseItem.getRemarks());
							inventoryService.save(inventory);
						}
					} else {
						FixedAssets fixedAssets = new FixedAssets();
						fixedAssets.setName(purchaseItem.getName());
						fixedAssets.setUnit(purchaseItem.getUnit());
						fixedAssets.setPrice(purchaseItem.getPrice());
						FixedAssets old = fixedAssetsService.findOne(fixedAssets);
						if (old != null) {
							old.setQuantity(old.getQuantity() + purchaseItem.getQuantity());
							old.setRemarks(combineRemarks(old.getRemarks(), purchaseItem.getRemarks()));
							fixedAssetsService.save(old);
						} else {
							fixedAssets.setQuantity(purchaseItem.getQuantity());
							fixedAssets.setRemarks(purchaseItem.getRemarks());
							fixedAssetsService.save(fixedAssets);
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
		if (cost != 0) {
			cashService.expense("支付" + purchase.getSeller() + "货款", purchase.getPayment(), purchase.getSeller(), cost);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Purchase purchase) {
		super.delete(purchase);
		purchaseItemDao.delete(new PurchaseItem(purchase));
	}

	private String combineRemarks(String remarks1, String remarks2) {
		List<String> remarks = new ArrayList<>(2);
		if (StringUtils.isNotEmpty(remarks1)) remarks.add(remarks1);
		if (StringUtils.isNotEmpty(remarks2)) remarks.add(remarks2);
		return remarks.isEmpty() ? null : String.join(";", remarks);
	}
	
}