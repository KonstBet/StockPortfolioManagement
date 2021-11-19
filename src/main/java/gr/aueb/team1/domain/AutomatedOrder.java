package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import javax.persistence.*;


@Entity
@DiscriminatorValue("AUTO")
public class AutomatedOrder extends Order {
	
	@Column(name="limit", precision = 10, scale = 4)
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
	
}
