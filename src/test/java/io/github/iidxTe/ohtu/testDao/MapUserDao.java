package io.github.iidxTe.ohtu.testDao;

import io.github.iidxTe.ohtu.dao.UserDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import io.github.iidxTe.ohtu.model.User;

@Repository
public class MapUserDao implements UserDao {

    private final Map<String, User> users = new HashMap<>();
    private int nextId;
    
    @Override
    public User getUser(String name) {
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

    @Override
    public User createUser(String name, String password) {
        return new User(name); // Ignore password
    }

    @Override
    public List<User> getUsersByGroup(String group) {
        return users.values().stream().filter(
                user -> group.equals(user.getGroup())).collect(Collectors.toList());
    }

}
