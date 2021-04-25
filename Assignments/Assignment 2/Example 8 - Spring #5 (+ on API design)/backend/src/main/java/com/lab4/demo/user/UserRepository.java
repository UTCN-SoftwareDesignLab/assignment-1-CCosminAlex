package com.lab4.demo.user;

import com.lab4.demo.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}
