package airdrop.backend.service;

import airdrop.backend.entity.AirdropRequestHistory;

import java.util.List;

public interface AirdropRequestHistoryService {

    List<AirdropRequestHistory> findAll();

    List<AirdropRequestHistory> findAllByUserId(int id);

    AirdropRequestHistory save(AirdropRequestHistory airdropRequestHistory);

}
