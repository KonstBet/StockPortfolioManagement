package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Authorization;
import org.acme.domain.Broker;
import org.acme.domain.Investor;
import org.acme.domain.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository  implements PanacheRepository<User> {

    public Investor findInvestorByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"investor").firstResult();
    }

    public Broker findBrokerByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"broker").firstResult();
    }

    //Find all authorizations of a User
    public List<User> findAllInvestors() {
        return list("type = ?1", "investor");
    }

    public List<User> findAllBrokers() {
        return list("type = ?1", "broker");
    }

    public Boolean saveUser(User u) {
        persist(u);
        return isPersistent(u);
    }
}
