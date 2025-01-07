package com.example.SCM.repo;

import com.example.SCM.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<User ,String> {
    // find by email
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1")
    User getEmailByEmail(String email);

    Optional<User> findByEmailAndPassword(String email , String password);

    Optional<User>findByEmailToken(String id);
}
