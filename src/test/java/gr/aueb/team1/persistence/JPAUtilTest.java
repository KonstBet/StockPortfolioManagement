package gr.aueb.team1.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import gr.aueb.team1.domain.User;


class JPAUtilTest {

	@Test
	void test1() {

		EntityManager em = JPAUtil.getCurrentEntityManager();
		assertNotNull(em);
		
	}
	
	@Test
	void test2() {
		EntityManager em = JPAUtil.createEntityManager();
		assertNotNull(em);
	}
	
	@Test
	void test3() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		JPAUtil.transactional(new Runnable() {
		    public void run() {
		    	User user = new User("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
				if (user.getId() != null) {
					user = em.merge(user);
				} else {
					em.persist(user);
				}
		    }
		});
	}

}
