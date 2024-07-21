package by.svistunovvv.accountManagement.repository;

import by.svistunovvv.accountManagement.model.Account;
import by.svistunovvv.accountManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUser (User user);
}
