package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Broker;
import org.acme.domain.Investor;
import org.acme.domain.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository  implements PanacheRepository<User> {

    public Investor findInvestorByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"I").firstResult();
    }

    public Broker findBrokerByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"B").firstResult();
    }

    public Boolean saveUser(User u) {
        persist(u);
        return isPersistent(u);
    }
}
