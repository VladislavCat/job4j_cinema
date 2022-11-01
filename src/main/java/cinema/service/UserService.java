package cinema.service;

import cinema.model.User;
import cinema.store.UserStore;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userStore.findUserByEmailAndPassword(email, password);
    }
}
