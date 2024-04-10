package com.sarop.saropbackend.user.repository;

import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    boolean existsByRole(Role role);

    List<User> findAllByStatus(UserStatus userStatus);



}
