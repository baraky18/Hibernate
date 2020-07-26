package main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import dto.entity.mainentity.UserDetails;

public class QueryTest {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		/*
		 * we can use sql queries in Hibernate. the query will return a list of objects as per the table that's being queried.
		 * in the query, we can also use a parameter binding which will bind a specific parameter to a place in the query.
		 * we can do that by "?" char and then use the query.setParameter method
		 */
		NativeQuery<UserDetails> query = session.createNativeQuery("select * from user_details where user_Id > ?");
		query.setParameter(1, 2);
		/*
		 * we can bring results by using pagination. we do that by using query.setFirstResult and query.setMaxResults
		 */
		query.setFirstResult(2);
		query.setMaxResults(1);
		List<UserDetails> users = query.getResultList();
		
		/*
		 * we can use named query by annotation @NamedNativeQuery in the returned object class. we are using it as follows.
		 */
		NativeQuery anotherQuery = session.getNamedNativeQuery("UserDetails.byName");
		anotherQuery.setParameter(1, "other name");
		List<UserDetails> otherUsers = anotherQuery.getResultList();
		
		
		session.getTransaction().commit();
		session.close();
		System.out.println("Size of list result is: " + users.size());
		System.out.println("Size of list result is: " + otherUsers.size());
	}
}
