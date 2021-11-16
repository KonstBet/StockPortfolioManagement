package com.example.app.domain;
import java.util.Date;
import javax.persistence.*;


@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="limit", precision = 10, scale = 4)
	private Float limit;
	
	public AutomatedOrder() {}
	
	public AutomatedOrder(Integer id, Integer amount, Float fee, Date date, Action action, Float limit) {
		super(amount, fee, date, action);
		this.limit = limit;
	}

	public Float getLimit() {
		return limit;
	}

	public void setLimit(Float limit) {
		this.limit = limit;
	}
	
	public String toString() {
		return super.toString() +
				"\nLimit: " + this.getLimit();
	
	}
	
	private Float calculatePrice() {
		return stock.getOpen() + Math.max(6, super.getAmount()*stock.getClose()*super.getFee());
	}
	
}
