package com.example.app.domain;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;


@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="limit", precision = 10, scale = 4)
	private Double limit;
	
	public AutomatedOrder() {}
	
	public AutomatedOrder(User user,Integer id, Integer amount, Double fee, LocalDateTime date, Action action, Double limit) {
		super(user, amount, fee, date, action);
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
	
	private Double calculatePrice() {
		return stock.getOpen() + Math.max(6, super.getAmount()*stock.getClose()*super.getFee());
	}
	
}
