package org.example.socials.facebook.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.socials.facebook.connector.FacebookConnector;
import org.example.socials.facebook.connector.FacebookType;
import org.example.socials.facebook.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FacebookService {

    private final AccountRepository accountRepository;
    private final Map<String, FacebookType> typeMap;
    private final FacebookConnector connector;


    public void fetch(String executorScheduler) {
        accountRepository.findFacebookAccountByExecutorScheduler(executorScheduler)
                .forEach(account -> Optional.ofNullable(typeMap.get(account.getFacebookType()))
                        .ifPresentOrElse(facebookType -> connector.loadData(account, facebookType),
                                () -> log.error("Can't find facebookType by key: '{}' from typeMap: {}",
                                    account.getFacebookType(), typeMap.keySet())));
    }

    public void refreshAccessToken(String accountName) {
        log.info("refreshing access token : " + accountName);
    }

}
