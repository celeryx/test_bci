package com.bci.auth.repositories;

import com.bci.auth.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
