package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dto.entity.mainentity.UserDetails;

public class HibernateStatesTest {

	/*
	 * this should be run only when there's a user with user_id = 1 in DB (performed by the other main class)
	 * and hbm2ddl.auto property in hibernate.cfg should be "update"
	 */
	public static void main(String[] args) {
		updateAfterSavingToSession();
		updateTwiceAfterSavingToSession();
		getUserInOneSessionUpdateUserInAnotherSession();
	}


	/*
	 * here, Hibernate caches the object from one session to another
	 */
	private static void getUserInOneSessionUpdateUserInAnotherSession() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		UserDetails user = (UserDetails)session.get(UserDetails.class, 1);
		
		session.getTransaction().commit();
		session.close();
		
		user.setUserName("update not in session");
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(user);
		
		session.getTransaction().commit();
		session.close();
	}


	/*
	 * here, Hibernate will perform only one update query (the last update query)
	 */
	private static void updateTwiceAfterSavingToSession() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		UserDetails user = (UserDetails)session.get(UserDetails.class, 1);

		user.setUserName("other name first update");
		session.update(user);
		user.setUserName("other name second update");
		session.update(user);
		
		session.getTransaction().commit();
		session.close();
	}

	/*
	 * till the session is committed and closed, I can do multiple changes on the object (even if I already saved it to session). 
	 * for example, I can do session.save(user) and after that do user.setName("updated name")
	 * and Hibernate will trigger an update query and will update the user's name
	 */
	private static void updateAfterSavingToSession() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		UserDetails user = (UserDetails)session.get(UserDetails.class, 1);

		user.setUserName("other name");
		session.update(user);
		
		session.getTransaction().commit();
		session.close();
	}

}
