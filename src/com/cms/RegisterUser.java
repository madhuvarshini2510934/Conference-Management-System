package com.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterUser {

    public static void register(String name, String email, String password, String role) {

        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);

            ps.executeUpdate();

            System.out.println("User registered successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        register(
            "Madhu",
            "madhu@gmail.com",
            "12345",
            "Participant"
        );
    }
}