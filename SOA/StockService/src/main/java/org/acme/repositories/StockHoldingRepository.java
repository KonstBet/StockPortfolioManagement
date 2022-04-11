package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.StockHolding;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StockHoldingRepository implements PanacheRepository<StockHolding> {

    public StockHolding findByPk(Long id) {
        return findById(id);
    }

    public List<StockHolding> findUserStockholdings(Long userId){
        return list("userId", userId);
    }

    public Boolean saveStockHolding(StockHolding stockHolding) {
        persist(stockHolding);
        return isPersistent(stockHolding);
    }

}
