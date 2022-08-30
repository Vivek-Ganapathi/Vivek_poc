package com.poc.application.poc1.dao;

import java.util.List;

import com.poc.application.poc1.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDao {
		
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAConfig");
	private EntityManager em;

	public UserDao() {
		em = emf.createEntityManager();
	}
	  
    public User findByUsernameOrEmail(String identifier) {
    	
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.userId = :identifier OR u.email = :identifier", User.class)
                .setParameter("identifier", identifier)
                .setMaxResults(1)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * Find all users.
     *
     * @return
     */
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Find a user by id.
     *
     * @param userId
     * @return
     */
    public User findByUserId(Long userId) {
    	
    	em.getTransaction().begin();
		User user = em.find(User.class, userId);
		em.getTransaction().commit();
		return user;
    }

	public void addUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return ;
		 
	}
}
