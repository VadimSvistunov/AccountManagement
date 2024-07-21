package by.svistunovvv.accountManagement.service;

import by.svistunovvv.accountManagement.config.JwtService;
import by.svistunovvv.accountManagement.model.Account;
import by.svistunovvv.accountManagement.model.User;
import by.svistunovvv.accountManagement.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final JwtService jwtService;
    private final UserService userService;

    public Account save(Account account) {
        return repository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return repository.findAccountByUser(userService.findByEmail(email)).orElse(null);
    }

    public void delete(Account account) {
        repository.deleteById(account.getId());
    }

    public Account findAccountAuthHeader(String authHeader) {
        String email = jwtService.extractEmailFromHeader(authHeader);
        return findAccountByEmail(email);
    }
}
