package com.revature.User;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Serviceable<User> {
    private List<User> userList = new ArrayList<>();
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
        userList.add(newUser);
        return newUser;
    }

    @Override
    public User findById(int userId) {

        for (User user : userList) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void update(User updatedUser) {

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == updatedUser.getUserId()) {
                userList.set(i, updatedUser);
                return;
            }
        }
        throw new DataNotFoundException("User with ID provided not within database");
    }




}
