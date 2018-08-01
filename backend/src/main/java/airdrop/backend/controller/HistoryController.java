package airdrop.backend.controller;


import airdrop.backend.entity.AirdropRequestHistory;
import airdrop.backend.entity.TransferHistory;
import airdrop.backend.entity.User;
import airdrop.backend.service.AirdropRequestHistoryService;
import airdrop.backend.service.RedisService;
import airdrop.backend.service.TransferHistoryService;
import airdrop.backend.service.UserService;
import airdrop.backend.util.Const;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/history")
public class HistoryController {

    private final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    private final TransferHistoryService transferHistoryService;
    private final AirdropRequestHistoryService airdropRequestHistoryService;
    private final RedisService redisService;
    private final UserService userService;
    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    public HistoryController(TransferHistoryService transferHistoryService,
                             AirdropRequestHistoryService airdropRequestHistoryService,
                             RedisService redisService,
                             UserService userService,
                             PlatformTransactionManager platformTransactionManager) {
        this.transferHistoryService = transferHistoryService;
        this.airdropRequestHistoryService = airdropRequestHistoryService;
        this.redisService = redisService;
        this.userService = userService;
        this.platformTransactionManager = platformTransactionManager;
    }

    @RequestMapping(value = "saveAirdropRequestHistory", method = RequestMethod.GET)
    public ResponseEntity save() {
        AirdropRequestHistory airdropRequestHistory = saveAirdropRequestHistory("emma", 100, new BigDecimal(100.234), Const.FAILED, 0);
        logger.info("save airdropRequestHistory: {}", airdropRequestHistory.toString());
        return ResponseEntity.ok(airdropRequestHistory);
    }

    public AirdropRequestHistory saveAirdropRequestHistory(String userName, int addressTotal, BigDecimal amountTotal, String status, int successCount) {
        User user = userService.findUserByName(userName);

        AirdropRequestHistory airdropRequestHistory = new AirdropRequestHistory();
        airdropRequestHistory.setUserId(user.getId());
        airdropRequestHistory.setTime(new Date());
        airdropRequestHistory.setAddressTotal(addressTotal);
        airdropRequestHistory.setAmountTotal(amountTotal);
        airdropRequestHistory.setStatus(status);
        airdropRequestHistory.setSuccessCount(successCount);

        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        AirdropRequestHistory airdropRequest = null;
        try {
            airdropRequest = airdropRequestHistoryService.save(airdropRequestHistory);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);
        } finally {
            return airdropRequest;
        }
    }

    @RequestMapping(value = "/airdropRequestHistory", method = RequestMethod.GET)
    public ResponseEntity getAllAirdropRequestHistory() {
        try {
            List<AirdropRequestHistory> airdropRequestHistories = airdropRequestHistoryService.findAll();
            return ResponseEntity.ok(airdropRequestHistories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(value = "/transferHistory", method = RequestMethod.POST)
    public ResponseEntity getTransferHistoryByRequestId(@RequestBody String body) {
        Map info = (Map) JSON.parse(body);
        int requestId = (int) info.get("requestId");
        try {
            List<TransferHistory> transferHistories = transferHistoryService.findAllByRequestId(requestId);
            return ResponseEntity.ok(transferHistories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(null);
        }
    }

}
