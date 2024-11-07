package it2d;

import java.util.Scanner;

public class IT2d {
    public static void main(String[] args) {
        String response;
        Scanner sc = new Scanner(System.in); 
        do { 
            System.out.println("|-----------|'Job Application Management System'|--------------|");
            System.out.println("|---------------------|1. APPLICANTS    |----------------------|");
            System.out.println("|---------------------|2. JOB LISTING   |----------------------|");
            System.out.println("|---------------------|3. APPLICATION   |----------------------|");
            System.out.println("|---------------------|4. EXIT          |----------------------|");
           
            System.out.print  ("|-------------------|Enter action number: ");
            System.out.println("|--------------------|");
            int action = sc.nextInt();
            sc.nextLine(); 
            
            Applicant Action1 = new Applicant();
            Job_Listing  Action2  = new Job_Listing();
            APPLICATIONS Action3 = new APPLICATIONS();
            switch(action) {
                case 1: 
                    Action1.Applicants(); 
                    break;
                case 2:
                    Action2.Job_Listings();
                    break;
                case 3:
                    
                   Action3.record();
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
            
            System.out.print("Do you want to continue? (yes or no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you, see you!");
        sc.close(); 
    }
}
