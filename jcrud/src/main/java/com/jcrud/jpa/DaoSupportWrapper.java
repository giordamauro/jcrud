package com.jcrud.jpa;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DaoSupportWrapper extends HibernateDaoSupport {

	public DaoSupportWrapper(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

}
