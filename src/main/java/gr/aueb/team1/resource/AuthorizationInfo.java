package gr.aueb.team1.resource;

import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.AuthStock;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.domain.Transaction;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;

@XmlRootElement
public class AuthorizationInfo {

    private Integer id;

    private String startdate;

    private String enddate;

    private Integer investorid;

    private Integer brokerid;

    private Double amount;

    private String stockName;

    public AuthorizationInfo() {
    }

    public AuthorizationInfo(AuthStock auth) {
        this.id = auth.getId();
        this.startdate = auth.getStartdate().format(dateTimeFormatter);
        this.enddate = auth.getEnddate().format(dateTimeFormatter);
        this.investorid = auth.getInvestor().getId();
        this.brokerid = auth.getBroker().getId();
        this.amount = auth.getAmount().doubleValue();
        this.stockName = auth.getStockHolding().getStock().getCompanyName();
    }

    public AuthorizationInfo(AuthCapital auth) {
        this.id = auth.getId();
        this.startdate = auth.getStartdate().format(dateTimeFormatter);
        this.enddate = auth.getEnddate().format(dateTimeFormatter);
        this.investorid = auth.getInvestor().getId();
        this.brokerid = auth.getBroker().getId();
        this.amount = auth.getAmount();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

//    public static List<AuthorizationInfo> wrap(List<Authorization> auths) {
//
//        List<AuthorizationInfo> authInfoList = new ArrayList<>();
//
//        for (Authorization a : auths) {
//            authInfoList.add(new AuthorizationInfo(a));
//        }
//
//        return authInfoList;
//    }
}
