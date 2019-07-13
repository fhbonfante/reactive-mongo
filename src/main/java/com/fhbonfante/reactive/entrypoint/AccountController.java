package com.fhbonfante.reactive.entrypoint;

import com.fhbonfante.reactive.domain.Account;
import com.fhbonfante.reactive.repository.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/all")
    Flux<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Account> getAccountById(@PathVariable("id") String accountId) {
        return accountRepository.findById(accountId);
    }

    @GetMapping("/owner/{owner}")
    Flux<Account> getAccountByOwner(@PathVariable("owner") String owner) {
        //return accountRepository.findByOwner(".*"+owner+".*");
        return accountRepository.findByOwner(owner);
    }

}
