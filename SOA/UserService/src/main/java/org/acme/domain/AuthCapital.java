package org.acme.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("AuthCapital")
public class AuthCapital extends Authorization {

    @Column(name = "amount", nullable = false)
    private Double amount;

    public AuthCapital() {
        super();
    }

    public AuthCapital(Investor investor, Broker broker, LocalDateTime startdate, LocalDateTime enddate, Double amount) {
        super(investor, broker, startdate, enddate);
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
