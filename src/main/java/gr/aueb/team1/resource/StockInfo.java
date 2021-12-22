package gr.aueb.team1.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import gr.aueb.team1.domain.Stock;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;

@XmlRootElement
public class StockInfo {

	private Integer id;

	private String companyName;
	
	private String date;
	
	private Double open;
	
	private Double close;
	
	private Double high;
	
	private Double low;
	
	private Double vol;

	public StockInfo() {

	}
	
	public StockInfo(Integer id, String companyName, String date, Double open, Double close, Double high, Double low, Double vol) {
		this(companyName, date, open, close, high, low, vol);
		this.id = id;
	}
	
	public StockInfo(String companyName, String date, Double open, Double close, Double high, Double low, Double vol)
	{
		this.companyName = companyName;
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.vol = vol;
	}
	
	public StockInfo(Stock stock) {
		this.id = stock.getId();
		this.companyName = stock.getCompanyName();
		this.date = stock.getDate().format(dateTimeFormatter);;
		this.open = stock.getOpen();
		this.close = stock.getClose();
		this.high = stock.getHigh();
		this.low = stock.getLow();
		this.vol = stock.getVol();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	
	public static List<StockInfo> wrap(List<Stock> stocks) {

		List<StockInfo> stockInfoList = new ArrayList<>();

		for (Stock s : stocks) {
			stockInfoList.add(new StockInfo(s));
		}

		return stockInfoList;
	}	
}
