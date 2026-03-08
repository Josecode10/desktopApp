# Product Management Desktop App

## 📘 Overview
A Java Swing desktop application for managing product inventory with CRUD operations (Create, Read, Update, Delete) connected to a MySQL database.  
The app provides a simple and intuitive interface for inserting, updating, deleting, and searching products.

---

## 🚀 Features
- Insert new products into the database
- Update existing product details
- Delete products by ID
- Search for a product by ID
- View all products in a table
- Auto-fill form fields by clicking a table row
- Clear input fields with one click
- Row counter for displayed items
- Confirmation dialogs for safe updates and deletions
- Icons and tooltips for intuitive navigation

---

## 🛠 Technologies Used
- **Java Swing** for the graphical user interface  
- **JDBC** for database connectivity  
- **MySQL** for product data storage  
- **Eclipse IDE** for development  
- **MVC pattern** for structured design  

---

## 📂 Project Structure
ProductManagementApp/
├── README.md
├── src/
│   ├── com.desktop.model/      # Product.java (data model)
│   ├── com.desktop.dao/        # ProductDAO.java (database operations)
│   └── com.desktop.view/       # ProductForm.java (GUI and event handling)
├── icons/                      # Button icons
└── images/                     # Screenshots and diagrams


---

## ⚙️ Setup Instructions
1. Clone the repository or copy the source files into your Java project.
2. Ensure MySQL is running and create a `product` table with columns:  
   - `id`, `name`, `description`, `price`, `quantity`
3. Update your JDBC connection string in `ProductDAO.java`.
4. Run `ProductForm.java` from your IDE (e.g., Eclipse).

---

## 🧠 How It Works

➕ Insert
User Action: Fill in product details → click Insert

GUI (ProductForm): Calls insertProduct(ActionEvent e)

DAO (ProductDAO): Executes insertProduct(Product p) using JDBC

Database (MySQL): Runs INSERT INTO product (...)

Result: Product is added, table refreshes with viewAllProducts()

-------------------------------------------------

✏️ Update
User Action: Select a row or enter ID → edit fields → click Update

GUI (ProductForm): Calls updateProduct(ActionEvent e) after confirmation dialog

DAO (ProductDAO): Executes updateProduct(Product p)

Database (MySQL): Runs UPDATE product SET ... WHERE id=?

Result: Product is updated, table refreshes with viewAllProducts()

-------------------------------------------------
🗑 Delete
User Action: Enter ID → click Delete

GUI (ProductForm): Calls deleteProduct(ActionEvent e) after confirmation dialog

DAO (ProductDAO): Executes deleteProduct(int id)

Database (MySQL): Runs DELETE FROM product WHERE id=?

Result: Product is removed, table refreshes with viewAllProducts()

-------------------------------------------------
👁 View All
User Action: Click View All

GUI (ProductForm): Calls viewAllProducts(ActionEvent e)

DAO (ProductDAO): Executes getAllProducts()

Database (MySQL): Runs SELECT * FROM product

Result: Table is cleared and repopulated with all products, row counter resets

-------------------------------------------------
🔍 Search by ID
User Action: Enter ID → click Search by ID

GUI (ProductForm): Calls searchProductById(ActionEvent e)

DAO (ProductDAO): Executes getProductById(int id)

Database (MySQL): Runs SELECT * FROM product WHERE id=?

Result: Fields auto‑fill with product data

-------------------------------------------------
🧹 Clear Fields
User Action: Click Clear Fields

GUI (ProductForm): Calls clearFields()

DAO: Not involved

Database: Not involved

Result: Input fields reset, table unchanged

-------------------------------------------------
🖱 Row Click
User Action: Click a row in the table

GUI (ProductForm): ListSelectionListener copies row data into fields

DAO: Not involved

Database: Not involved

Result: Fields auto‑fill with selected row’s data


## 🛡 Security Notes
- All SQL queries use **parameterized statements** to prevent SQL injection.  
- Confirmation dialogs are used before update and delete operations.  

---
## 👨‍🏫 Author
**José Manuel Colmenares León - Aprendiz ADSO**  
Java Developer | Security-aware | GUI Enthusiast  
Location: Bucaramanga, Colombia

