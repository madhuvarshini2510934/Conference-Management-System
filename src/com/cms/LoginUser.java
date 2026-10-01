package com.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUser {

    public static String login(String email, String password) {

        String sql = "SELECT name, role FROM users "
                   + "WHERE email = ? AND password = ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                String role = rs.getString("role");

                System.out.println("Login successful!");
                System.out.println("Welcome " + name);
                System.out.println("Role: " + role);

                con.close();

                return name + "|" + role;
            }

            con.close();

            return "invalid";

        } catch (Exception e) {

            e.printStackTrace();

            return "error";
        }
    }

    public static void main(String[] args) {

        System.out.println(
            login("madhu@gmail.com", "12345")
        );

    }
}