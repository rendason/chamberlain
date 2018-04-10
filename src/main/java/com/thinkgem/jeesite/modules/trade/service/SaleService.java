/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.trade.service;

import java.util.List;

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
	
	public Sale get(String id) {
		Sale sale = super.get(id);
		sale.setSaleItemList(saleItemDao.findList(new SaleItem(sale)));
		return sale;
	}
	
	public List<Sale> findList(Sale sale) {
		return super.findList(sale);
	}
	
	public Page<Sale> findPage(Page<Sale> page, Sale sale) {
		return super.findPage(page, sale);
	}
	
	@Transactional(readOnly = false)
	public void save(Sale sale) {
		if (sale.getDiscount() == null) sale.setDiscount(100);
		if (sale.getExempt() == null) sale.setExempt(0.0);
		super.save(sale);
		for (SaleItem saleItem : sale.getSaleItemList()){
			if (saleItem.getId() == null){
				continue;
			}
			if (SaleItem.DEL_FLAG_NORMAL.equals(saleItem.getDelFlag())){
				if (StringUtils.isBlank(saleItem.getId())){
					saleItem.setSale(sale);
					saleItem.preInsert();
					saleItemDao.insert(saleItem);
				}else{
					saleItem.preUpdate();
					saleItemDao.update(saleItem);
				}
			}else{
				saleItemDao.delete(saleItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Sale sale) {
		super.delete(sale);
		saleItemDao.delete(new SaleItem(sale));
	}
	
}