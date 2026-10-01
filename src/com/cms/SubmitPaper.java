package com.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SubmitPaper {

    public static boolean submit(
            String title,
            String authorName) {

        String status = "Submitted";
        String reviewerName = "Not Assigned";

        String sql = "INSERT INTO papers "
                   + "(title, author_name, status, reviewer_name) "
                   + "VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, authorName);
            ps.setString(3, status);
            ps.setString(4, reviewerName);

            ps.executeUpdate();

            System.out.println("Paper submitted successfully!");

            con.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

        submit(
            "My First Conference Paper",
            "Test Author"
        );
    }
}