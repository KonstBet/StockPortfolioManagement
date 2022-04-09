package org.acme.resources;

import org.acme.domain.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class UserDTO {
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String phoneNo;

    private String type;

    private Double committedBalance;

    private Double brokerageFee;

    public UserDTO() {}

    public UserDTO(Integer id, String name, String surname, String email, String phoneNo, String type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.type = type;
    }
    public UserDTO(Integer id, String name, String surname, String email, String phoneNo, String type, Double temp) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.type = type;
        if (type.equals("investor"))
            this.committedBalance = temp;
        else if (type.equals("broker"))
            this.brokerageFee = temp;
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

    public Double getCommittedBalance() {
        return committedBalance;
    }

    public void setCommittedBalance(Double committedBalance) {
        this.committedBalance = committedBalance;
    }

    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Double brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

}
