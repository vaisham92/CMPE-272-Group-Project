package org.vaisham.perf.db.api;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.vaisham.perf.enums.LogLevel;
import org.vaisham.perf.utils.factory.ILogger;
import org.vaisham.perf.utils.factory.LoggerManager;

/**
 *	AbstractGenericDAO.java
 *	KH1868 Vaishampayan Reddy
 */

@Transactional
public abstract class AbstractGenericDAO implements IGenericDAO {


	public abstract Session getSession();
	
	private static ILogger logger = LoggerManager.getLoggerFactory().getLogger(
			AbstractGenericDAO.class.getName());
	
	public <T> T addEntity(T entity) throws HibernateException{
		Session session = getSession();
			session.save(entity);
		return entity;
	}

	/**
	 * This method is used to save data into database.
	 * 
	 */
	public <T> boolean addEntity(String entityName, T entity) throws HibernateException{

		Session session = getSession();
			session.save(entityName, entity);
			return true;
	}
	/**
	 * This method will return the list of entities related to query in given object.
	 * 
	 * @param query  This will contain the actual query to be called on DB object
	 * @param entity Object on which query going be apply.
	 * @return {@link List} list contained entities of give object       
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getEntities(String query, List<Object> values)throws HibernateException{
		Session session = getSession();
		logger.logMessage(LogLevel.INFO, "getEntities : Query "+query);
		List<T> entities = null;
			Query sqlQuery = session.createQuery(query);
			if (values != null){
				for (int i = 0 ; i < values.size(); i++){
					sqlQuery.setParameter(i, values.get(i));
				}
			}
			entities = sqlQuery.list(); 
		return entities;
	}

	@SuppressWarnings("unchecked")
	public <T> T getEntity(String query, List<Object> values)throws HibernateException{
		Session session = getSession();
		logger.logMessage(LogLevel.INFO, "getEntity : Query "+query);
		T entity = null;
			Query sqlQuery = session.createQuery(query);
			if (values != null){
				for (int i = 0 ; i < values.size(); i++){
					sqlQuery.setParameter(i, values.get(i));
				}
			}
			entity = (T)sqlQuery.uniqueResult();
		return entity;
	}

	public <T> T addUpdateEntity(T entity )throws HibernateException{
		Session session = getSession();
			session.saveOrUpdate(entity);
			return entity;
	}

	/**
	 * This method is used to update data into database.
	 * 
	 */
	public <T> T updateEntity(T entity)throws HibernateException{
		Session session = getSession();
			session.update(entity);
			return entity;
	}

	/**
	 * This method is used to delete data from database.
	 * 
	 */
	public void deleteEntity(Object entity)throws HibernateException{
		Session session = getSession();
			session.delete(entity);
	}

	public <T> int deleteEntities(String query, List<Object> values)throws HibernateException{
		Session session = getSession();
		logger.logMessage(LogLevel.INFO, "deleteEntities : Query "+query);
		Query sqlQuery = session.createQuery(query);
		if (values != null){
			for (int i = 0 ; i < values.size(); i++){
				sqlQuery.setParameter(i, values.get(i));
			}
		}
		return sqlQuery.executeUpdate(); 
	}
	
}
