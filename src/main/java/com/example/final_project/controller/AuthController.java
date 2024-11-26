package com.example.final_project.controller;

import com.example.final_project.model.User;
import com.example.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint untuk registrasi user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Mengecek apakah username sudah terdaftar
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username sudah terdaftar");
        }
        // Menyimpan user baru ke database
        userService.registerUser(user);
        return ResponseEntity.ok("Registrasi berhasil!");
    }

    // Endpoint untuk menampilkan halaman login
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Merender file login.html dari folder templates
    }

    // Endpoint untuk login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(401).body("Username tidak ditemukan");
        }

        // Membandingkan password yang diinput dengan password yang ter-hash di database
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Password salah");
        }

        return ResponseEntity.ok("Login berhasil!");
    }
}
