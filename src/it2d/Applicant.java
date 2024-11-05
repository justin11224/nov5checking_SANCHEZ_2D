package it2d;

import java.util.Scanner;

public class Applicant {
    

    public void Applicants() { 
        String response;

        do {
            Scanner sc=new Scanner(System.in);
            System.out.println("|--------------------|Choose an action:  |----------------------|");
            System.out.println("|--------------------|1. ADD APPLICANT   |----------------------|");
            System.out.println("|--------------------|2. VIEW APPLICANT  |----------------------|");
            System.out.println("|--------------------|3. UPDATE APPLICANT|----------------------|");
            System.out.println("|--------------------|4. DELETE APPLICANT|----------------------|");
            System.out.println("|--------------------|5. EXIT            |----------------------|");
            
            System.out.print  ("|-------------------|Enter action number:");
            System.out.println("|---------------------|");
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
                    updateApplicant();
                    viewEmployees();
                    break;
                case 4:
                    viewEmployees();
                    deleteEmployee();
                    viewEmployees();
                    break;
                case 5:
                    System.out.println("|-------------------|Exiting...|-------------------|");
                    return;
                default:
                    System.out.println("|-------------------|Invalid option. Please try again.|-------------------|");
                    break;
            }

            System.out.print("|-------------------|Do you want to continue? (yes or no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you, see you!");
       
    }

    public void addEmployee() {
        Scanner sc = new Scanner(System.in);
       
        System.out.print("|-------------------|Applicant Full Name : ");
        String fname = sc.next();
        System.out.print("|-------------------|Applicant Email : ");
        String email = sc.next();
        System.out.print("|-------------------|Applicant Phone Number : ");
        String pn = sc.next();
        System.out.print("|-------------------|Applicant Resume : ");
        String res = sc.next();
        String sql = "INSERT INTO Applicants (Name,Email,PhoneNumber,Resume) VALUES (?,?,?,?)";
        config conf = new config();
        conf.addRecord(sql, fname, email, pn, res);
    }

    public void viewEmployees() {
        config conf = new config();
        String query = "SELECT * FROM Applicants";    
        String[] headers = {"ApplicantID", "Name", "Email", "PhoneNumber", "Resume"};
        String[] columns = {"ApplicantID", "Name", "Email", "PhoneNumber", "Resume"};

        conf.viewRecords(query, headers, columns);
    }

    public void updateApplicant() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    
    String ApplicantID;
    while (true) {
        System.out.print("|--------------------|Enter Applicant ID: ");
        ApplicantID = sc.next();
        String checkApplicantQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
        if (conf.recordExists(checkApplicantQuery, ApplicantID)) {
            break;
        } else {
            System.out.println("Applicant ID does not exist. Please try again.");
        }
    }
    
    System.out.print("|-------------------|EDIT Name: ");
    String nname = sc.next();
    System.out.print("|-------------------|EDIT Email: ");
    String nemail = sc.next();
    System.out.print("|-------------------|EDIT PhoneNumber: ");
    String npn = sc.next();
    System.out.print("|-------------------|EDIT Resume: ");
    String nres = sc.next();

    String qry = "UPDATE Applicants SET Name = ?, Email = ?, PhoneNumber = ?, Resume = ? WHERE ApplicantID = ?";
    conf.updateRecord(qry, nname, nemail, npn, nres, ApplicantID); 
}


    public void deleteEmployee() {
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        String ApplicantID;
    while (true) {
        System.out.print("|--------------------|Enter Employee ID: ");
        ApplicantID = sc.next();
        
        String checkEmployeeQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
        if (conf.recordExists(checkEmployeeQuery, ApplicantID)) {
            break;
        } else {
            System.out.println("ApplicantID does not exist. Please try again.");
        }
    }
        
        String qry = "DELETE FROM Applicants WHERE ApplicantID = ?";
        
        conf.deleteRecord(qry, ApplicantID);
    }
}
