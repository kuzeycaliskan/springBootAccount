package com.folksdev.account.service;


import com.folksdev.account.dto.TransactionDto;
import com.folksdev.account.dto.converter.TransactionDtoConverter;
import com.folksdev.account.exception.TransactionNotFoundException;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDtoConverter transactionDtoConverter;

    public TransactionService(TransactionRepository transactionRepository,TransactionDtoConverter transactionDtoConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    protected Transaction findTransactionById(String id){
        return transactionRepository.findById(id)
                .orElseThrow(
                        () -> new TransactionNotFoundException("Transaction could not find by id: " + id));
    }

    public TransactionDto getTransactionById(String transactionId){
        return transactionDtoConverter.convert(findTransactionById(transactionId));
    }
}
