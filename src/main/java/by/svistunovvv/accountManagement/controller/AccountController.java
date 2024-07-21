package by.svistunovvv.accountManagement.controller;

import by.svistunovvv.accountManagement.config.JwtService;
import by.svistunovvv.accountManagement.controller.dto.AccountRequest;
import by.svistunovvv.accountManagement.controller.dto.AccountResponse;
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

    @GetMapping("/")
    public AccountResponse get(@RequestHeader(name = "Authorization") String authHeader) {
        return convertFromAccount(accountService.findAccountAuthHeader(authHeader));
    }

    @PostMapping("/topup")
    public AccountResponse topup(@RequestBody AccountRequest accountRequest, @RequestHeader(name = "Authorization") String authHeader) {
        Account account = accountService.findAccountAuthHeader(authHeader);
        if (account.isBlock()) {
            convertFromAccount(accountService.save(account));
        }
        account.setAmount(account.getAmount() + accountRequest.getAmount());
        return convertFromAccount(accountService.save(account));
    }

    @PostMapping("/takeoff")
    public AccountResponse takeoff(@RequestBody AccountRequest accountRequest, @RequestHeader(name = "Authorization") String authHeader) {
        Account account = accountService.findAccountAuthHeader(authHeader);
        if (account.isBlock()) {
            convertFromAccount(accountService.save(account));
        }
        account.setAmount(account.getAmount() - accountRequest.getAmount());
        return convertFromAccount(accountService.save(account));
    }

    @PostMapping("/block")
    public AccountResponse block(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.findAccountByEmail(accountRequest.getEmail());
        account.setBlock(true);
        accountService.save(account);
        return convertFromAccount(account);
    }

    @PostMapping("/unblock")
    public AccountResponse unblock(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.findAccountByEmail(accountRequest.getEmail());
        account.setBlock(false);
        accountService.save(account);
        return convertFromAccount(account);
    }

    private AccountResponse convertFromAccount(Account account) {
        return AccountResponse.builder()
                .amount(account.getAmount())
                .email(account.getUser().getEmail())
                .isBlock(account.isBlock())
                .build();
    }
}