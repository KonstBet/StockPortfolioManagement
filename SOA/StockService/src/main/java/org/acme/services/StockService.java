package org.acme.services;

import org.acme.domain.Stock;
import org.acme.repositories.StockRepository;
import org.acme.resources.StockDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StockService {

    @Inject
    StockRepository stockRepository;


    public List<StockDTO> getAllStocks(){

        List<Stock> stocks;
        System.out.println("AAA");
        stocks = stockRepository.listStocks();
        System.out.println("AAA");
        if(stocks == null) return null;

        return StockDTO.listToDTOList(stocks);
    }

    public StockDTO getStockById(Long id){
        Stock stock = stockRepository.findByPk(id);
        if(stock == null) return null;
        return new StockDTO(stock);
    }

    public boolean createStock(StockDTO stockDTO){

        Stock stock = new Stock(stockDTO.getCompanyName(),stockDTO.getOpen(),
                stockDTO.getClose(), stockDTO.getHigh(), stockDTO.getLow(), stockDTO.getVol());

        return stockRepository.saveStock(stock);

    }
}
