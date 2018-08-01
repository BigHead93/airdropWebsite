package airdrop.backend.service;

public interface EmailService {

    void noticeForLoginVerifyCode(String email, String name, String code);
}
