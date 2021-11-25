package gr.aueb.team1.domain;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name="Transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Transaction {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @Column(name ="amount", precision = 10, scale = 4)
	private Double amount;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name="Userid", nullable = false)
	private User user;

	
	public Transaction(User user, Double amount, LocalDateTime date) {
		super();
		this.user = user;
		this.amount = amount;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
