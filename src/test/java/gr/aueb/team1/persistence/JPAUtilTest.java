package gr.aueb.team1.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import gr.aueb.team1.persistence.JPAUtil;

class JPAUtilTest {

	@Test
	void test() {
		
		EntityManager em = JPAUtil.getCurrentEntityManager();
		assertNotNull(em);
		
	}

}
