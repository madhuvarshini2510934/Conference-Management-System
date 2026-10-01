package com.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReviewPaper {

    public static boolean review(
            int paperId,
            String reviewerName,
            String comments,
            String recommendation) {

        String sql = "UPDATE papers "
                   + "SET reviewer_name = ?, "
                   + "review_comments = ?, "
                   + "recommendation = ?, "
                   + "status = 'Reviewed' "
                   + "WHERE paper_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, reviewerName);
            ps.setString(2, comments);
            ps.setString(3, recommendation);
            ps.setInt(4, paperId);

            int rows = ps.executeUpdate();

            con.close();

            if (rows > 0) {
                System.out.println("Review submitted successfully!");
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}