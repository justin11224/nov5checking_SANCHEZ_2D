package it2d;

import java.util.Scanner;

public class APPLICATIONS {

    Scanner sc = new Scanner(System.in);

    public void record() {
        String response;

        do {
            System.out.println("|--------------------|     APPLICATIONS      |--------------------|");
            System.out.println("|--------------------|Choose an action:      |--------------------|");
            System.out.println("|--------------------|A. ADD APPLICATION     |--------------------|");
            System.out.println("|--------------------|B. VIEW APPLICATION    |--------------------|");
            System.out.println("|--------------------|C. UPDATE APPLICATION  |--------------------|");
            System.out.println("|--------------------|D. DELETE APPLICATION  |--------------------|");
            System.out.println("|--------------------|E. EXIT                |--------------------|");

            boolean validInput = false;
String action;
do {
    System.out.print("|-------------------|Enter action letter (A, B, C, D, E): ");
    action = sc.nextLine().toUpperCase();

    if (action.equals("A") || action.equals("B") || action.equals("C") || action.equals("D") || action.equals("E")) {
        validInput = true;
    } else {
        System.out.println("|--------------------|Invalid input! Please enter A, B, C, D, or E.");
    }
} while (!validInput);

if (action.equals("A")) {
    addApplications();
} else if (action.equals("B")) {
    viewApplications();
} else if (action.equals("C")) {
    updateApplications();
} else if (action.equals("D")) {
    deleteApplications();
} else if (action.equals("E")) {
    System.out.println("|--------------------|Exiting...");
    return;
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
        ap.viewApplicant();
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
        a.viewJob();
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
        System.out.println("Enter Status(Approved-Pending-Decline):");
        String status = sc.next();

        String qry = "INSERT INTO Applications (ApplicantID, JobID, ApplicationDate, Status) VALUES (?, ?, ?, ?)";
        conf.addRecord(qry, ApplicantID, JobID, date, status);
    }

      public void viewApplications() {
    config conf = new config();

    System.out.println("Choose an option:");
    System.out.println("A. View all applications");
    System.out.println("B. View applications applying for one Job ID");
    System.out.println("C. View applications with past ApplicationDate");
    System.out.println("D. View applicant report by ApplicantID");
    System.out.println("E. View all approved applications");
    System.out.println("F. View all pending applications");
    System.out.println("G. View all declined applications");
    System.out.println("H. Exit");
    System.out.print("Enter your choice: ");

    char choice = sc.next().toUpperCase().charAt(0);

    if (choice == 'A') {
        String query = "SELECT * FROM Applications";
        String[] headers = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
        String[] columns = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
        conf.viewRecords(query, headers, columns);
    } else if (choice == 'B') {
        String query0 = "SELECT DISTINCT a.JobID, j.JobTitle, j.Department FROM Applications a JOIN JobListings j ON a.JobID = j.JobID";
        String[] headers0 = {"JobID", "JobTitle", "Department"};
        String[] columns0 = {"JobID", "JobTitle", "Department"};
        conf.viewRecords(query0, headers0, columns0);

        int jobId;
        boolean isValidJobId = false;

        while (!isValidJobId) {
            System.out.print("Enter JobID to view applicants: ");
            jobId = sc.nextInt();

            String validationQuery = "SELECT 1 FROM Applications WHERE JobID = ?";
            isValidJobId = conf.checkExistsWithParam(validationQuery, jobId);

            if (!isValidJobId) {
                System.out.println("Invalid JobID. Please enter a valid JobID from the list above.");
            } else {
                String query1 = "SELECT JobListings.JobID, Applicants.ApplicantID, Applicants.Name, Applicants.Resume, Applications.Status FROM JobListings INNER JOIN Applications ON JobListings.JobID = Applications.JobID INNER JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID WHERE JobListings.JobID = ?";
                String[] headers1 = {"JobID", "ApplicantID", "Name", "Resume", "Status"};
                String[] columns1 = {"JobID", "ApplicantID", "Name", "Resume", "Status"};
                conf.viewRecordsWithParam(query1, headers1, columns1, jobId);
            }
        }

    } else if (choice == 'C') {
        String query2 = "SELECT Applications.ApplicationID, Applications.ApplicantID, Applications.ApplicationDate, " +
                        "JobListings.JobID, Applicants.Name, Applicants.Email, Applicants.PhoneNumber " +
                        "FROM Applications " +
                        "LEFT JOIN JobListings ON Applications.JobID = JobListings.JobID " +
                        "LEFT JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID " +
                        "WHERE Applications.ApplicationDate < CURRENT_DATE";
        
        String[] headers2 = {"ApplicationID", "ApplicantID", "ApplicationDate", "JobID", "Name", "Email", "PhoneNumber"};
        String[] columns2 = {"ApplicationID", "ApplicantID", "ApplicationDate", "JobID", "Name", "Email", "PhoneNumber"};
        
        conf.viewRecords(query2, headers2, columns2);

    } else if (choice == 'D') {
        String query5 = "SELECT Applicants.ApplicantID, Applicants.Name, JobListings.JobTitle, JobListings.Department " +
                        "FROM Applicants " +
                        "LEFT JOIN Applications ON Applicants.ApplicantID = Applications.ApplicantID " +
                        "LEFT JOIN JobListings ON Applications.JobID = JobListings.JobID";

        String[] headers5 = {"ApplicantID", "Name", "JobTitle", "Department"};
        String[] columns5 = {"ApplicantID", "Name", "JobTitle", "Department"};

        conf.viewRecords(query5, headers5, columns5);

        System.out.print("Enter ApplicantID to view their report: ");
        int applicantId = sc.nextInt();

        String query4 = "SELECT Applicants.ApplicantID, Applicants.Name, Applicants.Email, Applicants.Phonenumber, Applications.JobID, Applications.ApplicationDate, Applications.Status FROM Applicants LEFT JOIN Applications ON Applicants.ApplicantID = Applications.ApplicantID WHERE Applicants.ApplicantID = ?";
        String[] headers4 = {"ApplicantID", "Name", "Email", "Phonenumber", "JobID", "ApplicationDate", "Status"};
        String[] columns4 = {"ApplicantID", "Name", "Email", "Phonenumber", "JobID", "ApplicationDate", "Status"};
        conf.viewRecordsWithParam(query4, headers4, columns4, applicantId);

    } else if (choice == 'E') {
        String query3 = "SELECT Applications.ApplicationID, Applications.ApplicantID, Applications.ApplicationDate, " +
                        "Applications.Status, JobListings.JobID, JobListings.JobTitle, Applicants.Name " +
                        "FROM Applications " +
                        "LEFT JOIN JobListings ON Applications.JobID = JobListings.JobID " +
                        "LEFT JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID " +
                        "WHERE Applications.Status = 'Approved'";
        String[] headers3 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        String[] columns3 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        
        
        conf.viewRecords(query3, headers3, columns3);

    } else if (choice == 'F') {
        String query6 = "SELECT Applications.ApplicationID, Applications.ApplicantID, Applications.ApplicationDate, " +
                        "Applications.Status, JobListings.JobID, JobListings.JobTitle, Applicants.Name " +
                        "FROM Applications " +
                        "LEFT JOIN JobListings ON Applications.JobID = JobListings.JobID " +
                        "LEFT JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID " +
                        "WHERE Applications.Status = 'Pending'";
        
        String[] headers6 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        String[] columns6 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        
        conf.viewRecords(query6, headers6, columns6);

    } else if (choice == 'G') {
        String query7 = "SELECT Applications.ApplicationID, Applications.ApplicantID, Applications.ApplicationDate, " +
                        "Applications.Status, JobListings.JobID, JobListings.JobTitle, Applicants.Name " +
                        "FROM Applications " +
                        "LEFT JOIN JobListings ON Applications.JobID = JobListings.JobID " +
                        "LEFT JOIN Applicants ON Applications.ApplicantID = Applicants.ApplicantID " +
                        "WHERE Applications.Status = 'Declined'";
        
        String[] headers7 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        String[] columns7 = {"ApplicationID", "Name", "ApplicationDate", "Status", "JobID", "JobTitle", "ApplicantID"};
        
        conf.viewRecords(query7, headers7, columns7);

    } else if (choice == 'H') {
        System.out.println("Exiting program. Goodbye!");
    } else {
        System.out.println("Invalid choice. Please try again.");
    }
}




    public void updateApplications() {
        config conf = new config();
        String applicationID;
        
        String query = "SELECT * FROM Applications";
            String[] headers = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            String[] columns = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            conf.viewRecords(query, headers, columns);

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
    String query1 = "SELECT * FROM Applications";
            String[] headers1 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            String[] columns1 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            conf.viewRecords(query1, headers1, columns1);
    }
    
    

    public void deleteApplications() {
        config conf = new config();
        String applicationID;
        String query1 = "SELECT * FROM Applications";
            String[] headers1 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            String[] columns1 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            conf.viewRecords(query1, headers1, columns1);

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
        String query11 = "SELECT * FROM Applications";
            String[] headers11 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            String[] columns11 = {"ApplicationID", "ApplicantID", "JobID", "ApplicationDate", "Status"};
            conf.viewRecords(query11, headers11, columns11);
    }
}
