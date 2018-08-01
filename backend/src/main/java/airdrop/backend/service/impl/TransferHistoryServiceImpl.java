package airdrop.backend.service.impl;

import airdrop.backend.Repository.TransferHistoryRepository;
import airdrop.backend.entity.TransferHistory;
import airdrop.backend.service.TransferHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferHistoryServiceImpl implements TransferHistoryService {

    @Autowired
    TransferHistoryRepository transferHistoryRepository;

    @Override
    public List<TransferHistory> findAllByRequestId(int requestId) {
        return transferHistoryRepository.findAllByRequestId(requestId);
    }

//    @Override
//    public List<TransferHistory> findAllByAddress(String address) {
//        return transferHistoryRepository.findAllByAddress(address);
//    }

    @Override
    public TransferHistory save(TransferHistory transferHistory) {
        return transferHistoryRepository.save(transferHistory);
    }
}
