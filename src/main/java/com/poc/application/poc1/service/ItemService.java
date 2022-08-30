package com.poc.application.poc1.service;

import com.poc.application.poc1.dao.ItemDao;
import com.poc.application.poc1.entity.Item;

public class ItemService {
	
	ItemDao itemDao = new ItemDao();

	public Item addItem(Item item) {	
		return itemDao.addItem(item);	
	}

	public Item updateItem(Item item, String itemId) {
		return itemDao.updateItemById(itemId, item);
	}

	public void deleteItem(String itemId) {
		itemDao.deleteItemById(itemId);
	}

	public Item getItemByIdOrName(String itemId, String name) {	
		return itemDao.findByItemIdOrName(itemId, name);	
	}

}
