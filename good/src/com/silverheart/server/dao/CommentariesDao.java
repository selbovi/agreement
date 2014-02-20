package com.silverheart.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.silverheart.server.domain.Commentaries;

public class CommentariesDao extends BaseDao {


	public boolean createCommentaries(Commentaries commentaries) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(commentaries);
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
		return createCommentaries((Commentaries) object);
	}

	@Override
	public List<Object> retrieveObjects(int maxResults, int firstResult) {

		EntityManager em = createEntityManager();
		List<Object> list = null;

		try {
			TypedQuery<Object> query = em.createQuery("select a from Commentaries a", Object.class);
	/*		query.setMaxResults(maxResults);
			query.setFirstResult(firstResult); */
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

	public CommentariesDao updateCommentaries(CommentariesDao commentaries) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		CommentariesDao commentaries2 = null;

		try {
			tx.begin();
			commentaries2 = em.merge(commentaries);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return commentaries2;
	}

	public void deleteCommentaries(CommentariesDao commentaries) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(commentaries));
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public List<Commentaries> retrieveCommentaries(String agreementId) {

		EntityManager em = createEntityManager();
		List<Commentaries> list = null;

		try {
			TypedQuery<Commentaries> query = em.createQuery("select a from Commentaries a where a.agreementId = ?1",
					Commentaries.class);
			query.setParameter(1, agreementId);
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

}
