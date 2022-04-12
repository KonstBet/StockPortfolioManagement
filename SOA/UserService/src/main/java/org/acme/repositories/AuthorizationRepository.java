package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Authorization;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AuthorizationRepository  implements PanacheRepository<Authorization> {

    public Authorization findAuthorizationById(Long id){
        return findById(id);
    }

    public List<Authorization> findPairAuthorizationList(Long investorId, Long brokerId){
        return list("investor_id = ?1 and broker_id = ?2", investorId, brokerId);
    }

    public List<Authorization> findInvestorAuthorizations(Long userId){
        return list("investor_id", userId);
    }

    public List<Authorization> findBrokerAuthorizations(Long userId){
        return list("broker_id", userId);
    }

    public Boolean saveAuthorization(Authorization auth) {
        persist(auth);
        return isPersistent(auth);
    }

}
