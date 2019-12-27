package com.mokrousov.lab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
  private static final String URL = "jdbc:mysql://localhost:3306/project_versioning";
  private static final String USER = "root";
  private static final String PASS = "root";

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(URL, USER, PASS);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
