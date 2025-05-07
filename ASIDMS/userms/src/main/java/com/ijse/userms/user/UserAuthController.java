package com.ijse.userms.user;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.userms.user.User;
import com.ijse.userms.user.UserRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserAuthController {
    


    @Autowired
    UserRepository userRepository;


    @PostMapping("/auth/register")
    public ResponseEntity<?> registierUser(@RequestBody User user){
        
        if(userRepository.existsByUsername(user.getUsername())){

            return ResponseEntity.badRequest().body("Username already exists");

        }

        if(userRepository.existsByEmail(user.getEmail())){

            return ResponseEntity.badRequest().body("Email is already being used");

        }

        User newUser = new User(null, null, null, null, null);
        newUser.setFullname(user.getFullname());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User created successfully"));

    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser() {
     List<User> existUser = userRepository.findAll();
        return new ResponseEntity<>(existUser, HttpStatus.OK);
    }


    @GetMapping("/id/{username}") 
    public ResponseEntity<Optional<User>> getUserIdByUsername(@PathVariable String username) {
        Optional<User> userId = userRepository.findByUsername(username);

        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  
}
