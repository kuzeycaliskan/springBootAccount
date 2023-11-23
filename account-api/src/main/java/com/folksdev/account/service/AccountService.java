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
import java.time.Clock;
import java.time.Instant;
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
    /***
     * Normalde bir servis class'i ana bir service olarak kullanilmamali. Cok fazla sorumluluk almamali. Ancak bu class
     * boyle oldu. Biz bu projede en onemli seyi "Account create etmeyi" yapiyoruz. Bunu da yapmak icin once createAccount
     * functionunun ilk satirinda olan customer service'i cagirma islemini yapiyoruz. Bu islem bize bu ID'ye ait
     * customer varsa getirir, yoksa hata firlatir. Ikincisi, account repository kullanmaliyim ki degeri kaydedeyim.
     * Account'da transaction'a bagli. Bu yuzden onu olusturdum ki geri gondereyim. Yani aciklamasi bu. Normalde
     * bi class'in bu kadar yogun olmasindan kaciniriz.
     */
    private final AccountRepository accountRepository; //Bir service sadece kendine hizmet eden repository'yi kullanabilir.
    private final CustomerService customerService; //burada customer bilgisi donen function oldugu icin isime yarayacak
    private final AccountDtoConverter converter;
    private final Clock clock;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter, Clock clock) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
        this.clock = clock;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                getLocalDateTimeNow());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(
                    createAccountRequest.getInitialCredit(),
                    getLocalDateTimeNow(),
                    account);

            account.getTransaction().add(transaction);
        }
        return converter.convert(accountRepository.save(account));
    }

    private LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }
}
