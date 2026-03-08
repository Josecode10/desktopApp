package com.desktop.test;

import com.desktop.utils.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
