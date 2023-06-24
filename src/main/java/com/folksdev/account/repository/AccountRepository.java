package com.folksdev.account.repository;

import com.folksdev.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Modeller tekil isimde olmalidir. Accounts degil de account gibi.
 * Repository ismi de xxxRepository olur
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
