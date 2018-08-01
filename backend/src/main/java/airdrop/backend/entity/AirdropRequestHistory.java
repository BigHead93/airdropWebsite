package airdrop.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "airdrop_request_history")
public class AirdropRequestHistory implements Serializable {

    private static final long serialVersionUID = 8826956470118446967L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "datestamp")
    private Date time;

    @Column(name = "total_address")
    private int addressTotal;

    @Column(name = "total_amount", precision = 16, scale = 10)
    private BigDecimal amountTotal;

    private String status;

    @Column(name = "success_num")
    private int successCount;

//    public AirdropRequestHistory() {
//    }
//
//    public AirdropRequestHistory(int userId, Date time, int addressTotal, BigDecimal amountTotal, String status, int successCount) {
//        this.userId = userId;
//        this.time = time;
//        this.addressTotal = addressTotal;
//        this.amountTotal = amountTotal;
//        this.status = status;
//        this.successCount = successCount;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAddressTotal() {
        return addressTotal;
    }

    public void setAddressTotal(int addressTotal) {
        this.addressTotal = addressTotal;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " +
                "userId: " + userId + ", " +
                "time: " + time + ", " +
                "addressTotal: " + addressTotal + ", " +
                "amountTotal: " + amountTotal.toString() + ", " +
                "status: " + status + ", " +
                "successCount: " + successCount;
    }
}
