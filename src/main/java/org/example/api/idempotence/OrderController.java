package org.example.api.idempotence;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/createOrderInfo")
    @Idempotent(name = "requestData", type = RequestData.class, field = "value")
    public String createOrderInfo(@RequestBody RequestData<Order> requestData) {
        return "success";
    }

}