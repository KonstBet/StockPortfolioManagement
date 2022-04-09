package org.acme.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="Users")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="type",
        discriminatorType=DiscriminatorType.STRING
)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "phoneNo", length = 20, nullable = false)
    private String phoneNo;

    @Embedded
    private Address address;



    // Below columns are located at different microservices

    //    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    //    private Map<Stock, StockHolding> stockHoldings = new HashMap<Stock, StockHolding>();

    //    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    //    private Set<Order> orders= new HashSet<Order>();

    //    @Column(name = "balance", precision = 10, scale = 4)
    //    private Double balance;

    public User() {
        this.name = null;
        this.surname = null;
        this.email = null;
        this.phoneNo = null;
        //this.balance = 0.0;
    }

    public User(String name, String surname, String email, String phoneNo, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        //this.balance = 0.0;
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
}