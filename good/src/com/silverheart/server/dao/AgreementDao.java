package com.silverheart.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import com.silverheart.server.domain.Agreement;

public class AgreementDao extends BaseDao {

	public boolean createAgreement(Agreement agreement) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(agreement);
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
		return createAgreement((Agreement) object);
	}

	@Override
	public List<Object> retrieveObjects(int maxResults, int firstResult) {

		EntityManager em = createEntityManager();
		List<Object> list = null;

		try {
			TypedQuery<Object> query = em.createQuery("select a from Agreement a", Object.class);
			//query.setMaxResults(maxResults);
			//query.setFirstResult(firstResult);
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

	public Agreement updateAgreement(Agreement agreement) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Agreement agreement2 = null;

		try {
			tx.begin();
			agreement2 = em.merge(agreement);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return agreement2;
	}

	public void deleteAgreement(String id) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(retrieveAgreement(id)));
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public Agreement retrieveAgreement(String agreement_id) {

		EntityManager em = createEntityManager();
		Agreement account = null;

		try {
			TypedQuery<Agreement> query = em.createQuery("select a from Agreement a where a.id = ?1",
					Agreement.class);
			query.setParameter(1, agreement_id);
			account = query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			em.close();
		}

		return account;
	}
}
