package gr.aueb.team1.resource;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import gr.aueb.team1.domain.Address;
import gr.aueb.team1.domain.User;

@XmlRootElement
public class UserInfo {

	private Integer id;
	
	private String name;
	
	private String surname;
	
	@XmlTransient
	private String password;
	
	private String email;
	
	private String phoneNo;
	
	private Address address;
	
	private Double balance;
	
	public UserInfo() {

	}
	
	public UserInfo(Integer id, String name, String surname, String email, String phoneNo, String password, Double balance) {
		this(name, surname, email, phoneNo, password, balance);
		this.id = id;
	}
	
	public UserInfo(String name, String surname, String email, String phoneNo, String password, Double balance) {
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.setEmail(email);
		this.setPhoneNo(phoneNo);
		this.setBalance(0.0);
	}
	
	public UserInfo(User u) {
		this.name = u.getName();
		this.surname = u.getSurname();
		this.setEmail(u.getEmail());
		this.setPhoneNo(u.getPhoneNo());
		this.setBalance(u.getBalance());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public static UserInfo wrap(User user) {

		UserInfo u = new UserInfo(user);

		return u;
	}	
}