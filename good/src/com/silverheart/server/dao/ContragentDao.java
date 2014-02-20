package com.silverheart.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import com.silverheart.server.domain.Contragent;

public class ContragentDao extends BaseDao {

	public boolean createContragent(Contragent contragent) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(contragent);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return true;
	}

	@Override
	public boolean createObject(Object object) {
		return createContragent((Contragent) object);
	}

	@Override
	public List<Object> retrieveObjects(int maxResults, int firstResult) {

		EntityManager em = createEntityManager();
		List<Object> list = null;

		try {
			TypedQuery<Object> query = em.createQuery("select a from Contragent a", Object.class);
	/*		query.setMaxResults(maxResults);
			query.setFirstResult(firstResult); */
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

	public ContragentDao updateContragent(ContragentDao contragent) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		ContragentDao contragent2 = null;

		try {
			tx.begin();
			contragent2 = em.merge(contragent);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return contragent2;
	}

	public void deleteContragent(ContragentDao contragent) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(contragent));
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public ContragentDao retrieveContragent(String name) {

		EntityManager em = createEntityManager();
		ContragentDao account = null;

		try {
			TypedQuery<ContragentDao> query = em.createQuery("select a from Contragent a where a.name = ?1",
					ContragentDao.class);
			query.setParameter(1, name);
			account = query.getSingleResult();
		} finally {
			em.close();
		}

		return account;
	}
}
