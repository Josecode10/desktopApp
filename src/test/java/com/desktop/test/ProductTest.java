package com.desktop.test;

import com.desktop.dao.ProductDAO;
import com.desktop.model.Product;

public class ProductTest {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        // Insert
        Product p1 = new Product("Laptop", "Gaming laptop", 1200.50, 10);
        dao.insertProduct(p1);

        // Update
        Product p2 = new Product(1, "Laptop", "Updated description", 1100.00, 8);
        dao.updateProduct(p2);

        // Delete
//        dao.deleteProduct(1);

        // List all
        dao.getAllProducts().forEach(System.out::println);
    }
}
