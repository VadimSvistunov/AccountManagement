package by.svistunovvv.accountManagement.auth;

import by.svistunovvv.accountManagement.auth.dto.AuthenticationRequest;
import by.svistunovvv.accountManagement.auth.dto.AuthenticationResponse;
import by.svistunovvv.accountManagement.config.JwtService;
import by.svistunovvv.accountManagement.model.Role;
import by.svistunovvv.accountManagement.model.User;
import by.svistunovvv.accountManagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(AuthenticationRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.findByEmail(request.getEmail());
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
