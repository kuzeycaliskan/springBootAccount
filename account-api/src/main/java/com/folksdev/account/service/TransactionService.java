package com.folksdev.account.service;


import com.folksdev.account.dto.CreateTransactionRequest;
import com.folksdev.account.dto.TransactionDto;
import com.folksdev.account.dto.converter.TransactionDtoConverter;
import com.folksdev.account.exception.TransactionNotFoundException;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDtoConverter transactionDtoConverter;
    private final AccountService accountService; //burada customer bilgisi donen function oldugu icin isime yarayacak

    private final Clock clock;

    public TransactionService(TransactionRepository transactionRepository,
                              TransactionDtoConverter transactionDtoConverter,
                              AccountService accountService, Clock clock) {
        this.transactionRepository = transactionRepository;
        this.transactionDtoConverter = transactionDtoConverter;
        this.accountService = accountService;
        this.clock = clock;
    }

    public TransactionDto createTransaction(CreateTransactionRequest request) {
        Transaction transaction;
        Account account = accountService.findAccountById(request.getAccountId());

        transaction = new Transaction(
                request.getInitialCredit(),
                getLocalDateTimeNow(),
                account
        );

//        account.getTransaction().add(transaction);

        return transactionDtoConverter.convert(transactionRepository.save(transaction));
    }

    protected Transaction findTransactionById(String id){
        return transactionRepository.findById(id)
                .orElseThrow(
                        () -> new TransactionNotFoundException("Transaction could not find by id: " + id));
    }

    public TransactionDto getTransactionById(String transactionId){
        return transactionDtoConverter.convert(findTransactionById(transactionId));
    }

    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }


}
