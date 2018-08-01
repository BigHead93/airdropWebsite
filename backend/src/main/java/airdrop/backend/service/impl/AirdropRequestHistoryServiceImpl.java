package airdrop.backend.service.impl;

import airdrop.backend.Repository.AirdropRequestHistoryRepository;
import airdrop.backend.entity.AirdropRequestHistory;
import airdrop.backend.service.AirdropRequestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirdropRequestHistoryServiceImpl implements AirdropRequestHistoryService {

    @Autowired
    AirdropRequestHistoryRepository airdropRequestHistoryRepository;

    @Override
    public List<AirdropRequestHistory> findAll() {
        return airdropRequestHistoryRepository.findAll();
    }

    @Override
    public List<AirdropRequestHistory> findAllByUserId(int id) {
        return airdropRequestHistoryRepository.findAllByUserId(id);
    }

    @Override
    public AirdropRequestHistory save(AirdropRequestHistory airdropRequestHistory) {
        return airdropRequestHistoryRepository.save(airdropRequestHistory);
    }
}
