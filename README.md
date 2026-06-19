<div align="center">
  
# 🍕 Advanced Pizza Store POS & Data Analytics Engine
  
**A robust, enterprise-grade Point of Sale system built entirely in Java.**<br>
*Demonstrating expertise in Object-Oriented Software Architecture, Data Engineering, and UI/UX Design.*

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Software Architecture](https://img.shields.io/badge/Software_Architecture-Design_Patterns-blue?style=for-the-badge)
![Data Engineering](https://img.shields.io/badge/Data_Engineering-ETL_Pipelines-success?style=for-the-badge)
![Data Analytics](https://img.shields.io/badge/Data_Analytics-Custom_Visualizations-purple?style=for-the-badge)

</div>

---

## 📖 Project Overview

This project is far more than a standard CRUD application. It is a comprehensive demonstration of **backend data pipelines**, **frontend GUI rendering**, and **scalable software design**. The system handles the entire lifecycle of a restaurant transaction: from a dynamic 2D visual pizza builder on the frontend, to real-time Kitchen Display Systems (KDS), all the way down to nightly automated ETL data warehouse migrations.

## 💼 Key Technical Competencies Demonstrated

### 1. Advanced Software Architecture (OOP Design Patterns)
This application was architected from the ground up using **9 GoF Design Patterns**, proving the ability to write highly scalable, decoupled, and maintainable enterprise code:
*   **Decorator:** Powers the **Visual Pizza Builder**. Base pizzas are wrapped with `ToppingDecorator` classes. These decorators dynamically calculate prices and instruct the Java `Graphics2D` canvas on exactly which visual layers to render in real-time.
*   **Visitor:** Drives the **ETL Data Pipeline**. It traverses in-memory historical order objects, elegantly separating the complex JSON data transformation logic from the core data structures.
*   **Observer:** Powers the asynchronous **Kitchen Display System (KDS)** and the **Cashier Gamification System** (triggering animated "Achievement Unlocked" UI toasts when specific sales conditions are met).
*   **Command:** Encapsulates cashier interactions, powering a robust **Undo/Redo** engine utilizing Stack data structures.
*   **Factory Method:** Instantiates complex, predefined traditional pizzas (e.g., Margherita, Meat Lovers) with the correct combinations of decorators.
*   **Data Access Object (DAO):** Encapsulates and abstracts all database persistence logic, seamlessly saving completed orders to the backend CSV database.
*   **Composite:** Manages the menu system (`MenuCategory` and `MenuItem`) as an N-ary tree data structure, allowing for infinitely nested sub-menus (Drinks, Sides, Dips).
*   **State:** Tracks the strict lifecycle of an order (`NewOrder`, `Preparing`, `Baking`, `Ready`).
*   **Strategy:** Abstracts the checkout process, allowing the system to easily switch between custom payment algorithms (e.g., Cash Change Calculation, Credit Card Verification).

### 2. Data Engineering & ETL Pipelines
To demonstrate backend data proficiency, this system includes a standalone **ETL (Extract, Transform, Load)** cron-style job:
*   **Extract:** Ingests raw, denormalized logs from the `orders_database.csv`.
*   **Transform:** Cleans the data, parsing complex string payloads to extract relational data.
*   **Load:** Normalizes the data into a structured schema (`fact_orders.json`, `dim_order_items.json`) and exports it to a `/warehouse` directory for downstream analytics.

### 3. Data Analytics & Custom Visualizations
Instead of relying on external charting libraries, a completely custom **Data Analytics Dashboard** was built from scratch using Java `Graphics2D`. It parses the backend database, mathematically aggregates Key Performance Indicators (Total Sales, Average Order Value), and dynamically plots a Bar Chart ranking the top-selling items.

---

## 🛠️ Installation & Execution

This project uses core Java with zero external dependencies, meaning it can be compiled and run instantly on any machine with the JDK installed.

1. **Clone the repository:**
   ```bash
   git clone <your-repo-link>
   cd PizzaStorePOS/src
   ```

2. **Compile the architecture:**
   ```bash
   javac Driver.java pos/*.java model/*.java composite/*.java command/*.java decorator/*.java observer/*.java state/*.java strategy/*.java factory/*.java database/*.java analytics/*.java etl/*.java
   ```

3. **Run the Application:**
   ```bash
   java Driver
   ```
*(Note: To test the Data Analytics and ETL pipelines, ring up a few orders in the POS and click "Checkout" to populate the database!)*
