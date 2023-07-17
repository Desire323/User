package com.desire323.user.repository;


import com.desire323.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void deleteById(Integer id);
}