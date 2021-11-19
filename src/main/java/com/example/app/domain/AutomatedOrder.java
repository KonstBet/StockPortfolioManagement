package com.example.app.domain;
import java.time.LocalDateTime;
import javax.persistence.*;


@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="limit", precision = 10, scale = 4)
	private Double limit;
	
    @Enumerated(EnumType.STRING)
    @Column(name="status")
	private Status status;
	
    enum Status {
		PENDING,
		COMPLETED
	}
	
	public AutomatedOrder() {}
	
	public AutomatedOrder(User user, Stock stock, Integer amount, Double fee, LocalDateTime date, Action action, Double limit) {
		super(user, stock, amount, fee, date, action);
		this.limit = limit;
		this.status = Status.PENDING;
	}


	public Double getLimit() {
		return limit;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String toString() {
		return super.toString() +
				"\nLimit: " + this.getLimit();
	
	}
	
}
