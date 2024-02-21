package com.sarop.saropbackend.user.repository;

import com.sarop.saropbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

}
