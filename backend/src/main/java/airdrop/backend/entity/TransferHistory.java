package airdrop.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "transfer_history")
public class TransferHistory implements Serializable {

    private static final long serialVersionUID = -8262310026069005672L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "request_id")
    private int requestId;
    private String address;
    @Column(precision = 16, scale = 10)
    private BigDecimal amount;
    @Column(name = "tx_hash")
    private String txHash;
    private int nonce;
    private String status;

//    public TransferHistory() {
//    }
//
//    public TransferHistory(int requestId, String address, BigDecimal amount, String txHash, int nonce, String status) {
//        this.requestId = requestId;
//        this.address = address;
//        this.amount = amount;
//        this.txHash = txHash;
//        this.nonce = nonce;
//        this.status = status;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
