package by.svistunovvv.accountManagement.controller;

import by.svistunovvv.accountManagement.config.JwtService;
import by.svistunovvv.accountManagement.model.Account;
import by.svistunovvv.accountManagement.model.User;
import by.svistunovvv.accountManagement.service.AccountService;
import by.svistunovvv.accountManagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/")
    public Account get(@RequestHeader(name = "Authorization") String authHeader) {
        return accountService.findAccountAuthHeader(authHeader);
    }

    @PostMapping("/topup")
    public Account topup(@RequestBody double amount, @RequestHeader(name = "Authorization") String authHeader) {
        Account account = accountService.findAccountAuthHeader(authHeader);
        account.setAmount(account.getAmount() + amount);
        return accountService.save(account);
    }

    @PostMapping("/takeoff")
    public Account takeoff(@RequestBody double amount, @RequestHeader(name = "Authorization") String authHeader) {
        Account account = accountService.findAccountAuthHeader(authHeader);
        account.setAmount(account.getAmount() - amount);
        return accountService.save(account);
    }

    @PostMapping("/block")
    public Account block(@RequestBody String email) {
        Account account = accountService.findAccountByEmail(email);
        account.setBlock(true);
        return account;
    }

    @PostMapping("/unblock")
    public Account unblock(@RequestBody String email) {
        Account account = accountService.findAccountByEmail(email);
        account.setBlock(false);
        return account;
    }
}