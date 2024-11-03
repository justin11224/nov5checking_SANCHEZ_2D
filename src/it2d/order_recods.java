package it2d;

import java.util.Scanner;

public class order_recods {
     
Scanner sc=new Scanner(System.in);
    public void record() {
        String response;
        
        do {
            System.out.println("|--------------------|Choose an action:");
            System.out.println("|--------------------|1. ADD RECORD");
            System.out.println("|--------------------|2. VIEW RECORD");
            System.out.println("|--------------------|3. UPDATE RECORD");
            System.out.println("|--------------------|4. DELETE RECORD");
            System.out.println("|--------------------|5. EXIT");

            System.out.print("|--------------------|Enter action number: ");
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    viewEmployees();
                    updateEmployee();
                    viewEmployees();
                    break;
                case 4:
                    viewEmployees();
                    deleteEmployee();
                    viewEmployees();
                    break;
                case 5:
                    System.out.println("|--------------------|Exiting...");
                    return;
                default:
                    System.out.println("|--------------------|Invalid option. Please try again.");
                    break;
            }

            System.out.print("|--------------------|Do you want to continue? (yes or no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("|--------------------|Thank you, see you!");
    }

   public void addEmployee() {
    
     config conf = new config();
    Scanner sc = new Scanner(System.in); 
    String e_id, p_id;

    // Check for a valid Employee ID
    while (true) {
        System.out.print("|--------------------|Enter Employee ID: ");
        e_id = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM tbl_employee WHERE e_id = ?";
        if (conf.recordExists(checkEmployeeQuery, e_id)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("Employee ID does not exist. Please try again.");
        }
    }

    // Check for a valid Product ID
    while (true) {
        System.out.print("|--------------------|Enter Product ID: ");
        p_id = sc.next();
        
        // Query to check if the Product ID exists
        String checkProductsQuery = "SELECT 1 FROM tbl_product WHERE p_id = ?";
        if (conf.recordExists(checkProductsQuery, p_id)) {
            break; // Product ID exists, exit the loop
        } else {
            System.out.println("Product ID does not exist. Please try again.");
        }
    }

    // After obtaining valid IDs, proceed with the rest of the order entry process
    System.out.print("|--------------------|ORDER Date: ");
    String odate = sc.next();
    System.out.print("|--------------------|ORDER Total price: ");
    String otoral = sc.next();
    System.out.print("|--------------------|ORDER Quantity: ");
    String oquan = sc.next();
    System.out.print("|--------------------|ORDER TotalTransaction: ");
    String otrans = sc.next();
    System.out.print("|--------------------|ORDER Number of Approved: ");
    String onum1 = sc.next();
    System.out.print("|--------------------|ORDER Number of Pending: ");
    String onum2 = sc.next();
    System.out.print("|--------------------|ORDER Number of Decline: ");
    String onum3 = sc.next();
    System.out.print("|--------------------|ORDER Address Delivery: ");
    String add = sc.next();

    // SQL insert statement
    String sql = "INSERT INTO tbl_orders (e_id, p_id, o_date, o_totalprice, o_quantity, o_totaltrans, o_number_of_approved, number_of_pending, number_of_decline, o_delivery_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    conf.addRecord(sql, e_id, p_id, odate, otoral, oquan, otrans, onum1, onum2, onum3, add);
}


    public void viewEmployees() {
        config conf = new config();
        String query = "SELECT * FROM tbl_orders";
        String[] headers = {"ORDER_ID", "EMPLOYEE_ID", "PRODUCT_ID", "ORDER_DATE", "ORDER_TOTALPRICE", "ORDER_QUANTITY", "ORDER_TOTALTRANSACTION", "ORDER_NUMBER_OF_APPROVED", "ORDER_NUMBER_OF_PENDING", "ORDER_NUMBER_OF_DECLINE", "ORDER_DELIVERY_ADDRESS"};
        String[] columns = {"O_id", "e_id", "p_id", "o_date", "o_totalprice", "o_quantity", "o_totaltrans", "o_number_of_approved", "number_of_pending", "number_of_decline", "o_delivery_address"};

        conf.viewRecords(query, headers, columns);
    }

    public void updateEmployee() {
        System.out.print("|--------------------|Enter the ID to update: ");
        int id = sc.nextInt();

        System.out.print("|--------------------|EDIT ORDER Date: ");
        String Nodate = sc.next();
        System.out.print("|--------------------|EDIT ORDER Total price: ");
        String Notoral = sc.next();
        System.out.print("|--------------------|EDIT ORDER Quantity: ");
        String Noquan = sc.next();
        System.out.print("|--------------------|EDIT ORDER TotalTransaction: ");
        String Notrans = sc.next();
        System.out.print("|--------------------|EDIT ORDER Number of Approved: ");
        String Nonum1 = sc.next();
        System.out.print("|--------------------|EDIT ORDER Number of Pending: ");
        String Nonum2 = sc.next();
        System.out.print("|--------------------|EDIT ORDER Number of Decline: ");
        String Nonum3 = sc.next();
        System.out.print("|--------------------|EDIT ORDER Address Delivery: ");
        String Nadd = sc.next();

        String qry = "UPDATE tbl_orders SET o_date = ?, o_totalprice = ?, o_quantity = ?, o_totaltrans = ?, o_number_of_approved = ?, number_of_pending = ?, number_of_decline = ?, o_delivery_address = ? WHERE o_id = ?";
        config conf = new config();
        conf.updateRecord(qry, Nodate, Notoral, Noquan, Notrans, Nonum1, Nonum2, Nonum3, Nadd, id); // Pass the id parameter
    }

    public void deleteEmployee() {
        System.out.print("|--------------------|Enter the ID to delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_orders WHERE o_id = ?"; // Changed to o_id
        config conf = new config();
        conf.deleteRecord(qry, id);
    }
}
