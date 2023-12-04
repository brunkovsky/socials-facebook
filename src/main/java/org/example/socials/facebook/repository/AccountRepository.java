package org.example.socials.facebook.repository;

import org.example.socials.facebook.model.FacebookAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<FacebookAccount, String> {

    List<FacebookAccount> findFacebookAccountByExecutorScheduler(String message);

}
