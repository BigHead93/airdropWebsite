package airdrop.backend.service;

import airdrop.backend.entity.TransferHistory;

import java.util.List;

public interface TransferHistoryService {

    List<TransferHistory> findAllByRequestId(int requestId);

//    List<TransferHistory> findAllByAddress(String address);

    TransferHistory save(TransferHistory transferHistory);
}
