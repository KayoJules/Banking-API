package com.revature.User;
import com.revature.util.interfaces.Serviceable;

import java.util.List;

public class UserService implements Serviceable<User> {
    private final UserRepository userRepository;

    public UserService(UserRepository userList) {
        this.userRepository = userList;
    }

    @Override
    public List<User> findAll() {
        return null;
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
