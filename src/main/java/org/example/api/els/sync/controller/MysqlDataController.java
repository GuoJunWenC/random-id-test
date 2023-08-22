package org.example.api.els.sync.controller;

import org.example.api.els.sync.MysqlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysqlData")
public class MysqlDataController {
    @Autowired
    private MysqlData mysqlData;

    @GetMapping("/queryCount")
    public void queryCount(){
        System.out.println(mysqlData.queryCount());
    }
}
