package com.revature.User;
import com.revature.Account.Account;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;
import java.util.function.Predicate;

import java.util.List;

public class UserService implements Serviceable<User> {
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users == null || users.isEmpty()) {
            throw new DataNotFoundException("No user information available");
        }
        return users;
    }


    public boolean isEmpty(User[] arr) { // defining the parameter of a string array to be included when executing this method
        for (User element : arr) { // enhanced for each loop, that iterates through the arrays elements returning & assigning the value to the declared variable flight
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public User create(User newUser) {
        return userRepository.create(newUser);
    }

    public boolean delete(User removedUser) {
        return userRepository.delete(removedUser);
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public User findByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public boolean update(User updatedUser) {
        return userRepository.update(updatedUser);
}




}
