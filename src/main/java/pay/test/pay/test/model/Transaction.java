package pay.test.pay.test.model;

import java.util.Date;
import java.util.UUID;

/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/20/18.
 */
public class Transaction {

    private String uuid;
    private Integer userId;
    private Date date;
    private String description;
    private Float amount;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
