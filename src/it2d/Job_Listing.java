package it2d;

import java.util.Scanner;

public class Job_Listing {
    public void Job_Listings() { 
        String response;
Scanner sc = new Scanner(System.in);

        do {
            System.out.println("|--------------------|Choose an action: |----------------------|");
            System.out.println("|--------------------|1. ADD JOB        |----------------------|");
            System.out.println("|--------------------|2. VIEW JOB       |----------------------|");
            System.out.println("|--------------------|3. UPDATE JOB     |----------------------|");
            System.out.println("|--------------------|. DELETE JOB      |----------------------|");
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
        System.out.print("|-------------------|JOB Title: ");
        String title = sc.next();
        System.out.print("|-------------------|JOB Department: ");
        String dep = sc.next();
        System.out.print("|-------------------|JOB Location: ");
        String loc = sc.next();
        System.out.print("|-------------------|JOB Application Deadline: ");
        String ad = sc.next();

        String sql = "INSERT INTO JobListings (JobTitle, Department, Location, ApplicationDeadline) VALUES (?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, title, dep, loc, ad);
    }

    public void viewProducts() {
        config conf = new config();
        String query = "SELECT * FROM JobListings";
        String[] headers = {"JobID", "JobTitle", "Department", "Location", "ApplicationDeadline"};
        String[] columns = {"JobID", "JobTitle", "Department", "Location", "ApplicationDeadline"};

        conf.viewRecords(query, headers, columns);
    }

    public void updateProduct() {
         config conf = new config();
        Scanner sc = new Scanner(System.in);
        String JobID;
    while (true) {
        System.out.print("|--------------------|Enter JobID: ");
        JobID = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
        if (conf.recordExists(checkEmployeeQuery, JobID)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("JobID does not exist. Please try again.");
        }
    }

        System.out.print("|-------------------|EDIT JOB Title: ");
        String ntitle = sc.next();
        System.out.print("|-------------------|EDIT JOB Department: ");
        String ndep = sc.next();
        System.out.print("|-------------------|EDIT JOB Location: ");
        String nloc = sc.next();
        System.out.print("|-------------------|EDIT JOB ApplicationDeadline: ");
        String nad = sc.next();

        String qry = "UPDATE JobListings SET JobTitle = ?, Department = ?, Location = ?, ApplicationDeadline = ? WHERE JobID = ?";
       
        conf.updateRecord(qry, ntitle, ndep, nloc, nad,JobID); 
    }

    public void deleteProduct() {
        
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        String JobID;
    while (true) {
        System.out.print("|--------------------|Enter JobID: ");
        JobID = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
        if (conf.recordExists(checkEmployeeQuery, JobID)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("JobID does not exist. Please try again.");
        }
    }

        String qry = "DELETE FROM JobListings WHERE JobID = ?";
        
        conf.deleteRecord(qry, JobID);
    }
}
