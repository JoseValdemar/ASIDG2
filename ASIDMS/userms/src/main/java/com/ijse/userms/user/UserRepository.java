package com.ijse.userms.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.userms.user.User;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{

    Optional<User> findByUsername(String userName);
    
    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String email);

   
}
