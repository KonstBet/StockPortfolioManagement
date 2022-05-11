package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Stock;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {

    public Stock findByPk(Long id) {
        return findById(id);
    }

    public Stock findByCompanyName(String name){
        return find("companyName", name).firstResult();
    }


    public List<Stock> listStocks(){
        return list("id > 1");
    }

    public Boolean saveStock(Stock stock) {
        persist(stock);
        return isPersistent(stock);
    }
}
