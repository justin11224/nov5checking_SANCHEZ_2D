package it2d;

import java.util.Scanner;

public class APPLICATIONS {
     
    Scanner sc = new Scanner(System.in);

    public void record() {
        String response;
        
        do {
            System.out.println("|--------------------|     APPLICATIONS      |--------------------|");
            System.out.println("|--------------------|Choose an action:      |--------------------|");
            System.out.println("|--------------------|1. ADD APPLICATION     |--------------------|");
            System.out.println("|--------------------|2. VIEW APPLICATION    |--------------------|");
            System.out.println("|--------------------|3. UPDATE APPLICATION  |--------------------|");
            System.out.println("|--------------------|4. DELETE APPLICATION  |--------------------|");
            System.out.println("|--------------------|5. EXIT                |--------------------|");

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
        config conf = new config();
        String ApplicantID, JobID;

        Applicant ap = new Applicant();
        ap.viewEmployees();
        while (true) {
            System.out.print("|--------------------|Enter Applicant ID: ");
            ApplicantID = sc.next();

            String checkEmployeeQuery = "SELECT 1 FROM Applicants WHERE ApplicantID = ?";
            if (conf.recordExists(checkEmployeeQuery, ApplicantID)) {
                break;
            } else {
                System.out.println("Applicant ID does not exist. Please try again.");
            }
        }

        Job_Listing a = new Job_Listing();
        a.viewProducts();
        while (true) {
            System.out.print("|--------------------|Enter Job ID: ");
            JobID = sc.next();

            String checkEmployeeQuery = "SELECT 1 FROM JobListings WHERE JobID = ?";
            if (conf.recordExists(checkEmployeeQuery, JobID)) {
                break;
            } else {
                System.out.println("Job ID does not exist. Please try again.");
            }
        }

        System.out.println("Enter ApplicationDate(YYYY-MM-DD)");
        String date = sc.next();
        System.out.println("Enter Status:");
        String status = sc.next();

        String qry = "INSERT INTO Applications (ApplicantID, JobID, ApplicationDate, Status) VALUES (?, ?, ?, ?)";
        conf.addRecord(qry, ApplicantID, JobID, date, status);
    }

    public void viewApplications() {
    config conf = new config();

    System.out.println("Choose an option:");
    System.out.println("1. View all applications");
    System.out.println("2. View applications applying for one Job ID");
    System.out.println("3. View applications with due ApplicationDate");
    System.out.println("4. View applicant report by ApplicantID"); // Updated option
    System.out.print("Enter your choice: ");
    
    int choice = sc.nextInt();
    
    switch (choice) {
        case 1:
            String query = "SELECT * FROM Applications";
            String[] headers = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            String[] columns = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            conf.viewRecords(query, headers, columns);
            break;

        case 2:
            String query0 = "SELECT JobID FROM Applications";  // Change the query to select JobID
            String[] headers0 = {"JobID"};  
            String[] columns0 = {"JobID"}; 
            conf.viewRecords(query0, headers0, columns0);
            System.out.print("Enter JobID to view applicants: ");
            int jobId = sc.nextInt();

            String query1 = "SELECT JobListings.JobID, Applicants.ApplicantID, Applications.Status " +
                            "FROM JobListings " +
                            "INNER JOIN Applications ON JobListings.JobID = Applications.JobID " +
                            "INNER JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID " +
                            "WHERE JobListings.JobID = ?";

            String[] headers1 = {"JobID", "ApplicantID", "Status"};
            String[] columns1 = {"JobID", "ApplicantID", "Status"};
            
            conf.viewRecordsWithParam(query1, headers1, columns1, jobId);
            break;

        case 3:
            System.out.println("Viewing applications with past ApplicationDate:");
String query2 = "SELECT ApplicationID, ApplicantID, ApplicationDate " +
                "FROM Applications WHERE ApplicationDate < CURRENT_DATE";
String[] headers2 = {"ApplicationID", "ApplicantID", "ApplicationDate"};
String[] columns2 = {"ApplicationID", "ApplicantID", "ApplicationDate"};

conf.viewRecords(query2, headers2, columns2);

            break;

        case 4:
           
    System.out.println("Viewing all ApplicationIDs:");
    String query5 = "SELECT ApplicantID FROM Applicants";
    String[] headers5 = {"ApplicantID"};
    String[] columns5 = {"ApplicantID"};
    conf.viewRecords(query5, headers5, columns5);

    System.out.print("Enter ApplicantID to view their report: ");
    int applicantId = sc.nextInt();

    System.out.println("Viewing report for ApplicantID: " + applicantId);

    String query4 = "SELECT Applicants.ApplicantID, Applicants.Name, Applicants.Email, Applicants.Phonenumber, " +
                    "Applications.JobID, Applications.ApplicationDate, Applications.Status " +
                    "FROM Applicants " +
                    "LEFT JOIN Applications ON Applicants.ApplicantID = Applications.ApplicantID " +
                    "WHERE Applicants.ApplicantID = ?";

    String[] headers4 = {"ApplicantID", "Name", "Email", "Phonenumber", "JobID", "ApplicationDate", "Status"};
    String[] columns4 = {"ApplicantID", "Name", "Email", "Phonenumber", "JobID", "ApplicationDate", "Status"};

    conf.viewRecordsWithParam(query4, headers4, columns4, applicantId);
    break;

            

        default:
            System.out.println("Invalid choice. Please try again.");
            break;
    }
}



    

    public void updateApplications() {
        config conf = new config();
        String applicationID;

        while (true) {
            System.out.print("|--------------------|Enter Application ID: ");
            applicationID = sc.next();

            String checkApplicationQuery = "SELECT 1 FROM Applications WHERE ApplicationID = ?";
            if (conf.recordExists(checkApplicationQuery, applicationID)) {
                break;
            } else {
                System.out.println("Application ID does not exist. Please try again.");
            }
        }

        System.out.print("|--------------------|EDIT ApplicantID: ");
        String newApplicantID = sc.next();
        System.out.print("|--------------------|EDIT JobID: ");
        String newJobID = sc.next();
        System.out.print("|--------------------|EDIT ApplicationDate: ");
        String newApplicationDate = sc.next();
        System.out.print("|--------------------|EDIT Status: ");
        String newStatus = sc.next();

        String qry = "UPDATE Applications SET ApplicantID = ?, JobID = ?, ApplicationDate = ?, Status = ? WHERE ApplicationID = ?";
        conf.updateRecord(qry, newApplicantID, newJobID, newApplicationDate, newStatus, applicationID);
    }

    public void deleteApplications() {
        config conf = new config();
        String applicationID;

        while (true) {
            System.out.print("|--------------------|Enter Application ID: ");
            applicationID = sc.next();

            String checkApplicationQuery = "SELECT 1 FROM Applications WHERE ApplicationID = ?";
            if (conf.recordExists(checkApplicationQuery, applicationID)) {
                break;
            } else {
                System.out.println("Application ID does not exist. Please try again.");
            }
        }

        String qry = "DELETE FROM Applications WHERE ApplicationID = ?";
        conf.deleteRecord(qry, applicationID);
    }
}
