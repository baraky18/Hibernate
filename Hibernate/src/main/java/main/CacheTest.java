package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dto.entity.mainentity.UserDetails;

public class CacheTest {

	/*
	 * explanation regarding second level cache in the explanation.txt
	 */
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		UserDetails user = (UserDetails) session.get(UserDetails.class, 1);
		
		session.getTransaction().commit();
		session.close();
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		UserDetails user1 = (UserDetails) session.get(UserDetails.class, 1);
		
		session.getTransaction().commit();
		session.close();
	}
}
