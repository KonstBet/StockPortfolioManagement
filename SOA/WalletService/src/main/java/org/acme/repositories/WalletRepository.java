package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Wallet;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WalletRepository implements PanacheRepository<Wallet> {

    public Wallet findByID(Integer id) {
        System.out.println(find("userid", id));
        return find("userid", id).firstResult();
    }

    public Boolean saveWallet(Wallet wallet) {
        persist(wallet);
        return isPersistent(wallet);
    }
}
