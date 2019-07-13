package com.fhbonfante.reactive.repository;

import com.fhbonfante.reactive.domain.Account;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account,String> {
    @Query("{'owner': { $regex: ?0 , $options: 'i' }}")
    Flux<Account> findByOwner(String owner);
}
