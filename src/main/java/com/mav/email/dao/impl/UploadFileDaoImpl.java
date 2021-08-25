package com.mav.email.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.mav.email.dao.UploadFileDao;
import com.mav.email.entity.UploadedDocument;

@Repository
public class UploadFileDaoImpl implements UploadFileDao {

	@PersistenceContext(unitName = "emailDB")
	private EntityManager entityManger;

	@Override
	public void persistUploadedDocumentInfo(UploadedDocument uploadedDocument) {
		entityManger.persist(uploadedDocument);
	}

	@Override
	public List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds) {
		TypedQuery<UploadedDocument> typedQuery = entityManger.createNamedQuery("getUploadedDocumentList",
				UploadedDocument.class);
		typedQuery.setParameter("serverFileIds", serverFileIds);
		return typedQuery.getResultList();

	}

}
