package org.acme.resources;

import org.acme.domain.StockHolding;
import java.util.ArrayList;
import java.util.List;

public class StockHoldingDTO {

    private Long id;
    private Integer amount;
    private Integer committedAmount;
    private Long userId;
    private Long stockId;

    public StockHoldingDTO(){}

    public StockHoldingDTO(StockHolding stockHolding){
        this(stockHolding.getId(), stockHolding.getAmount(),
                stockHolding.getCommittedAmount(), stockHolding.getUserId(), stockHolding.getStock().getId());
    }

    public StockHoldingDTO(Long id, Integer amount, Integer committedAmount, Long userId, Long stockId) {
        this.id = id;
        this.amount = amount;
        this.committedAmount = committedAmount;
        this.userId = userId;
        this.stockId = stockId;
    }

    public static List<StockHoldingDTO> listToDTOList(List<StockHolding> stockHoldings){
        List<StockHoldingDTO> stockDTOS = new ArrayList<>();
        for(StockHolding stockHolding: stockHoldings){
            stockDTOS.add(new StockHoldingDTO(stockHolding));
        }
        return stockDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }
}
