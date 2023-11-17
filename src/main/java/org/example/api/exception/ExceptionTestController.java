package org.example.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.api.enums.ErrorCodeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/exceptionTest")
public class ExceptionTestController {

    @RequestMapping("/exceptionTest")
    public void exceptionTest() {
        throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
    }
    public static void readFileStream(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
        }catch (IOException ioException){
            log.error(ioException.getMessage());
        }
    }
}
