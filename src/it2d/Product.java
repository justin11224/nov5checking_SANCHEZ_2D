package it2d;

import java.util.Scanner;

public class Product {
    public void Products() { 
        String response;
Scanner sc = new Scanner(System.in);

        do {
            System.out.println("|--------------------|Choose an action: |----------------------|");
            System.out.println("|--------------------|1. ADD PRODUCT    |----------------------|");
            System.out.println("|--------------------|2. VIEW PRODUCTS  |----------------------|");
            System.out.println("|--------------------|3. UPDATE PRODUCT |----------------------|");
            System.out.println("|--------------------|. DELETE PRODUCT  |----------------------|");
            System.out.println("|--------------------|5. EXIT           |----------------------|");
            
            System.out.print("Enter action number: ");
            int action = sc.nextInt();
            
            switch (action) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;  
                case 3:
                    viewProducts();
                    updateProduct();
                    viewProducts();
                    break;
                case 4:
                    viewProducts();
                    deleteProduct();
                    viewProducts();
                    break;
                case 5:
                    System.out.println("|-------------------|Exiting...");
                    return;
                default:
                    System.out.println("|-------------------|Invalid option. Please try again.|-------------------|");
                    break;
            }

            System.out.print("|-------------------|Do you want to continue? (yes or no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("|-------------------|Thank you, see you!");
    }

    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("|-------------------|Product Name: ");
        String name = sc.next();
        System.out.print("|-------------------|Product Stucks: ");
        String category = sc.next();
        System.out.print("|-------------------|Product Price: ");
        double price = sc.nextDouble();
        System.out.print("|-------------------|Product Status: ");
        int stock = sc.nextInt();

        String sql = "INSERT INTO tbl_product (p_name, p_stucks, p_price, p_status) VALUES (?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, name, category, price, stock);
    }

    public void viewProducts() {
        config conf = new config();
        String query = "SELECT * FROM tbl_product";
        String[] headers = {"Product ID", "Product Name", "Product Stucks", "Product Price", "Product Stock"};
        String[] columns = {"p_id", "p_name", "p_stucks", "p_price", "p_status"};

        conf.viewRecords(query, headers, columns);
    }

    public void updateProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("|-------------------|Enter the ID to update: ");
        int id = sc.nextInt();

        System.out.print("|-------------------|EDIT Product Name: ");
        String nname = sc.next();
        System.out.print("|-------------------|EDIT Stucks: ");
        String ncategory = sc.next();
        System.out.print("|-------------------|EDIT Price: ");
        double nprice = sc.nextDouble();
        System.out.print("|-------------------|EDIT Stock: ");
        int nstock = sc.nextInt();

        String qry = "UPDATE tbl_product SET p_name = ?, p_stucks = ?, p_price = ?, p_status = ? WHERE p_id = ?";
        config conf = new config();
        conf.updateRecord(qry, nname, ncategory, nprice, nstock, id); 
    }

    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("|-------------------|Enter the ID to delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_product WHERE p_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, id);
    }
}
