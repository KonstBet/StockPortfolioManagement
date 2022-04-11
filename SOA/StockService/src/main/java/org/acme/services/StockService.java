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

        stocks = stockRepository.listStocks();

        if(stocks == null) return null;

        return  StockDTO.listToDTOList(stocks);
    }

    public StockDTO getStockById(Long id){
        Stock stock = stockRepository.findByPk(id);
        if(stock == null) return null;
        return new StockDTO(stock);
    }
}
