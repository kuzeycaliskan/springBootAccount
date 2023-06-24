package com.folksdev.account;

import com.folksdev.account.model.Account;
import kotlin.collections.SetsKt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Account account_a = new Account("id_a", BigDecimal.ONE, LocalDateTime.now(), null, SetsKt.emptySet());
		Account account_b = new Account("id_a", BigDecimal.ONE, LocalDateTime.now(), null, SetsKt.emptySet());

	}
}
