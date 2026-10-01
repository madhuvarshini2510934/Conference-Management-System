package com.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewPapers {

    public static String getPapersByAuthor(String authorName) {

        StringBuilder result = new StringBuilder();

        String sql = "SELECT paper_id, title, status, reviewer_name, "
                   + "recommendation, review_comments "
                   + "FROM papers WHERE author_name = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, authorName);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                result.append(rs.getInt("paper_id"))
                      .append("|")
                      .append(rs.getString("title"))
                      .append("|")
                      .append(rs.getString("status"))
                      .append("|")
                      .append(rs.getString("reviewer_name"))
                      .append("|")
                      .append(rs.getString("recommendation"))
                      .append("|")
                      .append(rs.getString("review_comments"))
                      .append("\n");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result.toString();
    }
}