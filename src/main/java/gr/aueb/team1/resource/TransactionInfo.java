package gr.aueb.team1.resource;

import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.domain.Transaction;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TransactionInfo {

    private Integer id;

    private Double amount;

    private LocalDateTime date;

    public TransactionInfo() {
    }

    public TransactionInfo(Integer id, Double amount, LocalDateTime date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public TransactionInfo(Transaction t) {
        this.id = t.getId();
        this.amount = t.getAmount();
        this.date = t.getDate();
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

    public static List<TransactionInfo> wrap(List<Transaction> transactions) {

        List<TransactionInfo> transactionInfoList = new ArrayList<>();

        for (Transaction t : transactions) {
            transactionInfoList.add(new TransactionInfo(t));
        }

        return transactionInfoList;
    }
}
