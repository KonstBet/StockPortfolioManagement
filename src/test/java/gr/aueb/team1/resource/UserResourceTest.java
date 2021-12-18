package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.InvocationInterceptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.*;

import static org.junit.jupiter.api.Assertions.*;

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
}