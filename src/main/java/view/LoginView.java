package view;

import model.User;
import java.sql.*;
import java.util.Scanner;
import repository.DBUtil;

public class LoginView {
    public User login() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            try (Connection conn = DBUtil.getConnection()) {
                String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return new User(username, true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
