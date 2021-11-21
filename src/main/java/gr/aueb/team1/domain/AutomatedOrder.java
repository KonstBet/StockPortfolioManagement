package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="_limit", precision = 10, scale = 4)
	private Double limit;
	
	public AutomatedOrder() {}
	
	public AutomatedOrder(User user, Stock stock, Integer amount, Double fee, LocalDateTime date, Action action, Double limit) {
		super(user, stock, amount, fee, date, action, Status.PENDING);
		this.limit = limit;
	}


	public Double getLimit() {
		return limit;
	}
	
	public void setLimit(Double limit) {
		this.limit = limit;
	}
	
	public String toString() {
		return super.toString() +
				"\nLimit: " + this.getLimit();
	}
	
	private Double calculatePriceB() {
		return stock.getClose()*getAmount() + Math.max(6, getAmount()*stock.getOpen()*getFee());
	}
	
	private Double calculatePriceS() {
		return stock.getClose()*getAmount() - Math.max(6, getAmount()*stock.getOpen()*getFee());
	}
	
	@Override
	public Boolean applyOrder() {
		if (getStatus() != Status.PENDING) {
			return false;
		}
				
		// Check if balance is enough
		if (getAction() == Action.BUY) {
			
			setOrderPrice(this.calculatePriceB());
			if (user.getBalance() < getOrderPrice()) {
				//System.err.println("Not enough Balance");
				return false;
			}
			
			// ????????????????????????????????????? Me vasi ti 8a agorazei?
			if (stock.getClose() <= limit) {
				super.buy();
			}
			
			return true;
		} else {
			
			setOrderPrice(this.calculatePriceS());
			// Check stock holdings for stock
			if (!user.getStockHoldings().containsKey(stock)) {
				return false;
			}
			
			// Retrieve stock holding
			StockHolding sh = user.getStockHoldings().get(stock);
			
			if (sh.getAmount() < getAmount()) {
				return false;
			}
			
			// ????????????????????????????????????? Me vasi ti 8a poulaei?
			if (stock.getClose() >= limit) {
				super.sell(sh);
			}

		}
		return true;
	}	
}
