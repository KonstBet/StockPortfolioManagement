package org.acme.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("AuthStock")
public class AuthStock extends Authorization {

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "stockholdingid")
    private Integer stockholdingid;

    public AuthStock() {
        super();
    }

    public AuthStock(Investor investor, Integer stockholdingid, Broker broker,
                     LocalDateTime startdate, LocalDateTime enddate, Integer amount) {

        super(investor, broker, startdate, enddate);
        this.amount = amount;
        this.stockholdingid = stockholdingid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStockholdingid() {
        return stockholdingid;
    }

    public void setStockholdingid(Integer stockholdingid) {
        this.stockholdingid = stockholdingid;
    }
}