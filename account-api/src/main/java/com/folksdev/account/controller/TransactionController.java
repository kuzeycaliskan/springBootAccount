package com.folksdev.account.controller;


import com.folksdev.account.dto.CreateTransactionRequest;
import com.folksdev.account.dto.TransactionDto;
import com.folksdev.account.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody CreateTransactionRequest request){
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable String transactionId){
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }
}
