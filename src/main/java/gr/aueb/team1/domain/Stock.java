package gr.aueb.team1.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="stocks")
public class Stock 
{
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="company", length=40, nullable=false)
	private String companyName;
	
 
	private LocalDateTime date;
	
    @Column(name ="open", precision = 10, scale = 4)
	private Double open;
	
	@Column(name="close", precision = 10, scale = 4)
	private Double close;
	
	@Column(name="high", precision = 10, scale = 4)
	private Double high;
	
	@Column(name="low", precision = 10, scale = 4)
	private Double low;
	
	@Column(name="vol", precision = 10, scale = 4)
	private Double vol;
	
	@OneToMany(mappedBy="stock", fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	private Set<Order> orders = new HashSet<Order>();
	
	@OneToMany(mappedBy="stock", fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	private Map<Stock,StockHolding> holdings = new HashMap<Stock,StockHolding>();

	public Stock() {
		
	}
	
	public Stock(String companyName, LocalDateTime date,
			Double open, Double close, Double high, Double low, Double vol) 
	{
		this.companyName = companyName;
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.vol = vol;
	}
	
	public Integer getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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
	
	public Set<Order> getOrders() {
		return orders;
	}

	public Map<Stock, StockHolding> getHoldings() {
		return holdings;
	}


	public String toString() {
		return "ID: " + getId() + 
			   "\nCompany Name: " + getCompanyName() + 
			   "\nDate: " + getDate().toString() +
			   "\nOpen: " + getOpen() + "€" +
			   "\nClose: " + getClose() + "€" +
			   "\nHigh: " + getHigh() + "€" +
			   "\nLow: " + getLow() + "€" +
			   "\nVol: " + getVol() + "€";
	}
}

