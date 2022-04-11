package org.acme.services;

import org.acme.domain.Stock;
import org.acme.domain.StockHolding;
import org.acme.repositories.StockHoldingRepository;
import org.acme.repositories.StockRepository;
import org.acme.resources.StockDTO;
import org.acme.resources.StockHoldingDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StockHoldingService {

    @Inject
    StockHoldingRepository stockHoldingRepository;


    public List<StockHoldingDTO> getUserStockHoldings(Long userId){
        List<StockHolding> stockHoldings;

        stockHoldings = stockHoldingRepository.findUserStockholdings(userId);
        if(stockHoldings == null) return null;


        return StockHoldingDTO.listToDTOList(stockHoldings);
    }

    public StockHoldingDTO getStockHoldingById(Long id){
        StockHolding stockHolding = stockHoldingRepository.findByPk(id);
        if(stockHolding == null) return null;
        return new StockHoldingDTO(stockHolding);
    }

    public Boolean updateStockHoldingStatus(Long id, Boolean isLocked){

        StockHolding stockHolding = stockHoldingRepository.findByPk(id);
        if(stockHolding == null) return false;
        stockHolding.setIsLocked(isLocked);

        return stockHoldingRepository.saveStockHolding(stockHolding);

    }
}
