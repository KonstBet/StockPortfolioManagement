package org.acme.repositories;

import org.acme.domain.AuthCapital;
import org.acme.domain.AuthStock;
import org.acme.domain.Broker;
import org.acme.domain.Investor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class Initializer {

    @Inject
    UserRepository userRepository;

    @Inject
    AuthorizationRepository authorizationRepository;

    private Investor investor;
    private Broker broker;
    private AuthStock authStock;
    private AuthCapital authCapital;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public AuthStock getAuthStock() {
        return authStock;
    }

    public void setAuthStock(AuthStock authStock) {
        this.authStock = authStock;
    }

    public AuthCapital getAuthCapital() {
        return authCapital;
    }

    public void setAuthCapital(AuthCapital authCapital) {
        this.authCapital = authCapital;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Transactional
    public void EraseData() {
        authorizationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Transactional
    public void Initialize() {
        investor = new Investor("Nikos","Papadopoulos","nikosme@mailbox.gr","6987654321","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        broker = new Broker("Makhs","Gewrgios","makhsg@mailbox.gr","6987654313","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976c",10.0);
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(24);

        authCapital = new AuthCapital(investor,broker,startTime,endTime,1000.0);
        authStock = new AuthStock(investor,9,broker,startTime,endTime,50);


        investor.getAuthorizations().add(authCapital);
        investor.getAuthorizations().add(authStock);
        broker.getAuthorizations().add(authCapital);
        broker.getAuthorizations().add(authStock);

        userRepository.saveUser(investor);
        userRepository.saveUser(broker);
        authorizationRepository.saveAuthorization(authCapital);
        authorizationRepository.saveAuthorization(authStock);
    }
}
