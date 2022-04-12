package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("investor")
public class Investor extends User {

    public Investor(){}

    public Investor(String name, String surname, String email, String phoneNo, String password) {
        super(name, surname, email, phoneNo, password);
    }

    @OneToMany(mappedBy = "investor", cascade = CascadeType.PERSIST)
    private Set<Authorization> authorizations = new HashSet<Authorization>();

    public Set<Authorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Set<Authorization> authorizations) {
        this.authorizations = authorizations;
    }
}
