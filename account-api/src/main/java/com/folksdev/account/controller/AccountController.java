package com.folksdev.account.controller;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /***
     * @PathVariable ve @RequestBody farki: PathVariable URL'den alirken, @RequestBody json'dan parametre alir
     */
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest request){
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId){
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
}
