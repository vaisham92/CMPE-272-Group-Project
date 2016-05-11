package org.vaisham.perf.db.api;

import java.util.List;

import org.hibernate.HibernateException;

/**
 *	IGenericDAO.java
 *	KH1868 Vaishampayan Reddy
 */

public interface IGenericDAO {
	
	public <T> T addEntity(T object)  throws HibernateException;

	public <T> boolean addEntity(String entityName, T entity) throws HibernateException;

	public <T> T addUpdateEntity(T entity) throws HibernateException;

	public <T> T updateEntity(T object) throws HibernateException;
	
	public <T> void deleteEntity(T object)  throws HibernateException;
	public <T> int deleteEntities(String query, List<Object> values) throws HibernateException;

	public <T> List<T> getEntities(String query, List<Object> values) throws HibernateException;
	
	public <T> T getEntity(String query, List<Object> values) throws HibernateException;

}
