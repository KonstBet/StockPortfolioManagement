package org.acme.resources;

import org.acme.domain.Address;
import org.acme.domain.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class UserDTO {
    private Long id;

    private String name;

    private String surname;

    private String password;

    private String email;

    private String phoneNo;

    private String type;

    private Address address;

    private Double brokerageFee;

    public UserDTO() {}

    public UserDTO(User user, String type, Double temp){
        this(user.getId(), user.getName(), user.getSurname(), user.getPassword(),
                user.getEmail(), user.getPhoneNo(), user.getAddress(), type, temp);
    }

    public UserDTO(Long id, String name, String surname, String password, String email, String phoneNo, Address address, String type, Double temp) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.type = type;

        this.address = address;
        this.password = password;
        if (type.equals("broker")) this.brokerageFee = temp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Double brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
