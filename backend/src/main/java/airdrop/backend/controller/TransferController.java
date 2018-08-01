package airdrop.backend.controller;

import airdrop.backend.util.ResponseEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    public ResponseEntity uploadCSVFile(@RequestBody MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.ok(ResponseEnum.SUCCESS);
        }

        File file = new File(multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
