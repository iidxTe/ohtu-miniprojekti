package io.github.iidxTe.ohtu.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.iidxTe.ohtu.model.User;

@Repository
public class MapUserDao implements UserDao {

    private final Map<String, User> users = new HashMap<>();
    private int nextId;
    
    @Override
    public User getOrCreateUser(String name) {
        return users.computeIfAbsent(name, k -> {
            User user = new User(name);
            user.setId(nextId++);
            user.setDisplayName(name);
            return user;
        });
    }

    @Override
    public void updateUser(User user) {
        // Do nothing, we don't store users
    }

}
