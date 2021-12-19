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

    public StockHoldingInfo(Integer id, Integer amount, Integer committedAmount) {
        this.id = id;
        this.amount = amount;
        this.committedAmount = committedAmount;
    }

    public StockHoldingInfo(StockHolding sh) {
        this.id = sh.getId();
        this.amount = sh.getAmount();
        this.committedAmount = sh.getCommittedAmount();
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

    public static List<StockHoldingInfo> wrap(List<StockHolding> holdings) {

        List<StockHoldingInfo> stockHoldingInfoList = new ArrayList<>();

        for (StockHolding h : holdings) {
            stockHoldingInfoList.add(new StockHoldingInfo(h));
        }

        return stockHoldingInfoList;
    }
}
