package com.desktop.view;

import com.desktop.dao.ProductDAO;
import com.desktop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductForm extends JFrame {
    // --- GUI components ---
    private JTextField txtId, txtName, txtDescription, txtPrice, txtQuantity; // input fields
    private JTable table;                        // table to display products
    private DefaultTableModel tableModel;        // table model for managing rows
    private ProductDAO dao;                      // Data Access Object for database operations

    public ProductForm() {
        dao = new ProductDAO(); // initialize DAO

        // --- Window setup ---
        setTitle("Product Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Main panel with vertical layout (stack components top to bottom) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // --- Input Panel (fields for product details) ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        
        // --- Add a title --- //
        JLabel title = new JLabel("Product Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(title, 0); // add before inputPanel
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtId = new JTextField(); // used only for search/update/delete
        txtName = new JTextField();
        txtDescription = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        // add labels + fields
        inputPanel.add(new JLabel("ID (for search/update/delete):"));
        inputPanel.add(txtId);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(txtDescription);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(txtQuantity);

        // --- Button Panel (CRUD + search + clear) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSearch = new JButton("Search by ID");
        JButton btnInsert = new JButton("Insert");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnViewAll = new JButton("View All");
        JButton btnClear = new JButton("Clear Fields");

        // add buttons to panel
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);
        
        // --- Add icons to the buttons --- //
        btnInsert.setIcon(new ImageIcon("src/icons/icons8-add-50.png"));
        btnUpdate.setIcon(new ImageIcon("src/icons/icons8-update-50.png"));
        btnDelete.setIcon(new ImageIcon("src/icons/icons8-delete-48.png"));
        btnViewAll.setIcon(new ImageIcon("src/icons/icons8-show-50.png"));
        btnSearch.setIcon(new ImageIcon("src/icons/icons8-search-50.png"));
        btnClear.setIcon(new ImageIcon("src/icons/icons8-clear-32.png"));
        
        // --- Add styles to the buttons --- //
        btnInsert.setBackground(Color.GREEN);
        btnUpdate.setBackground(Color.ORANGE);
        btnDelete.setBackground(Color.RED);
        btnViewAll.setBackground(Color.CYAN);
        btnSearch.setBackground(Color.LIGHT_GRAY);
        btnClear.setBackground(Color.PINK);


        // --- Table for products ---
        String[] columnNames = {"#", "ID", "Name", "Description", "Price", "Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        // Adjust column widths for better balance //
        table.getColumnModel().getColumn(0).setPreferredWidth(30);   // # (row number) very narrow
        table.getColumnModel().getColumn(1).setPreferredWidth(30);   // ID narrow
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Name medium
        table.getColumnModel().getColumn(3).setPreferredWidth(200);  // Description wide
        table.getColumnModel().getColumn(4).setPreferredWidth(30);   // Price narrow
        table.getColumnModel().getColumn(5).setPreferredWidth(30);
        
        table.setFillsViewportHeight(true);          // table fills the scroll pane height
        table.setRowHeight(25);                      // each row is taller for readability
        table.setSelectionBackground(Color.YELLOW);  // highlight selected row in yellow
        table.setGridColor(Color.LIGHT_GRAY);        
        
        JScrollPane scrollPane = new JScrollPane(table); // scrollable table

        // --- Add everything to main panel ---
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(mainPanel); // add main panel to frame

        // --- Button Actions (connect buttons to methods) ---
        btnInsert.addActionListener(this::insertProduct);
        btnUpdate.addActionListener(this::updateProduct);
        btnDelete.addActionListener(this::deleteProduct);
        btnViewAll.addActionListener(this::viewAllProducts);
        btnSearch.addActionListener(this::searchProductById);
        btnClear.addActionListener(e -> clearFields());

        // --- Table Row Click: auto-fill fields when selecting a row ---
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                txtId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                txtName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                txtDescription.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                txtPrice.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                txtQuantity.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
            }
        });
    }

    // --- Insert product (ID ignored, auto-generated by DB) ---
    private void insertProduct(ActionEvent e) {
        try {
            Product p = new Product(
                    txtName.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQuantity.getText())
            );
            dao.insertProduct(p);
            JOptionPane.showMessageDialog(this, "Product inserted successfully!");
            clearFields();
            viewAllProducts(null); // refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inserting product: " + ex.getMessage());
        }
    }

    // --- Update product (requires ID) ---
    private void updateProduct(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());

            // Confirmation dialog before updating
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to update product ID " + id + "?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Product p = new Product(
                        id,
                        txtName.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtQuantity.getText())
                );
                dao.updateProduct(p);
                JOptionPane.showMessageDialog(this, "Product updated successfully!");
                clearFields();
                viewAllProducts(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage());
        }
    }


    // --- Delete product (requires ID) ---
    private void deleteProduct(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());

            // Confirmation dialog before deleting
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete product ID " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteProduct(id);
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                clearFields();
                viewAllProducts(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage());
        }
    }


    // --- View all products (refresh table) ---
    private void viewAllProducts(ActionEvent e) {
        try {
            List<Product> products = dao.getAllProducts();
            tableModel.setRowCount(0); // clear table before adding new rows
            int counter = 1;
            for (Product p : products) {
                Object[] row = {
                		counter++,
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getQuantity()
                };
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching products: " + ex.getMessage());
        }
    }

    // --- Search product by ID ---
    private void searchProductById(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());
            Product p = dao.getProductById(id);

            if (p != null) {
                txtName.setText(p.getName());
                txtDescription.setText(p.getDescription());
                txtPrice.setText(String.valueOf(p.getPrice()));
                txtQuantity.setText(String.valueOf(p.getQuantity()));
                JOptionPane.showMessageDialog(this, "Product found!");
            } else {
                JOptionPane.showMessageDialog(this, "No product found with ID: " + id);
                clearFields();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching product: " + ex.getMessage());
        }
    }

    // --- Clear all input fields ---
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    // --- Main method: start the app ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductForm form = new ProductForm();
            form.setVisible(true);
        });
    }
}
