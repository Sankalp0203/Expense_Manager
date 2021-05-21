package com.example.Setu.controller;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.PaymentReciept;
import com.example.Setu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paymentLog")
public class PaymentLogController {

    @Autowired
    SplitWiseDatabase splitWiseDatabase;

    @GetMapping("")
    public List<PaymentReciept> getPaymentRecieptList(){
        List<PaymentReciept> paymentLogList = splitWiseDatabase.getTransactionLog();
        return paymentLogList;
    }
}
