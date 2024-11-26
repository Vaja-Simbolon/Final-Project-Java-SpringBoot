package com.example.final_project.controller;

import com.example.final_project.model.User;
import com.example.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Menginjeksi PasswordEncoder dari Spring Security

    // Endpoint untuk registrasi user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Mengecek apakah username sudah terdaftar
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username sudah terdaftar");
        }
        // Menyimpan user baru ke database
        return ResponseEntity.ok(userService.registerUser(user));
    }

    // Endpoint untuk menampilkan halaman login
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Merender file login.html dari folder templates
    }

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = Optional.ofNullable(userService.findUserByUsername(username));
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body("Username tidak ditemukan");
        }

        // Membandingkan password yang diinput dengan password yang ter-hash di database
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseEntity.status(401).body("Password salah");
        }

        // Jika login berhasil, tidak perlu menggunakan ResponseEntity.
        // Pengalihan akan ditangani oleh Spring Security otomatis.
        return ResponseEntity.ok("Login berhasil!");  // Ini akan tetap mengirimkan pesan, tapi sebenarnya tidak perlu.
    }

    // Endpoint untuk menampilkan halaman dashboard setelah login
    @GetMapping("/home")
    public String dashboard() {
        return "dashboard";  // Merender file dashboard.html dari folder templates
    }
}
