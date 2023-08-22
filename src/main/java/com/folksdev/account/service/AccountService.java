package com.folksdev.account.service;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.dto.converter.AccountDtoConverter;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/***
 * Bu class disaridaki bir service veya controller'a hizmet edecegi icin Service assortionu'nu alir
 * Service ve controller arasindaki fark budur
 */
@Service
public class AccountService {

    /***
     * @Autowired yerine 'final' kullandik. Cunku Autowired cok eski bir teknoloji. Artik spring bile kullanmiyor
     * Autowired ile benim account table'm immutable olmuyor. Ek olarak, benim test icin mock datami da
     * buna gore ayarlamam lazim. Testibilty'de dusuyor
     */
    private final AccountRepository accountRepository; //Bir service sadece kendine hizmet eden repository'yi kullanabilir.
    private final CustomerService customerService; //burada customer bilgisi donen function oldugu icin isime yarayacak
    private final TransactionService transactionService;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          TransactionService transactionService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0){
            Transaction transaction = transactionService.initiateMoney(account, createAccountRequest.getInitialCredit());
//            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }

        return converter.convert(accountRepository.save(account));
    }
}
