package me.wane.utility;

import java.sql.*;
import java.util.Random;

public class DummyDataGenerator {

  // MYSQL 데이터베이스 연결 정보
  private static final String DB_URL = "jdbc:mysql://localhost:3306/pay";
  private static final String DB_USER = "mysqluser";
  private static final String DB_PASSWORD = "mysqlpw";

  // 샘플 주소 데이터 배열
  private static final String[] ADDRESSES = {"강남구", "관악구", "서초구"};

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

      generateDummyData(conn);

      conn.close();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static void generateDummyData(Connection conn) throws SQLException {
    String insertQuery = "INSERT INTO membership(membership_id, address, email, is_corp, is_valid, name) VALUES (?, ?, ?, ?, ?, ?)";
    Random random = new Random();

    PreparedStatement pstmt = conn.prepareStatement(insertQuery);

    int numberOfDummyData = 1000;

    for (int i = 1; i < numberOfDummyData; i++) {
      pstmt.setLong(1, i);
      pstmt.setString(2, ADDRESSES[random.nextInt(ADDRESSES.length)]);
      pstmt.setString(3, "email_" + i + "@example.com");
      pstmt.setBoolean(4, random.nextBoolean());
      pstmt.setBoolean(5, random.nextBoolean());
      pstmt.setString(6, "User " + i);

      pstmt.executeUpdate();
    }

    pstmt.close();
  }

}
