package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.AuthCapital;
import org.acme.domain.AuthStock;
import org.acme.domain.Authorization;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AuthorizationRepository  implements PanacheRepository<Authorization> {

    //Find single authorization
    public AuthStock findAuthStockByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"AuthStock").firstResult();
    }

    public AuthCapital findAuthCapitalByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"AuthCapital").firstResult();
    }

    //Find all authorizations of a User
    public List<Authorization> findAllAuthStocksByUserID(Integer userid) {
        return list("(Investorid = ?1 or Brokerid = ?1) and type = ?2", userid,"AuthStock");
    }

    public List<Authorization> findAllAuthCapitalsByUserID(Integer userid) {
        return list("(Investorid = ?1 or Brokerid = ?1) and type = ?2", userid,"AuthCapital");
    }

    public Boolean saveAuthorization(Authorization auth) {
        persist(auth);
        return isPersistent(auth);
    }

}
