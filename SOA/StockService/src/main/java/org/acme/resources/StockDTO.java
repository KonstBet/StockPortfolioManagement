package org.acme.resources;

import org.acme.domain.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StockDTO {

    private Long id;
    private String companyName;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double vol;

    public StockDTO(){}

    public StockDTO(Stock stock){
        this(stock.getId(), stock.getCompanyName(),
                stock.getOpen(), stock.getClose(), stock.getHigh(), stock.getLow(), stock.getVol());
    }


    public StockDTO(Long id, String companyName, Double open, Double close, Double high, Double low, Double vol) {
        this.id = id;
        this.companyName = companyName;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.vol = vol;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public static List<StockDTO> listToDTOList(List<Stock> stocks){
        List<StockDTO> stockDTOS = new ArrayList<>();
        for(Stock stock: stocks){
            stockDTOS.add(new StockDTO(stock));
        }
        return stockDTOS;
    }
}
