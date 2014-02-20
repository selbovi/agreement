package com.silverheart.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import com.silverheart.server.domain.AttachedFile;

public class AttachedFileDao extends BaseDao {

	public boolean createAttachedFile(AttachedFile attachedFile) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(attachedFile);
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
		return createAttachedFile((AttachedFile) object);
	}

	@Override
	public List<Object> retrieveObjects(int maxResults, int firstResult) {

		EntityManager em = createEntityManager();
		List<Object> list = null;

		try {
			TypedQuery<Object> query = em.createQuery("select a from AttachedFile a", Object.class);
			/*query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);*/
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

	public AttachedFile updateAttachedFile(AttachedFile attachedFile) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		AttachedFile attachedFile2 = null;

		try {
			tx.begin();
			attachedFile2 = em.merge(attachedFile);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return attachedFile2;
	}

	public void deleteAttachedFile(AttachedFile attachedFile) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(attachedFile));
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public AttachedFile retrieveAttachedFile(Long id) {

		EntityManager em = createEntityManager();
		AttachedFile account = null;

		try {
			TypedQuery<AttachedFile> query = em.createQuery("select a from AttachedFile a where a.id = ?1",
					AttachedFile.class);
			query.setParameter(1, id);
			account = query.getSingleResult();
		} finally {
			em.close();
		}

		return account;
	}
	public List<AttachedFile> retrieveFilesForAgreement(String agreementId) {

		EntityManager em = createEntityManager();
		List<AttachedFile> list = null;

		try {
			TypedQuery<AttachedFile> query = em.createQuery("select a from AttachedFile a where a.agreementId = ?1",
					AttachedFile.class);
			query.setParameter(1, agreementId);
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}
}
