package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="stocks")
public class Stock
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="company", length=40, nullable=false)
    private String companyName;

    @Column(name ="open", precision = 10, scale = 4)
    private Double open;

    @Column(name="close", precision = 10, scale = 4)
    private Double close;

    @Column(name="high", precision = 10, scale = 4)
    private Double high;

    @Column(name="low", precision = 10, scale = 4)
    private Double low;

    @Column(name="vol", precision = 10, scale = 4)
    private Double vol;

    public Stock() {

    }

    public Stock(String companyName, Double open, Double close, Double high, Double low, Double vol)
    {
        this.companyName = companyName;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.vol = vol;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }


    public Double getOpen() {
        return open;
    }


    public Double getClose() {
        return close;
    }


    public Double getHigh() {
        return high;
    }


    public Double getLow() {
        return low;
    }


    public Double getVol() {
        return vol;
    }
}