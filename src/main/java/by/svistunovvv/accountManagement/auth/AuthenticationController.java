package by.svistunovvv.accountManagement.auth;

import by.svistunovvv.accountManagement.auth.dto.AuthenticationRequest;
import by.svistunovvv.accountManagement.auth.dto.AuthenticationResponse;
import by.svistunovvv.accountManagement.model.Account;
import by.svistunovvv.accountManagement.service.AccountService;
import by.svistunovvv.accountManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.register(request);
        Account account = Account.builder()
                .amount(Double.valueOf(0))
                .user(userService.findByEmail(request.getEmail()))
                .build();
        accountService.save(account);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
