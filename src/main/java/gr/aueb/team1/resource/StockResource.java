package gr.aueb.team1.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.dao.impl.StockDAOImpl;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.service.StockService;

@Path("stocks")
public class StockResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StockInfo> listStocks() {
		StockDAO sd = new StockDAOImpl();
		StockService stockService = new StockService(sd);
		
		List<Stock> stocks = stockService.showStocks();

		List<StockInfo> stockInfo = StockInfo.wrap(stocks);

		return stockInfo;
	}
}
