package by.svistunovvv.accountManagement.service;

import by.svistunovvv.accountManagement.model.User;
import by.svistunovvv.accountManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail (String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }
    public User save (User user) {
        return userRepository.save(user);
    }
}
