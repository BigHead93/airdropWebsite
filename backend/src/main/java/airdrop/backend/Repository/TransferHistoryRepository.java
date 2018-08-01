package airdrop.backend.Repository;

import airdrop.backend.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Integer> {

    //TransferHistory: requestId
    List<TransferHistory> findAllByRequestId(int requestId);

    //TransferHistory: address, to find a customer in all activity
//    List<TransferHistory> findAllByAddress(String address);


}
