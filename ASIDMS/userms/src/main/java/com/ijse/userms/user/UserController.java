package com.ijse.userms.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ijse.userms.dto.UserDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO dto) {
        User user = new User();
        user.setFullname(dto.fullname);
        user.setUsername(dto.username);
        user.setEmail(dto.email);
        user.setShippingOrderId(dto.shippingOrderId);

        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserName(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser
                .map(user -> ResponseEntity.ok(user.getFullname()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> userExists(@PathVariable Long id) {
        boolean exists = userRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
