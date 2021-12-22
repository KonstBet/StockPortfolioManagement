package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.Address;
import gr.aueb.team1.domain.Investor;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserResourceTest extends JerseyTest {

    Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }

    @Test
    public void getUserInfoTest() {
        Form form = new Form();
        form.param("email","mitcharal@gmail.com");
        form.param("password","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        UserInfo ui = target("user").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),new GenericType<UserInfo>() {});

        assertEquals(ui.getSurname(),"Charalampidis");
    }

    @Test
    public void getUserInfoWrongPasswordTest() {
        Form form = new Form();
        form.param("email","mitcharal@gmail.com");
        form.param("password","5365401c442f8806ef83b612976b");

        UserInfo ui = target("user").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),new GenericType<UserInfo>() {});

        assertNull(ui);
    }
    @Test
    public void getUserInfoNonExistentAccountTest() {
        Form form = new Form();
        form.param("email","dummymail@gmail.com");
        form.param("password","5365401c442f8806ef83b612976b");

        UserInfo ui = target("user").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),new GenericType<UserInfo>() {});

        assertNull(ui);
    }
    
    @Test
    public void getInvestorByIdTest() {
        UserInfo ii = target("user/investor/"+init.investor.getId()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<UserInfo>() {});

        assertEquals("Mitsos", ii.getName());
    }
    
    @Test
    public void getInvestorByIdFailTest() {
        UserInfo ii = target("user/investor/"+init.broker.getId()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<UserInfo>() {});

        assertNull(ii);
    }
    
    @Test
    public void getBrokerByIdTest() {
        UserInfo bi = target("user/broker/"+init.broker.getId()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<UserInfo>() {});

        assertEquals("Stefanos", bi.getName());
    }
    
    @Test
    public void getBrokerByIdFailTest() {
        UserInfo bi = target("user/broker/"+init.investor.getId()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<UserInfo>() {});

        assertNull(bi);
    }
    
    @Test
    public void getAllInvestors() {
        List<UserInfo> invList = target("user/investors").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<UserInfo>>() {});

        assertEquals(1,invList.size());
    }
    
    @Test
    public void getAllBrokers() {
        List<UserInfo> brokList = target("user/brokers").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<UserInfo>>() {});

        assertEquals(2,brokList.size());
    }
    
    @Test
	public void testCreateInvestor() {

		// Create a user info object and submit
		UserInfo ui = new UserInfo("Savvas", "Nikolaou", "snikol@pouts.io", "6910203040", "nikos1821", 0.0);
		ui.setAddress(new Address("Str Chatzidimitriou","45","13135"));
		Response response = target("user/createi").request().post(Entity.entity(ui, MediaType.APPLICATION_JSON));

		// Check status and database state
		assertEquals(201, response.getStatus());
		
		UserDAO ud = new UserDAOImpl();
		List<Investor> li = ud.findAllInvestors();
		assertEquals(2, li.size());

	}
}