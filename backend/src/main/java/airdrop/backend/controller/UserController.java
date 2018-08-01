package airdrop.backend.controller;

import airdrop.backend.entity.User;
import airdrop.backend.service.RedisService;
import airdrop.backend.service.UserService;
import airdrop.backend.util.AESCrypt;
import airdrop.backend.util.Const;
import airdrop.backend.util.ResponseEnum;
import airdrop.backend.util.Utils;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;
    private final PlatformTransactionManager transactionManager;
    private final RedisService redisService;

    @Autowired
    public UserController(UserService userService,
                          PlatformTransactionManager transactionManager,
                          RedisService redisService) {
        this.userService = userService;
        this.transactionManager = transactionManager;
        this.redisService = redisService;
    }


    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody String body) {
        Map info = (Map) JSON.parse(body);
        String name = (String) info.get("name");
        String email = (String) info.get("email");
        String psw = (String) info.get("psw");
        String note = (String) info.get("note");
        if(name.equals("") || email.equals("") || psw.equals("")) {
            return Utils.makeupResponseEntity(ResponseEnum.NEEDINFO);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPsw(AESCrypt.getEncryptedValue(name, psw));
        user.setCreateTime(new Date());
        user.setNote(note);

        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            userService.save(user);
            transactionManager.commit(tx);
            logger.info("Create new user success: name: {}, email: {}", name, email);
            return Utils.makeupResponseEntity(ResponseEnum.SUCCESS);
        }catch (Exception e) {
            transactionManager.rollback(tx);
            logger.error("Create new user error: name: {}, email: {}", name, email);
            return Utils.makeupResponseEntity(ResponseEnum.SAVEFAILED);
        }

    }


    @RequestMapping(value = "/getLoginCode", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String body) {

        Map info = (Map)JSON.parse(body);
        String name = (String) info.get("name");
        String psw = (String) info.get("psw");

        if(name.equals("")) {
            return Utils.makeupResponseEntity(ResponseEnum.NEEDNAME);
        }

        if(psw.equals("")) {
            return Utils.makeupResponseEntity(ResponseEnum.NEEDPSW);
        }

        User user = userService.findUserByName(name);

        if(user == null){
            return Utils.makeupResponseEntity(ResponseEnum.USERNOTEXIST);
        }

        String decryptedPsw = AESCrypt.getDecryptedValue(name, user.getPsw());

        if(!decryptedPsw.equals(psw)) {
            return Utils.makeupResponseEntity(ResponseEnum.WRONGPSW);
        }

        char[] vcode = Utils.getVerificationCode();

        redisService.set(Const.LOGIN_MARK + name, String.valueOf(vcode));
        //todo: send email

        return ResponseEntity.ok(vcode);
    }

    @RequestMapping(value = "/verifyLoginCode", method = RequestMethod.POST)
    public ResponseEntity verifyLoginCode(@RequestBody String body) {
        Map info = (Map) JSON.parse(body);
        String name = (String) info.get("name");
        String psw = (String) info.get("psw");
        String vcode = (String) info.get("vcode");

        if(name.equals("")) {
            return Utils.makeupResponseEntity(ResponseEnum.NEEDNAME);
        }

        if(psw.equals("")) {
            return Utils.makeupResponseEntity(ResponseEnum.NEEDPSW);
        }

        User user = userService.findUserByName(name);

        if(user == null){
            return Utils.makeupResponseEntity(ResponseEnum.USERNOTEXIST);
        }

        String decryptedPsw = AESCrypt.getDecryptedValue(name, user.getPsw());

        if(!decryptedPsw.equals(psw)) {
            return Utils.makeupResponseEntity(ResponseEnum.WRONGPSW);
        }

        String codeInRedis = redisService.get(Const.LOGIN_MARK + name);

        if(!codeInRedis.equals(vcode)) {
            return Utils.makeupResponseEntity(ResponseEnum.WRONGVCODE);
        }
        try {
            redisService.del(Const.LOGIN_MARK + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Utils.makeupResponseEntity(ResponseEnum.SUCCESS);
    }

}
