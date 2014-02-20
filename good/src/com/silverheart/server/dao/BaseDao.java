package com.silverheart.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDao {

	public abstract boolean createObject(Object object);

	public abstract List<Object> retrieveObjects(int maxResults, int firstResult);

	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("com.silverheart");

	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
