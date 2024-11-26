package com.example.final_project.service;

import com.example.final_project.model.User;
import com.example.final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Menyimpan user baru dengan password yang sudah di-encode
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Mencari user berdasarkan username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
