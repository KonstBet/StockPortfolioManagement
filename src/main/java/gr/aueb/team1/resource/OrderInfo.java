package gr.aueb.team1.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import gr.aueb.team1.domain.Order;


@XmlRootElement
public class OrderInfo {

	private Integer id;
	
	private Integer amount;
	
	private LocalDateTime date;
	
	private Double orderPrice;

	private Order.Action action;
	
	private String status;
	
	OrderInfo(){
		
	}
	
	OrderInfo(Integer id, Integer amount, LocalDateTime date, Double orderPrice, Order.Action action, String status){
		this(amount, date, orderPrice, action, status);
		this.id = id;
	}
	
	OrderInfo(Integer amount, LocalDateTime date, Double orderPrice, Order.Action action, String status){
		this.amount = amount;
		this.date = date;
		this.orderPrice = orderPrice;
		this.action = action;
		this.status = status;
	}
	
	OrderInfo(Order o) {
		this.amount = o.getAmount();
		this.date = o.getDate();
		this.orderPrice = o.getOrderPrice();
		this.action = o.getAction();
		this.status = o.statusToString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Order.Action getAction() {
		return action;
	}

	public void setAction(Order.Action action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    public static List<OrderInfo> wrap(List<Order> orders) {

        List<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order o : orders) {
        	orderInfoList.add(new OrderInfo(o));
        }

        return orderInfoList;
    }
}
