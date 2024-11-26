package com.example.final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		// Jalankan Spring Boot aplikasi
		ApplicationContext context = SpringApplication.run(FinalProjectApplication.class, args);

		// Periksa koneksi database
		try {
			DataSource dataSource = context.getBean(DataSource.class);
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("Koneksi ke database berhasil!");
			}
		} catch (Exception e) {
			System.err.println("Gagal terhubung ke database: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
