package com.poc.application.poc1.dao;

import java.util.List;

import com.poc.application.poc1.entity.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ItemDao {
	

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAConfig");
	private EntityManager em;

	public ItemDao() {
		em = emf.createEntityManager();
	}
	  
 
	public Item addItem(Item item) {
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
		return item;
		 
	}
	
	public Item updateItemById(String itemId , Item item) {
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
		return item;
		 
	}
	
    public Item findByItemIdOrName(String itemId , String name) {
    	
        List<Item> users = em.createQuery("SELECT u FROM Item u WHERE u.itemId = :itemId OR u.name = :name", Item.class)
                .setParameter("itemId", itemId)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
    
    public Item findByItemId(String itemId) { 	
        
    	em.getTransaction().begin();
    	Item item = em.find(Item.class, itemId);
		em.getTransaction().commit();
		return item;
    }


	public void deleteItemById(String itemId) {
		em.getTransaction().begin();
		em.remove(em.find(Item.class, itemId));
		em.getTransaction().commit();
		return ;
		 
	}
	

}
