package com.cybermediatheque.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Ce composant permet d'instancier la session hibernate depuis le fichier de
 * configuration
 * 
 * @author sylar
 * @version 1.0
 */
public abstract class HibernateService {

	/**
	 * Objet Session de Hibernate
	 */
	private /* static */ Session session = null;

	/**
	 * Constructeur
	 */
	public HibernateService() {

	}

	// eviter le static -> multi-session
	public /* static */ Session getSession() {
		if (null == session) {
			// on charge le fichier de configuration : hibernate.cfg.xml par defaut
			Configuration configuration = new Configuration().configure();

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			configuration.buildSessionFactory();

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
		}
		return session;
	}
}
