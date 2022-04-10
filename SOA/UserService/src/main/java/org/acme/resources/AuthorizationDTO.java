package org.acme.resources;

import org.acme.domain.Broker;
import org.acme.domain.Investor;

import javax.persistence.*;
import java.time.LocalDateTime;

public class AuthorizationDTO {

    private Integer id;

    private LocalDateTime startdate;

    private LocalDateTime enddate;

    private Integer investorid;

    private Integer brokerid;

    private String type;

    private Double amount;

    private Integer stockholdingid;

    public AuthorizationDTO() {
    }

    public AuthorizationDTO(LocalDateTime startdate, LocalDateTime enddate, Integer investorid, Integer brokerid, String type, Double amount) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.investorid = investorid;
        this.brokerid = brokerid;
        this.type = type;
        this.amount = amount;
    }

    public AuthorizationDTO(LocalDateTime startdate, LocalDateTime enddate, Integer investorid, Integer brokerid, String type, Double amount, Integer stockholdingid) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.investorid = investorid;
        this.brokerid = brokerid;
        this.type = type;
        this.amount = amount;
        this.stockholdingid = stockholdingid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public Integer getInvestorid() {
        return investorid;
    }

    public void setInvestorid(Integer investorid) {
        this.investorid = investorid;
    }

    public Integer getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(Integer brokerid) {
        this.brokerid = brokerid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStockholdingid() {
        return stockholdingid;
    }

    public void setStockholdingid(Integer stockholdingid) {
        this.stockholdingid = stockholdingid;
    }
}
