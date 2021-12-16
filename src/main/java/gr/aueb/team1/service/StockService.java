package gr.aueb.team1.service;

import java.util.List;
import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.domain.Stock;

public class StockService {
	
	private StockDAO sd;

	public StockService(StockDAO sd) {
		this.sd = sd;
	}


	public List<Stock> showStocks() {

		List<Stock> results = null;
		results = sd.findAll(); ; 
				
		return results;
	}
}
