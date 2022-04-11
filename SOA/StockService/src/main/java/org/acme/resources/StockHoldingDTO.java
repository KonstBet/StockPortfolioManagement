package org.acme.resources;

import org.acme.domain.StockHolding;
import java.util.ArrayList;
import java.util.List;

public class StockHoldingDTO {

    private Long id;
    private Integer amount;
    private Long userId;
    private Long stockId;
    private Boolean isLocked = false;

    public StockHoldingDTO(){}

    public StockHoldingDTO(StockHolding stockHolding){
        this(stockHolding.getId(), stockHolding.getAmount(), stockHolding.getUserId(),
                stockHolding.getStock().getId(), stockHolding.getIsLocked());
    }

    public StockHoldingDTO(Long id, Integer amount, Long userId, Long stockId, Boolean isLocked) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.stockId = stockId;
        this.isLocked = isLocked;
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

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
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
