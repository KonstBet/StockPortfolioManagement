package com.example.app.domain;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="StockHolding")
public class StockHolding {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="ammount", length=100, nullable=false)
	private Integer ammount;
	
	@Column(name="committedAmmount", length=100, nullable=false)
	private Integer committedAmmount;
	
	@ManyToOne
	@JoinColumn(name="UserId", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="StockId", nullable=false)
	private Stock stock;
	
	@OneToMany(mappedBy="user")
	private Set<AuthStocks> authStock=new HashSet<AuthStocks>();

}
