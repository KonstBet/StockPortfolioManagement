package gr.aueb.team1.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import gr.aueb.team1.domain.Order;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;


@XmlRootElement
public class OrderInfo {

	private Integer id;
	
	private Integer amount;
	
	private String date;
	
	private Double orderPrice;

	private Order.Action action;
	
	private String status;
	
	OrderInfo(){
		
	}

	
	OrderInfo(Order o) {
		this.id = o.getId();
		this.amount = o.getAmount();
		this.date = o.getDate().format(dateTimeFormatter);;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
