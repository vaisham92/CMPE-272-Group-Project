package org.vaisham.perf.db.api;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository("GlobalDAO")
public class GlobalGenricDAOImpl extends AbstractGenericDAO {
	
	public GlobalGenricDAOImpl() {
	}
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
