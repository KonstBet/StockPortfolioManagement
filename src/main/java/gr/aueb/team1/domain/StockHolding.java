package gr.aueb.team1.domain;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="StockHolding")
public class StockHolding {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="amount", length=100, nullable=false)
	private Integer amount;
	
	@Column(name="committedAmount", length=100, nullable=false)
	private Integer committedAmount;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="UserId")
	private User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="StockId")
	private Stock stock;
	
	@OneToMany(mappedBy="stockholding",cascade = CascadeType.PERSIST)
	private Set<AuthStock> authStock=new HashSet<AuthStock>();

	
	public StockHolding() {
		
	}
	
	public StockHolding(Integer amount, Stock stock, User user) {
		this.amount = amount;
		this.stock = stock;
		this.user = user;
		this.committedAmount = 0;
	}
	
	public StockHolding(Integer amount, Stock stock, User user, Integer committedAmount) {
		this.amount = amount;
		this.stock = stock;
		this.user = user;
		this.committedAmount = committedAmount;
		stock.getHoldings().put(stock, this);
	}

	public Integer getId() {
		return this.id;
	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getCommittedAmount() {
		return this.committedAmount;
	}

	public void setCommittedAmount(Integer committedAmount) {
		this.committedAmount = committedAmount;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Set<AuthStock> getAuthStock() {
		return this.authStock;
	}

	public void setAuthStock(Set<AuthStock> authStock) {
		this.authStock = authStock;
	}

	public void remove() {
		user.getStockHoldings().remove(stock);
		stock.getHoldings().remove(stock);
		user = null;
		stock = null;

		for(Object it : authStock.toArray()) {
			((AuthStock) it).removeAuth();
		}

		authStock.clear();
		authStock = null;
	}
}
