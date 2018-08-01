package airdrop.backend.service.impl;

import airdrop.backend.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void noticeForLoginVerifyCode(String email, String name, String code) {

    }

    public void sendEmail() {

    }
}
