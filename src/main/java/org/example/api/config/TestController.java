package org.example.api.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/testa")
    public void testa() {

        try (BufferedInputStream bin = new BufferedInputStream(Files.newInputStream(new File("test.txt").toPath()));
             BufferedOutputStream bout = new BufferedOutputStream(Files.newOutputStream(new File("out.txt").toPath()))) {
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/testC")
    public void testC() throws InterruptedException {
       TimeUnit.MILLISECONDS.sleep(150);
    }
    private static final TestController instance = new TestController();
    private final boolean b = a;
    private static final boolean a = initA();

    private static final boolean c = a;

    private static boolean initA() {
        return true;
    }

    private static boolean getC() {
        return c;
    }

    public static void main(String[] args) {
        System.out.println(instance.b + " " + getC());
    }
}
