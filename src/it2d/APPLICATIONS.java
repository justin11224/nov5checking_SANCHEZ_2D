package it2d;

import java.util.Scanner;

public class APPLICATIONS {
     
Scanner sc=new Scanner(System.in);
    public void record() {
        String response;
        
        do {
            System.out.println("|--------------------|Choose an action:");
            System.out.println("|--------------------|1. ADD APPLICATION");
            System.out.println("|--------------------|2. VIEW APPLICATION");
            System.out.println("|--------------------|3. UPDATE APPLICATION");
            System.out.println("|--------------------|4. DELETE APPLICATION");
            System.out.println("|--------------------|5. EXIT");

            System.out.print("|--------------------|Enter action number: ");
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    addApplications();
                    break;
                case 2:
                    viewApplications();
                    break;
                case 3:
                    viewApplications();
                    updateApplications();
                    viewApplications();
                    break;
                case 4:
                    viewApplications();
                    deleteApplications();
                    viewApplications();
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

   public void addApplications() {
    
     Scanner sc =new Scanner(System.in);
     config conf=new config();
     String ApplicantID,JobID;   
     
     Applicant ap=new Applicant();
     ap.viewEmployees();
    while (true) {
        System.out.print("|--------------------|Enter Applicant ID: ");
        ApplicantID = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
        if (conf.recordExists(checkEmployeeQuery, ApplicantID)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("Applicant ID does not exist. Please try again.");
        }
    }
     Job_Listing a=new Job_Listing();
     a.viewProducts();
    while (true) {
        System.out.print("|--------------------|Enter Job ID: ");
        JobID = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
        if (conf.recordExists(checkEmployeeQuery, JobID)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("Job ID does not exist. Please try again.");
        }
    }
       System.out.println("Enter ApplicationDate(YYYY-MM-DD)");
       String date =sc.next();
       System.out.println("Enter Status:");
       String status =sc.next();
       
       String qry ="INSERT INTO Applications (ApplicantID,JobID,ApplicationDate,Status) Values(? ,? ,? , ?)";
       conf.addRecord(qry,ApplicantID,JobID, date,status);
}


    public void viewApplications() {
        config conf = new config();
        String query = "SELECT * FROM Applications";
        String[] headers = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate","Status"};
        String[] columns = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
        conf.viewRecords(query, headers, columns);
    }

    public void updateApplications () {
        config conf=new config();
     String job;

    // Check for a valid Employee ID
    while (true) {
        System.out.print("|--------------------|Enter Applications ID: ");
        job = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
        if (conf.recordExists(checkEmployeeQuery, job)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("Applications ID does not exist. Please try again.");
        }
    }

        System.out.print("|--------------------|EDIT ApplicantID: ");
        String nap = sc.next();
        System.out.print("|--------------------|EDIT JobID: ");
        String njob = sc.next();
        System.out.print("|--------------------|EDIT ApplicationDate: ");
        String npd = sc.next();
        System.out.print("|--------------------|EDIT Status: ");
        String ns = sc.next();

        String qry = "UPDATE Applicants SET ApplicantID = ?,JobID  = ?, ApplicationDate =?,Status+?";
        
        conf.updateRecord(qry, njob, npd, ns,nap);
    }

    public void deleteApplications() {
         config conf=new config();
     String job;

    // Check for a valid Employee ID
    while (true) {
        System.out.print("|--------------------|Enter Applications ID: ");
        job = sc.next();
        
        // Query to check if the Employee ID exists
        String checkEmployeeQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
        if (conf.recordExists(checkEmployeeQuery, job)) {
            break; // Employee ID exists, exit the loop
        } else {
            System.out.println("Applications ID does not exist. Please try again.");
        }
    }
        String qry = "DELETE FROM tbl_orders WHERE o_id = ?"; 
      
        conf.deleteRecord(qry, job);
    }
}
