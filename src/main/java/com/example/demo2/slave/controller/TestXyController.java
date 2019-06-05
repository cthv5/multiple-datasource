package com.example.demo2.slave.controller;

import com.example.demo2.slave.model.ErpCheck;
import com.example.demo2.slave.repository.ErpCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/testXy")
public class TestXyController {
    @Autowired
    private ErpCheckRepository erpCheckRepo;

    @GetMapping("erpCheck")
//    @TargetDataSource(name = "xy")
    public String getErpCheck() {
        ErpCheck check = erpCheckRepo.findCheckById(360448L);
        return check.getAccName();
    }
}
