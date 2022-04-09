package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("B")
public class Broker extends User {

    @Column(name = "brokerageFee", length = 20, nullable = false, columnDefinition = "double default 0.0")
    private Double brokerageFee;

    public Broker() {}

    public Broker(String name, String surname, String email, String phoneNo, String password, Double brokerageFee) {
        super(name, surname, email, phoneNo, password);
        this.brokerageFee = brokerageFee;
    }

    @OneToMany(mappedBy = "broker", cascade = CascadeType.PERSIST)
    private Set<Authorization> authorizations = new HashSet<Authorization>();

    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Double brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public Set<Authorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Set<Authorization> authorizations) {
        this.authorizations = authorizations;
    }
}