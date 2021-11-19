package gr.aueb.team1.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="stocks")
public class Stock 
{
	@Id
	@Column(name="id", length=6, unique = true, nullable = false)
	private String id;
	
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
	
	@OneToMany(mappedBy="stock", fetch=FetchType.LAZY)
	private Set<Order> orders = new HashSet<Order>();
	
	@OneToMany(mappedBy="stock", fetch=FetchType.LAZY)
	private Set<StockHolding> holdings = new HashSet<StockHolding>();
	
	public Stock() {
		
	}
	
	public Stock(String id, String companyName, LocalDateTime date,
			Double open, Double close, Double high, Double low, Double vol) 
	{
		this.id = id;
		this.companyName = companyName;
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.vol = vol;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

