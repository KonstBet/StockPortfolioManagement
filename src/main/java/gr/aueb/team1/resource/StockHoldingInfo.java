package gr.aueb.team1.resource;

import gr.aueb.team1.domain.StockHolding;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class StockHoldingInfo {

    private Integer id;

    private Integer amount;

    private Integer committedAmount;

    private Integer userid;

    private String stockName;

    public StockHoldingInfo() {
    }


    public StockHoldingInfo(StockHolding sh) {
        this.id = sh.getId();
        this.amount = sh.getAmount();
        this.committedAmount = sh.getCommittedAmount();
        this.userid = sh.getUser().getId();
        this.stockName = sh.getStock().getCompanyName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCommittedAmount() {
        return committedAmount;
    }

    public void setCommittedAmount(Integer committedAmount) {
        this.committedAmount = committedAmount;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public static List<StockHoldingInfo> wrap(List<StockHolding> holdings) {

        List<StockHoldingInfo> stockHoldingInfoList = new ArrayList<>();

        for (StockHolding h : holdings) {
            stockHoldingInfoList.add(new StockHoldingInfo(h));
        }

        return stockHoldingInfoList;
    }
}
