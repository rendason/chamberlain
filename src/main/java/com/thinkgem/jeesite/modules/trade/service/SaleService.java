/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thinkgem.jeesite.modules.assets.entity.Inventory;
import com.thinkgem.jeesite.modules.assets.service.CashService;
import com.thinkgem.jeesite.modules.assets.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.trade.entity.Sale;
import com.thinkgem.jeesite.modules.trade.dao.SaleDao;
import com.thinkgem.jeesite.modules.trade.entity.SaleItem;
import com.thinkgem.jeesite.modules.trade.dao.SaleItemDao;

/**
 * 销售Service
 * @author dason
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class SaleService extends CrudService<SaleDao, Sale> {

	@Autowired
	private SaleItemDao saleItemDao;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private CashService cashService;
	
	public Sale get(String id) {
		Sale sale = super.get(id);
		sale.setSaleItemList(saleItemDao.findList(new SaleItem(sale)));
		return sale;
	}
	
	public List<Sale> findList(Sale sale) {
		return super.findList(sale);
	}
	
	public Page<Sale> findPage(Page<Sale> page, Sale sale) {
		dataScopeFilter(sale, "sale", "", "id=a.seller_id");
		return super.findPage(page, sale);
	}
	
	@Transactional(readOnly = false)
	public void save(Sale sale) {
		super.save(sale);
		double cost = 0;
		for (SaleItem saleItem : sale.getSaleItemList()){
			if (saleItem.getId() != null && StringUtils.isBlank(saleItem.getId())){
				saleItem.setSale(sale);
				saleItem.preInsert();
				saleItemDao.insert(saleItem);
				cost += saleItem.getQuantity() * saleItem.getPrice();
				Inventory inventory = new Inventory();
				inventory.setName(saleItem.getName());
				inventory.setSellingPrice(saleItem.getPrice());
				inventory.setUnit(saleItem.getUnit());
				inventory = inventoryService.findOne(inventory);
				inventory.setQuantity(inventory.getQuantity() - saleItem.getQuantity());
				inventoryService.save(inventory);
			}
		}
		if (cost != 0) {
			String billName = sale.getSaleItemList().stream().map(SaleItem::getName).collect(Collectors.joining(",", "销售", ""));
			cashService.income(billName, sale.getReceipt(), "散客", cost * sale.getDiscount() / 100 - sale.getExempt());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Sale sale) {
		super.delete(sale);
		saleItemDao.delete(new SaleItem(sale));
	}

	public void enough(Sale sale) {
		List<String> messages = new ArrayList<>();
		for (SaleItem saleItem : sale.getSaleItemList()) {
			if (saleItem.getId() != null && StringUtils.isBlank(saleItem.getId())) {
				Inventory inventory = new Inventory();
				inventory.setName(saleItem.getName());
				inventory.setSellingPrice(saleItem.getPrice());
				inventory.setUnit(saleItem.getUnit());
				inventory = inventoryService.findOne(inventory);
				if (inventory.getQuantity() < saleItem.getQuantity()) {
					messages.add(inventory.getName() + "库存不足");
				}
			}
		}
		if (!messages.isEmpty()) {
			throw new IllegalArgumentException(String.join(",", messages));
		}
	}
	
}