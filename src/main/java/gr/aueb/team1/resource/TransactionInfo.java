package gr.aueb.team1.resource;

import gr.aueb.team1.domain.Transaction;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;

@XmlRootElement
public class TransactionInfo {

    private Integer id;

    private Double amount;

    private String date;

    private String type;

    public TransactionInfo() {
    }

    public TransactionInfo(Integer id, Double amount, String date, String type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public TransactionInfo(Transaction t,String type) {
        this.id = t.getId();
        this.amount = t.getAmount();
        this.date = t.getDate().format(dateTimeFormatter);
        this.type = type;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<TransactionInfo> wrap(List<Transaction> transactions, String type) {

        List<TransactionInfo> transactionInfoList = new ArrayList<>();

        for (Transaction t : transactions) {
            transactionInfoList.add(new TransactionInfo(t,type));
        }

        return transactionInfoList;
    }
}
