package com.example.app.domain;

import java.util.Date;
import javax.persistence.*;


@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="limit", precision = 10, scale = 4)
	private Float limit;
	
	public AutomatedOrder() {}
	
	public AutomatedOrder(Integer id, Integer amount, Double stockPrice, Float fee, Date date, Action action, Float limit) {
		super(id, amount, stockPrice, fee, date, action);
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
	
}
