package gr.aueb.team1.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.dao.impl.StockDAOImpl;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.service.StockService;

@Path("stock")
public class StockResource {

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<StockInfo> listStocks() {
		StockDAO sd = new StockDAOImpl();
		StockService stockService = new StockService(sd);

		List<Stock> stocks = stockService.showStocks();

		List<StockInfo> stockInfo = StockInfo.wrap(stocks);

		return stockInfo;
	}

	@GET
	@Path("{stockid}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public StockInfo fetchStock(@PathParam("stockid") Integer id) {

		try {
			StockDAO sd = new StockDAOImpl();
			StockService stockService = new StockService(sd);

			Stock stock = stockService.getStock(id);

			StockInfo stockInfo = new StockInfo(stock);

			return stockInfo;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
}
