package com.fhbonfante.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fhbonfante.reactive.core.model.Account;
import com.fhbonfante.reactive.dataprovider.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j
public class AccountDatabaseInitializationConfig implements CommandLineRunner {

    public static final String ACCOUNT_DATABASE_JSON = "accounts.json";

    private final AccountRepository accountRepository;

    public AccountDatabaseInitializationConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String jsonFilePath = getJsonFilePath();
        JSONImporter jsonImporter = new JSONImporter(new File(jsonFilePath));
        List<Account> accounts = jsonImporter.importJSONFile(Account.class);
        accounts.stream().forEach(account -> account.setId(UUID.randomUUID().toString()));
        accountRepository.deleteAll()
                         .thenMany(Flux.just(accounts)
                                       .flatMap(accountRepository::saveAll)
                         ).subscribe(result -> log.info("Created account {}", result.toString()));

    }

    private String getJsonFilePath() {
        return getResourcesFolderPath().concat(ACCOUNT_DATABASE_JSON);
    }

    private String getResourcesFolderPath() {
        StringBuilder resourcesPathBuilder = new StringBuilder();
        return resourcesPathBuilder.append(Paths.get("").toAbsolutePath().toString())
                                   .append(File.separator.concat("src"))
                                   .append(File.separator.concat("main"))
                                   .append(File.separator.concat("resources"))
                                   .append(File.separator)
                                   .toString();
    }

    class JSONImporter {
        private final File jsonFile;


        JSONImporter(File jsonFile) {
            this.jsonFile = jsonFile;
        }

        public <T> List<T> importJSONFile(Class clazz) throws IOException {
            ObjectMapper jsonMapper = new ObjectMapper();
            TypeFactory jsonTypeFactory = jsonMapper.getTypeFactory();
            return jsonMapper.readValue(jsonFile, jsonTypeFactory.constructCollectionType(List.class, clazz));
        }
    }
}
