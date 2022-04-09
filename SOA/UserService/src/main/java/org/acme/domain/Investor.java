package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("I")
public class Investor extends User {

    @Column(name = "committedBalance", length = 100, nullable = false, columnDefinition = "double default 0.0")
    private Double committedBalance;

    public Investor(){}

    public Investor(String name, String surname, String email, String phoneNo, String password) {
        super(name, surname, email, phoneNo, password);
        this.committedBalance = 0.0;
    }

    public Double getCommittedBalance() {
        return committedBalance;
    }

    public void setCommittedBalance(Double committedBalance) {
        this.committedBalance = committedBalance;
    }

    //TODO
//    @OneToMany(mappedBy = "investor", cascade = CascadeType.PERSIST)
//    private Set<Authorization> authorizations = new HashSet<Authorization>();

}
