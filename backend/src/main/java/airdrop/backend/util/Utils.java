package airdrop.backend.util;

import org.springframework.http.ResponseEntity;

import java.util.Random;

public class Utils {

    public static ResponseEntity makeupResponseEntity(ResponseEnum e){
        ResponseCode responseCode = new ResponseCode(e.getIndex(), e.getText());
        return ResponseEntity.ok(responseCode);
    }

    public static char[] getVerificationCode() {
        Random random = new Random();
        String[] chars = Const.chars;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String rand = chars[random.nextInt(61)];
            code.append(rand);
        }
        return code.toString().toCharArray();
    }
}
